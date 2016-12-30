package com.example.webpr.asynctaskvsrxjava.rx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.webpr.asynctaskvsrxjava.R;
import com.example.webpr.asynctaskvsrxjava.rx.observable.RxImageDownloader;

import rx.Subscription;


public class RxActivity extends AppCompatActivity {

    private static final String IMAGE_URL = "https://s-media-cache-ak0.pinimg.com/originals/ec/7b/9b/ec7b9b446dd134f6cfd39a600ed74027.jpg";
    private ImageView mImageView;
    private Subscription mImageDownloadSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);

        mImageView = (ImageView) findViewById(R.id.imageView);
        Button btnDownload = (Button) findViewById(R.id.btnDownload);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxImageDownloader rxImageDownloader = new RxImageDownloader(mImageView);
                mImageDownloadSubscription = rxImageDownloader.setImageFromNetwork(IMAGE_URL);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mImageDownloadSubscription != null && !mImageDownloadSubscription.isUnsubscribed() && isFinishing()){
            mImageDownloadSubscription.unsubscribe();
        }
    }
}
