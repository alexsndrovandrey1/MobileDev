package ru.mirea.alexsandrovaa.control_task_pr4;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {

    public static final String TAG = "MyWorker";

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Фоновая задача началась");
        try {
            Thread.sleep(5000); // Симуляция работы
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.failure();
        }
        Log.d(TAG, "Фоновая задача завершена");
        return Result.success();
    }
}