package ru.mirea.alexsandrovaa.favoritebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> activityResultLauncher;
    private TextView textViewUserBook;

    static final String BOOK_NAME_KEY = "book_name";
    static final String QUOTES_KEY = "quotes_name";
    static final String USER_MESSAGE = "MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewUserBook = findViewById(R.id.textViewBook);

        // Ловим результат со второй активности
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            String userBook = result.getData().getStringExtra(USER_MESSAGE);
                            textViewUserBook.setText(userBook);
                        }
                    }
                }
        );
    }

    // Метод вызывается при нажатии на кнопку
    public void getInfoAboutBook(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra(BOOK_NAME_KEY, "");
        intent.putExtra(QUOTES_KEY, "");
        activityResultLauncher.launch(intent);
    }
}