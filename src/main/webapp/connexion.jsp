<%-- 
    Document   : connexion
    Created on : 28 nov. 2017, 15:02:45
    Author     : Eman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ACCUEIL : MINI PROJET</title>
    </head>
    <body>
        <h1>Accueil Projet</h1>
        
        <div style="color:red">${errorMessage}</div>
        
        <!--connexion d'un client -->
        <form action="<c:url value="connexion.jsp"/>" method="GET">
            <label>Pseudo</label><input type="text" name="loginParam"> </input><br/>
            <label>Mdp</label><input type="text" name="passwordParam"> </input><br/>
            <input name="action" type="submit" value="CONNEXION"> </input>
            
        </form>
    </body>
</html>
