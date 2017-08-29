package br.org.maanaim.checkpray.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Silvinho Cedrim on 06/07/2017.
 */

public class Compromisso implements Serializable{

    long id;

    String nome;

    String tipo;

    boolean isCheck;

    Date date;


    public Compromisso(String nome, String tipo, boolean isCheck) {
        this.nome = nome;
        this.tipo = tipo;
        this.isCheck = isCheck;
    }

    public Compromisso(long id, String nome, String tipo, boolean isCheck) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.isCheck = isCheck;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
