package com.poec.servlets.Beans;

public class SinistreCouvert {
	private String typeSinistre;
	private double franchise;
	
	public SinistreCouvert (String typeSinistre, double franchise) {
		this.typeSinistre = typeSinistre;
		this.franchise = franchise;
	}

	public String getTypeSinistre() {
		return typeSinistre;
	}

	public void setTypeSinistre(String typeSinistre) {
		this.typeSinistre = typeSinistre;
	}

	public double getFranchise() {
		return franchise;
	}

	public void setFranchise(double franchise) {
		this.franchise = franchise;
	}

}
