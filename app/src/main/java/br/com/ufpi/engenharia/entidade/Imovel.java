package br.com.ufpi.engenharia.entidade;

/**
 * Created by Nei on 13/12/2016.
 */
public class Imovel {

    private String nome;
    private String corretor;

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getCorretor() {
        return corretor;
    }

    public void setCorretor(String corretor) {
        this.corretor = corretor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    private double valor;

}
