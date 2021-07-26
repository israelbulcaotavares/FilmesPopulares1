package com.israelbulcaotavares.filmespopulares.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.israelbulcaotavares.filmespopulares.R;
import com.israelbulcaotavares.filmespopulares.model.Filme;
import com.israelbulcaotavares.filmespopulares.util.QueryUtils;
import com.squareup.picasso.Picasso;

public class DetalhesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView posterCapaImageView = findViewById(R.id.image_caapa);
        TextView titulo = findViewById(R.id.text_titulo);
        TextView data = findViewById(R.id.text_data_lancamento);
        TextView votos = findViewById(R.id.text_votacao);
        TextView sinopse = findViewById(R.id.text_sinopse);


        Filme filme = getIntent().getExtras().getParcelable(Filme.class.getSimpleName());
        String requestUrlForPoster = QueryUtils.makeRequestUrlParaPosterImagem(filme.getCartaz());

        Picasso.with(this)
                .load(requestUrlForPoster)
                .into(posterCapaImageView);

        titulo.setText(filme.getTitulo());
        data.setText(filme.getDataLancamento());
        votos.setText(filme.getVotacaoMedia());
        sinopse.setText(filme.getSinopse());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
