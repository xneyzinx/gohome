package br.com.ufpi.engenharia.controle;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import br.com.ufpi.engenharia.DAOs.ImovelDAO;
import br.com.ufpi.engenharia.ListaImoveisActivity;
import br.com.ufpi.engenharia.entidade.Imovel;

/**
 * Created by Nei on 13/12/2016.
 */
public class ControleImovel {

    List<Imovel> imoveis;
    Context context;


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
     * Em desenvolvimento: retorna todos os imóveis cadastrados pelo usuário no servidor Firebase.
     * @param context
     * @param mDatabase
     * @param mUserId
     * @return
     */
    public List<Imovel> buscaImoveisFirebase(Context context, DatabaseReference mDatabase, final String mUserId) {

        // Get a reference to our
        mDatabase = mDatabase.getDatabase().getReference("users/" + mUserId + "/imoveis");

// Attach a listener to read the data at our posts reference

       /* mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Imovel imovel = dataSnapshot.getValue(Imovel.class);
                imoveis.add(imovel);
                System.out.println(imovel);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });*/


       /*mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("Count " ,""+snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Imovel imovel = postSnapshot.getValue(Imovel.class);
                    imoveis.add(imovel);
                    //Log.e("Get Data", roupa.<YourMethod>());
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " );
            }
        });*/

        return imoveis;

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
}
