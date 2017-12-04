/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import entitys.ProductEntity;
import entitys.PurchaseOrderEntity;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import static org.junit.Assert.*;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.After;

/**
 *
 * @author afaure04
 */
public class TestCommande {
    
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
    
    @Test
    public void testAllProduits() {
        List<ProductEntity> produits;
        ProductEntity toFind;
        int produitID = 980001;
        int produitIDInexistant = -1;
        
        try {
            produits = myDAO.allProducts();
            toFind = null;
            for(ProductEntity p : produits) 
                if(p.getProductId() == produitID) 
                    toFind = p;
            
            assertNotNull(toFind);
            
            
            toFind = null;
            for(ProductEntity p : produits) 
                if(p.getProductId() == produitIDInexistant) 
                    toFind = p;
            assertNull(toFind);
            
        } catch (DAOException ex) {
            Logger.getLogger(TestCommande.class.getName()).log(Level.SEVERE, null, ex);
            fail("All produits : " + ex.getMessage());
        }
        
        
    
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
        try {
             listeCommandes = myDAO.rqtCommandes(customerIDExistant1, null, null, 0, null);
             assertEquals(listeCommandes.size(), nbCommandes1);
        } catch (DAOException ex) {
             Logger.getLogger(TestCommande.class.getName()).log(Level.SEVERE, null, ex);
             fail("ECHEC Test afficher commandes : échec sur un client existant"); 
        }
        
        // Test pour un second client valide
        try {
            listeCommandes = myDAO.rqtCommandes(customerIDExistant2, null, null, 0, null);
            assertEquals(listeCommandes.size(), nbCommandes2);
        } catch (DAOException ex) {
            Logger.getLogger(TestCommande.class.getName()).log(Level.SEVERE, null, ex);
            fail("ECHEC Test afficher commandes : échec sur un client existant"); 
        }
        
        
        // Test pour un client non valide
        try {
            listeCommandes = myDAO.rqtCommandes(customerIDNonExistant, null, null, 0, null);
            if (!listeCommandes.isEmpty())
                fail("ECHEC Test afficher commandes : des lignes sur un client inéxistant " + listeCommandes.size()); 
        } catch (DAOException ex) {
            Logger.getLogger(TestCommande.class.getName()).log(Level.SEVERE, null, ex);
            fail("ECHEC Test afficher commandes : échec sur un client inéxistant");
        }
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
      
        // nombre de commandes avant ajout
        try {
            int customerID = 2;
            int productID = 980001;
            List<PurchaseOrderEntity> commandes =
                    myDAO.rqtCommandes(customerID, null, null, 0, null);
            int nbCommandesAvant = commandes.size();
            
            // ajout d'une commande
            PurchaseOrderEntity commande = 
                    new PurchaseOrderEntity(1111, customerID, productID, 3, 3,
                            "2017-11-22", "2017-11-22", "JFC");
            myDAO.ajoutCommande(commande);
             
            // nombre de commandes après ajout
            commandes = myDAO.rqtCommandes(customerID, null, null, 0, null);
            int nbCommandesApres = commandes.size();
             
            // Verif que la commande a été ajout 
            assertTrue(nbCommandesAvant + 1 == nbCommandesApres);
            
        } catch (DAOException ex) {
            Logger.getLogger(TestCommande.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
       
    }
    
    /**
     * Tests quand utilisateur modifie une commande
     * 
     * modif valide
     * modif invalide 
     * 
     * modif invalide -> quantité négative
     * modif invalide -> quantité indisponible
     * modif invalide -> produit inéxistant 
     */
    @Test
    public void testModificationCommande() {
        
        int customerID;
        int productID;
        int qte;
        List<PurchaseOrderEntity> commandes;
        PurchaseOrderEntity commandeModifiee;
        
        // modification valide
        try {
            customerID = 3;
            productID = 980030; // qté = 250   
            qte = 200;
            
            commandes = myDAO.rqtCommandes(customerID, null, null, 0, null);
            
            ProductEntity produit = myDAO.getProduit(productID);
            
            commandeModifiee = commandes.get(0);
            commandeModifiee.setProductId(productID);
            commandeModifiee.setQuantity(qte);
            
            myDAO.modificationCommande(commandeModifiee);
            
            commandes = myDAO.rqtCommandes(customerID, null, null, 0, null);
            
            // vérification commande à la bonne quantité et stock baissé
            assertTrue(commandes.get(0).getProductId() == commandeModifiee.getProductId()
                    && commandes.get(0).getQuantity() == qte
                    /*&& myDAO.getProduit(productID).getQuantityOnHand() == produit.getQuantityOnHand() - qte*/);
           
            
        } catch (DAOException ex) {
            Logger.getLogger(TestCommande.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        } 
    }
    
    /**
     * 
     */
    @Test
    public void testModificationCommandeFail() {
        int customerID;
        int productID;
        int qte;
        List<PurchaseOrderEntity> commandes;
        PurchaseOrderEntity commandeModifiee;
        
         // quantité négative
        try {
            customerID = 3;
            productID = 980030;  
            qte = -5;
            
           commandes = myDAO.rqtCommandes(customerID, null, null, 0, null);
            
            commandeModifiee = commandes.get(0);
            commandeModifiee.setProductId(productID);
            commandeModifiee.setQuantity(qte);
            
            myDAO.modificationCommande(commandeModifiee);
            
            fail("Modification commande : échec sur quantité négative !");
            
        } catch (DAOException ex) {
            Logger.getLogger(TestCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // quantité indisponible
        /*try {
            customerID = 3;
            productID = 980030;  
            qte = 251;
            
            commandes = myDAO.rqtCommandes(customerID, null, null, 0, null);
            
            commandeModifiee = commandes.get(0);
            commandeModifiee.setProductId(productID);
            commandeModifiee.setQuantity(qte);
            
            myDAO.modificationCommande(commandeModifiee);
            
            fail("Modification commande : échec sur quantité indisponible !");
            
        } catch (DAOException ex) {
            Logger.getLogger(TestCommande.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        // produit inéxistant
        try {
            customerID = 1;
            productID = -1;
            qte = 1;
            
            commandes = myDAO.rqtCommandes(customerID, null, null, 0, null);

            commandeModifiee = commandes.get(0);
            commandeModifiee.setProductId(productID);
            commandeModifiee.setQuantity(qte);
            
            
            myDAO.modificationCommande(commandeModifiee);
            fail("Modification commande : échec sur  produit inéxistant!");
            
        } catch (DAOException ex) {
            Logger.getLogger(TestCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Tests quand utilisateur supprime une commande  
     */
    @Test
    public void testSuppressionCommande() {;
        
        // nombre de commandes avant suppression
        try {
            int customerID = 36;
            List<PurchaseOrderEntity> commandes =
                    myDAO.rqtCommandes(customerID, null, null, 0, null);
            int nbCommandesAvant = commandes.size();
            
            // suppression d'une commande
            myDAO.suppressionCommande(commandes.get(0));
             
            // nombre de commandes après suppression
            commandes = myDAO.rqtCommandes(customerID, null, null, 0, null);
            int nbCommandesApres = commandes.size();
             
            // Verif que la commande a été supprimé 
            assertTrue(nbCommandesAvant == nbCommandesApres + 1);
            
        } catch (DAOException ex) {
            Logger.getLogger(TestCommande.class.getName()).log(Level.SEVERE, null, ex);
            fail("Suppression commande echec : la suppression à échouée !");
        }
    }
    
}
