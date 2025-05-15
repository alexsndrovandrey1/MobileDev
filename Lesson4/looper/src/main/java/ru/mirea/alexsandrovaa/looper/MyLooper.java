package ru.mirea.alexsandrovaa.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MyLooper extends Thread {
    public Handler mHandler;
    private Handler mainHandler;

    public MyLooper(Handler mainThreadHandler) {
        this.mainHandler = mainThreadHandler;
    }

    @Override
    public void run() {
        Looper.prepare();
        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String ageStr = msg.getData().getString("AGE");
                String job = msg.getData().getString("JOB");

                int age = Integer.parseInt(ageStr);
                try {
                    Thread.sleep(age * 1000L); // Задержка в секундах
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String result = "Профессия: " + job + ", возраст: " + age;
                Log.d("MyLooper", "Обработка завершена: " + result);

                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("result", result);
                message.setData(bundle);
                mainHandler.sendMessage(message);
            }
        };
        Looper.loop();
    }
}
