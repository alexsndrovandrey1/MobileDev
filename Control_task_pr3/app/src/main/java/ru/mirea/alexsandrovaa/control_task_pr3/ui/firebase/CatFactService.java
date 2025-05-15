package ru.mirea.alexsandrovaa.control_task_pr3.ui.firebase;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CatFactService {
    @GET("fact")
    Call<CatFactResponse> getRandomFact();
}
