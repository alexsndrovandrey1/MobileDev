package ru.mirea.alexsandrovaa.favoritebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    private EditText userBookEditText;
    private EditText userQuoteEditText;
    private TextView developerBookTextView;
    private TextView developerQuoteTextView;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Инициализация элементов UI
        developerBookTextView = findViewById(R.id.textViewDeveloperBook);
        developerQuoteTextView = findViewById(R.id.textViewDeveloperQuote);
        userBookEditText = findViewById(R.id.editTextBook);
        userQuoteEditText = findViewById(R.id.editTextQuote);
        sendButton = findViewById(R.id.button);

        // Получаем данные о книге и цитате разработчика из Intent
        Intent intent = getIntent();
        String devBook = intent.getStringExtra(MainActivity.BOOK_NAME_KEY);
        String devQuote = intent.getStringExtra(MainActivity.QUOTES_KEY);

        // Отображаем их в TextView
        developerBookTextView.setText("Любимая книга: " + devBook);
        developerQuoteTextView.setText("Цитата: " + devQuote);

        // Добавляем слушатель для поля книги пользователя
        userBookEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Не нужно делать ничего до изменения текста
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Обновляем TextView в реальном времени
                developerBookTextView.setText("Любимая книга: " + charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Здесь также не нужно делать ничего
            }
        });

        // Добавляем слушатель для поля цитаты пользователя
        userQuoteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Не нужно делать ничего до изменения текста
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Обновляем TextView в реальном времени
                developerQuoteTextView.setText("Цитата: " + charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Здесь также не нужно делать ничего
            }
        });

        // Обработчик кнопки для отправки данных
        sendButton.setOnClickListener(view -> {
            String userBook = userBookEditText.getText().toString();
            String userQuote = userQuoteEditText.getText().toString();

            Intent resultIntent = new Intent();
            resultIntent.putExtra(MainActivity.USER_MESSAGE, "Название любимой книги: " + userBook + ". Цитата: " + userQuote);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }

    // Метод вызывается при нажатии кнопки "Отправить данные"
    public void sendDataToMain(View view) {
        String userBook = userBookEditText.getText().toString();
        String userQuote = userQuoteEditText.getText().toString();
        String resultMessage = "Название любимой книги: " + userBook + ". Цитата: " + userQuote;

        // Создаём интент для возврата данных
        Intent data = new Intent();
        data.putExtra(MainActivity.USER_MESSAGE, resultMessage);
        setResult(RESULT_OK, data);
        finish();
    }
}