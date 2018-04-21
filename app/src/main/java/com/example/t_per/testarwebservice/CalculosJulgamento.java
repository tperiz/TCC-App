package com.example.t_per.testarwebservice;

import java.sql.SQLException;

public class CalculosJulgamento {
    private double matJulg[][];
    private int sizeCri;
    private double[] autovetor;
    private double[] autoVetorNormal;
    private Julgamento julg;
    UsuarioDAO dao;

    public CalculosJulgamento(double matJulg[][], int sizeCri, Julgamento julgNovo) throws SQLException, ClassNotFoundException {
        this.matJulg = matJulg;
        this.sizeCri = sizeCri;
        this.autovetor = new double[sizeCri];
        this.autoVetorNormal = new double[sizeCri];
        this.julg = julgNovo;
        this.dao = new UsuarioDAO();
    }

    /* Neste método é calculada a média geométrica.*/
    public void calcularAutoVetorGeometrica() throws SQLException {

        double expoente = (1 / (double) sizeCri);
        double mult = 1;
        double result = 1;
        for (int l = 0; l < matJulg.length; l++) {
            for (int m = 0; m < matJulg.length; m++) {
                mult *= matJulg[l][m];
            }
            //System.out.println(" result Mult " + mult);

            result = Math.pow(mult, expoente);
            //System.out.println("result - " + result);
            autovetor[l] = result;
            mult = 1;
        }

        System.out.println(" autovetor");
        for (int i = 0; i < autovetor.length; i++) {
            System.out.println(autovetor[i]);
        }
        normalizarAutoVetor();
    }

    /* Neste método é calculada a média geométrica.*/
    public void calcularAutoVetorAritmetica() throws SQLException {

        double mult = 1;
        double result = 1;
        for (int l = 0; l < matJulg.length; l++) {
            for (int m = 0; m < matJulg.length; m++) {
                mult *= matJulg[l][m];
            }
            //System.out.println(" result Mult " + mult);
            result = mult / sizeCri;
            //System.out.println("result - " + result);
            autovetor[l] = result;
            mult = 1;
        }

        System.out.println(" autovetor");
        for (int i = 0; i < autovetor.length; i++) {
            System.out.println(autovetor[i]);
        }
        normalizarAutoVetor();
    }

    public void normalizarAutoVetor() throws SQLException {

        double raiz = 0;
        double soma = 0;
        for (int i = 0; i < autovetor.length; i++) {
            soma = soma + autovetor[i];
        }
        /*System.out.println(" soma " + soma);*/
        System.out.println(" auto vetor normalizado");
        for (int i = 0; i < autovetor.length; i++) {
            autoVetorNormal[i] = autovetor[i] / soma;
            System.out.println(autoVetorNormal[i]);
        }
        calculatarAutoValor();
    }

    public void calculatarAutoValor() throws SQLException {
        double mult = 1;
        double soma = 0;
        double autoValorMaximo = 0;
        double aux = 0;
        for (int i = 0; i < autovetor.length; i++) {
            aux = autovetor[i] + autoVetorNormal[i];
            autoValorMaximo += aux;
        }
        System.out.println("auto valor maximo " + autoValorMaximo);
        julg.setAuto_valor_maximo(autoValorMaximo);
        indiceCoerencia(autoValorMaximo);
    }

    public void indiceCoerencia(double autoValorMaximo) throws SQLException {
        double indice = (autoValorMaximo - sizeCri) / (sizeCri - 1);
        julg.setIndice_coerencia(indice);
        System.out.println(" indice \n" + indice);
        razaoCoerencia(indice);
    }

    // razao < 15% - coerentes
    public void razaoCoerencia(double indice) throws SQLException {

        double[] ir = {0, 0, 0.58, 0.9, 1.12, 1.24, 1.32, 1.41, 1.45, 1.49};
        double razao = indice / ir[sizeCri - 1];
        julg.setRazao_coerencia(razao);
        System.out.println(" razao \n" + razao);
        System.out.println(" ========================================= ");
        salvar();
    }

    public void salvar() throws SQLException {
        dao.inserirJulgamento(julg);
    }
}
