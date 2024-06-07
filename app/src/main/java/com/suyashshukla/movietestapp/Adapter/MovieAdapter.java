package com.suyashshukla.movietestapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.suyashshukla.movietestapp.DataBase.MovieEntity;
import com.suyashshukla.movietestapp.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<MovieEntity> movie_list;

    public MovieAdapter(List<MovieEntity> movie_list) {
        this.movie_list = movie_list;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        MovieEntity movie = movie_list.get(position);
        //If there is no net or no image a default image will show up.
        Glide.with(holder.itemView.getContext())
                .load(movie.poster_url)  
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.movie_poster);

        holder.movie_name.setText(movie.name);
        holder.movie_runtime.setText("Runtime: " + movie.runtime);
        holder.movie_year.setText("Year: " + movie.year);
        holder.movie_genre.setText("Genre: " + movie.genre);
        holder.movie_cast.setText("Cast: " + movie.cast);
        holder.movie_director.setText("Director: " + movie.director);
        holder.position.setText((position+1)+"");
    }

    @Override
    public int getItemCount() {
        return movie_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView movie_poster;
        private TextView movie_name;
        private TextView movie_runtime;
        private TextView movie_year;
        private TextView movie_genre;
        private TextView movie_cast;
        private TextView movie_director;
        private TextView position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            position = itemView.findViewById(R.id.movie_position);
            movie_poster = itemView.findViewById(R.id.movie_poster);
            movie_name = itemView.findViewById(R.id.movie_name);
            movie_runtime = itemView.findViewById(R.id.movie_runtime);
            movie_year = itemView.findViewById(R.id.movie_year);
            movie_genre = itemView.findViewById(R.id.movie_genre);
            movie_cast = itemView.findViewById(R.id.movie_cast);
            movie_director = itemView.findViewById(R.id.movie_director);
        }
    }
}
