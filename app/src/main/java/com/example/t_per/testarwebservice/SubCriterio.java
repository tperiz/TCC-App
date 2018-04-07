package com.example.t_per.testarwebservice;

import java.util.ArrayList;

public class SubCriterio {
	
	private int idcriterio;
	private int idsubcriterio;
	private int idhierarquia;
	private String nome;
	private String descricao;
	private ArrayList<Alternativa> alt = new ArrayList<Alternativa>();
	
	
	public int getIdcriterio() {
		return idcriterio;
	}
	public void setIdcriterio(int idcriterio) {
		this.idcriterio = idcriterio;
	}
	public int getIdsubcriterio() {
		return idsubcriterio;
	}
	public void setIdsubcriterio(int idsubcriterio) {
		this.idsubcriterio = idsubcriterio;
	}
	public int getIdhierarquia() {
		return idhierarquia;
	}
	public void setIdhierarquia(int idhierarquia) {
		this.idhierarquia = idhierarquia;
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

	public ArrayList<Alternativa> getAlt() {
		return alt;
	}

	public void setAlt(ArrayList<Alternativa> alt) {
		this.alt = alt;
	}
}
