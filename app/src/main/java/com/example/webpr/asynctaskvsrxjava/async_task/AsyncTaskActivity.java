package com.example.webpr.asynctaskvsrxjava.async_task;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.webpr.asynctaskvsrxjava.R;

public class AsyncTaskActivity extends AppCompatActivity {

    private static final String TAG = "AsyncTaskActivity_TAG";

    private static final String IMAGE_URL = "https://s-media-cache-ak0.pinimg.com/originals/ec/7b/9b/ec7b9b446dd134f6cfd39a600ed74027.jpg";
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        mImageView = (ImageView) findViewById(R.id.imageView);
        Button btnDownload = (Button) findViewById(R.id.btnDownload);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadImageAsyncTask(mImageView, AsyncTaskActivity.this).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, IMAGE_URL);
            }
        });
    }
}
