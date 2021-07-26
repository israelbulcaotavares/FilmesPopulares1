package com.israelbulcaotavares.filmespopulares.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.israelbulcaotavares.filmespopulares.R;
import com.israelbulcaotavares.filmespopulares.activities.DetalhesActivity;
import com.israelbulcaotavares.filmespopulares.model.Filme;
import com.israelbulcaotavares.filmespopulares.util.QueryUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FilmesAdapter extends RecyclerView.Adapter<FilmesAdapter.MovieHolder>{

    private List<Filme> mFilmes;
    private Context mContext;

    public FilmesAdapter(Context context, List<Filme> filmes) {
        mFilmes = filmes;
        mContext = context;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        View view = mLayoutInflater.inflate(R.layout.adapter_item, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, final int position) {
        final Filme filme = mFilmes.get(position);

        Picasso.with(mContext)
                .load(QueryUtils.makeRequestUrlParaPosterImagem(mFilmes.get(position).getCartaz()))
                .into(holder.mPosterFilme);

        holder.mPosterFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetalhesActivity.class);
                intent.putExtra(Filme.class.getSimpleName(), mFilmes.get(position));
                mContext.startActivity(intent);
            }
        });

        holder.maisVotados.setText(filme.getVotacaoMedia());

    }

    @Override
    public int getItemCount() {
        return mFilmes.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        private ImageView mPosterFilme;
        private TextView maisVotados;

        public MovieHolder(View itemView) {
            super(itemView);

            mPosterFilme =  itemView.findViewById(R.id.movie_list_poster_image);
            maisVotados =   itemView.findViewById(R.id.text_votacao);

        }
    }

    public void atualizarLista(List<Filme> filmes) {
        mFilmes.clear();
        mFilmes.addAll(filmes);
        notifyDataSetChanged();
    }

}
