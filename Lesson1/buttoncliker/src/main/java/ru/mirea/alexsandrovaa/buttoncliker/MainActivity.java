package ru.mirea.alexsandrovaa.buttoncliker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView textViewStudent;
    private Button btnWhoAmI;
    private Button btnItIsNotMe;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewStudent = findViewById(R.id.textViewStudent);
        btnWhoAmI = findViewById(R.id.btnWhoAmI);
        btnItIsNotMe = findViewById(R.id.btnItIsNotMe);
        checkBox = findViewById(R.id.checkBox);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewStudent.setText("Мой номер из списка № 1");
                checkBox.setChecked(!checkBox.isChecked()); //Инвертируем состояние чекбокса
            }
        };
        btnWhoAmI.setOnClickListener(onClickListener);

        //Второй способ через XML
        btnItIsNotMe.setOnClickListener(this::onButtonClick);
    }
    public  void  onButtonClick(View v) {
        textViewStudent.setText("Это не я сделал!");
        checkBox.setChecked(!checkBox.isChecked()); // Инвертируем состояние чекбокса
    }
}