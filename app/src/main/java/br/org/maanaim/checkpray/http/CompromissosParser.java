package br.org.maanaim.checkpray.http;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import br.org.maanaim.checkpray.bean.Compromisso;
import br.org.maanaim.checkpray.bean.ListCompromissos;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Silvinho Cedrim on 02/11/2016.
 */


public class CompromissosParser {

    public static final String URL_SEARCH = "http://www.comunidademaanaim.org.br/compromissos.json";

    public static List<Compromisso> search() throws IOException {
        Response response = null;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL_SEARCH)
                .build();

        response = client.newCall(request).execute();
        if(response.networkResponse().code() == HttpURLConnection.HTTP_OK){
            String json = response.body().string();

            Gson gson = new Gson();

            ListCompromissos result = gson.fromJson(json, ListCompromissos.class);

            if (result != null) {
                    return result.getCompromissos();
            }
        }

        return null;

    }

}
