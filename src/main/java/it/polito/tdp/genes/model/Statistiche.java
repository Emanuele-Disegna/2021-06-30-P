package it.polito.tdp.genes.model;

public class Statistiche {
	private String vicino;
	private int peso;
	public Statistiche(String vicino, int peso) {
		super();
		this.vicino = vicino;
		this.peso = peso;
	}
	public String getVicino() {
		return vicino;
	}
	public int getPeso() {
		return peso;
	}
	@Override
	public String toString() {
		return vicino + " dal peso di " + peso;
	}
	
	
}
