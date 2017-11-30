package br.org.maanaim.checkpray.http;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;
import java.util.List;

import br.org.maanaim.checkpray.bean.Compromisso;
import br.org.maanaim.checkpray.db.CompromissosDAO;

/**
 * Created by Silvinho Cedrim on 02/11/2016.
 */

public class CompromissosTask extends AsyncTaskLoader<List<Compromisso>> {
    List<Compromisso> mCompromissos;

    CompromissosDAO mDAO;

    public CompromissosTask(Context context) {
        super(context);
        mDAO = new CompromissosDAO(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(mCompromissos == null){
            forceLoad();
        }else{
            deliverResult(mCompromissos);
        }
    }

    @Override
    public List<Compromisso> loadInBackground() {
        try {
            mCompromissos = CompromissosParser.search();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Compromisso c : mCompromissos) {
            mDAO.salvarCompromisso(c);
        }
        return mCompromissos;
    }
}
