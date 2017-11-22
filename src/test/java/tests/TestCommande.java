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
     * modif invalide -> livraison déjà  éfféctuée 
     * modif invalide -> quantité négative
     * modif invalide -> produit inéxistant 
     */
    @Test
    public void testModificationCommande() {
        fail("STUB");
       
    }
    
    /**
     * Tests quand utilisateur supprimer une commande  
     */
    @Test
    public void testSuppressionCommande() {;
        
        // nombre de commandes avant suppression 
        // suppression d'une commande
        // nombre de commandes après suppression
    
        // TODO Verif que la commande a été supprimé 

        
        fail("STUB");
       
    }
    
    /**
     * Tests de modifications des stocks d'un produit
     * 
     * ajout de stock
     * diminution du stock avec capacité suffisante
     * 
     * diminution de stock avec capacité insuffisante -> erreur
     */
    @Test 
    public void testModifStock () {
        
        int produitID = 988765;
        int qte = 25;

        int ajoutQte = 5;
        int retraitQte = 30;
      
        // ajout de stock
        try {
            myDAO.modificationStock(produitID, ajoutQte);
            qte += ajoutQte;
                    
            assertTrue( myDAO.getProduit(produitID).getQuantityOnHand() == qte);
        } catch (DAOException ex) {
            Logger.getLogger(TestCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // suppresion de stock avec capacité suffisante
        try {
            myDAO.modificationStock(produitID, retraitQte);
            qte -= retraitQte;
                    
            assertTrue( myDAO.getProduit(produitID).getQuantityOnHand() == qte);
        } catch (DAOException ex) {
            Logger.getLogger(TestCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // suppresion de stock avec capacité insuffisante
        try {
            myDAO.modificationStock(produitID, retraitQte);
            fail("Modification stock échec : suppression de stock avec capacité insuffisante !");
        } catch (DAOException ex) {
            Logger.getLogger(TestCommande.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    
}
