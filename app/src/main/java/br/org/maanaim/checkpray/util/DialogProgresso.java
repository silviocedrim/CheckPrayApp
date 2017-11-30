package br.org.maanaim.checkpray.util;

import android.app.ProgressDialog;
import android.content.Context;

import br.org.maanaim.checkpray.R;

/**
 * Created by Silvio Cedrim on 29/11/2017.
 */

public class DialogProgresso extends ProgressDialog{

    public DialogProgresso(Context context) {
        super(context, R.style.MyDialogTheme);
    }

    public void showDialog() {
        if (!isShowing())
            show();
    }

    public void hideDialog() {
        if (isShowing())
            dismiss();
    }


}
