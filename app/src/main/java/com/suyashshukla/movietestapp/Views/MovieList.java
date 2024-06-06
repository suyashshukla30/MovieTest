package com.suyashshukla.movietestapp.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.suyashshukla.movietestapp.Adapter.MovieAdapter;
import com.suyashshukla.movietestapp.DataBase.MovieDao;
import com.suyashshukla.movietestapp.DataBase.MovieDatabase;
import com.suyashshukla.movietestapp.DataBase.MovieEntity;
import com.suyashshukla.movietestapp.R;

import java.util.List;

public class MovieList extends AppCompatActivity {
    RecyclerView rv_movie_details;
    private MovieDao movieDao;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        rv_movie_details = findViewById(R.id.rv_movie_list);
        movieDao = MovieDatabase.getInstance(MovieList.this).movieDao();
        new GetMoviesTask().execute();
    }

    private class GetMoviesTask extends AsyncTask<Void, Void, List<MovieEntity>> {

        @Override
        protected List<MovieEntity> doInBackground(Void... voids) {
            if (movieDao.getMovieCount() != 0) {
                return movieDao.getAllMovies();
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<MovieEntity> movieEntities) {
            super.onPostExecute(movieEntities);
            if (movieEntities != null) {
                if (!isNetworkConnected()) {
                    Toast.makeText(MovieList.this, "In offline Mode...", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(MovieList.this, "Data found in room...", Toast.LENGTH_LONG).show();
                movieAdapter = new MovieAdapter(movieEntities);
                rv_movie_details.setAdapter(movieAdapter);
                rv_movie_details.setLayoutManager(new LinearLayoutManager(MovieList.this));
            } else {
                if (isNetworkConnected()) {
                    Toast.makeText(MovieList.this, "Data getting saved in Room...please wait", Toast.LENGTH_LONG).show();
                    new GetMoviesTask().execute();
                } else {
                    Toast.makeText(MovieList.this, "App never connected to server, Kindly connect and restart app", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MovieList.this, MainActivity.class));
        finish();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}