package br.com.ufpi.engenharia.controle;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.ufpi.engenharia.DAOs.ImovelDAO;
import br.com.ufpi.engenharia.ListaImoveisActivity;
import br.com.ufpi.engenharia.ResultadosBuscaActivity;
import br.com.ufpi.engenharia.entidade.Imovel;

/**
 * Created by Nei on 13/12/2016.
 */
public class ControleImovel {

    List<Imovel> imoveis;
    Context context;
    ArrayList<Imovel> i = new ArrayList<>();


    public ControleImovel(Context context){
        this.context = context;
    }

    /***
     * Lista todos os imóveis cadastrados pelo usuário
     * @return imoveis
     */
    public List<Imovel> buscarImovel(){
        ImovelDAO dao = new ImovelDAO(context);
        imoveis = dao.buscaImoveis();
        dao.close();
        return imoveis;
    }

    /***
     * Busca os imoveis de acordo com os parametros passados
     * @param valorMaximo
     * @param quantidadeDeQuartos
     * @param bairro
     * @return
     */
    public List<Imovel> buscarImovel(int valorMaximo, int quantidadeDeQuartos, String bairro){
        ImovelDAO dao = new ImovelDAO(context);
        imoveis = dao.buscaImoveisPorValorQuantidadeDeQuartosBairro(valorMaximo, quantidadeDeQuartos, bairro);
        dao.close();
        return imoveis;
    }


    /***
     * Busca imóveis até um valor máximo limite
     * @param valorMaximo
     * @return
     */
    public List<Imovel> buscarImovel(int valorMaximo){
        ImovelDAO dao = new ImovelDAO(context);
        imoveis = dao.buscaImoveisPorValor(valorMaximo);
        dao.close();
        return imoveis;
    }


    /***
     * Busca imóveis por valor e quantidade de quartos
     * @param valorMaximo
     * @param quantidadeDeQuartos
     * @return
     */
    public List<Imovel> buscarImovel(int valorMaximo, int quantidadeDeQuartos){
        ImovelDAO dao = new ImovelDAO(context);
        imoveis = dao.buscaImoveisPorValorEQuartos(valorMaximo, quantidadeDeQuartos);
        dao.close();
        return imoveis;
    }


    /***
     * Busca imóveis por e bairro
     * @param valorMaximo
     * @param bairro
     * @return
     */
    public List<Imovel> buscarImovel(int valorMaximo, String bairro){
        ImovelDAO dao = new ImovelDAO(context);
        imoveis = dao.buscaImoveisPorValorEBairro(valorMaximo, bairro);
        dao.close();
        return imoveis;
    }


    /***
     * Adiciona um imóvel ao usuário, tanto no banco local como no firebase
     * @param imovel
     * @param mDatabase
     * @param mUserId
     */
    public void addImovel(Imovel imovel, DatabaseReference mDatabase, String mUserId) {

        mDatabase.child("users").child(mUserId).child("imoveis").setValue(imovel);
        ImovelDAO dao = new ImovelDAO(context);
        dao.insere(imovel);
        dao.close();

    }

    // PEGA SO 1 IMOVEL
    /*
    public Imovel getImovelFirebase(DatabaseReference mDatabase, final String mUserId){
        final Imovel[] imovel = {new Imovel()};
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imovel[0] = dataSnapshot.child("users").child(mUserId).child("imoveis").getValue(Imovel.class);
                if(imovel[0] ==null)
                    return;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return imovel[0];
    }
    */

    /***
     * Adiciona um imóvel ao usuário no banco local
     *
     */
    public void addImovel(Imovel imovel) {

        //mDatabase.child("imoveis").push().setValue(imovel);

        ImovelDAO dao = new ImovelDAO(context);
        dao.insere(imovel);
        dao.close();

    }

    // PEGA SO 1 IMOVEL
    /*
    public Imovel getImovelFirebase(DatabaseReference mDatabase, final String mUserId){
        final Imovel[] imovel = {new Imovel()};
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imovel[0] = dataSnapshot.child("users").child(mUserId).child("imoveis").getValue(Imovel.class);
                if(imovel[0] ==null)
                    return;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return imovel[0];
    }
    */

    public void retrieveDados(DataSnapshot dataSnapshot){

        i.clear();

        for(DataSnapshot ds : dataSnapshot.getChildren()){
            Imovel im = ds.getValue(Imovel.class);
            i.add(im);
        }
    }
    /***
     * Em desenvolvimento: retorna todos os imóveis cadastrados pelo usuário no servidor Firebase.
     * @param mDatabase
     * @param mUserId
     * @return
     */
    public List<Imovel> buscaImoveisFirebase(DatabaseReference mDatabase, final String mUserId) {


        final List<Imovel> i = new ArrayList<Imovel>();

        // Get a reference to our
        mDatabase = mDatabase.getDatabase().getReference();

        mDatabase.child("imoveis").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children) {
                    Imovel im = child.getValue(Imovel.class);
                    i.add(im);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return i;

    }

    /***
     * Exclui um imóvel do usuário.
     * TODO: Excluir imóvel do servidor do Firebase
     * @param imovel
     * @param listaImoveisActivity
     */
    public void deleta(Imovel imovel, ListaImoveisActivity listaImoveisActivity) {
        ImovelDAO dao = new ImovelDAO(context);
        dao.deleta(imovel);
        dao.close();
    }

    /*
    * Método de busca para apenas 1 imóvel
    * @param nomeImovel
    * @return Imovel
    * */
    public Imovel busca(String nomeImovel){
        ImovelDAO dao = new ImovelDAO(context);
        Imovel i = new Imovel();
        i = dao.buscaImovel(nomeImovel);
        dao.close();

        return i;
    }

    /***
     * Altera dados de um imóvel previamente cadastrado
     * TODO: alterar também no servidor Firebase
     * @param imovel
     * @param listaImoveisActivity
     */
    public void altera(Imovel imovel, ListaImoveisActivity listaImoveisActivity) {
        ImovelDAO dao = new ImovelDAO(context);
        dao.altera(imovel);
        dao.close();
    }

    public void aluga(Imovel imovel, ResultadosBuscaActivity listaImoveisActivity) {
        ImovelDAO dao = new ImovelDAO(context);
        dao.aluga(imovel);
        dao.close();
    }


}
