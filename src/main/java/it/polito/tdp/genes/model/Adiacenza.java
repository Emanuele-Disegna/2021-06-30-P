package it.polito.tdp.genes.model;

public class Adiacenza {
	private String L1;
	private String L2;
	private int peso;
	public Adiacenza(String l1, String l2, int peso) {
		super();
		L1 = l1;
		L2 = l2;
		this.peso = peso;
	}
	public String getL1() {
		return L1;
	}
	public void setL1(String l1) {
		L1 = l1;
	}
	public String getL2() {
		return L2;
	}
	public void setL2(String l2) {
		L2 = l2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	
}
