package com.suyashshukla.movietestapp;

import com.suyashshukla.movietestapp.Modal.Movie;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("test")
    Call<ResponseBody> getMoviesRaw();
}
