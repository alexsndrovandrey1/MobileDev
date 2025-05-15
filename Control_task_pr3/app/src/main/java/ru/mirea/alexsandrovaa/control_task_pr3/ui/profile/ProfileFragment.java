package ru.mirea.alexsandrovaa.control_task_pr3.ui.profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import ru.mirea.alexsandrovaa.control_task_pr3.R;

public class ProfileFragment extends Fragment {

    private EditText nameEditText;
    private EditText emailEditText;
    private Button saveButton;
    private static final String FILENAME = "user_info.txt";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        nameEditText = rootView.findViewById(R.id.editTextName);
        emailEditText = rootView.findViewById(R.id.editTextEmail);
        saveButton = rootView.findViewById(R.id.saveButton);

        // Загружаем данные при старте
        loadProfileData();

        // Обработчик нажатия на кнопку сохранения
        saveButton.setOnClickListener(v -> saveProfileData());

        return rootView;
    }

    private void loadProfileData() {
        try {
            FileInputStream fis = getActivity().openFileInput(FILENAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String name = reader.readLine();
            String email = reader.readLine();
            fis.close();

            // Устанавливаем значения в EditText
            if (name != null && email != null) {
                nameEditText.setText(name);
                emailEditText.setText(email);
            }
        } catch (Exception e) {
            // Если файла нет, ничего не делаем
        }
    }

    private void saveProfileData() {
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();

        try {
            FileOutputStream fos = getActivity().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write((name + "\n" + email).getBytes());
            fos.close();
            Toast.makeText(getActivity(), "Данные сохранены!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Ошибка сохранения данных!", Toast.LENGTH_SHORT).show();
        }
    }
}