package tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.SQLException;
import javax.sql.DataSource;
import modele.DAO;
import modele.DAOException;
import modele.DataSourceFactory;
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


    @Before
    public void setUp() throws SQLException {
            myDataSource = DataSourceFactory.getDataSource();
            myDAO = new DAO(myDataSource);
    }
    
    /**
     * Test de connexion avec des comptes utilisateurs
     * selon leur email et customer ID
     */
    @Test
    public void testConnexionUser() {
        fail("STUB");
    } 
    
    /**
     * Test de connexion avec le compte admin
     * selon son id / password
     */
    @Test
    public void testConnexionAdmin() {
        fail("STUB");
    }
}