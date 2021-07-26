package com.israelbulcaotavares.filmespopulares.loader;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import com.israelbulcaotavares.filmespopulares.model.Filme;
import com.israelbulcaotavares.filmespopulares.util.QueryUtils;

import java.util.List;

public class FilmeLoader extends AsyncTaskLoader<List<Filme>> {
    private String mUrl;

    public FilmeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Filme> loadInBackground() {
        return QueryUtils.fetchMovieData(mUrl);
    }
}
