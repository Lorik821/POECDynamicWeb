package com.poec.servlets;

import java.time.LocalDate;
import java.util.Date;

public class Client {
	private String code;
	private String nom;
	private String prenom;
	private LocalDate dateNaissance;
	private String adresse;
	private String codePostal;
	private String ville;
	
	public Client ()
	{
		
	}
	
	public Client (String code, String nom, String prenom, String jourNaissance, String moisNaissance, String anneeNaissance, String adresse, String codePostal, String ville)
	{
		this.code = code;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = LocalDate.of(Integer.parseInt(anneeNaissance), Integer.parseInt(moisNaissance), Integer.parseInt(jourNaissance));
		this.adresse = adresse;
		this.codePostal = codePostal;
		this.ville = ville;		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

}
