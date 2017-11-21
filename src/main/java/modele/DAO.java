/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import entitys.CustomerEntity;
import entitys.ProductEntity;
import entitys.PurchaseOrderEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Eman
 */
public class DAO {
    
    private final DataSource myDataSource;

    // identifiant / mot de passe de l'admin
    // TODO Optionnel : faire une table pour permettre plusieurs admins
    // TODO FAIRE ID / MDP PLUS FORT !
    private static final String ID_ADMIN = "administrateur"; // 
    private static final String PWD_ADMIN = "mdp_admin";
    
    /**
     *
     * @param dataSource la source de données à utiliser
     */
    public DAO(DataSource dataSource) {
            this.myDataSource = dataSource;
    }
    
    /**
     * Connexion d'un utilisateur selon la combinaison email / customerID
     * @param customerEmail l'email de cet utilisateur
     * @param customerID l'identifiant client de l'utilisateur
     * @return true si le couple email / customerID existe dans la base de donnée,
     *              soit que connexion réussit 
     *         false sinon
     * @throws modele.DAOException si une erreur survient lors du traitement BD 
     */
    public boolean logInUser(String customerEmail, int customerID) throws DAOException{
        
        CustomerEntity customer = null;
        
        // TODO Faire la requete SQL pour récupérer la ligne 
        // en fonction de l'email / customerID
        String sql = "SELECT * FROM CUSTOMER WHERE EMAIL = ? AND CUSTOMER_ID = ? ";
        try (Connection connection = myDataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, customerEmail);
            stmt.setInt(2, customerID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("NAME");
                    String email = rs.getString("EMAIL");
                    customer = new CustomerEntity(customerID, name, email);
                }
            }
        } catch(SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DAOException("Log in User : non implémenté");
        }
        
        return customer != null 
               && customer.getAddressLine1().equals(customerEmail) 
               && customer.getCustomerId() == customerID;
    }
    
    /**
     * Connexion de l'admin selon la combinaison pseudo / mot de passe
     * @param adminID identifiant de l'admin
     * @param adminPassword le mot de passe de l'admin
     * @return 
     */
    public boolean logInAdmin(String adminID, String adminPassword) {
        return adminID.equals(ID_ADMIN) && adminPassword.equals(PWD_ADMIN);
    }
    
    
    
    
    // TODO FAIRE METHODES DE COMMANDE
    
    /**
     * Affichage des commandes de ce client
     * 
     * On y affiche le produit commandé, la quantité, le prix unitaire (A VOIR),
     * le prix total, la date de vente / livraison, frais de livraison, 
     * 
     * @param customer le client des commandes à afficher 
     * @return la liste des commandes de ce client
     * @throws DAOException si une erreur survient lors de l'obtention des 
     *  commandes ou si le client n'existe pas
     */
    public List<PurchaseOrderEntity> afficherCommandes(/*CustomerEntity customer*/ int customerID)
            throws DAOException {
       
        // TODO : A VOIR POUR METTRE D'AUTRES ATT QUE CEUX DANS PURCHASE_ORDER
        List<PurchaseOrderEntity> listeCommandes = new ArrayList<PurchaseOrderEntity>();
       
        
        String sql = "SELECT * FROM PURCHASE_ORDER WHERE CUSTOMER_ID = ? ";
        try (Connection connection = myDataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, customerID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int orderNum = rs.getInt("OERDER_NUM");
                    int productID = rs.getInt("PRODUCT_ID");
                    int quantite = rs.getInt("QUANTITY");
                    int shippingCost = rs.getInt("SHIPPING_COST");
                    String salesDate = rs.getString("SALES_DATE");
                    String shippingDate = rs.getString("SHIPPING_DATE");
                    String freightCompany = rs.getString("FREIGHT_COMPANY");
                    
                    PurchaseOrderEntity commande = new PurchaseOrderEntity(
                            orderNum, customerID, productID, quantite, 
                            shippingCost, salesDate, shippingDate, 
                            freightCompany);
                    
                    listeCommandes.add(commande);
                }
            }
        } catch(SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DAOException("Afficher commandes");
        }
        
        
        return listeCommandes;
        //throw new DAOException("Afficher commandes : non implémenté");
        //return null; // STUB : TODO ECRIRE LE CODE
    }
    
    /**
     * Ajout d'une commande pour ce client, en fonction de :
     *  - numéro de produit (PRODUCT_ID)
     *  - la quantité (QUANTITY)
     *  - prix de livraison (SHIPPING_COST)
     *  - date de vente et date de livraison (SALES_DATE / SHIPPING_DATE)
     *  - compagnie de livraison (FREIGHT_COMPANY)
     * 
     * 
     * @param produit le produit de la commande à ajouter dans la BD 
     * @throws modele.DAOException si l'ajout de ce produit échoue 
     */
    public void ajoutCommande(ProductEntity produit) throws DAOException {
        
        // TODO ECRIRE LE CORPS 
        throw new DAOException("Ajout commande : non implémenté");
    }
    
    
    /**
     * Modification du produit de la commande avec le PRODUCT_ID de ce produit 
     * @param produit le produit de cette commande à modifier 
     * @throws DAOException si la modification de ce produit échoue
     */
    public void modificationCommande(ProductEntity produit) throws DAOException {
        
        // TODO ECRIRE LE CORPS 
        throw new DAOException("Modification commande : non implémenté");
    }
    
     
    /**
     * Suppression de ce produit 
     * @param produitID 
     * @throws DAOException si la suppression de ce produit échoue 
     */
    public void SuppressionCommande(int produitID) throws DAOException {
        
        // TODO ECRIRE LE CORPS 
        throw new DAOException("Suppression commande : non implémenté");
    }
    

}
