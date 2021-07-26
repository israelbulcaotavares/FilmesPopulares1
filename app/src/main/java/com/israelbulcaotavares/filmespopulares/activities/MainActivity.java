package com.israelbulcaotavares.filmespopulares.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.israelbulcaotavares.filmespopulares.R;
import com.israelbulcaotavares.filmespopulares.adapter.FilmesAdapter;
import com.israelbulcaotavares.filmespopulares.loader.FilmeLoader;
import com.israelbulcaotavares.filmespopulares.model.Filme;
import com.israelbulcaotavares.filmespopulares.util.QueryUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,  LoaderManager.LoaderCallbacks<List<Filme>> {

    private static final String PATH_POPULARES = "popular";
    private static final String PATH_MAIS_VOTADOS = "top_rated";
    private static final String PATH_TOP_CINEMAS = "now_playing";

    private static final String TOOLBAR_TITLE = "Filmes Famosos parte 1";

    private static final int LOADER_ID_POPULAR = 0; //Filmes populares
    private static final int LOADER_ID_MAIS_VOTADOS = 1;//Filmes mais votados
    private static final int LOADER_ID_EM_CINEMAS = 2; //Filmes em exibicao nos cinemas

    private static final int MOVIE_LISTA_COLUNAS = 3; //Quantidade de colunas

    private ProgressBar mLoadingIndicator;
    private TextView mSemResultados;
    private FilmesAdapter mListaFilmes;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(TOOLBAR_TITLE);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mLoadingIndicator =  findViewById(R.id.loading_indicator);
        TextView mSemConexao = findViewById(R.id.lista_sem_conexao);
        mSemResultados =  findViewById(R.id.text_sem_resultados);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, MOVIE_LISTA_COLUNAS));
        mRecyclerView.setHasFixedSize(true);
        mListaFilmes = new FilmesAdapter(this, new ArrayList<Filme>());
        mRecyclerView.setAdapter(mListaFilmes);

        if (isConnected()) {
            //INICIA PRIMEIRAMENTE A TELA COM OS FILMES POPULARES
            getSupportLoaderManager().initLoader(LOADER_ID_POPULAR, null, this);
        } else {
            mLoadingIndicator.setVisibility(View.GONE);
            mSemConexao.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_popular:
                getSupportLoaderManager().restartLoader(LOADER_ID_POPULAR, null, this);
                break;
            case R.id.nav_mais_votados:
                getSupportLoaderManager().restartLoader(LOADER_ID_MAIS_VOTADOS, null, this);
                break;
            case R.id.nav_exibicao_cinemas:
                getSupportLoaderManager().restartLoader(LOADER_ID_EM_CINEMAS, null, this);
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @NonNull
    @Override
    public Loader<List<Filme>> onCreateLoader(int loaderId, Bundle args) {
        String pathForFilter = "";
        switch (loaderId) {
            case LOADER_ID_MAIS_VOTADOS:
                pathForFilter = PATH_MAIS_VOTADOS;
                break;

            case LOADER_ID_EM_CINEMAS:
                pathForFilter = PATH_TOP_CINEMAS;
                break;

            default:
                pathForFilter = PATH_POPULARES;
        }
        String requestUrlForMovieList = QueryUtils.makeRequestUrlParaListaFilmes(pathForFilter);
        Loader<List<Filme>> mMovieLoader = new FilmeLoader(this, requestUrlForMovieList);
        return mMovieLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Filme>> loader, List<Filme> filmes) {
        mLoadingIndicator.setVisibility(View.GONE);
        if (filmes == null || filmes.size() == 0) {
            mSemResultados.setVisibility(View.VISIBLE);
        } else {
            mListaFilmes.atualizarLista(filmes);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Filme>> loader) {
        loader = null;
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}
