package ru.mirea.alexsandrovaa.thread;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.alexsandrovaa.thread.databinding.ActivityMainBinding;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding; // ViewBinding
    private int counter = 0; // Счётчик потоков

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Информация о главном потоке
        Thread mainThread = Thread.currentThread();
        binding.textViewResult.setText("Имя текущего потока: " + mainThread.getName());
        mainThread.setName("БИСО-02-20, НОМЕР: 1, ФИЛЬМ: Интерстеллар");
        binding.textViewResult.append("\nНовое имя потока: " + mainThread.getName());
        Log.d(MainActivity.class.getSimpleName(), "Stack: " + Arrays.toString(mainThread.getStackTrace()));
        Log.d(MainActivity.class.getSimpleName(), "Group: " + mainThread.getThreadGroup());

        // Обработка нажатия на кнопку
        binding.buttonMirea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Запускаем новый поток
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int numberThread = counter++;
                        Log.d("ThreadProject", String.format(
                                "Запущен поток № %d студентом группы БИСО-02-20, номер по списку 1", numberThread));
                        // Замедление: 20 секунд
                        long endTime = System.currentTimeMillis() + 20 * 1000;
                        while (System.currentTimeMillis() < endTime) {
                            synchronized (this) {
                                try {
                                    wait(endTime - System.currentTimeMillis());
                                    Log.d(MainActivity.class.getSimpleName(), "Endtime: " + endTime);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }

                        Log.d("ThreadProject", "Выполнен поток № " + numberThread);
                    }
                }).start();
            }
        });
    }
}