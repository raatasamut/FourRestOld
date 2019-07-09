package com.arsoft.api.util;


import android.os.Handler;
import android.os.Looper;
import com.arsoft.api.INetworkWithProgressCallback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ProgressRequestBody extends RequestBody {
    private static final int DEFAULT_BUFFER_SIZE = 2048;
    private File mFile;
    private String mPath;
    private INetworkWithProgressCallback mListener;
    private MediaType content_type;

    public ProgressRequestBody(MediaType contentType, final File file, final INetworkWithProgressCallback listener) {
        this.content_type = contentType;
        mFile = file;
        mListener = listener;
    }

    @Override
    public MediaType contentType() {
        return content_type;
    }

    @Override
    public long contentLength() throws IOException {
        return mFile.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long fileLength = mFile.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(mFile);
        long uploaded = 0;

        try {
            int read;
            Handler handler = new Handler(Looper.getMainLooper());
            while ((read = in.read(buffer)) != -1) {

                // update progress on UI thread
                handler.post(new ProgressUpdater(uploaded, fileLength));

                uploaded += read;
                sink.write(buffer, 0, read);
            }
        } finally {
            in.close();
        }
    }

    private class ProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;

        public ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }

        @Override
        public void run() {
            mListener.onProgress(mFile.getName(), (int) (100 * mUploaded / mTotal));
        }
    }
}