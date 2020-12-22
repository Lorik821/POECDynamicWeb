package com.poec.servlets.Beans;

import java.time.LocalDate;

public class Sinistre {
	private String codeSinistre;
	private Client client;
	private Contrat contrat;
	
	private String typeSinistre;
	private LocalDate dateDuSinistre;
	private double prejudice;
	
	public Sinistre () {
		
	}
	
	public Sinistre (String codeSinistre, Client client, Contrat contrat, String typeSinistre, String jourSinistre, String moisSinistre, String anneeSinistre, double prejudice) {
		this.codeSinistre = codeSinistre;
		this.client = client;
		this.contrat = contrat;
		
		this.typeSinistre = typeSinistre;
		this.dateDuSinistre = LocalDate.of(Integer.parseInt(anneeSinistre), Integer.parseInt(moisSinistre), Integer.parseInt(jourSinistre));
		this.prejudice = prejudice;
	}

	public String getCodeSinistre() {
		return codeSinistre;
	}

	public void setCodeSinistre(String codeSinistre) {
		this.codeSinistre = codeSinistre;
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

	public String getTypeSinistre() {
		return typeSinistre;
	}

	public void setTypeSinistre(String typeSinistre) {
		this.typeSinistre = typeSinistre;
	}

	public LocalDate getDateDuSinistre() {
		return dateDuSinistre;
	}

	public void setDateDuSinistre(LocalDate dateDuSinistre) {
		this.dateDuSinistre = dateDuSinistre;
	}

	public double getPrejudice() {
		return prejudice;
	}

	public void setPrejudice(double prejudice) {
		this.prejudice = prejudice;
	}

}
