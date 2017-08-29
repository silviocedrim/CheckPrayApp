package br.org.maanaim.checkpray.bean;

import java.util.Date;

/**
 * Created by Silvinho Cedrim on 12/07/2017.
 */

public class CumprimentoCompromisso{

    private int id;

    private Usuario usuario;

    private Date data;

    private Compromisso compromisso;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Compromisso getCompromisso() {
        return compromisso;
    }

    public void setCompromisso(Compromisso compromisso) {
        this.compromisso = compromisso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
