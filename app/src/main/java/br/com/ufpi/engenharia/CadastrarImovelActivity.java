package br.com.ufpi.engenharia;

import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

import br.com.ufpi.engenharia.controle.ControleImovel;
import br.com.ufpi.engenharia.entidade.Imovel;

//import com.google.android.gms.common


/*
* Método de cadastrar Imovel
* */
public class CadastrarImovelActivity extends AppCompatActivity {

    private String nomeImovel;
    private double valor;
    private String endereco;
    private String bairro;
    private int quantidade_de_quartos;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private String mUserId;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Imovel imovel = new Imovel();
    ControleImovel controleImovel = new ControleImovel(CadastrarImovelActivity.this);
    TextView latLongTV;
    static String ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_imovel);

        setFirebase();
        //pegaDados();
        botaoLocalizacao();
        botaoSalvar();

    }


    private void pegaDados() {
        //imovel = ControleImovel.getImovel(mDatabase, mUserId);
        ((EditText) findViewById(R.id.nome_imovel)).setText(imovel.getNome());
        //((EditText) findViewById(R.id.valor)).setText(imovel.getValor());
        ((EditText) findViewById(R.id.endereco)).setText(imovel.getEndereco());
        //((EditText) findViewById(R.id.nome_loja)).setEnabled(false);
        /*
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loja = dataSnapshot.child("users").child(mUserId).child("loja").getValue(Loja.class);
                if(loja==null)
                    return;
                ((EditText) findViewById(R.id.nome_loja)).setText(loja.getNome());
                ((EditText) findViewById(R.id.telefones)).setText(loja.getTelefone());
                ((EditText) findViewById(R.id.endereco)).setText(loja.getEndereco());
                ((EditText) findViewById(R.id.nome_loja)).setEnabled(false);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */

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
                    startActivity(new Intent(CadastrarImovelActivity.this, MainActivity.class));
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

    private void botaoLocalizacao() {
        Button local = (Button) findViewById(R.id.local);
        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CadastrarImovelActivity.this, MapsTesteActivity.class));
            }
        });
    }


    private void botaoSalvar(){
        Button salvar = (Button) findViewById(R.id.salvar_loja);
        //if(loja!=null)
        //  salvar.setEnabled(false);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nomeImovel = ((EditText) findViewById(R.id.nome_imovel)).getText().toString();
                valor = Double.parseDouble(((EditText) findViewById(R.id.valor)).getText().toString());
                endereco = ((EditText) findViewById(R.id.endereco)).getText().toString();
                bairro = ((EditText) findViewById(R.id.bairro)).getText().toString();
                quantidade_de_quartos = Integer.parseInt(((EditText) findViewById(R.id.quantidade_de_quartos)).getText().toString());


                /*
                * Busca das coordenadas de latitude e longitude
                * a partir do endereço dado no cadastro
                * */
                double latitude = 0;
                double longitude = 0;
                Geocoder geocoder = new Geocoder(getApplicationContext());
                List<android.location.Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocationName(endereco, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(addresses.size() > 0) {
                    latitude= addresses.get(0).getLatitude();
                    longitude= addresses.get(0).getLongitude();
                }


                Imovel imovel = new Imovel();
                imovel.setNome(nomeImovel);
                imovel.setValor(valor);
                imovel.setEndereco(endereco);
                imovel.setBairro(bairro);
                imovel.setQuantidade_de_quartos(quantidade_de_quartos);

                imovel.setLatA(latitude);
                imovel.setLongA(longitude);

                controleImovel.addImovel(imovel);
                Toast.makeText(getApplicationContext(),"Imóvel: " + imovel.getNome() + " Salvo!", Toast.LENGTH_SHORT).show();

                finish();

            }
        });
    }

}