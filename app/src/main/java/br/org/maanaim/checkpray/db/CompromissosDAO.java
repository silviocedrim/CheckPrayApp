package br.org.maanaim.checkpray.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.org.maanaim.checkpray.bean.Compromisso;
import br.org.maanaim.checkpray.bean.CumprimentoCompromisso;
import br.org.maanaim.checkpray.bean.Usuario;
import br.org.maanaim.checkpray.interfaces.CheckPrayContract;

/**
 * Created by Silvinho Cedrim on 30/11/2016.
 */

public class CompromissosDAO {

    private CheckPraySQLHelper mHelper;

    SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");

    public CompromissosDAO(Context context) {
        this.mHelper = new CheckPraySQLHelper(context);
    }

    private long insert(Compromisso compromisso) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues values = parser(compromisso);

        long id = db.insert(CheckPrayContract.TABLE_NAME_COMPROMISSO, null, values);
        if (id != -1) {
            compromisso.setId(id);
        }
        db.close();
        return id;

    }

    private void insertCompromissoCumprido(CumprimentoCompromisso compromisso){
        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues values = parserCompromissosCumpridos(compromisso);
        long id = db.insert(CheckPrayContract.TABLE_NAME_COMPROMISSOS_CUMPRIDOS, null, values);
        db.close();
    }


    private void updateCumprimento(CumprimentoCompromisso compromisso){
        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues values = parserCompromissosCumpridos(compromisso);
        db.update(CheckPrayContract.TABLE_NAME_COMPROMISSOS_CUMPRIDOS,
                values,
                CheckPrayContract.ID + " = ?",
                new String[]{String.valueOf(compromisso.getId())});
        db.close();
    }

    public void marcarCumprimento(List<CumprimentoCompromisso> compromissos, Usuario usuario, Date date){

        for(CumprimentoCompromisso compromisso : compromissos){
            if (compromisso.getId() == 0) {
                compromisso.setUsuario(usuario);
                compromisso.setData(date);
                insertCompromissoCumprido(compromisso);
            } else {
                compromisso.setUsuario(usuario);
                compromisso.setData(date);
                updateCumprimento(compromisso);
            }
        }

    }


    private int update(Compromisso compromisso) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues values = parser(compromisso);

        int linhasAfetadas = db.update(CheckPrayContract.TABLE_NAME_COMPROMISSO,
                values,
                CheckPrayContract.ID + " = ?",
                new String[]{String.valueOf(compromisso.getId())});
        db.close();
        return linhasAfetadas;

    }

    public void salvarCompromisso(Compromisso compromisso) {
        if (compromisso.getId() == 0) {
            insert(compromisso);
        } else {
            update(compromisso);
        }
    }

    public int delete(Compromisso compromisso) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int linhasAfetadas = db.delete(CheckPrayContract.TABLE_NAME_COMPROMISSO,
                CheckPrayContract.ID + " = ?",
                new String[]{String.valueOf(compromisso.getId())});
        db.close();
        return linhasAfetadas;
    }

    public void delete(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(CheckPrayContract.TABLE_NAME_COMPROMISSO,
                null,
                null);
        db.close();
        db = mHelper.getWritableDatabase();
        db.delete(CheckPrayContract.TABLE_NAME_COMPROMISSOS_CUMPRIDOS,
                null,
                null);
        db.close();
        db = mHelper.getWritableDatabase();
        db.delete(CheckPrayContract.TABLE_NAME_USUARIO,
                null,
                null);
        db.close();
    }

    public boolean existeCompromissos() {
        boolean existe = false;
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + CheckPrayContract.TABLE_NAME_COMPROMISSO;

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            existe = true;
        }
        cursor.close();
        db.close();
        return existe;
    }

    public List<CumprimentoCompromisso> listarCompromissosCumpridosPorDia(Date date, Usuario usuario) {

        SQLiteDatabase db = mHelper.getReadableDatabase();
        List<CumprimentoCompromisso> compromissos = new ArrayList<>();
        String sql = "";
        String[] argumentos = null;

        sql = "SELECT " + CheckPrayContract.TABLE_NAME_COMPROMISSOS_CUMPRIDOS + "." + CheckPrayContract.ID + " AS " + CheckPrayContract.ID + ", " +
                CheckPrayContract.TABLE_NAME_COMPROMISSO + "." + CheckPrayContract.ID + " AS "+ CheckPrayContract.ID_COMPROMISSO + ", " +
                CheckPrayContract.TABLE_NAME_COMPROMISSO + "." + CheckPrayContract.NOME + ", " +
                CheckPrayContract.TABLE_NAME_COMPROMISSO + "." + CheckPrayContract.TIPO + ", " +
                CheckPrayContract.TABLE_NAME_COMPROMISSOS_CUMPRIDOS + "." + CheckPrayContract.CUMPRIDO +
        " FROM " + CheckPrayContract.TABLE_NAME_COMPROMISSOS_CUMPRIDOS;
        sql += " JOIN " + CheckPrayContract.TABLE_NAME_COMPROMISSO + " ON "
                        + CheckPrayContract.TABLE_NAME_COMPROMISSO + "." + CheckPrayContract.ID + " = "
                        + CheckPrayContract.TABLE_NAME_COMPROMISSOS_CUMPRIDOS  + "." + CheckPrayContract.ID_COMPROMISSO;
        sql += " WHERE " + CheckPrayContract.DATA + " = ?";
        sql += " AND " + CheckPrayContract.ID_USUARIO + " = ?";
        argumentos = new String[]{dt.format(date), String.valueOf(usuario.getId())};

        Cursor cursor = db.rawQuery(sql, argumentos);

        if (cursor.getCount() > 0) {
            int idxId = cursor.getColumnIndex(CheckPrayContract.ID);
            int idxIdCompromisso = cursor.getColumnIndex(CheckPrayContract.ID_COMPROMISSO);
            int idxFeito = cursor.getColumnIndex(CheckPrayContract.CUMPRIDO);
            int idxNome = cursor.getColumnIndex(CheckPrayContract.NOME);
            int idxTipo = cursor.getColumnIndex(CheckPrayContract.TIPO);

            while (cursor.moveToNext()) {

                int id = cursor.getInt(idxId);

                long idCompromisso = cursor.getLong(idxIdCompromisso);

                String nome = cursor.getString(idxNome);

                String tipo = cursor.getString(idxTipo);

                String check = cursor.getString(idxFeito);

                boolean feito = check.equals("1") ? true : false;


                Compromisso compromisso = new Compromisso(idCompromisso, nome, tipo, feito);
                compromisso.setId(idCompromisso);
                CumprimentoCompromisso cumprimentoCompromisso = new CumprimentoCompromisso();
                cumprimentoCompromisso.setCompromisso(compromisso);
                cumprimentoCompromisso.setId(id);

                compromissos.add(cumprimentoCompromisso);
            }
            cursor.close();
            db.close();
        }

        return compromissos;
    }

    public List<CumprimentoCompromisso> listarCompromissos() {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + CheckPrayContract.TABLE_NAME_COMPROMISSO;
        List<CumprimentoCompromisso> compromissos = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            int idxIdCompromisso = cursor.getColumnIndex(CheckPrayContract.ID);
            int idxTipo = cursor.getColumnIndex(CheckPrayContract.TIPO);
            int idxNome = cursor.getColumnIndex(CheckPrayContract.NOME);

            while (cursor.moveToNext()) {

                long idCompromisso = cursor.getLong(idxIdCompromisso);

                String nome = cursor.getString(idxNome);

                String tipo = cursor.getString(idxTipo);

                Compromisso compromisso = new Compromisso(idCompromisso, nome, tipo, false);
                CumprimentoCompromisso cumprimentoCompromisso = new CumprimentoCompromisso();
                cumprimentoCompromisso.setCompromisso(compromisso);

                compromissos.add(cumprimentoCompromisso);
            }
            cursor.close();
            db.close();
        }
        return compromissos;
    }


    private ContentValues parser(Compromisso compromisso) {
        ContentValues values = new ContentValues();
        values.put(CheckPrayContract.NOME, compromisso.getNome());
        values.put(CheckPrayContract.TIPO, compromisso.getTipo());

        return values;
    }

    private ContentValues parserCompromissosCumpridos(CumprimentoCompromisso compromisso) {
        ContentValues values = new ContentValues();
        values.put(CheckPrayContract.ID_USUARIO, compromisso.getUsuario().getId());
        values.put(CheckPrayContract.ID_COMPROMISSO, compromisso.getCompromisso().getId());
        values.put(CheckPrayContract.DATA, dt.format(compromisso.getData()));
        values.put(CheckPrayContract.CUMPRIDO, compromisso.getCompromisso().isCheck());

        return values;
    }
}
