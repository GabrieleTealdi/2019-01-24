package it.polito.tdp.extflightdelays.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulatore {
	
	private Graph<String, DefaultWeightedEdge> grafo;
	private int numPersone;
	private int numGiorni;
	private Map<String, Integer> statiPersone;
	private PriorityQueue<Event> queue;
	
	public void init(Integer numPersone, Integer numGiorni, Graph<String, DefaultWeightedEdge> grafo, String statoIniziale) {
		this.numPersone=numPersone;
		this.numGiorni=numGiorni;
		this.grafo=grafo;
		
		this.statiPersone = new HashMap<String,Integer>();
		for(String stato: grafo.vertexSet()) {
			if(stato.compareTo(statoIniziale)!=0)
				statiPersone.put(stato, 0);
			else
				statiPersone.put(stato, numPersone);
		}
		this.queue=new PriorityQueue<Event>();
		for(int i=1; i<=numPersone; i++) {
			queue.add(new Event(i, 0, statoIniziale));
		}
			
	}
	public Map<String,Integer> simula(){
		Event e;
		while((e=this.queue.poll())!=null) {
			
			System.out.println(e);
			
			if(e.getGiorniPassati()<numGiorni) {
				Map<String, Intervallo> vicini = new LinkedHashMap<String, Intervallo>();
				double sommaPesi=0;
				double cntIntervallo=0;
				//double limIntervallo=1;
				//vicini.put("base", new Intervallo(0,0));
				for(DefaultWeightedEdge edge: this.grafo.outgoingEdgesOf(e.getStato())) {
					sommaPesi+=this.grafo.getEdgeWeight(edge);
					
					//System.out.println("pirla");
					
					// vicini.put(this.grafo.getEdgeTarget(edge), null);
				}
				
				//System.out.println("sommPesi: "+sommaPesi);
				
				for(DefaultWeightedEdge edge: this.grafo.outgoingEdgesOf(e.getStato())) {
					double pesoEdge = this.grafo.getEdgeWeight(edge);
					
					//System.out.println(pesoEdge);
					
					double perc = pesoEdge/sommaPesi;
					
					//System.out.println("Perc: "+perc);
					
					if(cntIntervallo==0) {
						vicini.put(this.grafo.getEdgeTarget(edge), new Intervallo(0,perc));
						cntIntervallo=perc;
						//System.out.println(cntIntervallo+"-------"+perc);
						//System.out.println("cane");
						
					} else {
						vicini.put(this.grafo.getEdgeTarget(edge), new Intervallo(cntIntervallo,cntIntervallo+perc));
						cntIntervallo=cntIntervallo+perc;
						
						//System.out.println("gatto");
					}
				}
				double r = Math.random();
				for(String s: vicini.keySet()) {
					if(r>vicini.get(s).getI() && r<=vicini.get(s).getF()) {
						
						//System.out.println("colione");
						
						queue.add(new Event(e.getNumPersona(), e.getGiorniPassati()+1, s));
						statiPersone.put(e.getStato(), statiPersone.get(e.getStato())-1);
						statiPersone.put(s, statiPersone.get(s)+1);
					}
				}
				
			}
		}
		return statiPersone;
	}

}
