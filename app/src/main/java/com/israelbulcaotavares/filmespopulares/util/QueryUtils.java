package com.israelbulcaotavares.filmespopulares.util;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.israelbulcaotavares.filmespopulares.R;
import com.israelbulcaotavares.filmespopulares.model.Filme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.israelbulcaotavares.filmespopulares.constants.Constants.API_KEY;
import static com.israelbulcaotavares.filmespopulares.constants.Constants.CONN_CONNECT_TIME;
import static com.israelbulcaotavares.filmespopulares.constants.Constants.CONN_READ_TIME;
import static com.israelbulcaotavares.filmespopulares.constants.Constants.GET;
import static com.israelbulcaotavares.filmespopulares.constants.Constants.JSON_ARRAY_RESULTS;
import static com.israelbulcaotavares.filmespopulares.constants.Constants.JSON_KEY_DATA_LANCAMENTO;
import static com.israelbulcaotavares.filmespopulares.constants.Constants.JSON_KEY_ID;
import static com.israelbulcaotavares.filmespopulares.constants.Constants.JSON_KEY_POSTER_PATH;
import static com.israelbulcaotavares.filmespopulares.constants.Constants.JSON_KEY_SINOPSE;
import static com.israelbulcaotavares.filmespopulares.constants.Constants.JSON_KEY_TITULO;
import static com.israelbulcaotavares.filmespopulares.constants.Constants.JSON_KEY_VOTACAO;
import static com.israelbulcaotavares.filmespopulares.constants.Constants.LOG_TAG;
import static com.israelbulcaotavares.filmespopulares.constants.Constants.PARAM_API_KEY;
import static com.israelbulcaotavares.filmespopulares.constants.Constants.PARAM_LANGUAGE;
import static com.israelbulcaotavares.filmespopulares.constants.Constants.PARAM_REGIAO;
import static com.israelbulcaotavares.filmespopulares.constants.Constants.PT_BR;
import static com.israelbulcaotavares.filmespopulares.constants.Constants.REGIAO_BR;
import static com.israelbulcaotavares.filmespopulares.constants.Constants.THE_MOVIE_DB_IMAGE_REQUEST_URL;
import static com.israelbulcaotavares.filmespopulares.constants.Constants.THE_MOVIE_DB_REQUEST_URL;

public class QueryUtils {

    public static List<Filme> fetchMovieData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        jsonResponse = makeHttpRequest(url);
        List<Filme> filmes = extractFeatureFromJson(jsonResponse);

        return filmes;
    }

    private static URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, String.valueOf(R.string.erro_criar_url), e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(CONN_READ_TIME);
            httpURLConnection.setConnectTimeout(CONN_CONNECT_TIME);
            httpURLConnection.setRequestMethod(GET);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, String.valueOf(R.string.CODIGO_DE_ERRO_RESPOSTA) + httpURLConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, String.valueOf(R.string.ERRO_RECUPERAR_JSON_RESULTADOS), e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            try {
                line = bufferedReader.readLine();
                while (line != null) {
                    output.append(line);
                    line = bufferedReader.readLine();
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, String.valueOf(R.string.ERRO_LEITURA_JSON), e);
            }
        }
        return output.toString();
    }

    private static List<Filme> extractFeatureFromJson(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) return null;

        List<Filme> filmes = new ArrayList<>();
        String id = "";
        String titulo = "";
        String votacao = "";
        String posterPath = "";
        String sinopse = "";
        String dataLancamento = "";

        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            if (baseJsonResponse.has(JSON_ARRAY_RESULTS)) {
                JSONArray results = baseJsonResponse.getJSONArray(JSON_ARRAY_RESULTS);
                for (int i = 0; i < results.length() && obterTodosElementos(results.getJSONObject(i)); i++) {
                    JSONObject result = results.getJSONObject(i);
                    id = result.getString(JSON_KEY_ID);
                    titulo = result.getString(JSON_KEY_TITULO);
                    votacao = result.getString(JSON_KEY_VOTACAO);
                    posterPath = result.getString(JSON_KEY_POSTER_PATH).substring(1); //remove '/'
                    sinopse = result.getString(JSON_KEY_SINOPSE);
                    dataLancamento = result.getString(JSON_KEY_DATA_LANCAMENTO);
                    filmes.add(new Filme(id, titulo, votacao, posterPath, sinopse, dataLancamento));
                }
            } else {
                Log.i(LOG_TAG, String.valueOf(R.string.OBJETO_JSON_NAO_ENCONTRADO));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, String.valueOf(R.string.JSON_EXCEPTION), e);
            e.printStackTrace();
        }
        return filmes;
    }

    private static boolean obterTodosElementos(JSONObject result) {
        return result.has(JSON_KEY_ID) && result.has(JSON_KEY_TITULO)
                && result.has(JSON_KEY_VOTACAO) && result.has(JSON_KEY_POSTER_PATH)
                && result.has(JSON_KEY_SINOPSE) && result.has(JSON_KEY_DATA_LANCAMENTO);
    }

    public static String makeRequestUrlParaListaFilmes(String pathForFilter) {
        Uri.Builder uriBuilder = Uri.parse(THE_MOVIE_DB_REQUEST_URL)
                .buildUpon()
                .appendPath(pathForFilter)
                 .appendQueryParameter(PARAM_LANGUAGE, PT_BR)
                 .appendQueryParameter(PARAM_API_KEY, API_KEY)
                 .appendQueryParameter(PARAM_REGIAO, REGIAO_BR);
        return uriBuilder.toString();
    }

    public static String makeRequestUrlParaPosterImagem(String posterPath) {
        Uri.Builder uriBuilder = Uri.parse(THE_MOVIE_DB_IMAGE_REQUEST_URL)
                .buildUpon()
                .appendPath(posterPath);
        return uriBuilder.toString();
    }
}
