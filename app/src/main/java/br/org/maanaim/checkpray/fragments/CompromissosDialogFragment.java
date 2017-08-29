package br.org.maanaim.checkpray.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.org.maanaim.checkpray.R;
import br.org.maanaim.checkpray.SharedPreference.MySaveSharedPreference;
import br.org.maanaim.checkpray.adapter.CompromissoAdapter;
import br.org.maanaim.checkpray.bean.CumprimentoCompromisso;
import br.org.maanaim.checkpray.bean.Usuario;
import br.org.maanaim.checkpray.db.CompromissosDAO;

/**
 * Created by Silvinho Cedrim on 07/07/2017.
 */

public class CompromissosDialogFragment extends DialogFragment {


    ListView mListViewCompromissos;

    List<CumprimentoCompromisso> mListaCompromissosCumpridos;

    TextView txt_data;

    public static final String EXTRA_DATA = "EXTRA_DATA";

    SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");

    CompromissosDAO mDAO;

    Usuario mUsuario;

    CalendarDay day;

    CompromissoAdapter mAdapter;

    View mLayout;

    public CompromissosDialogFragment() {
    }


    public static CompromissosDialogFragment novaInstancia(CalendarDay date){
        Bundle parametros = new Bundle();
        parametros.putParcelable(EXTRA_DATA, date);
        CompromissosDialogFragment fragment = new CompromissosDialogFragment();
        fragment.setArguments(parametros);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDAO = new CompromissosDAO(getActivity());
        mUsuario = new Usuario(MySaveSharedPreference.getUserId(getActivity()));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        mLayout = inflater.inflate(R.layout.dialog_compromissos, null);

        day = (CalendarDay)getArguments().getParcelable(EXTRA_DATA);
        txt_data = (TextView)mLayout.findViewById(R.id.txt_data_compromissos);
        txt_data.setText(dt.format(day.getDate()));

        mListViewCompromissos = (ListView) mLayout.findViewById(R.id.list_compromissos);
        mListaCompromissosCumpridos = new ArrayList<CumprimentoCompromisso>();
        mListaCompromissosCumpridos.clear();

        mListaCompromissosCumpridos = mDAO.listarCompromissosCumpridosPorDia(day.getDate(), mUsuario);
        if(mListaCompromissosCumpridos.isEmpty()){
            mListaCompromissosCumpridos = mDAO.listarCompromissos();
        }

        mAdapter = new CompromissoAdapter(getActivity(), mListaCompromissosCumpridos);
        mListViewCompromissos.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(mLayout);
        builder.setPositiveButton(R.string.bnt_save, new BotaoSalvar());
        builder.setNegativeButton(R.string.bnt_cancel, new BotaoCancelar());

        AlertDialog alertDialog = builder.create();
        return alertDialog;
    }



    class BotaoSalvar implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            mDAO.marcarCumprimento(mListaCompromissosCumpridos, mUsuario, day.getDate());
            mListViewCompromissos.destroyDrawingCache();
            mAdapter.clear();
            dialogInterface.dismiss();
        }
    }

    class BotaoCancelar implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }
}
