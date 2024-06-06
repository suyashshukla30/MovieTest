package com.suyashshukla.movietestapp.DataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieEntity movie);

    @Query("SELECT * FROM movies")
    List<MovieEntity> getAllMovies();
    @Query("SELECT COUNT(*) FROM movies")
    int getMovieCount();
}
