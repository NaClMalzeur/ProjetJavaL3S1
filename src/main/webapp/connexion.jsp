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
        <link rel="stylesheet" type="text/css" href="projetCss.css">
        <title>ACCUEIL : MINI PROJET</title>
    </head>
    <body>
        <h1>Accueil Projet</h1>
        
        <div style="color:red">${errorMessage}</div>
        
        <!--connexion d'un client -->
        <form action="<c:url value="ServletControleur"/>" method="GET">
            <table id="table_connection">
                <tr>
                    <td>Pseudo </td>
                    <td><input type="text" name="loginParam"> </input></td>
                </tr>
                <tr>
                    <td>Mdp</td>
                    <td><input type="text" name="passwordParam" width="20"> </input></td>
                </tr>
                <tr>
                    <td colspan="2"> <input name="action" type="submit" value="CONNECTION"> </input></td>
                </tr>
            </table>
        </form>
    </body>
</html>
