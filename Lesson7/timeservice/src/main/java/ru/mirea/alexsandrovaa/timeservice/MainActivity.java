package ru.mirea.alexsandrovaa.timeservice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import ru.mirea.alexsandrovaa.timeservice.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final String host = "time.nist.gov";
    private final int port = 13;
    private static final String TAG = "TimeService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetTimeTask timeTask = new GetTimeTask();
                timeTask.execute();
            }
        });
    }

    private class GetTimeTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String timeResult = "";
            try {
                Socket socket = new Socket(host, port);
                BufferedReader reader = SocketUtils.getReader(socket);
                reader.readLine(); // пропускаем первую строку
                String response = reader.readLine(); // вторая строка содержит дату и время
                Log.d(TAG, response);
                timeResult = response;
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                timeResult = "Ошибка подключения";
            }
            return timeResult;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Пример строки: "60515 24-05-09 11:21:23 00 0 0 477.1 UTC(NIST) *"
            // Нужно достать "24-05-09 11:21:23"
            String[] parts = result.split(" ");
            if (parts.length >= 3) {
                String dateTime = parts[1] + " " + parts[2];
                binding.textView.setText("Дата и время:\n" + dateTime);
            } else {
                binding.textView.setText("Ошибка чтения времени");
            }
        }
    }
}