package br.org.maanaim.checkpray.interfaces;

import android.provider.BaseColumns;

/**
 * Created by Silvinho Cedrim on 30/11/2016.
 */

public interface CheckPrayContract extends BaseColumns {

    String TABLE_NAME_COMPROMISSO = "COMPROMISSO";

    String TABLE_NAME_USUARIO = "USUARIO";

    String TABLE_NAME_COMPROMISSOS_CUMPRIDOS = "COMPROMISSOS_CUMPRIDOS";

    String ID = "ID";

    String ID_COMPROMISSO = ID + "_" + TABLE_NAME_COMPROMISSO;

    String ID_USUARIO = ID + "_" + TABLE_NAME_USUARIO;

    String NOME = "NOME";

    String TIPO = "TIPO";

    String EMAIL = "EMAIL";

    String SENHA = "SENHA";

    String GRAU_PERTENCA = "GRAU_PERTENCA";

    String ATIVO = "ATIVO";

    String DATA = "DATA";

    String CUMPRIDO = "CUMPRIDO";

    String SQL_CREATE_COMPROMISSO = "CREATE TABLE " + TABLE_NAME_COMPROMISSO + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            NOME + " TEXT NOT NULL, " +
            TIPO + " TEXT NOT NULL) ";
    String SQL_CREATE_USUARIO = "CREATE TABLE " + TABLE_NAME_USUARIO + " (" +
            ID + " INTEGER PRIMARY KEY," +
            NOME + " TEXT NOT NULL," +
            EMAIL + " TEXT NOT NULL," +
            SENHA + " TEXT," +
            GRAU_PERTENCA + " TEXT NOT NULL) ";

    String SQL_CREATE_COMPROMISSOS_CUMPRIDOS = "CREATE TABLE " + TABLE_NAME_COMPROMISSOS_CUMPRIDOS + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ID_COMPROMISSO + " INTEGER NOT NULL, " +
            ID_USUARIO + " INTEGER NOT NULL, " +
            DATA + " TEXT NOT NULL," +
            CUMPRIDO + " TEXT NOT NULL," +
            "FOREIGN KEY(" + ID + "_" + TABLE_NAME_COMPROMISSO + ") " +
            "REFERENCES " + TABLE_NAME_COMPROMISSO + "(" + ID + ")," +
            "FOREIGN KEY(" + ID + "_" + TABLE_NAME_USUARIO + ") " +
            "REFERENCES " + TABLE_NAME_USUARIO + "(" + ID + "))";


}

