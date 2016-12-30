package com.example.webpr.asynctaskvsrxjava.rx.observable;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.webpr.asynctaskvsrxjava.downloader.BitmapDownloader;

import java.lang.ref.WeakReference;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by webpr on 29.12.2016.
 */

public class RxImageDownloader {

    private WeakReference<ImageView> mImageViewWeakReference;

    public RxImageDownloader(ImageView imageView) {
        this.mImageViewWeakReference = new WeakReference<ImageView>(imageView);
    }

    public Subscription setImageFromNetwork(final String imageUrl){
      return Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                  subscriber.onNext(BitmapDownloader.getBitmapFromURL(imageUrl));
//                try{
//                    Bitmap bitmap = BitmapDownloader.getBitmapFromURL(imageUrl);
//                    subscriber.onNext(bitmap);
//                    subscriber.onCompleted();
//                } catch (Throwable t){
//                    subscriber.onError(t);
//                }
            } }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                mImageViewWeakReference.get().setImageBitmap(bitmap);
            }
        });
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Bitmap>() {
//            @Override
//            public void onCompleted() {}
//
//            @Override
//            public void onError(Throwable e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onNext(Bitmap bitmap) {
//                mImageViewWeakReference.get().setImageBitmap(bitmap);
//            }
//        });
    }
}
