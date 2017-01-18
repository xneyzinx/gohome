package br.com.ufpi.engenharia.entidade;

import java.io.Serializable;

/**
 * Created by Nei on 13/12/2016.
 */
public class Imovel implements Serializable{

    private double valor;
    private String endereco;
    private String nome;
    private double latA;
    private double longA;
    private String bairro;
    private int quantidade_de_quartos;
    // private String corretor;
    //private LatLng local;

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public int getQuantidade_de_quartos() {
        return quantidade_de_quartos;
    }

    public void setQuantidade_de_quartos(int quantidade_de_quartos) {
        this.quantidade_de_quartos = quantidade_de_quartos;
    }

    public double getLatA() {
        return latA;
    }

    public void setLatA(double latA) {
        this.latA = latA;
    }

    public double getLongA() {
        return longA;
    }

    public void setLongA(double longA) {
        this.longA = longA;
    }

    public String getEndereco() { return endereco; }

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
