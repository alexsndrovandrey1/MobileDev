package ru.mirea.alexsandrovaa.control_task_pr3.ui.filework;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import ru.mirea.alexsandrovaa.control_task_pr3.R;
import ru.mirea.alexsandrovaa.control_task_pr3.databinding.FragmentFileWorkBinding;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWorkFragment extends Fragment {

    private FragmentFileWorkBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFileWorkBinding.inflate(inflater, container, false);

        binding.buttonLoad.setOnClickListener(v -> loadFileContent());
        binding.fabAddFile.setOnClickListener(v -> openCreateFileDialog());

        return binding.getRoot();
    }

    private void loadFileContent() {
        String fileName = binding.editTextFileName.getText().toString();
        if (fileName.isEmpty()) {
            Toast.makeText(getActivity(), "Введите название файла", Toast.LENGTH_SHORT).show();
            return;
        }

        try (FileInputStream fis = getActivity().openFileInput(fileName)) {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            String content = new String(buffer);
            binding.textViewFileContent.setText(content);
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Ошибка при загрузке файла", Toast.LENGTH_SHORT).show();
        }
    }

    private void openCreateFileDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_create_file, null);

        EditText editTextFileName = dialogView.findViewById(R.id.editTextFileName);
        EditText editTextFileContent = dialogView.findViewById(R.id.editTextFileContent);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView)
                .setTitle("Создать новый файл")
                .setPositiveButton("Сохранить", (dialog, which) -> {
                    String fileName = editTextFileName.getText().toString();
                    String fileContent = editTextFileContent.getText().toString();
                    saveFile(fileName, fileContent);
                })
                .setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void saveFile(String fileName, String content) {
        if (fileName.isEmpty() || content.isEmpty()) {
            Toast.makeText(getActivity(), "Введите название файла и содержимое", Toast.LENGTH_SHORT).show();
            return;
        }

        try (FileOutputStream fos = getActivity().openFileOutput(fileName, getActivity().MODE_PRIVATE)) {
            fos.write(content.getBytes());
            Toast.makeText(getActivity(), "Файл сохранен", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Ошибка при сохранении файла", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Очистка binding
    }
}