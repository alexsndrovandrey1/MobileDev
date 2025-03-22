package ru.mirea.alexsandrovaa.multiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.alexsandrovaa.multiactivity.SecondActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickNewActivity(View view) {
        EditText editText = findViewById(R.id.editText);
        String text = editText.getText().toString();

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("key", text);
        startActivity(intent);
    }
}