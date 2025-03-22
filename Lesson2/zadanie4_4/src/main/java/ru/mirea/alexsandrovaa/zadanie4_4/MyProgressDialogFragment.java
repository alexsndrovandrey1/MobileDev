package ru.mirea.alexsandrovaa.zadanie4_4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class MyProgressDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Загрузка")
                .setMessage("Пожалуйста, подождите...")
                .setCancelable(false);

        AlertDialog dialog = builder.create();

        // Закрываем диалог через 3 секунды
        new Handler().postDelayed(dialog::dismiss, 3000);

        return dialog;
    }
}
