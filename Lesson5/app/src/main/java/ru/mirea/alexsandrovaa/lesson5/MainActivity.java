package ru.mirea.alexsandrovaa.lesson5;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.widget.SimpleAdapter;

import ru.mirea.alexsandrovaa.lesson5.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Получаем объект SensorManager
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Получаем список всех сенсоров
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        // Получаем ListView для отображения сенсоров
        ListView listSensor = binding.listView;

        // Создаем список для отображения в ListView
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();

        // Заполняем список сенсоров
        for (int i = 0; i < sensors.size(); i++) {
            HashMap<String, Object> sensorTypeList = new HashMap<>();
            sensorTypeList.put("Name", sensors.get(i).getName());
            sensorTypeList.put("Value", sensors.get(i).getMaximumRange());
            arrayList.add(sensorTypeList);
        }

        // Создаем адаптер для отображения двух полей (Name и Value) в ListView
        SimpleAdapter mHistory = new SimpleAdapter(this, arrayList, android.R.layout.simple_list_item_2,
                new String[]{"Name", "Value"},
                new int[]{android.R.id.text1, android.R.id.text2});

        // Устанавливаем адаптер для ListView
        listSensor.setAdapter(mHistory);
    }
}