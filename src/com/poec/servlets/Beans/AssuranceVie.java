package com.poec.servlets.Beans;

import java.util.ArrayList;

public class AssuranceVie {
	private Contrat contrat;
	private ArrayList<Beneficiaire> beneficiaires;
	
	public AssuranceVie () {
		
	}
	
	public AssuranceVie (Contrat contrat, ArrayList<Beneficiaire> beneficiaires) {
		this.contrat = contrat;
		this.beneficiaires = beneficiaires;
	}

	public Contrat getContrat() {
		return contrat;
	}

	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}

	public ArrayList<Beneficiaire> getBeneficiaires() {
		return beneficiaires;
	}

	public void setBeneficiaires(ArrayList<Beneficiaire> beneficiaires) {
		this.beneficiaires = beneficiaires;
	}
	
	

}
