<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Gestion Assurances</title>
        <link rel="stylesheet" href="CSS/stl.css">
    </head>
    <body>
        <p>Résultat de la recherche.</p>
        <p>
            <% 
            String attribut = (String) request.getAttribute("test");
            out.println( attribut );
       		/*     
            String parametre = request.getParameter( "auteur" );
            out.println ( parametre );*/
            %>
        </p>
        </br>
        <a title="Acceuil" href="http://localhost:8080/POECDynamicWeb/index.html">Retour à l'accueil</a>
    </body>
</html>