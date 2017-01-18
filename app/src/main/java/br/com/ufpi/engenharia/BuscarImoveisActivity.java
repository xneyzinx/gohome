package br.com.ufpi.engenharia;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.ufpi.engenharia.controle.ControleImovel;
import br.com.ufpi.engenharia.entidade.Imovel;

//import com.google.android.gms.common


public class BuscarImoveisActivity extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private String mUserId;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Imovel imovel = new Imovel();
    ControleImovel controleImovel = new ControleImovel(BuscarImoveisActivity.this);
    private int valorMaximo;
    private  int quantidadeDeQuartos;
    private String bairro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_imoveis);

        setFirebase();
        botaoBuscar();

    }


    private void setFirebase() {
        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(BuscarImoveisActivity.this, MainActivity.class));
                    finish();
                }
            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (user == null) {
            // Not logged in, launch the Log In activity
            //loadLogInView();
        } else {
            mUserId = user.getUid();
        }

    }

    private void botaoBuscar(){
        Button buscar = (Button) findViewById(R.id.buscar_imoveis);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    quantidadeDeQuartos = Integer.parseInt(((EditText) findViewById(R.id.quantidade_de_quartos_busca)).getText().toString());
                }catch (NumberFormatException e){
                    quantidadeDeQuartos = 999;
                }
                try {
                    valorMaximo = Integer.parseInt(((EditText) findViewById(R.id.valor_maximo_busca)).getText().toString());
                }catch (NumberFormatException e){
                    valorMaximo = 9999;
                }
                try {
                    bairro = ((EditText) findViewById(R.id.bairro_busca)).getText().toString();
                    int aa = bairro.length();
                }catch (Exception e){
                    Toast.makeText(BuscarImoveisActivity.this, "aaaaaa", Toast.LENGTH_LONG).show();
                    bairro = null;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("quantidadeDeQuartos", quantidadeDeQuartos);
                bundle.putInt("valorMaximo", valorMaximo);
                bundle.putString("bairro", bairro);
                Intent intent = new Intent(BuscarImoveisActivity.this, ResultadosBuscaActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                //finish();
            }
        });
    }

}
