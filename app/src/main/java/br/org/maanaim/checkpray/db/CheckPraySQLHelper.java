package br.org.maanaim.checkpray.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.org.maanaim.checkpray.interfaces.CheckPrayContract;

/**
 * Created by Silvinho Cedrim on 30/11/2016.
 */

public class CheckPraySQLHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "dbCompromissos";

    private static final int VERSAO_BANCO = 1;


    public CheckPraySQLHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CheckPrayContract.SQL_CREATE_COMPROMISSO);
        db.execSQL(CheckPrayContract.SQL_CREATE_USUARIO);
        db.execSQL(CheckPrayContract.SQL_CREATE_COMPROMISSOS_CUMPRIDOS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CheckPrayContract.TABLE_NAME_COMPROMISSO);
        db.execSQL("DROP TABLE IF EXISTS " + CheckPrayContract.TABLE_NAME_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + CheckPrayContract.TABLE_NAME_COMPROMISSOS_CUMPRIDOS);

        // create new tables
        onCreate(db);
    }
}
