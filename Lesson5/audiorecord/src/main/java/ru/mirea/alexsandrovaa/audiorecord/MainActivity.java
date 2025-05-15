package ru.mirea.alexsandrovaa.audiorecord;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.mirea.alexsandrovaa.audiorecord.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String fileName;
    private boolean isRecording = false;
    private boolean isPlaying = false;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Проверка и запрос разрешений
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_RECORD_AUDIO_PERMISSION);
        }

        // Генерация имени файла
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        fileName = getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath()
                + "/" + timeStamp + "_audio_record.mp3";

        Log.d("AUDIO", "Файл будет сохранён: " + fileName);

        // Обработчики кнопок
        binding.recordButton.setOnClickListener(v -> {
            if (!isRecording) startRecording();
        });

        binding.stopButton.setOnClickListener(v -> {
            if (isRecording) stopRecording();
            else if (isPlaying) stopPlaying();
        });

        binding.playButton.setOnClickListener(v -> {
            if (!isPlaying) startPlaying();
        });
    }

    private void startRecording() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(fileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;

            updateUI("Идет запись...", false, true, false);

        } catch (IOException e) {
            Toast.makeText(this, "Ошибка при подготовке записи", Toast.LENGTH_SHORT).show();
            Log.e("AUDIO", "Ошибка startRecording", e);
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;

            updateUI("Запись завершена", true, false, true);
            Log.d("AUDIO", "Файл записан: " + fileName);
        }
    }

    private void startPlaying() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(fileName);
            mediaPlayer.prepare();
            mediaPlayer.start();
            isPlaying = true;

            updateUI("Воспроизведение...", false, true, false);

            mediaPlayer.setOnCompletionListener(mp -> stopPlaying());

        } catch (IOException e) {
            Toast.makeText(this, "Ошибка при воспроизведении", Toast.LENGTH_SHORT).show();
            Log.e("AUDIO", "Ошибка startPlaying", e);
        }
    }

    private void stopPlaying() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            isPlaying = false;

            updateUI("Готов к записи", true, false, true);
        }
    }

    private void updateUI(String status, boolean recordEnabled, boolean stopEnabled, boolean playEnabled) {
        binding.statusText.setText(status);
        binding.recordButton.setEnabled(recordEnabled);
        binding.stopButton.setEnabled(stopEnabled);
        binding.playButton.setEnabled(playEnabled);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Разрешения необходимы", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}