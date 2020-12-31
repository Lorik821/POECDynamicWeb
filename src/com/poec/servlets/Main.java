package com.poec.servlets;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poec.servlets.Beans.Beneficiaire;
import com.poec.servlets.Beans.Client;
import com.poec.servlets.Beans.Contrat;
import com.poec.servlets.Beans.Sinistre;

public class Main extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String option;
	private String message;
	
	private ArrayList<Client> listClients;
	private ArrayList<Contrat> listContrats;
	private ArrayList<Sinistre> listSinistres;
	private ArrayList<Beneficiaire> listBeneficiaires;
	
	private Client clientCourant;
	private Contrat contratCourant;
	
	private JDBC jdbc;
	
	public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		initialisation();
		option = request.getParameter( "option" );
		
		if (option.equals("1"))
			srchClient (request);
		else if (option.equals("2"))
			createClient();
		else if (option.contentEquals("3"))
			listContrats (request);
		else if (option.contentEquals("4"))
			listSinistres (request);
		else if (option.contentEquals("6"))
			detailAssuranceVie (request);
		
		request.setAttribute( "test", message );
		this.getServletContext().getRequestDispatcher( "/WEB-INF/vue.jsp" ).forward( request, response );
	}
	
	public void initialisation ()
	{
		jdbc = new JDBC();
		
	}
	
	public void srchClient (HttpServletRequest request)
	{
		listClients = jdbc.srchClientFor(request.getParameter("clientFirstName"), request.getParameter("clientName"), request.getParameter("clientCode"));
		message = makeListClients ();		
	}
	
	public String makeListClients ()
	{
		String str = "";
		
		str += "<table><caption>Liste des clients correspondants à la recherche</caption><tr><th>Code client</th><th>Nom</th><th>Prénom</th><th>Date de naissance</th><th>Adresse</th><th>Code postal</th><th>Ville</th></tr>";
		for (int i = 0 ; i < listClients.size() ; i++) {
			str += "<tr>";
			str += "<td>" + listClients.get(i).getCode() + "</td>";
			str += "<td>" + listClients.get(i).getNom() + "</td>";
			str += "<td>" + listClients.get(i).getPrenom() + "</td>";
			str += "<td>" + listClients.get(i).getDateNaissance() + "</td>";
			str += "<td>" + listClients.get(i).getAdresse() + "</td>";
			str += "<td>" + listClients.get(i).getCodePostal() + "</td>";
			str += "<td>" + listClients.get(i).getVille() + "</td>";
			str += "<td> <a title=\"VersContrats\" href=\"http://localhost:8080/POECDynamicWeb/acceuil?codeClient="+listClients.get(i).getCode()+"&option=3\"> Voir ses contrats</a>";
			str += "</tr>";
		}
		str += "</table>";
			
		
		return str;
	}

	public void detailAssuranceVie (HttpServletRequest request) 
	{
		String codeContrat = request.getParameter("codeContrat");
		for (int i = 0 ; i < listContrats.size(); i++)
			if (listContrats.get(i).getCode().equals(codeContrat)) contratCourant = listContrats.get(i);
		
		listBeneficiaires = jdbc.srchBeneficiairesFor(contratCourant);
		message = makeDetailsAssuranceVie();
	}
	
	public String makeDetailsAssuranceVie () 
	{
		String str = "";
		
		str += "<h3>Détail de l'assurance vie de " + clientCourant.getNom() + " " + clientCourant.getPrenom() + "</h3></br>";
		str += "<p>Date de signature du contrat : " + contratCourant.getSignature() + "</p>";
		str += "<table><caption>Liste des bénéficiaires de l'assurance vie</caption><tr><th>Bénéficiaire</th><th>Somme due</th></tr>";
		try {
			for (int i = 0 ; i < listBeneficiaires.size(); i++) {
				str += "<tr><td>" + listBeneficiaires.get(i).getClient().getPrenom() + " " + listBeneficiaires.get(i).getClient().getNom() +"</td>";
				str += "<td>" + listBeneficiaires.get(i).getSomme() + "</td></tr>";
			}
		}
		catch (Exception e) {
			str += "";	
		}
		str += "</table>";
		
		return str;
	}
	
	
	public void listContrats (HttpServletRequest request)
	{
		String codeClient = request.getParameter("codeClient");
		for (int i = 0 ; i < listClients.size(); i++)
			if (listClients.get(i).getCode().equals(codeClient)) clientCourant = listClients.get(i);
			
		listContrats = jdbc.srchContratFor(clientCourant);
		message = makeListContrats();
	}
	
	public String makeListContrats()

	{
		/*
		String str = "";
		str += "<script Language=\"JavaScript\">let list = new Array();let boutons = new Array();var indiceTab = 0;</script>";
		str +="<table><caption>Liste des contrats pour "+clientCourant.getNom()+"</caption><tr><th>Code du contrat</th><th>Date de signature du contrat</th>";
		for (int i = 0 ; i < listContrats.size(); i++) {
			str += "<tr >";
			str += "<td>" + listContrats.get(i).getCode() + "</td>";
			str += "<td>" + listContrats.get(i).getSignature() + "</td>";
			str += "<td> <button id=\"ligne"+i+"\" type=\"button\">Voir/masquer détails</button>";
			str += "</tr>";
			str += "<script Language=\"JavaScript\">boutons[indiceTab] = document.getElementById(\"ligne"+i+"\");</script>";
			String sinistresCouverts = "";
			for (int j = 0 ; j < listContrats.get(i).getSinistresCouverts().size(); j++) {
				sinistresCouverts += listContrats.get(i).getSinistresCouverts().get(j).getTypeSinistre() + " franchise : ";
				sinistresCouverts += listContrats.get(i).getSinistresCouverts().get(j).getFranchise() + "</br>";
			}
			//str += "<tr style=display:none><td>Sinistres couverts : " + sinistresCouverts + "</td></tr>";
			str += "<tr id=\"dtlContrat"+i+"\" style=display:\"none\"><td colspan=\"2\" >" + sinistresCouverts + "</td></tr>";
			str += "<SCRIPT Language=\"JavaScript\">list[indiceTab] = document.getElementById(\"dtlContrat"+i+"\");indiceTab++;</script>";
		}
		str += "<script Language=\"JavaScript\">function togg(idLigne) { if(getComputedStyle(idLigne).display != \"none\") { idLigne.style.display=\"none\"; } else { idLigne.style.display = \"block\";}};</script>";
		str +="<script Language=\"JavaScript\">var i = 0; while (i < indiceTab) { boutons[i].onclick = togg(list[i]); i++; console.log(i);}</script>";
		str += "</table>";*/
		
		/*
		String str = "";
		str += "<script Language=\"JavaScript\">var list = [];var boutons = [];var indiceTab = 0;</script>";
		str +="<table><caption>Liste des contrats pour "+clientCourant.getNom()+"</caption><tr><th>Code du contrat</th><th>Date de signature du contrat</th>";
		for (int i = 0 ; i < listContrats.size(); i++) {
			str += "<tr >";
			str += "<td>" + listContrats.get(i).getCode() + "</td>";
			str += "<td>" + listContrats.get(i).getSignature() + "</td>";
			str += "<td> <button id=\"ligne"+i+"\" type=\"button\">Voir/masquer détails</button>";
			str += "</tr>";
			str += "<script Language=\"JavaScript\">"
					+ "boutons[indiceTab] = document.getElementById(\"ligne"+i+"\");"
				    + "var test = document.getElementById(\"ligne1\");"
				 + "</script>";
			String sinistresCouverts = "";
			for (int j = 0 ; j < listContrats.get(i).getSinistresCouverts().size(); j++) {
				sinistresCouverts += listContrats.get(i).getSinistresCouverts().get(j).getTypeSinistre() + " franchise : ";
				sinistresCouverts += listContrats.get(i).getSinistresCouverts().get(j).getFranchise() + "</br>";
			}
			//str += "<tr style=display:none><td>Sinistres couverts : " + sinistresCouverts + "</td></tr>";
			str += "<tr id=\"dtlContrat"+i+"\" style=display:\"none\"><td colspan=\"2\" >" + sinistresCouverts + "</td></tr>";
			str += "<SCRIPT Language=\"JavaScript\">"
					+ "list[indiceTab] = document.getElementById(\"dtlContrat"+i+"\");"
					+ "testD = document.getElementById(\"dtlContrat1\");"
					+ "list[indiceTab].addEventListener(\"click\", () => { "
						+ "if (getComputedStyle(testD).display != \"none\") { "
							+ "testD.style.display = \"none\"; "
						+ "} "
							+ "else { "
								+ "testD.style.display = \"block\""
							+ "}"
						+ "}"
					+ ");"
					+ "indiceTab++;"
					+ "</script>";
		}
		str += "</table>";*/
		
		String str = "";
		String typeContrat = "";
		str += "<script Language=\"JavaScript\">var list = [];var boutons = [];var indiceTab = 0;</script>";
		str +="<table><caption>Liste des contrats pour "+clientCourant.getNom()+"</caption><tr><th>Code du contrat</th><th>Date de signature du contrat</th>";
		for (int i = 0 ; i < listContrats.size(); i++) {
			if (listContrats.get(i).isAssuranceVie())
				typeContrat = "<td> <a title=\"VersAssuranceVie\" href=\"http://localhost:8080/POECDynamicWeb/acceuil?codeContrat="+listContrats.get(i).getCode()+"&option=6\">Détails de l'assurance vie</a></td>";
			else
				typeContrat = "<td> <a title=\"VersSinistres\" href=\"http://localhost:8080/POECDynamicWeb/acceuil?codeContrat="+listContrats.get(i).getCode()+"&option=4\">Consulter ses sinistres</a></td>";
			str += "<tr >";
			str += "<td>" + listContrats.get(i).getCode() + "</td>";
			str += "<td>" + listContrats.get(i).getSignature() + "</td>";
			str += "<td> <button id=\"ligne"+i+"\" type=\"button\">Voir/masquer détails</button>";
			str += typeContrat;
			str += "</tr>";
			str += "<script Language=\"JavaScript\">"
				    + "boutons[indiceTab] = document.getElementById(\"ligne"+i+"\");"
				 + "</script>";
			String sinistresCouverts = "";
			for (int j = 0 ; j < listContrats.get(i).getSinistresCouverts().size(); j++) {
				sinistresCouverts += listContrats.get(i).getSinistresCouverts().get(j).getTypeSinistre() + " franchise : ";
				sinistresCouverts += listContrats.get(i).getSinistresCouverts().get(j).getFranchise() + " €</br>";
			}
			//str += "<tr style=display:none><td>Sinistres couverts : " + sinistresCouverts + "</td></tr>";
			str += "<tr id=\"dtlContrat"+i+"\" style=display:\"none\"><td colspan=\"3\" >" + sinistresCouverts + "</td></tr>";
			str += "<SCRIPT Language=\"JavaScript\">"
					+ "list[indiceTab] = document.getElementById(\"dtlContrat"+i+"\");"
					+ "boutons[indiceTab].addEventListener(\"click\", () => { "
						+ "if (getComputedStyle(list[indiceTab]).display != \"none\") { "
							+ "list[indiceTab].style.display = \"none\"; "
						+ "} "
							+ "else { "
								+ "list[indiceTab].style.display = \"block\""
							+ "}"
						+ "}"
					+ ");"
					+ "indiceTab++;"
					+ "</script>";
		}
		str += "</table>";
		return str;
	}
	
	public void listSinistres (HttpServletRequest request)
	{
		String codeContrat = request.getParameter("codeContrat");
		for (int i = 0 ; i < listContrats.size(); i++)
			if (listContrats.get(i).getCode().equals(codeContrat)) contratCourant = listContrats.get(i);
		
		listSinistres = jdbc.srchSinistreFor(contratCourant);
		message = makeListSinistres();
		
	}
	
	private String makeListSinistres() {
		String str = "";
		double remboursable;
		double franchise = 0;
		
		str += "<table><caption>Liste des sinistres liés au contrat sélectionné</caption><tr><th>Code sinistre</th><th>Nom du signataire</th><th>Type du sinistre</th><th>Date du sinistre</th><th>Préjudice</th><th>Rembousable</th>";
		for (int i = 0 ; i < listSinistres.size(); i++) {
			for (int j = 0 ; j < contratCourant.getSinistresCouverts().size() ; j++) {
				if (contratCourant.getSinistresCouverts().get(j).getTypeSinistre().equals(listSinistres.get(i).getTypeSinistre()))
					franchise = contratCourant.getSinistresCouverts().get(j).getFranchise();
			}
			remboursable = listSinistres.get(i).getPrejudice() - franchise;
			str += "<tr>";
			str += "<td>" + listSinistres.get(i).getCodeSinistre() + "</td>";
			str += "<td>" + listSinistres.get(i).getClient().getNom() + "</td>";
			str += "<td>" + listSinistres.get(i).getTypeSinistre() + "</td>";
			str += "<td>" + listSinistres.get(i).getDateDuSinistre() + "</td>";
			str += "<td>" + listSinistres.get(i).getPrejudice() + "</td>";
			str += "<td>" + remboursable + "</td>";
		}
		
		return str;
	}

	public void createClient ()
	{
		
	}

}
