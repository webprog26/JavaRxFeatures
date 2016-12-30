package com.example.webpr.asynctaskvsrxjava.map;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.webpr.asynctaskvsrxjava.R;
import com.example.webpr.asynctaskvsrxjava.downloader.BitmapDownloader;

import java.util.concurrent.Callable;

import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class MapActivity extends AppCompatActivity {

    private static final String IMAGE_URL = "https://s-media-cache-ak0.pinimg.com/originals/ec/7b/9b/ec7b9b446dd134f6cfd39a600ed74027.jpg";

    private static final String TAG = "MapActivity_TAG";

    private ImageView mImageView;
    private Subscription mImageDownloadSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);



        Button btnDownload = (Button) findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageDownloadSubscription = bitmapSingle
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleSubscriber<Bitmap>() {
                            @Override
                            public void onSuccess(Bitmap value) {
                                mImageView = (ImageView) findViewById(R.id.imageView);
                                mImageView.setImageBitmap(value);
                            }

                            @Override
                            public void onError(Throwable error) {
                                error.printStackTrace();
                            }
                        });
            }
        });
    }

    Single<Bitmap> bitmapSingle = Single.fromCallable(new Callable<Bitmap>() {
        @Override
        public Bitmap call() throws Exception {
            return BitmapDownloader.getBitmapFromURL(IMAGE_URL);
        }
    });
}
