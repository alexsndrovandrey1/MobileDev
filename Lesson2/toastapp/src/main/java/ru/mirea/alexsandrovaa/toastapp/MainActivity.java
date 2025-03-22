package ru.mirea.alexsandrovaa.toastapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void showToast(View view) {
        EditText editText = findViewById(R.id.editText);
        String text = editText.getText().toString();
        int length = text.length();

        String toastMessage = "Студент № 1 Группа БИСО-02-20 Количество символов - " + length;
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }
}