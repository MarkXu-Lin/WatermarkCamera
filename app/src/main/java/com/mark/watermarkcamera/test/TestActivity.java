package com.mark.watermarkcamera.test;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

public class TestActivity extends Activity implements LifecycleOwner {


    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }
}

