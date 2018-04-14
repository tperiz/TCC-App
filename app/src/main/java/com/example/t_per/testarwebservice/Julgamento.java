package com.example.t_per.testarwebservice;

public class Julgamento {

    private double auto_valor_maximo;
    private double indice_coerencia;
    private double razao_coerencia;

    public Julgamento() {
    }

    public Julgamento(double auto_valor_maximo, double indice_coerencia, double razao_coerencia) {
        this.auto_valor_maximo = auto_valor_maximo;
        this.indice_coerencia = indice_coerencia;
        this.razao_coerencia = razao_coerencia;
    }

    /**
     * @return the auto_valor_maximo
     */
    public double getAuto_valor_maximo() {
        return auto_valor_maximo;
    }

    /**
     * @param auto_valor_maximo the auto_valor_maximo to set
     */
    public void setAuto_valor_maximo(double auto_valor_maximo) {
        this.auto_valor_maximo = auto_valor_maximo;
    }

    /**
     * @return the indice_coerencia
     */
    public double getIndice_coerencia() {
        return indice_coerencia;
    }

    /**
     * @param indice_coerencia the indice_coerencia to set
     */
    public void setIndice_coerencia(double indice_coerencia) {
        this.indice_coerencia = indice_coerencia;
    }

    /**
     * @return the razao_coerencia
     */
    public double getRazao_coerencia() {
        return razao_coerencia;
    }

    /**
     * @param razao_coerencia the razao_coerencia to set
     */
    public void setRazao_coerencia(double razao_coerencia) {
        this.razao_coerencia = razao_coerencia;
    }

}
