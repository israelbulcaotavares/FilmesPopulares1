package com.israelbulcaotavares.filmespopulares.constants;

import com.israelbulcaotavares.filmespopulares.util.QueryUtils;

public class Constants {
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public static final int CONN_READ_TIME = 10000;
    public static final int CONN_CONNECT_TIME = 15000;

    public static final String JSON_ARRAY_RESULTS = "results";
    public static final String JSON_KEY_ID = "id";
    public static final String JSON_KEY_TITULO = "title"; //titulo
    public static final String JSON_KEY_VOTACAO = "vote_average"; //media de votacao
    public static final String JSON_KEY_POSTER_PATH = "poster_path"; //capa do poster
    public static final String JSON_KEY_SINOPSE = "overview"; //sinopse
    public static final String JSON_KEY_DATA_LANCAMENTO = "release_date"; //data de lancamento

    public static final String THE_MOVIE_DB_REQUEST_URL = "https://api.themoviedb.org/3/movie/";
    public static final String PARAM_API_KEY = "api_key";
    public static final String GET = "GET";
    public static final String API_KEY = "BuildConfig.API_KEY"; //TODO: local para API deixei na gradle.properties

    public static final String PARAM_LANGUAGE = "language";  //lingua
    public static final String PT_BR = "pt-BR";  //pt-br
    public static final String PARAM_REGIAO = "region";  //lingua
    public static final String REGIAO_BR = "BR";  //lingua
    public static final String THE_MOVIE_DB_IMAGE_REQUEST_URL = "https://image.tmdb.org/t/p/w185";
}
