<%-- 
    Document   : connexion
    Created on : 28 nov. 2017, 15:02:45
    Author     : Eman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MEGA ERP : ACCUEIL</title>
    </head>
    <body>
        <h1>Accueil ERP : Bienvenue</h1>
        
        <!--connexion d'un client -->
        <form action="ServletControleur" method="GET">
            <label>Pseudo</label><input type="text" name="loginParam"> </input><br/>
            <label>Mdp</label><input type="text" name="passwordParam"> </input><br/>
            <input type="submit" value="CONNEXION"> </input>
            
        </form>
    </body>
</html>
