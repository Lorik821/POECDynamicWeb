package com.poec.servlets.Beans;

public class Beneficiaire {
	private Client client;
	private Contrat contrat;
	private double somme;
	
	public Beneficiaire () {
		
	}
	
	public Beneficiaire (Client client, Contrat contrat, double somme) {
		this.client = client;
		this.contrat = contrat;
		this.somme = somme;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Contrat getContrat() {
		return contrat;
	}

	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}

	public double getSomme() {
		return somme;
	}

	public void setSomme(double somme) {
		this.somme = somme;
	}

}
