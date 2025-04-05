package ru.mirea.alexsandrovaa.intentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Получаем intent, который запустит эту активность
        Intent intent = getIntent();
        String message = intent.getStringExtra("message");

        //Находим TextView и устанавливаем текст
        TextView textViewResult = findViewById(R.id.textViewResult);
        textViewResult.setText(message);
    };
}