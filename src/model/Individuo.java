/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Idelfonso
 */
public class Individuo implements Comparable<Individuo>{
    
    private String bin;
    private float peso;
    private float aptidao;
    private float probabilidade;

    public Individuo() {
    }

    public Individuo(String bin, float peso, float aptidao) {
        this.bin = bin;
        this.peso = peso;
        this.aptidao = aptidao;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }
    
    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getAptidao() {
        return aptidao;
    }

    public void setAptidao(float valor) {
        this.aptidao = valor;
    }
    
     public float getProbabilidade() {
        return probabilidade;
    }

    public void setProbabilidade(float probabilidade) {
        this.probabilidade = probabilidade;
    }

    public void println(){
        System.out.println("Binario : " + this.getBin()+ "-->  " +   
                           "Peso : " + this.getPeso() + "   |   " + 
                           "Valor: " + this.getAptidao()
        );
    }
    
    
    @Override
    public int compareTo(Individuo idv) {
        if (this.aptidao > idv.aptidao) {
            return -1;
        }
        if (this.aptidao < idv.aptidao) {
            return 1;
        }
        
        return 0;
    }
}
