package br.usp.pesquisaorigemdestino;
import java.util.LinkedList;
import java.util.List;

public class Local {
	
	private int coordenada_x;
	private int coordenada_y;
	private List<Integer> frequentadores;
	
	public Local(int coordenada_x, int coordenada_y, int frequentador) {
		this.coordenada_x = coordenada_x;
		this.coordenada_y = coordenada_y;
		this.frequentadores = new LinkedList<>();
		this.frequentadores.add(frequentador);
	}
	
	public int getCoordenada_x() {
		return coordenada_x;
	}

	public void setCoordenada_x(int coordenada_x) {
		this.coordenada_x = coordenada_x;
	}

	public int getCoordenada_y() {
		return coordenada_y;
	}

	public void setCoordenada_y(int coordenada_y) {
		this.coordenada_y = coordenada_y;
	}

	public List<Integer> getFrequentadores() {
		return frequentadores;
	}

	public void setFrequentadores(List<Integer> frequentadores) {
		this.frequentadores = frequentadores;
	}

	public void adicionaFrequentador(int frequentador) {
		frequentadores.add(frequentador);
	}

}
