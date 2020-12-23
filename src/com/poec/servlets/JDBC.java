package com.poec.servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.poec.servlets.Beans.Beneficiaire;
import com.poec.servlets.Beans.Client;
import com.poec.servlets.Beans.Contrat;
import com.poec.servlets.Beans.Sinistre;

public class JDBC {
	private Statement statement;
	private Connection connection;
	
	public JDBC ()
	{
		/*
		Connection conn = null;
        String dbName = "stagePOECCobol";
        String serverip="DESKTOP-G3KGIN3";
        String serverport="1433";
        String url = "jdbc:sqlserver://"+serverip+"\\SQLEXPRESS:"+serverport+";databaseName="+dbName+";IntegratedSecurity=true";
        Statement stmt = null;
        ResultSet result = null;
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String databaseUserName = "POEC";
        String databasePassword = "chronos";
        try {
            try {
				Class.forName(driver).newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            conn = DriverManager.getConnection(url, databaseUserName, databasePassword);
        }
        catch (SQLException e) {
        	e.printStackTrace();
        }*/
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	/* Connexion à la base de données */
    	String url = "jdbc:mysql://localhost:3306/stagePOECCobol";
    	String utilisateur = "root";
    	String motDePasse = "";
    	try {
    	    connection = DriverManager.getConnection( url, utilisateur, motDePasse );

    	    /* Ici, nous placerons nos requêtes vers la BDD */
    	    /* ... */

    	} catch ( SQLException e ) {
    		System.out.println ("probleme !");
    		e.printStackTrace();
    	    /* Gérer les éventuelles erreurs ici */
    	} finally {
    	    if ( connection != null )
				/* Fermeture de la connexion */
				//connexion.close();
				System.out.println ("Connexion ok ! ");
    	}
		
	}
	
	/*
	 * Méthode qui permet de récupérer la liste des clients pour tel prenom, nom ou codeClient
	 */
	public ArrayList<Client> srchClientFor (String prenom, String nom, String codeClient)
	{
		
		/* Création de l'objet gérant les requêtes */
    	Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet resultSet = null;
		ArrayList<Client> listeClients = new ArrayList<Client>();
		
		//Petite astuce ici qui permet de ne pas changer la requête sur l'utilisateur laisse des champs vide ; autrement sql fait une mauvaise recherche
		if (prenom.equals("")) prenom = "   ";
		if (nom.equals("")) nom = "   ";
		if (codeClient.equals("")) codeClient = "  ";
		
		String selectSQL = "SELECT codeClient, nom, prenom, DAY(dateNaissance), MONTH(dateNaissance), YEAR(dateNaissance), adresse, codePostal, ville FROM clients where prenom like '" + prenom + "%' OR nom like '" + nom + "%' OR codeClient like '" + codeClient + "%'";
		try {
			resultSet = statement.executeQuery(selectSQL);
		
			while (resultSet.next()) {
				listeClients.add(new Client (resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listeClients;
	}
	
	public ArrayList<Contrat> srchContratFor (Client client)
	{
		/* Création de l'objet gérant les requêtes */
    	Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet resultSet = null;
		ArrayList<Contrat> listeContrats = new ArrayList<Contrat>();
		
		String selectSQL = "SELECT codeContrat, IT, PE, IA, MT, CH, AV, FRIT, FRPE, FRIA, FRMT, FRCH, DAY(dateSignature), MONTH(dateSignature), YEAR(dateSignature) FROM contrats WHERE codeClient ='"+client.getCode()+"'";
		try {
			resultSet = statement.executeQuery(selectSQL);
			
			while (resultSet.next()) {
				listeContrats.add(new Contrat (resultSet.getString(1), client, resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getInt(8), resultSet.getInt(9), resultSet.getInt(10), resultSet.getInt(11), resultSet.getInt(12), resultSet.getString(13), resultSet.getString(14), resultSet.getString(15)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listeContrats;
	}
	
	public ArrayList<Sinistre> srchSinistreFor (Contrat contrat) 
	{
		/* Création de l'objet gérant les requêtes */
    	Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet resultSet = null;
		ArrayList<Sinistre> listeSinistres = new ArrayList<Sinistre>();
		
		String selectSQL = "SELECT codeSinistre, codeClient, typeSinistre, DAY(dateDuSinistre), MONTH(dateDuSinistre), YEAR(dateDuSinistre), prejudice FROM sinistres WHERE codeContrat ='"+contrat.getCode()+"'";
		try {
			resultSet = statement.executeQuery(selectSQL);
			
			while (resultSet.next()) {
				listeSinistres.add(new Sinistre (resultSet.getString(1), contrat.getClient(), contrat, resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getDouble(7)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listeSinistres;
		
	}
	
	public ArrayList<Beneficiaire> srchBeneficiairesFor (Contrat contrat)
	{
		/* Création de l'objet gérant les requêtes */
    	Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet resultSet = null;
		ArrayList<Beneficiaire> listeBeneficiaires = new ArrayList<Beneficiaire>();
		
		String selectSQL = "SELECT codeClient, somme FROM assurancesVie WHERE codeContrat ='" + contrat.getCode() + "'";
		try {
			resultSet = statement.executeQuery(selectSQL);
			
			while (resultSet.next()) {
				listeBeneficiaires.add(new Beneficiaire (srchClientFor(resultSet.getString(1)), contrat, resultSet.getDouble(2)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return listeBeneficiaires;
	}
		
	/*
	 * Retourne un client selon le codeClient
	 */
	private Client srchClientFor (String codeClient) 
	{
		/* Création de l'objet gérant les requêtes */
    	Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet resultSet = null;
		
		String selectSQL = "SELECT nom, prenom, DAY(dateNaissance), MONTH(dateNaissance), YEAR(dateNaissance), adresse, codePostal, ville FROM clients WHERE codeClient='" +codeClient+"'";
		try {
			resultSet = statement.executeQuery(selectSQL);
			
			while (resultSet.next()) {
				Client tmp = new Client (codeClient, resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8)); 
				System.out.println ("Nom du client nouvellement créé :  " + tmp.getNom());
				return tmp;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
