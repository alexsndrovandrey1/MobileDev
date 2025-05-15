package ru.mirea.alexsandrovaa.cryptoloader;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import ru.mirea.alexsandrovaa.cryptoloader.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private ActivityMainBinding binding;
    private String encryptedText;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.encryptButton.setOnClickListener(v -> {
            String input = binding.inputEditText.getText().toString();
            if (!input.isEmpty()) {
                key = AESEncryption.generateKey();
                encryptedText = AESEncryption.encrypt(input, key);

                // Отображаем зашифрованный текст в TextView
                binding.encryptedTextView.setText(encryptedText);
                binding.encryptedTextView.setVisibility(android.view.View.VISIBLE);

                // Запускаем загрузчик для дешифровки
                LoaderManager.getInstance(this).restartLoader(1, null, this);
            } else {
                Toast.makeText(this, "Введите текст", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyLoader(this, encryptedText, key);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Toast.makeText(this, "Дешифрованный текст: " + data, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {}
}