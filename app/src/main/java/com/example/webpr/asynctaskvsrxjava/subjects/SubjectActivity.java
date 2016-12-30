package com.example.webpr.asynctaskvsrxjava.subjects;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.webpr.asynctaskvsrxjava.R;
import com.example.webpr.asynctaskvsrxjava.downloader.BitmapDownloader;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class SubjectActivity extends AppCompatActivity {

    private static final String TAG = "SubjectActivity_TAG";

    private static final String IMAGE_URL = "https://s-media-cache-ak0.pinimg.com/originals/ec/7b/9b/ec7b9b446dd134f6cfd39a600ed74027.jpg";


    private PublishSubject<String> bitmapPublishSubject = PublishSubject.create();
    private Subscription mBitmapPublishSubjectSubscription;

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        mBitmapPublishSubjectSubscription = bitmapPublishSubject
                .observeOn(Schedulers.io())
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        return BitmapDownloader.getBitmapFromURL(IMAGE_URL);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        if(null != bitmap){
                            Toast.makeText(SubjectActivity.this, "Image loaded", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        mImageView.setImageBitmap(bitmap);
                    }
                });

        mImageView = (ImageView) findViewById(R.id.imageView);
        Button btnDownload = (Button) findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageView.setImageBitmap(null);
                bitmapPublishSubject.onNext(IMAGE_URL);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mBitmapPublishSubjectSubscription != null && !mBitmapPublishSubjectSubscription.isUnsubscribed() && isFinishing()) {
            mBitmapPublishSubjectSubscription.unsubscribe();
            bitmapPublishSubject.onCompleted();
        }
    }
}