package br.org.maanaim.checkpray.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import br.org.maanaim.checkpray.R;
import br.org.maanaim.checkpray.SharedPreference.MySaveSharedPreference;
import br.org.maanaim.checkpray.bean.Compromisso;
import br.org.maanaim.checkpray.db.CompromissosDAO;
import br.org.maanaim.checkpray.http.CompromissosTask;
import br.org.maanaim.checkpray.interfaces.Splash;

/**
 * Created by Silvinho Cedrim on 11/07/2017.
 */

public class SplashCheckPrayFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Compromisso>> {


    // Timer da splash screen
    private static int SPLASH_TIME_OUT = 3000;

    List<Compromisso> mCompromissos;

    LoaderManager mLoader;

    CompromissosDAO mDAO;

    public ProgressBar mProgressBar;

    public SplashCheckPrayFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDAO = new CompromissosDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View layout = inflater.inflate(R.layout.splash_check_pray_fragment, container, false);

        mProgressBar = (ProgressBar) layout.findViewById(R.id.progressBar);
        mProgressBar.getProgressDrawable().setColorFilter(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);

        if (!mDAO.existeCompromissos()) {
            mLoader = getLoaderManager();
            mLoader.initLoader(0, null, this);
        }

//        mDAO.delete();
//        MySaveSharedPreference.clearSharedPreference(getActivity());

        ((Splash) getActivity()).redirect();

        return layout;
    }

    @Override
    public Loader<List<Compromisso>> onCreateLoader(int i, Bundle bundle) {
        return new CompromissosTask(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Compromisso>> loader, List<Compromisso> data) {
        if (data != null) {
            mCompromissos = data;
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Compromisso>> loader) {

    }

}
