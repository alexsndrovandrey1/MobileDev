package ru.mirea.alexsandrovaa.accelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.alexsandrovaa.accelerometer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ActivityMainBinding binding; // Привязка

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Инициализация менеджера сенсоров
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Регистрируем слушатель изменений датчика
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Отключаем слушатель сенсоров при приостановке активности
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Повторно регистрируем слушатель при возобновлении активности
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Получаем значения акселерометра
            float x = event.values[0];  // Азимут (X)
            float y = event.values[1];  // Угол наклона (Y)
            float z = event.values[2];  // Поворот (Z)

            // Обновляем текстовые поля с использованием привязки
            binding.textViewAzimuth.setText(String.format("Azimuth: %s", x));
            binding.textViewPitch.setText(String.format("Pitch: %s", y));
            binding.textViewRoll.setText(String.format("Roll: %s", z));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Проверяем, если это акселерометр
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            switch (accuracy) {
                case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                    // Высокая точность
                    Log.d("Точность сенсора", "Точность высокая");
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                    // Средняя точность
                    Log.d("Точность сенсора", "Точность средняя");
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                    // Низкая точность
                    Log.d("Точность сенсора", "Точность низкая");
                    break;
                case SensorManager.SENSOR_STATUS_UNRELIABLE:
                    // Недостоверные данные
                    Log.d("Точность сенсора", "Точность ненадежная");
                    break;
                default:
                    Log.d("Точность сенсора", "Неизвестный статус точности");
                    break;
            }
        }
    }
}