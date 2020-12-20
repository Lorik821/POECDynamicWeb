package com.poec.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Main extends HttpServlet {
	private final String url = "jdbc:sqlserver://DESKTOP-G3KGIN3\\SQLEXPRESS;user=Lorik;password=xenoblade13";
	private final String user = "Lorik";
	private final String pass = "xenoblade13";
	
	private String option;
	private String message;
	
	private ArrayList<Client> listClients;
	private JDBC jdbc;
	
	public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		initialisation();
		option = request.getParameter( "option" );
		
		if (option.equals("1"))
			srchClient (request);
		else if (option.equals("2"))
			createClient();
		
		request.setAttribute( "test", message );
		this.getServletContext().getRequestDispatcher( "/WEB-INF/vue.jsp" ).forward( request, response );
	}
	
	public void initialisation ()
	{
		jdbc = new JDBC(url, user, pass);
		
	}
	
	public void srchClient (HttpServletRequest request)
	{
		listClients = jdbc.srchClientFor(request.getParameter("clientFirstName"), request.getParameter("clientName"), request.getParameter("clientCode"));
		message = makeListClients ();		
	}
	
	public String makeListClients ()
	{
		String str = "";
		
		str += "<table>";
		for (int i = 0 ; i < listClients.size() ; i++) {
			str += "<tr>";
			str += "<td>" + listClients.get(i).getCode() + "</td>";
			str += "<td>" + listClients.get(i).getNom() + "</td>";
			str += "<td>" + listClients.get(i).getPrenom() + "</td>";
			str += "<td>" + listClients.get(i).getDateNaissance() + "</td>";
			str += "<td>" + listClients.get(i).getAdresse() + "</td>";
			str += "<td>" + listClients.get(i).getCodePostal() + "</td>";
			str += "<td>" + listClients.get(i).getVille() + "</td>";
			str += "</tr>";
		}
		str += "</table>";
			
		
		return str;
	}
	
	public void createClient ()
	{
		
	}

}
