package ru.mirea.alexsandrovaa.looper;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.os.Handler;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;;

import ru.mirea.alexsandrovaa.looper.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MyLooper myLooper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Handler mainHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.d(MainActivity.class.getSimpleName(),
                        "Задача завершена: " + msg.getData().getString("result"));
            }
        };

        myLooper = new MyLooper(mainHandler);
        myLooper.start();

        binding.buttonMirea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myLooper.mHandler != null) {
                    String age = binding.editTextAge.getText().toString();
                    String job = binding.editTextJob.getText().toString();

                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putString("AGE", age);
                    bundle.putString("JOB", job);
                    msg.setData(bundle);
                    myLooper.mHandler.sendMessage(msg);
                }
            }
        });
    }
}