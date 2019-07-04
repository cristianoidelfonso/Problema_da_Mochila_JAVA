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
public class Objetos {
    
    private int id;
    private float peso;
    private float valor;

    public Objetos() {
    }

    public Objetos(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
    
    
    public void println(){
        System.out.println("Onjeto : " + this.getId()  + "-->  " +   
                           "Peso : " + this.getPeso() + "   |   " + 
                           "Valor: " + this.getValor()
        );
    }
    
}
