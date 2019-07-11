package it.polito.tdp.extflightdelays.model;

public class Event implements Comparable<Event>{
	
	private int numPersona;
	private int giorniPassati;
	private String Stato;
	public Event(int numPersona, int giorniPassati, String stato) {
		super();
		this.numPersona = numPersona;
		this.giorniPassati = giorniPassati;
		Stato = stato;
	}
	public int getNumPersona() {
		return numPersona;
	}
	public void setNumPersona(int numPersona) {
		this.numPersona = numPersona;
	}
	public int getGiorniPassati() {
		return giorniPassati;
	}
	public void setGiorniPassati(int giorniPassati) {
		this.giorniPassati = giorniPassati;
	}
	public String getStato() {
		return Stato;
	}
	public void setStato(String stato) {
		Stato = stato;
	}
	@Override
	public int compareTo(Event o) {
		if((this.giorniPassati-o.giorniPassati)==0)
			return this.numPersona-o.numPersona;
		return this.giorniPassati-o.giorniPassati;
		
	}
	@Override
	public String toString() {
		return String.format("Event [numPersona=%s, giorniPassati=%s, Stato=%s]", numPersona, giorniPassati, Stato);
	}
	
	

}
