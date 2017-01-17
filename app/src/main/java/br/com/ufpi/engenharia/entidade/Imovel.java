package br.com.ufpi.engenharia.entidade;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Nei on 13/12/2016.
 */
public class Imovel {

    private double valor;
    private String endereco;
    private String nome;
   // private String corretor;
    //private LatLng local;

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public boolean isAlugado() {
        return alugado;
    }

    public void setAlugado(boolean alugado) {
        this.alugado = alugado;
    }

    private boolean alugado;

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
