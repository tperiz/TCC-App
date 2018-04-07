package com.example.t_per.testarwebservice;

import java.util.ArrayList;

/**
 * Created by thi on 09/01/18.
 */

public class Criterio {

    private int idcirterio;
    private int hierarquia;
    private String nome;
    private String descricao;
    private ArrayList<SubCriterio> sub = new ArrayList<SubCriterio>();
    private ArrayList<Alternativa> alt = new ArrayList<Alternativa>();

    public Criterio(){

    }

    public int getIdcirterio() {
        return idcirterio;
    }

    public void setIdcirterio(int idcirterio) {
        this.idcirterio = idcirterio;
    }

    public int getHierarquia() {
        return hierarquia;
    }

    public void setHierarquia(int hierarquia) {
        this.hierarquia = hierarquia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ArrayList<SubCriterio> getSub() {
        return sub;
    }

    public void setSub(ArrayList<SubCriterio> sub) {
        this.sub = sub;
    }

    public ArrayList<Alternativa> getAlt() {
        return alt;
    }

    public void setAlt(ArrayList<Alternativa> alt) {
        this.alt = alt;
    }
}
