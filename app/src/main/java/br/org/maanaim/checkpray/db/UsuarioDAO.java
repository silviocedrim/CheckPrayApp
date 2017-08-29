package br.org.maanaim.checkpray.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;

import br.org.maanaim.checkpray.bean.Usuario;
import br.org.maanaim.checkpray.interfaces.CheckPrayContract;

/**
 * Created by Silvinho Cedrim on 24/07/2017.
 */

public class UsuarioDAO {

    private CheckPraySQLHelper mHelper;

    SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");

    public UsuarioDAO(Context context) {
        this.mHelper = new CheckPraySQLHelper(context);
    }

    public long insert(Usuario usuario) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues values = parser(usuario);
        long id = 0;
        id = db.insertOrThrow(CheckPrayContract.TABLE_NAME_USUARIO, null, values);

        db.close();
        return id;
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

    public boolean existeUsuario(Usuario usuario){
        SQLiteDatabase db = mHelper.getReadableDatabase();

        boolean existe = false;

        String[] argumentos = null;

        String sql = "SELECT * FROM " + CheckPrayContract.TABLE_NAME_USUARIO
                + " WHERE " + CheckPrayContract.EMAIL + " = ? ";

        argumentos = new String[]{usuario.getEmail()};

        Cursor cursor = db.rawQuery(sql, argumentos);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                existe = true;
            } else {
                existe = false;
            }

        }
        return existe;
    }

    public void atualizarAtivoUsuario(Usuario usuario){

    }

    public Usuario buscarUsuario(Usuario usuario) {

        SQLiteDatabase db = mHelper.getReadableDatabase();

        Usuario usuarioEncontrado = null;
        String[] argumentos = null;

        String sql = "SELECT * FROM " + CheckPrayContract.TABLE_NAME_USUARIO
                + " WHERE " + CheckPrayContract.EMAIL + " = ? AND "
                + CheckPrayContract.SENHA + " = ? ";

        argumentos = new String[]{usuario.getEmail(), usuario.getSenha()};

        Cursor cursor = db.rawQuery(sql, argumentos);

        if (cursor.getCount() > 0) {

            int idxIdUsuario = cursor.getColumnIndex(CheckPrayContract.ID);
            int idxEmail = cursor.getColumnIndex(CheckPrayContract.EMAIL);
            int idxNome = cursor.getColumnIndex(CheckPrayContract.NOME);
            int idxGrau = cursor.getColumnIndex(CheckPrayContract.GRAU_PERTENCA);

            if (cursor.moveToFirst()) {

                long idUsuario = cursor.getLong(idxIdUsuario);

                String nome = cursor.getString(idxNome);

                String email = cursor.getString(idxEmail);

                String grauPertenca = cursor.getString(idxGrau);

                usuarioEncontrado = new Usuario();
                usuarioEncontrado.setId(idUsuario);
                usuarioEncontrado.setNome(nome);
                usuarioEncontrado.setEmail(email);
                usuarioEncontrado.setGrauPertenca(grauPertenca);

            }
            cursor.close();
            db.close();
        }
        return usuarioEncontrado;
    }

    private ContentValues parser(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put(CheckPrayContract.ID, usuario.getId());
        values.put(CheckPrayContract.NOME, usuario.getNome());
        values.put(CheckPrayContract.EMAIL, usuario.getEmail());
        values.put(CheckPrayContract.GRAU_PERTENCA, usuario.getGrauPertenca());

        return values;
    }
}
