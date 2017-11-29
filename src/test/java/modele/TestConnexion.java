package modele;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author afaure04
 */
public class TestConnexion {
    
    private DAO myDAO; // L'objet Ã  tester
    private DataSource myDataSource; // La source de donnÃ©es Ã  utiliser
    private static Connection myConnection ;

    @Before
    public void setUp() throws IOException, SqlToolError, SQLException {
        myDataSource = DataSourceFactory.getDataSource();
        myConnection = myDataSource.getConnection();
        executeSQLScript(myConnection, "export.sql");		

        myDAO = new DAO(myDataSource);
    }
    
    public static DataSource getDataSource() throws SQLException {
        org.hsqldb.jdbc.JDBCDataSource ds = new org.hsqldb.jdbc.JDBCDataSource();
        ds.setDatabase("jdbc:hsqldb:mem:testcase;shutdown=true");
        ds.setUser("sa");
        ds.setPassword("sa");
        return ds;
    }
    
    private void executeSQLScript(Connection connexion, String filename)  throws IOException, SqlToolError, SQLException {
        // On initialise la base avec le contenu d'un fichier de test
        String sqlFilePath = TestCommande.class.getResource(filename).getFile();
        SqlFile sqlFile = new SqlFile(new File(sqlFilePath));

        sqlFile.setConnection(connexion);
        sqlFile.execute();
        sqlFile.closeReader();		
    }
    
    @After
    public void tearDown() throws IOException, SqlToolError, SQLException {
        myConnection.close();

    }
    /**
     * Test de connexion avec des comptes utilisateurs
     * selon leur email et customer ID
     * 
     * email correct / customer ID correct -> correct
     * email correct / customer ID incorrect -> incorrect
     * email inexistant / customer ID quelconque -> incorrect
     */
    @Test
    public void testConnexionUser() {
        
        String email = "jumboeagle@example.com";
        String emailIncorrect = "Email Incorrect";
        
        String customerID = "1";
        String customerIDIncorrect = "-1";
        
        // email correct / customer ID correct -> correct
        try {
            myDAO.logInUser(email, customerID);
            assertTrue(myDAO.logInUser(email, customerID));
        } catch (DAOException ex) {
            Logger.getLogger(TestConnexion.class.getName()).log(Level.SEVERE, null, ex);
            fail("ECHEC Test afficher commandes : échec sur un client existant"); 
        }
        
        // email correct / customer ID incorrect -> incorrect
        try {
            myDAO.logInUser(email, customerID);
            assertFalse(myDAO.logInUser(email, customerIDIncorrect));
        } catch (DAOException ex) {
            Logger.getLogger(TestConnexion.class.getName()).log(Level.SEVERE, null, ex);
            fail("ECHEC Test afficher commandes : échec de connexion"); 
        }
        
        // email inexistant / customer ID quelconque -> incorrect
        try {
            myDAO.logInUser(emailIncorrect, customerID);
            assertFalse(myDAO.logInUser(email, customerIDIncorrect));
        } catch (DAOException ex) {
            Logger.getLogger(TestConnexion.class.getName()).log(Level.SEVERE, null, ex);
            fail("ECHEC Test afficher commandes : échec de connexion"); 
        }
        
        
        //fail("STUB");
    } 
    
    /**
     * Test de connexion avec le compte admin
     * selon son id / password
     * 
     * id correct / password correct -> correct
     * id correct / password incorrect -> incorrect
     * id incorrect / password correct -> incorrect
     * 
     */
    @Test
    public void testConnexionAdmin() {
        
        String ID_ADMIN = "administrateur"; // 
        String PWD_ADMIN = "mdp_admin";
        
        String badID = "prout";
        String badPWD = "toto";
        
        assertTrue(myDAO.logInAdmin(ID_ADMIN, PWD_ADMIN));
        assertFalse(myDAO.logInAdmin(ID_ADMIN, badID));
        assertFalse(myDAO.logInAdmin(badPWD, PWD_ADMIN));
    }
}