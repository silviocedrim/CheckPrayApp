package br.org.maanaim.checkpray.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import br.org.maanaim.checkpray.R;
import br.org.maanaim.checkpray.bean.Compromisso;
import br.org.maanaim.checkpray.bean.CumprimentoCompromisso;
import br.org.maanaim.checkpray.bean.ListCompromissos;

/**
 * Created by Silvinho Cedrim on 06/07/2017.
 */

public class CompromissoAdapter extends ArrayAdapter<CumprimentoCompromisso> {

    boolean[] check;
    public CompromissoAdapter(Context context, List<CumprimentoCompromisso> compromissos) {
        super(context, 0, compromissos);
        check = getChecks(compromissos);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final CumprimentoCompromisso compromisso = getItem(position);

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_compromisso, null);
            holder = new ViewHolder();
            holder.aSwitchCompromisso = (Switch) convertView.findViewById(R.id.switch_compromisso);
            holder.txtTipo = (TextView) convertView.findViewById(R.id.txt_tipo);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.aSwitchCompromisso.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                check[position] = b;
                compromisso.getCompromisso().setCheck(b);
            }
        });

        holder.aSwitchCompromisso.setText(compromisso.getCompromisso().getNome());
        holder.aSwitchCompromisso.setChecked(check[position]);
        holder.txtTipo.setText(compromisso.getCompromisso().getTipo());

        return convertView;
    }

    boolean[] getChecks(List<CumprimentoCompromisso> compromissos){
        boolean[] check = new boolean[compromissos.size()];
        int i = 0;
        for(CumprimentoCompromisso c : compromissos){
            check[i] = c.getCompromisso().isCheck();
            i++;
        }
        return check;
    }

    static class ViewHolder {
        Switch aSwitchCompromisso;
        TextView txtTipo;
    }
}
