package com.poec.servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JDBC {
	private Statement statement;
	private Connection connection;
	
	public JDBC (String connectionURL, String user, String pass)
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
		
		String selectSQL = "SELECT codeClient, nom, prenom, DAY(dateNaissance), MONTH(dateNaissance), YEAR(dateNaissance), adresse, codePostal, ville FROM clients where prenom like '" + prenom + "%' OR nom like '" + nom + "%' OR codeClient like '" + codeClient + "%'";
		System.out.println (selectSQL);
		//String selectSQL = "SELECT * FROM clients";
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

}
