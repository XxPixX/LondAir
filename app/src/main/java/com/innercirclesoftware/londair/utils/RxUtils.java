package com.innercirclesoftware.londair.utils;

import android.support.annotation.Nullable;

import io.reactivex.disposables.Disposable;

public class RxUtils {
    public static void dispose(Disposable... disposables) {
        for (Disposable d : disposables) {
            if (d != null && !d.isDisposed()) d.dispose();
        }
    }

    public static boolean isRunning(@Nullable Disposable disposable) {
        return disposable != null && !disposable.isDisposed();
    }
}
