package br.usp.pesquisaorigemdestino;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class BreadthFirstPaths {
	
	private Map<Integer, List<Integer>> grafo;
	private List<Integer> visitados;
	private int[] edgeTo;
	private final int s;
	private int tamanho;
	
	public BreadthFirstPaths(Map<Integer, List<Integer>> grafo, int s) {
		visitados = new LinkedList<Integer>();
		this.edgeTo = new int[86318+1];
		this.grafo = grafo;
		this.s = s;
		bfs(s);
	}
	
	public void bfs(int vertice) {
		Queue<Integer> queue = new ArrayDeque<>(); //
		visitados.add(vertice); //
		queue.add(vertice); //
		while(!queue.isEmpty()) {
			int v = queue.poll(); //
			for(Integer i : grafo.get(v)) {
				if (!visitados.contains(i)) { //
					
					queue.add(i);
					edgeTo[i] = v;
					visitados.add(i);
					tamanho++;
				}
			}
		}
	}

	public int getTamanho() {
		return tamanho;
	}
	
	 public boolean hasPathTo(int v)
	 { return visitados.contains(v); }
	 
	public List<Integer> pathTo(int v) {
		 if (!hasPathTo(v)) return null;
		 Stack<Integer> path = new Stack<Integer>();
		 for (int x = v; x != s; x = edgeTo[x])
		 path.push(x);
		 path.push(s);
		 return path;		
	}
	
	public void resetar() {
		this.tamanho = 0;
		this.visitados.clear();
	}

	public List<Integer> getVisitados() {
		// TODO Auto-generated method stub
		return new ArrayList<>(visitados);
	}
	
	
//	We put the source vertex on the
//	data structure, then perform the following steps until the data structure is empty:
//	■ Take the next vertex v from the data structure and mark it.
//	■ Put onto the data structure all unmarked vertices that are adjacent to v.
//	The algorithms differ only in the rule used to
//	take the next vertex from the data structure
//	(least recently added for BFS, most recently added for DFS). This difference leads to completely
//	different views of the graph, even though all the
//	vertices and edges connected to the source are
//	examined no matter what rule is used.

	
	
}
