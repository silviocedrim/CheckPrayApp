package br.org.maanaim.checkpray.http;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;

import br.org.maanaim.checkpray.bean.ListCompromissos;
import br.org.maanaim.checkpray.bean.Usuario;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by Silvinho Cedrim on 07/08/2017.
 */

public class DBServConnect {

    public static final String URL_DB_LOGIN = "http://comunidademaanaim.org.br/db_connect/login.php";

    public static final String URL_DB_CADASTRO = "http://comunidademaanaim.org.br/db_connect/cadastro.php";


    public static Usuario login(Usuario usuario){
        Response response = null;

        Usuario retorno = null;


        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody body = new FormBody.Builder()
                    .add("email", usuario.getEmail())
                    .add("senha", usuario.getSenha())
                    .build();


            Request request = new Request.Builder()
                    .url(URL_DB_LOGIN)
                    .post(body)
                    .build();

            response = client.newCall(request).execute();

            if(response.networkResponse().code() == HttpURLConnection.HTTP_OK){
                String json = response.body().string();

                Gson gson = new Gson();

                retorno = gson.fromJson(json, Usuario.class);


            }
        }catch (IOException e){

        }
        return retorno;

    }

    public static Usuario cadastrar(Usuario usuario){
        Response response = null;

        Usuario retorno = null;


        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody body = new FormBody.Builder()
                    .add("nome", usuario.getNome())
                    .add("email", usuario.getEmail())
                    .add("senha", usuario.getSenha())
                    .add("grau_pertenca", usuario.getGrauPertenca())
                    .build();


            Request request = new Request.Builder()
                    .url(URL_DB_CADASTRO)
                    .post(body)
                    .build();

            response = client.newCall(request).execute();

            if(response.networkResponse().code() == HttpURLConnection.HTTP_OK){
                String json = response.body().string();

                Gson gson = new Gson();

                retorno = gson.fromJson(json, Usuario.class);

            }
        }catch (IOException e){

        }
        return retorno;

    }

}
