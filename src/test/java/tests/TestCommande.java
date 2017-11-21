/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import entitys.ProductEntity;
import entitys.PurchaseOrderEntity;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import modele.DAO;
import modele.DAOException;
import modele.DataSourceFactory;
import static org.junit.Assert.*;
import static org.junit.Assert.fail;
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
     * Tests pour afficher les commandes d'un client
     * 
     * affichage d'un client existant 
     * affichage d'un client inexistant
     * 
     */
    @Test
    public void testAfficherCommandes() {
        
        int customerIDExistant1 = 1;
        int customerIDExistant2 = 777;
        int customerIDNonExistant = -1;

        List<PurchaseOrderEntity> listeCommandes;
        int nbCommandes1 = 2;
        int nbCommandes2 = 1;
       
        // Test pour un premier client valide
        /*try {
             listeCommandes = myDAO.afficherCommandes(customerIDExistant1);
             assertEquals(listeCommandes.size(), nbCommandes1);
        } catch (DAOException ex) {
             Logger.getLogger(TestCommande.class.getName()).log(Level.SEVERE, null, ex);
             fail("ECHEC Test afficher commandes : échec sur un client existant"); 
        }
        
        // Test pour un second client valide
        try {
            listeCommandes = myDAO.afficherCommandes(customerIDExistant2);
            assertEquals(listeCommandes.size(), nbCommandes2);
        } catch (DAOException ex) {
            Logger.getLogger(TestCommande.class.getName()).log(Level.SEVERE, null, ex);
            fail("ECHEC Test afficher commandes : échec sur un client existant"); 
        }
        
        
        // Test pour un client non valide
        try {
            myDAO.afficherCommandes(customerIDNonExistant);
            fail("ECHEC Test afficher commandes : réussite sur un client inéxistant"); 
        } catch (DAOException ex) {
            Logger.getLogger(TestCommande.class.getName()).log(Level.SEVERE, null, ex);
        }*/
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
        fail("STUB");
       
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
        fail("STUB");
       
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
        fail("STUB");
       
    }
    
    
}
