/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

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
        myDataSource = /*DataSourceFactory.*/getDataSource();
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
      
        // nombre de commandes avant ajout
        try {
            int customerID = 2;
            int productID = 980001;
            List<PurchaseOrderEntity> commandes =
                    myDAO.rqtCommandes(customerID, null, null, 0, null);
            int nbCommandesAvant = commandes.size();
            
            // ajout d'une commande
            PurchaseOrderEntity commande = 
                    new PurchaseOrderEntity(2, customerID, productID, 3, 3,
                            "2017-11-22", "2017-11-22", "JFC");
            myDAO.ajoutCommande(commande);
             
            // nombre de commandes après ajout
            commandes = myDAO.rqtCommandes(customerID, null, null, 0, null);
            int nbCommandesApres = commandes.size();
             
            // Verif que la commande a été ajout 
            assertTrue(nbCommandesAvant + 1 == nbCommandesApres);
            
        } catch (DAOException ex) {
            Logger.getLogger(TestCommande.class.getName()).log(Level.SEVERE, null, ex);
            fail("Ajout commande echec : l'ajout à échouée !");
        }
       
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
            myDAO.suppressionCommande(commandes.get(0).getOrderNum());
             
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
