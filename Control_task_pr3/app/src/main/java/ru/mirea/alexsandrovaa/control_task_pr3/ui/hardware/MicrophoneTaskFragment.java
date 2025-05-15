package ru.mirea.alexsandrovaa.control_task_pr3.ui.hardware;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.io.FileOutputStream;
import java.io.IOException;

import ru.mirea.alexsandrovaa.control_task_pr3.databinding.FragmentMicrophoneTaskBinding;

public class MicrophoneTaskFragment extends Fragment {
    private static final int SAMPLE_RATE_IN_HZ = 44100;
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private static final String FILE_NAME = "/mic_task.pcm";

    private AudioRecord audioRecord;
    private boolean isRecording = false;
    private Thread recordingThread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentMicrophoneTaskBinding binding = FragmentMicrophoneTaskBinding.inflate(inflater, container, false);

        String filePath = requireContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC) + FILE_NAME;

        binding.recordButton.setOnClickListener(v -> {
            if (!isRecording) {
                startRecording(filePath);
                binding.statusText.setText("Запись...");
            }
        });

        binding.stopButton.setOnClickListener(v -> {
            if (isRecording) {
                stopRecording();
                binding.statusText.setText("Запись завершена");
            }
        });

        return binding.getRoot();
    }

    private void startRecording(String filePath) {
        int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ, CHANNEL_CONFIG, AUDIO_FORMAT);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE_IN_HZ, CHANNEL_CONFIG, AUDIO_FORMAT, bufferSize);

        audioRecord.startRecording();
        isRecording = true;

        recordingThread = new Thread(() -> {
            byte[] data = new byte[bufferSize];
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                while (isRecording) {
                    int read = audioRecord.read(data, 0, data.length);
                    if (read != AudioRecord.ERROR_INVALID_OPERATION && read != AudioRecord.ERROR_BAD_VALUE) {
                        fos.write(data, 0, read);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        recordingThread.start();
    }

    private void stopRecording() {
        if (audioRecord != null) {
            isRecording = false;
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
            if (recordingThread != null) {
                try {
                    recordingThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                recordingThread = null;
            }
        }
    }
}