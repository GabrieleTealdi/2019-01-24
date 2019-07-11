package it.polito.tdp.extflightdelays.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	private List<String> stati;
	private Graph<String, DefaultWeightedEdge> grafo;
	private ExtFlightDelaysDAO dao;
	
	public Model() {
		this.stati = new LinkedList<String>();
		this.dao = new ExtFlightDelaysDAO();
		this.grafo = new DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	}
	
	

	public void creagrafo() {
		this.stati = dao.loadAllStates();
		
		Graphs.addAllVertices(this.grafo, this.stati);
		
		for(String s1: stati) {
			for(String s2:stati) {
				if(!this.grafo.containsEdge(s1, s2)) {
				//DefaultWeightedEdge e = this.grafo.getEdge(s1, s2);
				//if(e==null) {
					int numVoli=dao.contaVoli(s1,s2);
					if(numVoli>0)
						Graphs.addEdge(this.grafo, s1, s2, numVoli);
				}
				
			}
		}
		System.out.println("grafo creato!!! Vertici: "+this.grafo.vertexSet().size()+" Archi: "+this.grafo.edgeSet().size());
		
	}



	public List<String> getStati() {
		Collections.sort(stati);
		return stati;
	}



	public String getVeicoli(String stato) {
		List<DefaultWeightedEdge> e = new LinkedList<DefaultWeightedEdge>();
		e.addAll(this.grafo.outgoingEdgesOf(stato));
		
		Collections.sort(e, new Comparator<DefaultWeightedEdge>() {

			@Override
			public int compare(DefaultWeightedEdge o1, DefaultWeightedEdge o2) {
				return (int) (grafo.getEdgeWeight(o2)-grafo.getEdgeWeight(o1));
			}
			
		});
		String s ="";
		for(DefaultWeightedEdge e1: e) {
			s+="Stato: "+this.grafo.getEdgeTarget(e1)+": "+(int)this.grafo.getEdgeWeight(e1)+" voli\n";
		}
		
		return s;
	}
	public String simula(Integer numPersone, Integer numGiorni, String statoIniziale){
		Simulatore sim = new Simulatore();
		sim.init(numPersone, numGiorni, grafo, statoIniziale);
		Map<String, Integer> map = sim.simula();
		
		String s ="";
		for(String str: map.keySet()) {
			s+=str+": "+map.get(str)+" turisti\n";
		}
		
		return s;
	}

}
