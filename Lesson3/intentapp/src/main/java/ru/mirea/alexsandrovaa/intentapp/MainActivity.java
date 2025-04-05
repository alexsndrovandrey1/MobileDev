package ru.mirea.alexsandrovaa.intentapp;

import static java.lang.Math.pow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonSend = findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Получение текущего времени
                long dateInMillis = System.currentTimeMillis();
                String format = "yyyy-MM-dd HH:mm:ss";
                final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
                String dateString = sdf.format(new Date(dateInMillis));

                // Номер в списке группы
                int myNumber = 1;
                int squaredMyNumber = myNumber*myNumber;

                //Форматируем строку
                String message = "Квадрат значения моего номера по списку в группе составляет число "+ squaredMyNumber + ", а текущее время " + dateString;

                //Создаем интент для переходя на MainActivity2
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("message", message); //Передаем строку второй активити
                startActivity(intent);
            }
        });
    }
}