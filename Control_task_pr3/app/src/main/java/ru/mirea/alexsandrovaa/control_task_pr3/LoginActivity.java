package ru.mirea.alexsandrovaa.control_task_pr3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import ru.mirea.alexsandrovaa.control_task_pr3.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "FirebaseAuth";
    private FirebaseAuth mAuth;

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // убедись, что XML-файл называется именно так

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            signIn(email, password);
        });

        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            createAccount(email, password);
        });
    }

    private boolean validateForm(String email, String password) {
        if (email.isEmpty()) {
            Toast.makeText(this, "Введите email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Пароль должен быть минимум 6 символов", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void createAccount(String email, String password) {
        if (!validateForm(email, password)) return;

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                        goToMainScreen();
                    } else {
                        Toast.makeText(this, "Ошибка регистрации: " +
                                task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        Log.e(TAG, "Registration error", task.getException());
                    }
                });
    }

    private void signIn(String email, String password) {
        if (!validateForm(email, password)) return;

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(this, "Вход выполнен", Toast.LENGTH_SHORT).show();
                        goToMainScreen();
                    } else {
                        Toast.makeText(this, "Ошибка входа: " +
                                task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        Log.e(TAG, "Login error", task.getException());
                    }
                });
    }

    private void goToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class); // замени на нужный экран
        startActivity(intent);
        finish();
    }
}