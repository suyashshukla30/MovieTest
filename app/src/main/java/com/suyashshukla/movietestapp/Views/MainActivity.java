package com.suyashshukla.movietestapp.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.suyashshukla.movietestapp.ApiInterface;
import com.suyashshukla.movietestapp.DataBase.MovieDao;
import com.suyashshukla.movietestapp.DataBase.MovieDatabase;
import com.suyashshukla.movietestapp.DataBase.MovieEntity;
import com.suyashshukla.movietestapp.Modal.Movie;
import com.suyashshukla.movietestapp.R;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private MovieDatabase movie_database;
    private MovieDao movie_dao;
    Button btn_forward;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movie_database = MovieDatabase.getInstance(MainActivity.this);
        movie_dao = movie_database.movieDao();
        new CheckMovieCountTask().execute();
        btn_forward = findViewById(R.id.btn_movie_list);
        btn_forward.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,MovieList.class));
        });
    }

    private void fetchFromNetworkAndSave() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ccb5a4e4-af11-4352-8f54-6efdfbd07947.mock.pstmn.io/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiInterface apiService = retrofit.create(ApiInterface.class);
        apiService.getMoviesRaw().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonResponse = response.body().string();
                        parseJsonResponse(jsonResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void parseJsonResponse(String jsonResponse) {
        Gson gson = new Gson();
        List<Movie> dataArray = gson.fromJson(jsonResponse, new TypeToken<List<Movie>>() {
        }.getType());
        for (Movie movie : dataArray) {
            StringBuilder genreBuilder = new StringBuilder();
            String cast = "", director = "", genre = "";
            for (int i = 0; i < movie.genre.size(); i++) {
                genreBuilder.append(movie.genre.get(i));
                if (movie.genre != null) {
                    if (i < movie.genre.size() - 1) {
                        genreBuilder.append(", ");
                    }
                    genre = genreBuilder.toString();
                } else {
                    genre = "";
                }
            }
            StringBuilder castBuilder = new StringBuilder();
            if (movie.cast != null) {
                for (Movie.Cast castMember : movie.cast) {
                    castBuilder.append(castMember.name);
                    if (!castMember.equals(movie.cast.get(movie.cast.size() - 1))) {
                        castBuilder.append(", ");
                    }
                }
                cast = castBuilder.toString();
            } else {
                cast = "";
            }
            if (movie.director != null) {
                director = movie.director.name;
            } else {
                director = "";
            }
            MovieEntity movieEntity = new MovieEntity();
            movieEntity.imdbId = movie.ImdbId;
            movieEntity.poster_url = movie.poster_url;
            movieEntity.name = movie.name;
            movieEntity.runtime = movie.runtime;
            movieEntity.year = movie.year;
            movieEntity.genre = genre;
            movieEntity.director = director;
            movieEntity.cast = cast;
            new InsertMovieTask(movieEntity, movie_dao).execute();
        }
    }

    public class InsertMovieTask extends AsyncTask<Void, Void, Void> {

        private MovieEntity movieEntity;
        private MovieDao movie_dao;

        public InsertMovieTask(MovieEntity movieEntity, MovieDao movie_dao) {
            this.movieEntity = movieEntity;
            this.movie_dao = movie_dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            movie_dao.insert(movieEntity);
            return null;
        }
    }
    private class CheckMovieCountTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            int movieCount = movie_dao.getMovieCount();
            return movieCount == 0;
        }

        @Override
        protected void onPostExecute(Boolean isEmpty) {
            super.onPostExecute(isEmpty);

            if (isEmpty) {
                if(isNetworkConnected()) {
                    fetchFromNetworkAndSave();
                    Toast.makeText(MainActivity.this, "Fetching Data and saving in room", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Neither connected to Net nor any saved data", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}