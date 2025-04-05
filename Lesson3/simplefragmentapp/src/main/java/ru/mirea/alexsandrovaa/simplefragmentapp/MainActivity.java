package ru.mirea.alexsandrovaa.simplefragmentapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private Button buttonFragment1, buttonFragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Проверяем ориентацию экрана
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Горизонтальная ориентация
            setContentView(R.layout.activity_main_land);

            // Если состояние не сохранено, заменяем фрагменты
            if (savedInstanceState == null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer1, new FirstFragment());
                transaction.replace(R.id.fragmentContainer2, new SecondFragment());
                transaction.commit();
            }
        } else {
            // Вертикальная ориентация
            setContentView(R.layout.activity_main);

            buttonFragment1 = findViewById(R.id.buttonFragment1);
            buttonFragment2 = findViewById(R.id.buttonFragment2);

            // Показываем первый фрагмент по умолчанию
            if (savedInstanceState == null) {
                replaceFragment(new FirstFragment());
            }

            buttonFragment1.setOnClickListener(v -> replaceFragment(new FirstFragment()));
            buttonFragment2.setOnClickListener(v -> replaceFragment(new SecondFragment()));
        }
    }

    // Метод для замены фрагмента в контейнере
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }
}