package ru.mirea.alexsandrovaa.control_task_pr3.ui.firebase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mirea.alexsandrovaa.control_task_pr3.R;
import ru.mirea.alexsandrovaa.control_task_pr3.databinding.FragmentNetworkBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkFragment extends Fragment {

    private TextView activityTextView;
    private Button refreshButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_network, container, false);

        // Инициализация элементов UI
        activityTextView = rootView.findViewById(R.id.activityTextView);
        refreshButton = rootView.findViewById(R.id.refreshButton);

        // Обработчик кнопки для обновления факта
        refreshButton.setOnClickListener(v -> getCatFact());

        // Изначально загружаем факт о кошке
        getCatFact();

        return rootView;
    }

    private void getCatFact() {
        // Создание Retrofit-инстанса
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://catfact.ninja/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Создание интерфейса для API
        CatFactService service = retrofit.create(CatFactService.class);

        // Вызов метода получения случайного факта
        Call<CatFactResponse> call = service.getRandomFact();
        call.enqueue(new Callback<CatFactResponse>() {
            @Override
            public void onResponse(Call<CatFactResponse> call, Response<CatFactResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Выводим факт о кошке
                    activityTextView.setText("Факт о кошках:\n\n" + response.body().getFact());
                } else {
                    // Ошибка: пустой ответ от сервера
                    activityTextView.setText("Ошибка: пустой ответ от сервера");
                }
            }

            @Override
            public void onFailure(Call<CatFactResponse> call, Throwable t) {
                // Ошибка сети
                activityTextView.setText("Сетевая ошибка: " + t.getMessage());
            }
        });
    }
}
