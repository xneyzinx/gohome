package br.com.ufpi.engenharia;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ProgressBar;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button signOut, mostrarNoMapa, listarImoveis;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private Button cadastrarImovel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFirebase();


        signOutButton();
        cadastrarImovelButton();
        listarImoveisButton();

        mostrarNoMapaButton();

    }

    private void listarImoveisButton() {

        listarImoveis = (Button) findViewById(R.id.listar_imoveis);
        listarImoveis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListaImoveisActivity.class));
            }
        });

    }

    private void cadastrarImovelButton() {
        cadastrarImovel = (Button) findViewById(R.id.cadastrar_imovel);
        cadastrarImovel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CadastrarImovelActivity.class));
            }
        });
    }

    private void mostrarNoMapaButton() {
        mostrarNoMapa = (Button) findViewById(R.id.mostrar_imoveis_no_mapa);
        mostrarNoMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsLocationActivity.class));
            }
        });
    }

    private void signOutButton() {
        signOut = (Button) findViewById(R.id.sign_out);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
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
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
