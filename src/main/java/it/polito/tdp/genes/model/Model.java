package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	private GenesDao dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	private List<Adiacenza> adiacenze;
	private List<String> ottima;
	private int sommaOttima;
	
	public Model() {
		dao = new GenesDao();
	}
	
	public void creaGrafo() {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, dao.getVertici());
		
		adiacenze = dao.getArchi();
		for(Adiacenza a : adiacenze) {
			Graphs.addEdge(grafo, a.getL1(), a.getL2(), a.getPeso());
		}
		
		System.out.println(grafo.vertexSet().size());
		System.out.println(grafo.edgeSet().size());
	}
	
	public List<Statistiche> getStatistiche (String loc){
		List<Statistiche> stats = new ArrayList<>();
		
		System.out.println( Graphs.neighborListOf(grafo, loc).size());
		
		for(String s : Graphs.neighborListOf(grafo, loc)) {
			System.out.println(s+" dal peso "+grafo.getEdgeWeight(grafo.getEdge(loc, s)));
			stats.add(new Statistiche(s,(int) grafo.getEdgeWeight(grafo.getEdge(loc, s))));
		}
		
		return stats;
	}
	
	public List<String> getVertici(){
		return dao.getVertici();
	}
	
	
	public List<String> cerca(String loc) {
		List<String> parziale = new ArrayList<>();
		parziale.add(loc);
		sommaOttima = 0;
		
		cercaRicorsiva(parziale);
		
		return ottima;
	}

	private void cercaRicorsiva(List<String> parziale) {
		if(somma(parziale)>sommaOttima) {
			ottima = new ArrayList<>(parziale);
			sommaOttima = somma(parziale);
		}
		
		for(String s : Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(s)) {
				parziale.add(s);
				cercaRicorsiva(parziale);
				parziale.remove(s);
			}
		}
	}

	private int somma(List<String> parziale) {
		int somma = 0;
		
		if(parziale.size()==1) {
			return 0;
		} 
		
		for(int i=0; i<parziale.size()-1; i++) {
			somma += grafo.getEdgeWeight(grafo.getEdge(parziale.get(i), parziale.get(i+1)));
		}
		
		return somma;
	}

	public int getSommaOttima() {
		return sommaOttima;
	}

}