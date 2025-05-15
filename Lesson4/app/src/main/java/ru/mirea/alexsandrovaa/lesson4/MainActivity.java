package ru.mirea.alexsandrovaa.lesson4;

import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.alexsandrovaa.lesson4.databinding.ActivityMainBinding ;
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding; // создаём переменную для ViewBinding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Подключаем разметку через ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Устанавливаем текст
        binding.songTitle.setText("Imagine");
        binding.artistName.setText("John Lennon");
    }
}