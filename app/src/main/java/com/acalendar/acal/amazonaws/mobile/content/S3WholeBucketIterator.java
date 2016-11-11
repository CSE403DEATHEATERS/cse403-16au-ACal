//
// Copyright 2016 Amazon.com, Inc. or its affiliates (Amazon). All Rights Reserved.
//
// Code generated by AWS Mobile Hub. Amazon gives unlimited permission to 
// copy, distribute and modify it.
//
// Source code generated from template: aws-my-sample-app-android v0.10
//
package com.acalendar.acal.amazonaws.mobile.content;

import android.os.ConditionVariable;
import android.util.Log;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.acalendar.acal.amazonaws.mobile.util.S3Utils;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * An iterator that provides all object summaries from an S3 bucket. It handles streaming
 * objects into a queue asynchronously in a background thread to ensure lowest possible
 * latency to retrieve the next object, while also limiting the queue from growing too large
 * due to back pressure.  Creating the iterator also accepts an error handler to
 * receive any exception that may occur while attempting to list objects.
 **/
public class S3WholeBucketIterator implements Iterator<S3ContentSummary>, Iterable<S3ContentSummary>, Runnable {
    /** Logging tag for this class. */
    private static final String LOG_TAG = S3WholeBucketIterator.class.getSimpleName();

    /* The S3 client. */
    private final AmazonS3 s3Client;
    /** The S3 bucket to list. */
    private final String bucketName;
    /** The thread for listing objects. */
    private final Thread listingThread;

    /** flag indicating we are in the progress of listing objects. */
    private volatile boolean areListingObjects;
    /** Condition variable to use when waiting for objects. */
    private ConditionVariable waitingForObjects;
    /** Condition variable to use when Queue full. */
    private ConditionVariable waitingForReader;
    /** Thread safe queue of object summaries. */
    private final ConcurrentLinkedQueue<S3ContentSummary> summaries;
    /** the ConcurrentLinkedQueue doesn't keep a member tracking the count, so this does. */
    private AtomicInteger summaryCount;
    /** Error Handler to call on un-recoverable, un-expected errors while listing. */
    private final S3ListErrorHandler errorHandler;

    private final String s3ContentPrefix;
    /** S3 Object name prefix. */
    private final String prefix;
    /** S3 Object name Delimiter. */
    private final String delimiter;
    /** Whether include directories (common prefixes). */
    private final boolean includeDirectories;
    /** Once the queue has filled beyond this threshold, further retrieval is suspended. */
    private static final int QUEUED_ITEMS_FULL_THRESHOLD_VALUE = 1200;
    /** If retrieving items is suspended, once the number of items in the queue, drop below
     * this value, retrieving items in the background to fill the queue is resumed. */
    private static final int QUEUED_ITEMS_HYSTERISIS_VALUE = 900;
    /** Max keys to retrieve per service call to list objects. */
    private static final int MAX_KEYS_PER_REQUEST = 300;

    public interface S3ListErrorHandler {
        void onError(AmazonClientException ex);
    }

    /**
     * Constructs this iterator.
     * @param s3Client the S3 client.
     * @param bucketName the S3 bucket name.
     * @param s3ContentPrefix the portion of the s3 object prefix that should be omitted from the relative path
     *                        of the S3ContentSummary objects this iterator returns.
     * @param prefix the s3 object prefix; may be null.
     * @param delimiter the s3 object delimiter; may be null.
     * @param includeDirectories whether to include directories (common prefixes)
     * @param errorHandler an error handler.
     */
    public S3WholeBucketIterator(final AmazonS3 s3Client, final String bucketName, final String s3ContentPrefix,
                                 final String prefix, final String delimiter, final boolean includeDirectories,
                                 final S3ListErrorHandler errorHandler) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.errorHandler = errorHandler;
        this.s3ContentPrefix = s3ContentPrefix;
        this.prefix = prefix;
        this.delimiter = delimiter;
        this.includeDirectories = includeDirectories;
        summaries = new ConcurrentLinkedQueue<>();
        summaryCount = new AtomicInteger();
        summaryCount.set(0);
        waitingForObjects = new ConditionVariable();
        waitingForReader = new ConditionVariable(true);
        areListingObjects = true;

