package ru.mirea.alexsandrovaa.systemintentsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Метод для открытия окна набора номера
    public void onClickCall(View view) {
        // Создаем Intent для действия набора номера
        Intent intent = new Intent(Intent.ACTION_DIAL);
        // Устанавливаем номер телефона, который будет отображен в приложении для набора
        intent.setData(Uri.parse("tel:89684006901"));
        // Запускаем активность
        startActivity(intent);
    }

    // Метод для открытия веб-страницы в браузере
    public void onClickOpenBrowser(View view) {
        // Создаем Intent для открытия веб-страницы
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // Устанавливаем URL-адрес веб-страницы
        intent.setData(Uri.parse("https://github.com/alexsndrovandrey1/Lesson3"));
        // Запускаем браузер с указанной ссылкой
        startActivity(intent);
    }

    // Метод для открытия карты с координатами
    public void onClickOpenMaps(View view) {
        // Создаем Intent для открытия карты с координатами
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // Устанавливаем координаты для отображения на карте
        intent.setData(Uri.parse("geo:55.876859,37.727032"));
        // Запускаем приложение карты с указанными координатами
        startActivity(intent);
    }
}