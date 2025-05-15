package ru.mirea.alexsandrovaa.cryptoloader;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

public class MyLoader extends AsyncTaskLoader<String> {

    private final String encrypted;
    private final String key;

    public MyLoader(Context context, String encrypted, String key) {
        super(context);
        this.encrypted = encrypted;
        this.key = key;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        return AESEncryption.decrypt(encrypted, key);
    }
}