        // Start the background thread to begin listing objects.
        listingThread = new Thread(this);
        listingThread.start();
    }

    private boolean isThrowableDueToInterrupt(final Throwable throwable) {

        if (throwable == null) {
            return false;
        }

        Throwable th = throwable.getCause();

        while (th != null) {
            if (th instanceof InterruptedException) {
                return true;
            }

            th = th.getCause();
        }

        return false;
    }

    private String getRelativeS3Path(final String s3Key) {
        if (s3ContentPrefix == null) {
            return s3Key;
        }
        return s3Key.substring(s3ContentPrefix.length());
    }

    /** The worker method to list objects and add them to the queue. */
    public void run() {
        ObjectListing objListing;
        ListObjectsRequest request = new ListObjectsRequest()
            .withBucketName(bucketName)
            .withPrefix(prefix)
            .withDelimiter(delimiter)
            .withMaxKeys(MAX_KEYS_PER_REQUEST);
        String nextMarker = null;

        do {
            request.setMarker(nextMarker);
            final List<S3ObjectSummary> resultSummaries;
            try {
                objListing = s3Client.listObjects(request);
            } catch (final AmazonClientException ex) {
                Log.e(LOG_TAG, ex.getMessage());
                if (isThrowableDueToInterrupt(ex)) {
                    break;
                }
                if (errorHandler != null) {
                    errorHandler.onError(ex);
                }
                break;
            }
            resultSummaries = objListing.getObjectSummaries();
            if (summaryCount.addAndGet(resultSummaries.size()) >= QUEUED_ITEMS_FULL_THRESHOLD_VALUE) {
                waitingForReader.close();
            }
            for (final S3ObjectSummary objectSummary : objListing.getObjectSummaries()) {
                final String relativeS3Path = getRelativeS3Path(objectSummary.getKey());
                if (!relativeS3Path.isEmpty()) {
                    if (S3Utils.isDirectory(relativeS3Path)) {
                        if (includeDirectories) {
                            summaries.add(new S3ContentSummary(relativeS3Path));
                        } // else intentionally skip this item.
                    } else {
                        summaries.add(new S3ContentSummary(objectSummary, relativeS3Path));
                    }
                }
            }

            if (includeDirectories && !objListing.getCommonPrefixes().isEmpty()) {
                for (final String commonPrefix : objListing.getCommonPrefixes()) {
                    final String relativeS3Path = getRelativeS3Path(commonPrefix);
                    if (!relativeS3Path.isEmpty()) {
                        summaries.add(new S3ContentSummary(relativeS3Path));
                    }
                }
            }
            // Open the condition to waken hasNext() or next() if they are blocked.
            waitingForObjects.open();
            if (summaryCount.get() >= QUEUED_ITEMS_FULL_THRESHOLD_VALUE) {
                waitingForReader.block();
            }
        } while (((nextMarker = objListing.getNextMarker()) != null) && areListingObjects);

        // Open the condition to waken hasNext() or next() if they are blocked.
        waitingForObjects.open();
        areListingObjects = false;
    }

    /**
     * @return true if another S3ObjectSummary is available from the iterator.
     */
    @Override
    public boolean hasNext() {
        waitingForObjects.close();
        if (summaries.isEmpty() && !areListingObjects) {
            return false;
        } else if (!summaries.isEmpty()) {
            return true;
        }
        waitingForObjects.block();
        return !summaries.isEmpty();
    }

    /**
     * Gets the next object summary.  Blocks if there are no summaries available but more
     * still exist and we are pending network response to retrieve the next item.
     *
     * @return the next object summary, or null if all summaries have been exhausted.
     */
    @Override
    public S3ContentSummary next() {
        if (hasNext()) {
            return getNext();
        }
        return null;
    }

    /**
     * Retrieves the next item and unblocks the writer if necessary.
     * @return the next object summary.
     */
    private S3ContentSummary getNext() {
        final S3ContentSummary next = summaries.poll();
        if (summaryCount.decrementAndGet() == QUEUED_ITEMS_HYSTERISIS_VALUE) {
            waitingForReader.open();
        }
        return next;
    }

    /**
     * Call this while iterating to see if the subsequent call to next() will block.  This can
     * also be called in an enhanced for loop.
     * @return true if calling next() will block, otherwise return false.
     */
    public boolean willNextBlock() {
        return (summaries.isEmpty() && areListingObjects);
    }

    /**
     * Call this to ensure the background thread is stopped. This only needs to be called in
     * the case that iterator was not exhausted (meaning next() was not called on the iterator
     * until null was returned). After calling this method it is still possible to iterate the
     * remaining items that had been read in the background using next().
     */
    public void cancel() {
        if (!areListingObjects) {
            return;
        }
        areListingObjects = false;
        waitingForReader.open();
        listingThread.interrupt();
    }

    /** The remove operation is unsupported and therefore throws UnsupportedOperationException. */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove not supported.");
    }

    /** An instance of this class may be used directly in an enhanced for loop. */
    @Override
    public Iterator<S3ContentSummary> iterator() {
        return this;
    }
}
