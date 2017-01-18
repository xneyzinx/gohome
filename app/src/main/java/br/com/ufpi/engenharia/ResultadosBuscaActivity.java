package br.com.ufpi.engenharia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.ufpi.engenharia.controle.ControleImovel;
import br.com.ufpi.engenharia.entidade.Imovel;

public class ResultadosBuscaActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private DatabaseReference mDatabase;
    private String mUserId;

    private MyCustomAdapter dataAdapter = null;
    private ListView listView = null;
    private List<Imovel> imoveis = null;
    private ControleImovel controleImovel = new ControleImovel(ResultadosBuscaActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_busca);
        setFirebase();
        Bundle bundle = getIntent().getExtras();
        String bairro = bundle.getString("bairro");
        int valorMaximo = bundle.getInt("valorMaximo");
        int quantidadeDeQuartos = bundle.getInt("quantidadeDeQuartos");
        List<Imovel> imoveis = null;


        /*imoveis = controleImovel.buscarImovel(valorMaximo); *//*APAGAR ESTA LINHA, E REMOVER O COMENTÁRIO ABAIXO QUANDO O BANCO
        ESTIVER ATUALIZADO COM OS ATRIBUTOS*/

        if(bairro==null && quantidadeDeQuartos==999) {
            imoveis = controleImovel.buscarImovel(valorMaximo);
            Toast.makeText(ResultadosBuscaActivity.this, "por valor apenas", Toast.LENGTH_LONG).show();
        }

        if(bairro==null && quantidadeDeQuartos!=999) {
            imoveis = controleImovel.buscarImovel(valorMaximo, quantidadeDeQuartos);
            Toast.makeText(ResultadosBuscaActivity.this, "por quantidade de quartos", Toast.LENGTH_LONG).show();
        }

        if(bairro!=null && quantidadeDeQuartos==999) {
            imoveis = controleImovel.buscarImovel(valorMaximo, bairro);

            Toast.makeText(ResultadosBuscaActivity.this, "por bairro e valor", Toast.LENGTH_LONG).show();
        }

        if(bairro!=null && quantidadeDeQuartos!=999) {
            imoveis = controleImovel.buscarImovel(valorMaximo, quantidadeDeQuartos, bairro);

            Toast.makeText(ResultadosBuscaActivity.this, "por quantidade de quartos e bairro", Toast.LENGTH_LONG).show();
        }

        dataAdapter = new MyCustomAdapter(ResultadosBuscaActivity.this,
                R.layout.activity_resultados_busca, imoveis);
        listView = (ListView) findViewById(R.id.list_view_tela_lista_imoveis);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
        registerForContextMenu(listView);
    }

    private class MyCustomAdapter extends ArrayAdapter<Imovel> {
        private ArrayList<Imovel> listaImoveis2;
        public MyCustomAdapter(Context context, int textViewResourceId,
                               List<Imovel> listaImoveis) {
            super(context, textViewResourceId, listaImoveis);
            this.listaImoveis2 = new ArrayList<Imovel>();
            this.listaImoveis2.addAll(listaImoveis);
        }

        public class ViewHolder {

            TextView nome;
            //final ImageView imageView;
            TextView endereco;
            TextView valor;
            TextView quantidadeDeQuartos;
            TextView bairro;
            TextView status;

            public ViewHolder(View view) {

                nome = (TextView) view.findViewById(R.id.lista_nome_imovel);
                //imageView = (ImageView) view.findViewById(R.id.roupa_busca_imagem);
                endereco = (TextView) view.findViewById(R.id.lista_endereco);
                valor = (TextView) view.findViewById(R.id.lista_valor);
                quantidadeDeQuartos = (TextView) view.findViewById(R.id.lista_quantidade_de_quartos);
                bairro = (TextView) view.findViewById(R.id.lista_bairro);
                status = (TextView) view.findViewById(R.id.lista_status);
            }
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View view;
            ViewHolder holder;
            Log.v("ConvertView", String.valueOf(position));

            if(convertView==null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.lista_imoveis_result, null);
                view = convertView;
                holder = new ViewHolder(view);
                view.setTag(holder);
            }
            else{
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            holder.nome.setText(listaImoveis2.get(position).getNome());
            holder.endereco.setText(listaImoveis2.get(position).getEndereco());
            holder.valor.setText( String.valueOf(listaImoveis2.get(position).getValor()));
            holder.bairro.setText( String.valueOf(listaImoveis2.get(position).getBairro()));
            holder.quantidadeDeQuartos.setText( String.valueOf(listaImoveis2.get(position).getQuantidade_de_quartos()));
            holder.status.setText(listaImoveis2.get(position).isAlugado()? "Alugado":"Disponível");
            /*
            Bitmap bitmap = null;
            try {
                byte[] fotoEmBytes = Base64.decode(listaRoupas2.get(position).getFoto(), Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(fotoEmBytes, 0, fotoEmBytes.length);
            }catch(Exception e){

            }
            if(bitmap!=null)
                holder.imageView.setImageBitmap(bitmap);
            */

            //holder.imageView.setImageResource(listaRoupas2.get(position).getFoto());
            return view;
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuItem alugar = menu.add("Alugar");
        alugar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Imovel imovel = (Imovel) listView.getItemAtPosition(info.position);
                controleImovel.aluga(imovel, ResultadosBuscaActivity.this);
                imoveis = controleImovel.buscarImovel();
                //dataAdapter = new MyCustomAdapter(ResultadosBuscaActivity.this,
                //        R.layout.lista_imoveis_result, imoveis);
                //listView = (ListView) findViewById(R.id.list_view_tela_lista_imoveis);
                // Assign adapter to ListView
                //listView.setAdapter(dataAdapter);
                Toast.makeText(ResultadosBuscaActivity.this,"Alugado!", Toast.LENGTH_LONG).show();
                finish();
                return false;
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
                    startActivity(new Intent(ResultadosBuscaActivity.this, MainActivity.class));
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

}
