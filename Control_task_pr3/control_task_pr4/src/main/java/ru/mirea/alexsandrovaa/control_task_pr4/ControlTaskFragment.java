package ru.mirea.alexsandrovaa.control_task_pr4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import ru.mirea.alexsandrovaa.control_task_pr4.databinding.FragmentControlTaskBinding;

public class ControlTaskFragment extends Fragment {

    private FragmentControlTaskBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentControlTaskBinding.inflate(inflater, container, false);

        binding.buttonStartWork.setOnClickListener(view -> {
            WorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class).build();
            WorkManager.getInstance(requireContext()).enqueue(workRequest);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}