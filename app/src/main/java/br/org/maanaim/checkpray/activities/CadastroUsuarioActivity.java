package br.org.maanaim.checkpray.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import br.org.maanaim.checkpray.R;
import br.org.maanaim.checkpray.bean.Usuario;
import br.org.maanaim.checkpray.db.UsuarioDAO;
import br.org.maanaim.checkpray.http.DBServConnect;
import br.org.maanaim.checkpray.util.Constantes;
import br.org.maanaim.checkpray.util.GrauPertenca;
import br.org.maanaim.checkpray.util.Validator;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CadastroUsuarioActivity extends AppCompatActivity{

    @BindView(R.id.grau_pertenca)
    Spinner mSpinnerGrauPertenca;

    @BindView(R.id.edt_nome_cadastro)
    EditText mEdtNome;

    @BindView(R.id.edt_email_cadastro)
    EditText mEdtEmail;

    @BindView(R.id.edt_senha_cadastro)
    EditText mEdtSenha;

    @BindView(R.id.btnCadastrarUsuario)
    CircularProgressButton mBtnCadastrarUsuario;

    @BindView(R.id.toolbar_cadastro)
    Toolbar mToolbar;

    Usuario usuario;

    UsuarioDAO mDao;

    GrauPertenca mGrauPertenca;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        ButterKnife.bind(this);

        mDao = new UsuarioDAO(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addItensSpinnerGrauPerteca();
        mSpinnerGrauPertenca.setOnItemSelectedListener(new GrauPertencaSelector());
        mBtnCadastrarUsuario.setOnClickListener(new BotaoCadastrar());

    }

    public void addItensSpinnerGrauPerteca(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spiner_item, getResources().getStringArray(R.array.grau_pertenca));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerGrauPertenca.getBackground().setColorFilter(ContextCompat.getColor(this, R.color.white_color), PorterDuff.Mode.SRC_ATOP);
        mSpinnerGrauPertenca.setAdapter(adapter);
    }

    class GrauPertencaSelector implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            mSpinnerGrauPertenca.setSelection(position);
            mGrauPertenca = GrauPertenca.getValue(position);
            if(position != 0){
                ((TextView)view).setTextColor(Color.BLACK);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            adapterView.setSelection(0);
        }
    }

    private class TaskCadastrarUsuario extends AsyncTask<Usuario, Void, Usuario> {

        @Override
        protected void onPreExecute() {
            mBtnCadastrarUsuario.startAnimation();
        }

        @Override
        protected Usuario doInBackground(Usuario... params) {

            DBServConnect dbServConnect = new DBServConnect();
            Usuario usuario = dbServConnect.cadastrar(params[0]);
            return usuario;
        }

        @Override
        protected void onPostExecute(Usuario usuario) {

            if(usuario.getError() == "false"){

                if(!mDao.existeUsuario(usuario)) {
                    mDao.insert(usuario);
                }

                Toast.makeText(CadastroUsuarioActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                getIntent().putExtra(Constantes.USUARIO_CADASTRADO, usuario);
                setResult(RESULT_OK, getIntent());

                finish();
                mBtnCadastrarUsuario.stopAnimation();
            }else{
                mBtnCadastrarUsuario.revertAnimation(new OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {
                        mBtnCadastrarUsuario.setBackground(ContextCompat.getDrawable(CadastroUsuarioActivity.this, R.drawable.button_border_shape));
                    }
                });
                mEdtEmail.setError("Email já cadastrado");
                mEdtEmail.setFocusable(true);
                mEdtEmail.requestFocus();
                Toast.makeText(CadastroUsuarioActivity.this, "Usuário com esse e-mail já existe!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initAsyncTask(Usuario usuario){
        TaskCadastrarUsuario taskCadastrarUsuario = new TaskCadastrarUsuario();
        taskCadastrarUsuario.execute(usuario);
    }


    class BotaoCadastrar implements View.OnClickListener{
        @Override
        public void onClick(View view) {

            Validator.validateNotNull(mEdtNome,"Preencha o campo nome");
            Validator.validateNotNull(mEdtSenha,"Preencha o campo Senha");

            boolean email_valido = Validator.validateEmail(mEdtEmail.getText().toString());

            if(!email_valido){
                mEdtEmail.setError("Preencha o campo email");
                mEdtEmail.setFocusable(true);
                mEdtEmail.requestFocus();
            }

            boolean grau_mudado = !mSpinnerGrauPertenca.getSelectedItem().toString().equals(getString(R.string.spinner_grau_pertenca));
            if(!grau_mudado){
                ((TextView)mSpinnerGrauPertenca.getSelectedView()).setError("Selecione o Grau de Pertença");
                mSpinnerGrauPertenca.setFocusable(true);
                mSpinnerGrauPertenca.requestFocus();
            }

            if(!grau_mudado || !email_valido){
                return;
            }

            String nome = mEdtNome.getText().toString();
            String email = mEdtEmail.getText().toString();
            String senha = mEdtSenha.getText().toString();
            String grauPertenca = mGrauPertenca.toString();

            usuario = new Usuario(nome, email, senha, grauPertenca);

            initAsyncTask(usuario);





        }
    }

}
