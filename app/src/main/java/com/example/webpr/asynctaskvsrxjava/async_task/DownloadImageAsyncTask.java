package com.example.webpr.asynctaskvsrxjava.async_task;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.webpr.asynctaskvsrxjava.R;
import com.example.webpr.asynctaskvsrxjava.downloader.BitmapDownloader;

import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * Created by webpr on 29.12.2016.
 */

public class DownloadImageAsyncTask extends AsyncTask<String, Void, Result<Bitmap>>{

    private WeakReference<ImageView> mImageViewWeakReference;
    private WeakReference<Context> mContextWeakReference;

    public DownloadImageAsyncTask(ImageView imageView, Context context) {
        this.mImageViewWeakReference = new WeakReference<ImageView>(imageView);
        this.mContextWeakReference = new WeakReference<Context>(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Result<Bitmap> doInBackground(String... params) {
        Result<Bitmap> bitmapResult = new Result<>();

        try{
            bitmapResult.setResult(BitmapDownloader.getBitmapFromURL(params[0]));
        } catch (Throwable t){
            bitmapResult.setError(t);
        }
        return bitmapResult;
    }

    @Override
    protected void onPostExecute(Result<Bitmap> bitmapResult) {
        super.onPostExecute(bitmapResult);
        if(null != bitmapResult.getError()){
            Context context = mContextWeakReference.get();
            Toast.makeText(context, context.getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
            return;
        }

        mImageViewWeakReference.get().setImageBitmap(bitmapResult.getResult());
    }
}
