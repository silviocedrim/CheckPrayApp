package br.org.maanaim.checkpray.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Silvinho Cedrim on 10/07/2017.
 */

public class Usuario implements Serializable {

    long id;

    String nome;

    String email;

    String senha;

    @SerializedName("grau_pertenca")
    String grauPertenca;

    boolean ativo;

    String error;

    int tipo_error;


    public Usuario(long id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }


    public Usuario(String nome, String email, String senha, String grauPertenca) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.grauPertenca = grauPertenca;
    }

    public Usuario(){

    }

    public int getTipo_error() {
        return tipo_error;
    }

    public void setTipo_error(int tipo_error) {
        this.tipo_error = tipo_error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Usuario(long id) {
        this.id = id;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getGrauPertenca() {
        return grauPertenca;
    }

    public void setGrauPertenca(String grauPertenca) {
        this.grauPertenca = grauPertenca;
    }
}
