/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JTextArea;
import model.Mochila;

/**
 *
 * @author Idelfonso
 */
public class Control_AG {
    
    private final int valorObj[];
    private final int pesoObj[];
    private ArrayList<String> POPULACAO;
    private final JTextArea console;
    private final Mochila mochila = new Mochila();

    public Control_AG(int[] valor, int[] peso, JTextArea console, float capacidade) {
        this.valorObj = valor;
        this.pesoObj = peso;
        this.console = console;
        
        this.mochila.setCapacidade(capacidade);
    }
    //--------------------------------------------------------------------------
    public void exibePopulacao(ArrayList<String> populacao) {
        this.POPULACAO = populacao;
        console.append("População >> " + populacao + "\n");
    }
    //--------------------------------------------------------------------------
    public boolean validaCromossomo( String cromossomo ) { ///soma dos pesos
        int peso = 0;
        for ( int i = 0 ;  i < cromossomo.length(); i++ ) {
            String aux = String.valueOf( cromossomo.charAt(i) );
            if ( aux.equals("1") ) {
                peso += pesoObj[i];
            }  
        }
        return peso <= mochila.getCapacidade();
    }
    //--------------------------------------------------------------------------
    public float aptidao( String cromossomo ) { //soma dos valores
        float aptidao = -1;
      
        if ( validaCromossomo( cromossomo )) {
            for ( int i = 0 ; i < cromossomo.length(); i++) {
                String aux = String.valueOf( cromossomo.charAt(i));
                if ( aux.equals("1") ) {
                    aptidao += valorObj[i];
                }
                System.out.println("Posição " + i +"  | Valor do bit  " + aux);
            }
        }
        return aptidao;
    }
    //--------------------------------------------------------------------------
     public String crossover(String cromossomo_1, String cromossomo_2) {
        String filho = cromossomo_1.substring( 0, cromossomo_1.length() / 2) + 
                       cromossomo_2.substring(cromossomo_2.length() / 2);
        
        return filho;
    }
    //--------------------------------------------------------------------------
    public String mutacao( String cromossomo ) {
        Random rd = new Random();
        int pos = rd.nextInt(cromossomo.length());
        char[] aux = cromossomo.toCharArray(); 
        for (int i = 0; i < aux.length; i++) {
            if (i == pos) {
                if (aux[pos] == '1') {
                    aux[pos] = '0';
                } else {
                    aux[pos] = '1';
                }
            }
        }
        return String.valueOf(aux);
    }
    //--------------------------------------------------------------------------
    public ArrayList<String> aplicaCrossover( ArrayList<String> idvs ) {
        ArrayList<String> filhos = new ArrayList<>();

        filhos.add(crossover(idvs.get(0), idvs.get(1)));
        filhos.add(crossover(idvs.get(1), idvs.get(2)));
        filhos.add(crossover(idvs.get(3), idvs.get(4)));
        filhos.add(crossover(idvs.get(4), idvs.get(3)));

        console.append("Aplicando Crossover >> " + filhos + "\n");
        
        return filhos;
    }
    //--------------------------------------------------------------------------
    public ArrayList<String> aplicaMutacao(ArrayList<String> idvs) {
        ArrayList<String> mutacao = new ArrayList<>();

        mutacao.add(mutacao(idvs.get(0)));
        mutacao.add(mutacao(idvs.get(1)));
        mutacao.add(mutacao(idvs.get(2)));
        mutacao.add(mutacao(idvs.get(3)));

        console.append("Aplicando Mutação >> " + mutacao + "\n");
        
        return mutacao;
    }
    //--------------------------------------------------------------------------

