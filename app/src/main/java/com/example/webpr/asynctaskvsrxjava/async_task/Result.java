package com.example.webpr.asynctaskvsrxjava.async_task;

/**
 * Created by webpr on 29.12.2016.
 */

public class Result<T> {

    private T mResult;
    private Throwable mError;

    public T getResult() {
        return mResult;
    }

    public void setResult(T mResult) {
        this.mResult = mResult;
    }

    public Throwable getError() {
        return mError;
    }

    public void setError(Throwable mError) {
        this.mError = mError;
    }
}
