/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import java.sql.SQLException;
import javax.sql.DataSource;
import modele.DAO;
import modele.DataSourceFactory;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author afaure04
 */
public class TestCommande {
    
    private DAO myDAO; // L'objet Ã  tester
    private DataSource myDataSource; // La source de donnÃ©es Ã  utiliser


    @Before
    public void setUp() throws SQLException {
            myDataSource = DataSourceFactory.getDataSource();
            myDAO = new DAO(myDataSource);
    }

    
    /**
     * Tests quand utilisateur ajoute une commande 
     * 
     * ajout valide 
     * ajout invalide 
     * 
     * ajout valide avec produit indisponible
     */
    @Test
    public void testAjoutCommande() {
        
       
    }
    
    /**
     * Tests quand utilisateur modifie une commande
     * 
     * modif valide
     * modif invalide 
     * 
     * modif invalide -> livraison dÃ©jÃ  Ã©ffÃ©ctuÃ©e 
     * modif invalide -> quantitÃ© nÃ©gative
     * modif invalide -> produit inÃ©xistant 
     */
    @Test
    public void testModificationCommande() {
        
       
    }
    
    /**
     * Tests quand utilisateur supprimer une commande
     *
     * modif valide
     * modif invalide 
     * 
     * modif invalide -> livraison dÃ©jÃ  Ã©ffÃ©ctuÃ©e  
     */
    @Test
    public void testSuppressionCommande() {
        
       
    }
    
    
}