    /**
     *
     * @return
     */
    public ArrayList<String> selecionados(){
        ArrayList<String> selecao = new ArrayList<>();
        float beneficioTotal = 0;
        float peso = 0;
        for (String individuo : POPULACAO) {
            if ( validaCromossomo( individuo )) {
                char[] a = individuo.toCharArray();
                for (int i = 0; i < a.length; i++) {
                    if( a[i] == 1 ){
                        beneficioTotal += valorObj[i];
                        peso += pesoObj[i];
                        System.out.println("Individuo: " + individuo +
                                "\n |  Beneficio = " + beneficioTotal +
                                "\n | Peso = " + peso );
                    }
                }
                if(peso > mochila.getCapacidade()){
                    System.out.println("Individuo descartado!");
                }else{
                    
                    selecao.add(individuo);
                }  
            }
        } 
        return selecao;
    }
    
    
    public ArrayList<String[]> selecaoPorAptidao() {
        int cont = 0;
        ArrayList<String[]> idvs = new ArrayList<>();
        for ( String idv : POPULACAO ) {
            float apt = aptidao( idv ); 
            
            if ( apt > -1 && apt <= mochila.getCapacidade() ) { // se a aptidão do individuo for maior ou igual a zero
                idvs.add( new String[]{ idv, String.valueOf(apt)});
                console.append("[" + idv + ", " + apt + "] \n");
            } else { 
                cont++;
            }
        }
        //mostra quantos individuos foram descartados por não aptidão
        console.append("\nDescartados >> " + cont + " indivíduos com o peso maior que o suportado pela mochila.\n");
        return idvs;
    }
    //-------------------------------------------------------------------------- 
   /* public ArrayList<String> selecaoPorRoleta( ArrayList<String[]> idvs ) {

        float soma = 0;
        //percorre o ArrayList de cromossomos
        for ( String[] apt : idvs ) {
            //soma onde o valor do bit for igual a 1
            soma += Float.parseFloat( apt[1] );
        }

        //Calcula a % de probabilidade de um idv ser escolhido e imprimi no console  
        console.append("Probabilidade de Escolha >>\n");
        for (String[] apt : idvs) {
            apt[1] = String.valueOf(soma / Float.parseFloat(apt[1]));
            console.append("[" + apt[0] + ", " + apt[1] + "%]\n");
        }

        //Ordena o melhores IDVS de acordo com a probabilidade de ser escolhido
        //seleciona um primeiro cromossomo
        for (int i = 0; i < idvs.size(); i++) {
            String[] a = idvs.get(i);
            //salva o valor da sua probabilidade
            double prob_1 = Double.parseDouble(a[1]);

            //seleciona um segundo cromossomo
            for (int j = i; j < idvs.size(); j++) {
                String[] b = idvs.get(j);
                //salva o valor da sua probabilidade
                double prob_2 = Double.parseDouble(b[1]);

                //compara os valores de probabilidade dos dois cromossomos selecionados
                if (prob_2 > prob_1) {
                    String[] c = idvs.get(j);
                    idvs.remove(j);
                    idvs.add(i, c);
                }
            }
        }

        ArrayList<String> melhores = new ArrayList();

        int cont = 0;
        
        for (String[] aux : idvs) {
            if (cont <= POPULACAO.size() / 2) {
                melhores.add(aux[0]);
                cont++;
            }
        }

        console.append("\nIndividuos Selecionados >> " + melhores + "\n");

        return melhores;
    }*/
    
    /**=========================================================================
     * Método geraNovaPopulacao()
     * 
     * @return 
     */
    public ArrayList<String> geraNovaPopulacao() {

        //ArrayList<String[]> selecao = selecaoPorAptidao();
        
        
        //ArrayList<String> pais = selecaoPorRoleta(selecao);
        
        //Aplica o cross-over nos filhos
        //ArrayList filhos = aplicaCrossover(pais);
        
        //Aplica a mutaçao no filhos
        //ArrayList mutacao = aplicaMutacao(filhos);

        //Cria a nova populacao de inviduos
        ArrayList<String> nova_populacao = selecionados();
        
        //nova_populacao.addAll(pais);
        //nova_populacao.addAll(mutacao);
        
        /*
        for (String pai : pais) {
            if (!nova_populacao.contains(pai)) {
                nova_populacao.add( pai );
            }    
        }
        
        for (Object mut : mutacao) {
            if (!nova_populacao.contains(mut)) {
                nova_populacao.add(mut);
            }
        }
       */
        
        console.append("Nova Populacao >> " + nova_populacao + "\n");

        return nova_populacao;

    }
    
    
    
    
}
