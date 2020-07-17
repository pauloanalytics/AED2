package br.usp.pesquisaorigemdestino;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PODApplication {
	
	private static final String ARQUIVO_ENTRADA_EP1 = "C:\\Users\\paulo\\Desktop\\CURSOS\\USP\\AED2\\OD_2017.csv";
	private static final String ARQUIVO_SAIDA_EP1 = "C:\\Users\\paulo\\Desktop\\CURSOS\\USP\\AED2\\Resultado_EP1.csv";

	private static final String ARQUIVO_ENTRADA_EP2 = "C:\\Users\\paulo\\Desktop\\CURSOS\\USP\\AED2\\cenario1.txt";
	private static final String ARQUIVO_SAIDA_EP2 = "C:\\Users\\paulo\\Desktop\\CURSOS\\USP\\AED2\\Resultado_EP2.csv";
	
	
	private static final String ARQUIVO_ENTRADA_EP3 = "C:\\Users\\paulo\\Desktop\\CURSOS\\USP\\AED2\\cenario1.txt";
	private static final String ARQUIVO_SAIDA_EP3 = "C:\\Users\\paulo\\Desktop\\CURSOS\\USP\\AED2\\Resultado_EP3.csv";

	private static final String ARQUIVO_ENTRADA_EP4 = "C:\\Users\\paulo\\Desktop\\CURSOS\\USP\\AED2\\traducao3.txt";
	private static final String ARQUIVO_SAIDA_EP4 = "C:\\Users\\paulo\\Desktop\\CURSOS\\USP\\AED2\\Resultado_EP4.csv";
	
	private static final String ARQUIVO_ENTRADA_EP5 = "C:\\Users\\paulo\\Desktop\\CURSOS\\USP\\AED2\\traducao3.txt";
	private static final String ARQUIVO_SAIDA_EP5 = "C:\\Users\\paulo\\Desktop\\CURSOS\\USP\\AED2\\Resultado_EP5.csv";
	
	private static Logger logger = LoggerFactory.getLogger(PODApplication.class);
	private static CSVService csvService = new CSVService();

	public static void main(String[] args) throws IOException {
		SpringApplication.run(PODApplication.class, args);
		run();
	}
	
	private static void run() throws IOException {
		executarEP1();
		executarEP2();
		executarEP3();
		executarEP4();
		executarEP5();
	}
	
	
	public static void executarEP1() throws IOException {
		
		logger.info("Iniciando a execução do EP1");
		
		long inicial = System.currentTimeMillis();
		List<String[]> dadosArquivo = csvService.ler(ARQUIVO_ENTRADA_EP1);
		OrganizadorDados organizadorDados = new OrganizadorDados();
		Map<String, Local> frequentadoresPorLocal = organizadorDados.organizar(dadosArquivo);
		Map<Integer, Integer> qntLocaisPorFrequentadores = organizadorDados.obterDistribuicao(frequentadoresPorLocal);
		List<String[]> infoAdicional = new LinkedList<String[]>();
		infoAdicional.add(new String[]{"Quantidade Frequentadores", "Quantidade Locais"});
		csvService.escrever(ARQUIVO_SAIDA_EP1, infoAdicional, qntLocaisPorFrequentadores);
		
		logger.info("Tempo total de execução: " + String.valueOf(System.currentTimeMillis()-inicial));
			
	}
	
	public static void executarEP2() throws IOException {
		
		logger.info("Iniciando a execução do EP2");
		long inicial = System.currentTimeMillis();

		List<String[]> dadosArquivo = csvService.lerTxt(ARQUIVO_ENTRADA_EP2);
		GrafoService gs = new GrafoService();
		Map<Integer, List<Integer>> grafo = gs.processarGrafo(dadosArquivo);
		Map<Integer, Integer> y = gs.encontrarDistribuicaoGrauNos(grafo);
		List<String[]> infoAdicional = new LinkedList<String[]>();
		infoAdicional.add(new String[]{"Grau No", "Frequencia", "Total Vertices:"+String.valueOf(gs.encontrarTotalArestas(y))});
		csvService.escrever(ARQUIVO_SAIDA_EP2, infoAdicional, y);
		
		logger.info("Tempo total de execução: " + String.valueOf(System.currentTimeMillis()-inicial));

		
	}
	
	public static void executarEP3() throws IOException {
		
		logger.info("Iniciando a execução do EP3");
		
		long inicial = System.currentTimeMillis();
		
		List<String[]> dadosArquivo = csvService.lerTxt(ARQUIVO_ENTRADA_EP3);
		GrafoService grafoService = new GrafoService();
		Map<Integer, List<Integer>> grafo = grafoService.processarGrafo(dadosArquivo);
				
		Map<Integer, Integer> componentesConexas = new HashMap<Integer, Integer>();
		
		List<Integer> verticesOrdem = new ArrayList<Integer>(grafo.keySet());
		Collections.sort(verticesOrdem);
		
		List<Integer> visitados = new LinkedList<>();
		
		/* 
		 * verticesOrdem contém todos os vértices que possuem pelo menos um vertice adjacente, ou seja, estão no arquivo.
		 * É testado se o vértice já foi visitado na busca em larga de um vertice anterior, se foi, então não há a necessidade de realizar
		 * a busca a partir dele, pois ele já pertence a um componente já contabilizado.
		 */
		
		for (Integer i :verticesOrdem) {
			if (!visitados.contains(i)) {
				BreadthFirstPaths bp = new BreadthFirstPaths(grafo, i);
				componentesConexas.put(i, bp.getTamanho());
				visitados.addAll(bp.getVisitados());
				bp.resetar();
			}
		}
		
		int quantidadeComponentesConexas = grafoService.quantidadeComponentosConexos(componentesConexas);
		
		//Separa a quantidade de componentes conexos por tamanho.
		Map<Integer, Integer> componentesConexasOrdenadas = grafoService.obterComponentesConexosPorTamanho(componentesConexas);
		
		//O total de nós isolados é a quantidade total de nós menos os nós com grau >= 1, ou seja, que estão no arquivo. 
		componentesConexasOrdenadas.put(0, 86318 - grafo.size());
		
		List<String[]> infoAdicional = new LinkedList<String[]>();
		infoAdicional.add(new String[]{"Tamanho Componente", "Quantidade Componentes", "Numero Componentes:"+String.valueOf(quantidadeComponentesConexas)});
		
		csvService.escrever(ARQUIVO_SAIDA_EP3, infoAdicional, componentesConexasOrdenadas);

		logger.info("Tempo total de execução: " + String.valueOf(System.currentTimeMillis()-inicial));

	}
	
	public static void executarEP4() throws IOException {
		
		logger.info("Iniciando a execução do EP4");

		long inicial = System.currentTimeMillis();
		
		List<String[]> dadosArquivo = csvService.lerTxt(ARQUIVO_ENTRADA_EP4);
		GrafoService grafoService = new GrafoService();
		Map<Integer, List<Integer>> grafo = grafoService.processarGrafo(dadosArquivo);
		
		List<Integer> verticesComponenteGigante = new LinkedList<Integer>();
		
		//Sabemos que a componente gigante é aquele que contém o vértice 0.
		BreadthFirstPaths breadthFirstPaths = new BreadthFirstPaths(grafo, 0);
		
		//Visitados contém todos os vértices do Componente Gigante;
		verticesComponenteGigante = breadthFirstPaths.getVisitados();
		
		Map<Integer, List<Integer>> grafoComponenteGigante = new HashMap<Integer, List<Integer>>();
			
		//Cria um grafo contendo apenas o componente gigante
		for (Integer vertice : verticesComponenteGigante) {
			grafoComponenteGigante.put(vertice, grafo.get(vertice));
		}
		
		/*
		 * É feita a busca em largura para cada vertice do componente gigante.
		 * Para cada vertice do componente gigante é obtida a distância entre ele e o último vértice que passou pela busca em largua.  
		 */
		Map<Integer, Integer> mapaDistanciaEntrePares = new HashMap<Integer, Integer>();
		for (Integer x : grafoComponenteGigante.keySet()) {
			breadthFirstPaths = new BreadthFirstPaths(grafoComponenteGigante, x);
			for (Integer y : grafoComponenteGigante.keySet()) { 
				List<Integer> caminho = breadthFirstPaths.pathTo(y);
				if(mapaDistanciaEntrePares.containsKey(caminho.size()-1)) {
					mapaDistanciaEntrePares.put(caminho.size() - 1, mapaDistanciaEntrePares.get(caminho.size()-1) + 1);
				} else {
					mapaDistanciaEntrePares.put(caminho.size() - 1, 1);
				}
			}
			
		}
		List<String[]> infoAdicional = new LinkedList<String[]>();
		infoAdicional.add(new String[]{"Distância entre Vertices", "Quantidade"});

		csvService.escrever(ARQUIVO_SAIDA_EP4, infoAdicional, mapaDistanciaEntrePares);
		logger.info("Tempo total de execução: " + String.valueOf(System.currentTimeMillis()-inicial));
		
	}
	
	public static void executarEP5() throws IOException {
		
		logger.info("Iniciando a execução do EP5");

		long inicial = System.currentTimeMillis();
		
		List<String[]> dadosArquivo = csvService.lerTxt(ARQUIVO_ENTRADA_EP5);
		GrafoService grafoService = new GrafoService();
		Map<Integer, List<Integer>> grafo = grafoService.processarGrafo(dadosArquivo);
		
		List<Integer> verticesComponenteGigante = new LinkedList<Integer>();
		
		//Sabemos que a componente gigante é aquele que contém o vértice 0.
		BreadthFirstPaths breadthFirstPaths = new BreadthFirstPaths(grafo, 0);
		
		//Visitados contém todos os vértices do Componente Gigante;
		verticesComponenteGigante = breadthFirstPaths.getVisitados();
		
		Map<Integer, List<Integer>> grafoComponenteGigante = new HashMap<Integer, List<Integer>>();
			
		//Cria um grafo contendo apenas o componente gigante
		for (Integer vertice : verticesComponenteGigante) {
			grafoComponenteGigante.put(vertice, grafo.get(vertice));
		}
		
		List<Integer> infectados = new LinkedList<Integer>();
		List<Integer> recuperados = new LinkedList<Integer>();
		
		List<Integer> randomValues = new LinkedList<Integer>(grafoComponenteGigante.keySet());
		Collections.shuffle(randomValues);
		
		infectados.add(randomValues.get(0));
				
		Random random = new Random();
		
		int menor = 0;
		int maior = 1;
		
		double c = 0.75;
		double r = 0.40;
		
		List<String[]> valores = new LinkedList<String[]>();
		
		int passo = 1;
		
		long quantidadeMaximaPassos = 100; //maximo de passos
		
		while (!infectados.isEmpty() && passo < quantidadeMaximaPassos ) {
			
			if (r >= (menor + (maior - menor) * random.nextDouble())) {
				recuperados.add(infectados.get(0));
				infectados.remove(infectados.get(0));
			} else {
				for (Integer i : grafoComponenteGigante.get(infectados.get(0))) {
					if (c >= (menor + (maior - menor) * random.nextDouble()) && !recuperados.contains(i) && !infectados.contains(i)) {
						infectados.add(i);
					}
				}
			}
			valores.add(new String[] {String.valueOf(passo), String.valueOf(infectados.size()), String.valueOf(recuperados.size()), String.valueOf((randomValues.size()-(infectados.size() + recuperados.size())))});
			Collections.shuffle(infectados); //Embaralha a lista de infectados para que o mesmo infectado não seja chamando o mesmo até ele se curar. 
			passo++;
		}
		
		List<String[]> infoAdicional = new LinkedList<String[]>();
		infoAdicional.add(new String[]{"Passo", "Infectados", "Curados", "Sucetíveis"});
		
		
		csvService.escrever(ARQUIVO_SAIDA_EP5, infoAdicional, valores);

		
	}

}
