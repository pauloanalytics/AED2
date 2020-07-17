package br.usp.pesquisaorigemdestino;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GrafoService {
	
	Map<Integer, List<Integer>> mapaGrafo;

	public GrafoService() {
		this.mapaGrafo = new HashMap<Integer, List<Integer>>();
	}
	
	public Map<Integer, List<Integer>> processarGrafo(List<String[]> dados) {
				
		for (String[] nos : dados) {
//			System.out.println(Arrays.toString(nos));
			adicionarVertices(Integer.valueOf(nos[0]), Integer.valueOf(nos[1]));
			adicionarVertices(Integer.valueOf(nos[1]), Integer.valueOf(nos[0]));
		}
		
		return mapaGrafo;
	}
	
	private void adicionarVertices(int no, int no_adj) {

		if (mapaGrafo.containsKey(no)) {
			mapaGrafo.get(no).add(no_adj);	
		} else {
			List<Integer> listaAdj = new LinkedList<Integer>();
			listaAdj.add(no_adj);
			mapaGrafo.put(no, listaAdj);
		}
		
	}

	public Map<Integer, Integer> encontrarDistribuicaoGrauNos(Map<Integer, List<Integer>> grafo) {
		
		Map<Integer, Integer> distribuicao = new HashMap<Integer, Integer>();

		int freq = 0;
		int qnt = 0;
		
		for (Integer key : grafo.keySet()) {
			freq = grafo.get(key).size();
			if(distribuicao.containsKey(freq)) {
				qnt = distribuicao.get(freq);
				distribuicao.put(freq, (qnt+1));
			} else {
				distribuicao.put(freq, 1);
				if (freq == 1476) {
				}
			}
		}
		
		//Adiciona distribuicao do grau 0, ou seja, total nos - vertices com grau >= 1
		distribuicao.put(0, 86318-grafo.size());
		return distribuicao;
	}
	
	public int encontrarTotalArestas(Map<Integer, Integer> distribuicao) {
		int totalArestas = 0;
		for (Integer i : distribuicao.keySet())
			totalArestas+= (i * distribuicao.get(i));
		totalArestas = totalArestas/2;
		return totalArestas;
	}
	
	public int quantidadeComponentosConexos(Map<Integer, Integer> componentesConexas) {
		return componentesConexas.size();
	}
	
	public Map<Integer, Integer> obterComponentesConexosPorTamanho(Map<Integer, Integer> componentesConexas) {
		Map<Integer, Integer> componentesConexasOrdenadas = new HashMap<Integer, Integer>();
		for ( Integer i : componentesConexas.keySet()) {
			if (componentesConexasOrdenadas.containsKey(componentesConexas.get(i))) {
				componentesConexasOrdenadas.put(componentesConexas.get(i), componentesConexasOrdenadas.get(componentesConexas.get(i)) + 1);
			} else {
				componentesConexasOrdenadas.put(componentesConexas.get(i), 1);
			}
		}
		return componentesConexasOrdenadas;
	}
	
	private void imprimirGrafoListaAdj() {
		for (Integer i : mapaGrafo.keySet()) { 
			System.out.print(i);
			for (Integer x : mapaGrafo.get(i)) {
				System.out.print("-->"+x);
			}
			System.out.println();
		}
	}
	
	private void imprimirDistribuicao(Map<Integer, Integer> map) {
		System.out.println(map.keySet().size());
		for (Integer i : map.keySet())
			System.out.println(i+" "+map.get(i));
	}
	

}
