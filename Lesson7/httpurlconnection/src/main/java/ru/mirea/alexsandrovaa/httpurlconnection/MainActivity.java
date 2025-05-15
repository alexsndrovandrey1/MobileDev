package ru.mirea.alexsandrovaa.httpurlconnection;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView tvIp, tvCity, tvRegion, tvWeather;
    private final String ipInfoUrl = "https://ipinfo.io/json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvIp = findViewById(R.id.tvIp);
        tvCity = findViewById(R.id.tvCity);
        tvRegion = findViewById(R.id.tvRegion);
        tvWeather = findViewById(R.id.tvWeather);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> checkInternetAndDownload());
    }

    private void checkInternetAndDownload() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadIpInfoTask().execute(ipInfoUrl);
        } else {
            Toast.makeText(this, "Нет интернета", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadIpInfoTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream in = new BufferedInputStream(conn.getInputStream());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                int read;
                while ((read = in.read()) != -1) {
                    bos.write(read);
                }

                return new JSONObject(bos.toString());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            if (result != null) {
                try {
                    String ip = result.getString("ip");
                    String city = result.getString("city");
                    String region = result.getString("region");
                    String loc = result.getString("loc"); // формат: "52.52,13.41"

                    tvIp.setText("IP: " + ip);
                    tvCity.setText("Город: " + city);
                    tvRegion.setText("Регион: " + region);

                    String[] coords = loc.split(",");
                    new DownloadWeatherTask().execute(coords[0], coords[1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class DownloadWeatherTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... coords) {
            try {
                String apiUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + coords[0] +
                        "&longitude=" + coords[1] + "&current_weather=true";
                URL url = new URL(apiUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream in = new BufferedInputStream(conn.getInputStream());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                int read;
                while ((read = in.read()) != -1) {
                    bos.write(read);
                }

                return new JSONObject(bos.toString());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            if (result != null) {
                try {
                    JSONObject weather = result.getJSONObject("current_weather");
                    double temperature = weather.getDouble("temperature");
                    double wind = weather.getDouble("windspeed");

                    tvWeather.setText("Температура: " + temperature + "°C\nВетер: " + wind + " км/ч");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}