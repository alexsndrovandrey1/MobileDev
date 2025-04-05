package ru.mirea.alexsandrovaa.sharer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> imageActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация ActivityResultLauncher
        imageActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Intent data = result.getData();
                            Log.d(MainActivity.class.getSimpleName(), "Data: " + data.getDataString());
                        }
                    }
                }
        );

        // Запуск выбора файла
        pickFile();
    }

    private void pickFile() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("*/*");
        imageActivityResultLauncher.launch(intent);
    }
}
