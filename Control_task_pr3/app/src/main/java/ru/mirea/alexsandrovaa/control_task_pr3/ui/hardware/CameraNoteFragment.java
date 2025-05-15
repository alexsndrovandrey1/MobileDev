package ru.mirea.alexsandrovaa.control_task_pr3.ui.hardware;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.mirea.alexsandrovaa.control_task_pr3.databinding.FragmentCameraNoteBinding;

public class CameraNoteFragment extends Fragment {

    private static final int REQUEST_CODE_PERMISSION = 100;
    private FragmentCameraNoteBinding binding;
    private Uri imageUri;
    private ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCameraNoteBinding.inflate(inflater, container, false);

        // Проверка разрешений
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, REQUEST_CODE_PERMISSION);
        }

        // Регистрация launcher'а
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK && imageUri != null) {
                        binding.imageView.setImageURI(imageUri);
                    } else {
                        Toast.makeText(getContext(), "Не удалось сделать фото", Toast.LENGTH_SHORT).show();
                    }
                });

        binding.captureButton.setOnClickListener(v -> {
            try {
                File photoFile = createImageFile();
                imageUri = FileProvider.getUriForFile(
                        requireContext(),
                        requireContext().getPackageName() + ".fileprovider",
                        photoFile);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cameraLauncher.launch(intent);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Ошибка создания файла", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }
}