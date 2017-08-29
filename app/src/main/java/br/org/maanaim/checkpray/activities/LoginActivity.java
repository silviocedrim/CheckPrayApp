package br.org.maanaim.checkpray.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.org.maanaim.checkpray.R;
import br.org.maanaim.checkpray.SharedPreference.MySaveSharedPreference;
import br.org.maanaim.checkpray.bean.Usuario;
import br.org.maanaim.checkpray.db.CompromissosDAO;
import br.org.maanaim.checkpray.db.UsuarioDAO;
import br.org.maanaim.checkpray.http.DBServConnect;
import br.org.maanaim.checkpray.util.Constantes;
import br.org.maanaim.checkpray.util.Validator;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.bntLogin)
    CircularProgressButton mBntLogin;

    @BindView(R.id.link_cadastrar)
    View mLinkCadastrar;

    @BindView(R.id.edt_email)
    EditText mEdtEmail;

    @BindView(R.id.edt_senha)
    EditText mEdtSenha;

    UsuarioDAO mDAO;

    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mDAO = new UsuarioDAO(this);

        mBntLogin.setOnClickListener(new BotaoLogin());

        mLinkCadastrar.setOnClickListener(new LinkCadastrar());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constantes.CADASTRAR_USUARIO) {
            if (resultCode == RESULT_OK) {
                Usuario usuario = (Usuario) data.getSerializableExtra(Constantes.USUARIO_CADASTRADO);
                mEdtEmail.setText(usuario.getEmail());
            }
        }

    }

    private void initAsyncTask(Usuario usuario){
        TaskLogin task = new TaskLogin();
        task.execute(usuario);
    }

    private class TaskLogin extends AsyncTask<Usuario, Void, Usuario> {

        @Override
        protected void onPreExecute() {
            mBntLogin.startAnimation();
        }

        @Override
        protected Usuario doInBackground(Usuario... params) {

            DBServConnect dbServConnect = new DBServConnect();
            Usuario usuario = dbServConnect.login(params[0]);
            return usuario;
        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            if(usuario.getError() == "false"){

                Toast.makeText(LoginActivity.this, "Bem vindo, " + usuario.getNome(), Toast.LENGTH_LONG).show();

                MySaveSharedPreference.setUserId(LoginActivity.this, usuario.getId());
                MySaveSharedPreference.setUserName(LoginActivity.this, usuario.getNome());

                if(!mDAO.existeUsuario(usuario)) {
                    mDAO.insert(usuario);
                }

                Intent it = new Intent(LoginActivity.this, CompromissosActivity.class);
                startActivity(it);
                mBntLogin.stopAnimation();

            }else{
                Toast.makeText(LoginActivity.this, "Email ou senha inválidos!", Toast.LENGTH_LONG).show();

                mEdtEmail.setError("Email inválido");
                mEdtEmail.setFocusable(true);
                mEdtEmail.requestFocus();

                mEdtSenha.setError("Senha inválida");
                mEdtSenha.setFocusable(true);
                mEdtSenha.requestFocus();
            }
        }
    }

    class BotaoLogin implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            Validator.validateNotNull(mEdtSenha, "Preencha o campo Senha");

            boolean email_valido = Validator.validateEmail(mEdtEmail.getText().toString());

            if (!email_valido) {
                mEdtEmail.setError("Email inválido");
                mEdtEmail.setFocusable(true);
                mEdtEmail.requestFocus();
            }

            String email = mEdtEmail.getText().toString();
            String senha = mEdtSenha.getText().toString();

            usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setSenha(senha);

            initAsyncTask(usuario);

        }

    }



    class LinkCadastrar implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent it = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
            startActivityForResult(it, Constantes.CADASTRAR_USUARIO);
        }
    }
}
