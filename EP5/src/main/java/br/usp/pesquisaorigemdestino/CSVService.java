package br.usp.pesquisaorigemdestino;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVWriter;

public class CSVService {
	
	public List<String[]> ler(String pathCsv) throws IOException {
		List<String[]> dados = new LinkedList<String[]>();
		BufferedReader reader = new BufferedReader(new FileReader(pathCsv));
		String linha;
		while(((linha = reader.readLine()) != null)) {
			dados.add(linha.split(","));
		}
		dados.remove(0);
		reader.close();
		return dados;
	}
	
	public List<String[]> lerTxt(String path) throws IOException {
		List<String[]> dados = new LinkedList<String[]>();
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String linha;
		while(((linha = reader.readLine()) != null)) {
			dados.add(linha.split(" "));
		}
		dados.remove(0);
		dados.remove(0);
		reader.close();
		return dados;
	}
	
	
	public void escrever(String pathCsv, List<String[]> adicional, Map<Integer, Integer> map) throws IOException {
		File file = new File(pathCsv);
		CSVWriter writer = new CSVWriter(
				new OutputStreamWriter (new FileOutputStream(file)),  ';', '"', '"', "\n");
		List<String[]> dadosSaida = new LinkedList<String[]>();
		
		for(String[] info : adicional) {
			dadosSaida.add(info);
		}
		
		for (Integer key : map.keySet()) {
			dadosSaida.add(new String[]{String.valueOf(key), String.valueOf(map.get(key))});
		}
		
		writer.writeAll(dadosSaida);
		writer.close();
			
	}
	
	public void escrever(String pathCsv, List<String[]> adicional, List<String[]> lst) throws IOException {
		File file = new File(pathCsv);
		CSVWriter writer = new CSVWriter(
				new OutputStreamWriter (new FileOutputStream(file)),  ';', '"', '"', "\n");

		
		writer.writeAll(adicional);
		writer.writeAll(lst);

		writer.close();
			
	}
	
}
