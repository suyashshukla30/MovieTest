package com.suyashshukla.movietestapp.Modal;


import androidx.room.PrimaryKey;

import java.util.List;
public class Movie {
    @PrimaryKey(autoGenerate = true)
    public String ImdbId;
    public String poster_url;
    public String name;
    public String runtime;
    public String year;
    public List<String> genre;
    public Director director;
    public List<Cast> cast;

    public class Cast {
        public String name;
    }
    public class Director {
        public String name;
    }

}