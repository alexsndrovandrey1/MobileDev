package ru.mirea.alexsandrovaa.zadanie4_4;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickShowTimeDialog(View view) {
        MyTimeDialogFragment timeDialog = new MyTimeDialogFragment();
        timeDialog.show(getSupportFragmentManager(), "timePicker");
    }
    public void onClickShowDateDialog(View view) {
        MyDateDialogFragment dateDialog = new MyDateDialogFragment();
        dateDialog.show(getSupportFragmentManager(), "datePicker");
    }
    public void onClickShowProgressDialog(View view) {
        MyProgressDialogFragment progressDialog = new MyProgressDialogFragment();
        progressDialog.show(getSupportFragmentManager(), "progressDialog");
    }

}