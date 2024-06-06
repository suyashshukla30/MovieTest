package com.suyashshukla.movietestapp.DataBase;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class MovieEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String imdbId;
    public String poster_url;
    public String name;
    public String runtime;
    public String year;
    public String genre;
    public String director;
    public String cast;

}