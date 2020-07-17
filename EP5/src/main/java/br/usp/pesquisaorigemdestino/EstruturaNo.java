package br.usp.pesquisaorigemdestino;

import java.util.LinkedList;
import java.util.List;

public class EstruturaNo {
	
	private Integer no;
	private List<Integer> listaAdjacencia;
	
	public EstruturaNo(Integer no) {
		this.no = no;
		this.listaAdjacencia = new LinkedList<Integer>();
	}
	
	
	public void adicionarNoAdjacente(Integer no) {
		if (!listaAdjacencia.contains(no)) {
			listaAdjacencia.add(no);
		}
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public List<Integer> getListaAdjacencia() {
		return listaAdjacencia;
	}

	public void setListaAdjacencia(List<Integer> listaAdjacencia) {
		this.listaAdjacencia = listaAdjacencia;
	}
	
	
}
