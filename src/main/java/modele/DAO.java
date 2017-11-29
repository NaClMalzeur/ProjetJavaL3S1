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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        
        String name = null;
        
        // TODO Faire la requete SQL pour récupérer la ligne 
        // en fonction de l'email / customerID
        String sql = "SELECT * FROM CUSTOMER WHERE EMAIL = ? AND CUSTOMER_ID = ? ";
        try (Connection connection = myDataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, customerEmail);
            stmt.setInt(2, customerID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("NAME");
                }
            }
        } catch(SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DAOException("Log in User : non implémenté");
        }
        
        return name != null;
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
    
    /**
     * Affichage des commandes de ce client
     * 
     * On y affiche le produit commandé, la quantité, le prix unitaire (A VOIR),
     * le prix total, la date de vente / livraison, frais de livraison, 
     * 
     * @param customerID l'identifiant du client dont on affiche les commandes 
     * @param dateDebut date de début des commandes à afficher 
     * @param dateFin date de fin des commandes à afficher
     * @return la liste des commandes de ce client
     * @throws DAOException si une erreur survient lors de l'obtention des 
     *  commandes ou si le client n'existe pas
     */
    public List<PurchaseOrderEntity> afficherCommandes(/*int customerID, String dateDebut, String dateFin*/PreparedStatement stmt)
            throws DAOException {
       
        // TODO : A VOIR POUR METTRE D'AUTRES ATT QUE CEUX DANS PURCHASE_ORDER
        List<PurchaseOrderEntity> listeCommandes = new ArrayList<PurchaseOrderEntity>();
       
        
        //String sql = "SELECT * FROM PURCHASE_ORDER WHERE CUSTOMER_ID = ? AND SALES_DATE BETWEEN ? AND ?";
        //String sql = "SELECT * FROM PURCHASE_ORDER WHERE CUSTOMER_ID = ? AND SHIPPING_DATE BETWEEN > SYSDATE";
        try /*(Connection connection = myDataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql))*/ {

            /*stmt.setInt(1, customerID);
            stmt.setString(2, dateDebut);
            stmt.setString(3, dateFin);*/
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int orderNum = rs.getInt("ORDER_NUM");
                    int customerID = rs.getInt("CUSTOMER_ID");
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
     * 
     * @param customerId
     * @param dateDebut
     * @param dateFin
     * @param productId
     * @param zipCode
     * @return
     * @throws DAOException 
     */
    public List<PurchaseOrderEntity> rqtCommandes(int customerId, String dateDebut, String dateFin, int productId, String zipCode)
        throws DAOException {
        
        String query = "SELECT * FROM PURCHASE_ORDER po ";
        String comma = " WHERE ";
        
        if (zipCode!=null){
            query += " JOIN CUSTOMER cus ON po.CUSTOMER_ID = cus.CUSTOMER_ID ";
        }
        if (customerId != 0){
            query += comma + " po.CUSTOMER_ID = ? ";
            comma = " AND ";
        }
        if (dateDebut != null){
            query += comma + " po.SALES_DATE > ? ";
            comma = " AND ";
        }
        if (dateFin != null){
            query += comma + " po.SALES_DATE < ? ";
            comma = " AND ";
        }
        if (productId != 0){
            query += comma + " po.PRODUCT_ID = ? ";
            comma = " AND ";
        }
        if (zipCode!=null){
            query += comma + " cus.ZIP = ?";
            comma = " AND ";
        }
        
        query += " ORDER BY po.ORDER_NUM";
        
        try (Connection connection = myDataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)) {
            
            /*stmt.setString(1, dateDebut);
            stmt.setString(2, dateFin);
            */
            int index = 1;
            
            if (customerId != 0){
                stmt.setInt(index, customerId);
                index++;
            }
            if (dateDebut != null){
                stmt.setString(index, dateDebut);
                index++;
            }
            if (dateFin != null){
                stmt.setString(index, dateFin);
                index++;
            }
            if (productId != 0){
                stmt.setInt(index, productId);
                index++;
            }
            if (zipCode!=null){
                stmt.setString(index, zipCode);
                index++;
            }
            
            return afficherCommandes(stmt);
        } catch(SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DAOException("Afficher commandes");
        }
        
    }
    
    public List<ProductEntity> allProducts()
        throws DAOException {
        List<ProductEntity> listeProduct = new ArrayList<ProductEntity>();
        String query = "SELECT * FROM PRODUCT";
        try (Connection connection = myDataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)){

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt("PRODUCT_ID");
                    int manufacturerId = rs.getInt("MANUFACTURER_ID");
                    String productCode = rs.getString("PRODUCT_CODE");
                    float purchaseCode = rs.getFloat("PURCHASE_COST");
                    int quantityOnHand = rs.getInt("QUANTITY_ON_HAND");
                    float markup = rs.getFloat("MARKUP");
                    String description = rs.getString("DESCRIPTION");
                    
                    ProductEntity produit = new ProductEntity(
                            productId, manufacturerId, productCode, purchaseCode,
                            quantityOnHand, markup, description);
                    
                    listeProduct.add(produit);
                }
            }
        } catch(SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DAOException("All product : " + e.getMessage());
        }
        
        return listeProduct;
    }
    
    public List<CustomerEntity> allCustomers()
        throws DAOException {
        List<CustomerEntity> listeCustomer = new ArrayList<CustomerEntity>();
        String query = "SELECT * FROM CUSTOMER";
        try (Connection connection = myDataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)){

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int customerId = rs.getInt("CUSTOMER_ID");
                    String discountCode = rs.getString("DISCOUNT_CODE");
                    String zip = rs.getString("ZIP");
                    String name = rs.getString("NAME");
                    String addressLine1 = rs.getString("ADRESSLINE1");
                    String addressLine2 = rs.getString("ADRESSLINE2");
                    String city = rs.getString("CITY");
                    String state = rs.getString("STATE");
                    String phone = rs.getString("PHONE");
                    String fax = rs.getString("FAX");
                    String email = rs.getString("EMAIL");
                    int creditLimit = rs.getInt("CREDIT_LIMIT");
                    
                    
                    CustomerEntity customer = new CustomerEntity(
                            customerId, discountCode, zip, name, 
                            addressLine1, addressLine2, city, state, 
                            phone, fax, email, creditLimit);
                    
                    listeCustomer.add(customer);
                }
            }
        } catch(SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DAOException("All customers : " + e.getMessage());
        }
        
        
        return listeCustomer;
    }
    
    public List<String> allZipCodes()
        throws DAOException {
        List<String> listeCustomer = new ArrayList<String>();
        String query = "SELECT * FROM MICRO_MARKET";
        try (Connection connection = myDataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)){

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String zip = rs.getString("ZIP_CODE");
                    
                    
                    listeCustomer.add(zip);
                }
            }
        } catch(SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DAOException("All zip codes : " + e.getMessage());
        }
        
        
        return listeCustomer;
    }
    
    
    /**
     * @param produitID
     * @return l'entité produit associé à ce produitID
     * @throws DAOException 
     */
    public ProductEntity getProduit(int produitID) throws DAOException  {
        
        ProductEntity product = null;
        
        String rqtSql = "SELECT * "
                + "FROM PRODUCT "
                + "WHERE PRODUCT_ID = ?";
        
        try (Connection connection = myDataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(rqtSql)) {
            
            pstmt.setInt(1, produitID);
            
            pstmt.executeQuery();
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int manufacturerId = rs.getInt("MANUFACTURER_ID");
                    String productCode = rs.getString("PRODUCT_CODE");
                    float purchaseCode = rs.getFloat("PURCHASE_COST");
                    int quantityOnHand = rs.getInt("QUANTITY_ON_HAND");
                    float markup = rs.getFloat("MARKUP");
                    String description = rs.getString("DESCRIPTION");
                    
                    product = new ProductEntity(produitID, manufacturerId, 
                            productCode, purchaseCode, quantityOnHand, markup, 
                            description);
                }
            }
            
        } catch(SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DAOException("Get produit : " + e.getMessage());
        }
        
        return product;
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
    public void ajoutCommande(PurchaseOrderEntity commande) throws DAOException {
        
        String sql = "INSERT INTO PURCHASE_ORDER "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = myDataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            pstmt.setInt(1, commande.getOrderNum());
            pstmt.setInt(2, commande.getCustomerId());
            pstmt.setInt(3, commande.getProductId());
            pstmt.setInt(4, commande.getQuantity());
            pstmt.setFloat(5, commande.getShippingCost());
            pstmt.setDate(6, getDate(commande.getSalesDate()));
            pstmt.setDate(7, getDate(commande.getShippingDate()));
            pstmt.setString(8, commande.getFreightCompany());

            pstmt.executeUpdate();
            
            modificationStock(commande.getProductId(), -commande.getQuantity());
            
        } catch(SQLException | DAOException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DAOException(e.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("probleme de conversion de date !");
        }
    }
    
    /**
     * @param str la date à formater
     * @return L'objet Date sql issue d'une date sous la forme d'une string
     * @throws ParseException 
     */
    Date getDate(String str) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        java.util.Date date = dateFormat.parse(str);
            
        return new java.sql.Date(date.getTime());
    } 
 
     
    /**
     * Suppression de cette commande
     * @param commande 
     * @throws DAOException si la suppression de ce produit échoue 
     */
    public void suppressionCommande(PurchaseOrderEntity commande/*int order_num*/) throws DAOException {
        
        String sql = "DELETE FROM PURCHASE_ORDER " 
                    + "WHERE ORDER_NUM = ?";
        
        try (Connection connection = myDataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            pstmt.setInt(1, commande.getOrderNum());
            pstmt.executeUpdate();
            
            modificationStock(commande.getProductId(), commande.getQuantity());
            
        } catch(SQLException | DAOException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DAOException("Suppression commande : " + e.getMessage());
        }
    }
    
        /**
     * Modification de la commande avec le ORDER_NUM de cette commande
     * @param commande la commande à modifier 
     * @throws DAOException si la modification de ce produit échoue
     */
    public void modificationCommande(PurchaseOrderEntity commande) throws DAOException {
        String sql = "UPDATE PURCHASE_ORDER " 
                    + "SET PRODUCT_ID = ?, QUANTITY = ? "
                    + "WHERE ORDER_NUM = ?";
        
        try (Connection connection = myDataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            pstmt.setInt(1, commande.getProductId());
            pstmt.setInt(2, commande.getQuantity());
            pstmt.setInt(3, commande.getOrderNum());
            pstmt.executeUpdate();
            
        } catch(SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DAOException("Modification commande : " + e.getMessage());
        }
    }
    
    /**
     * Modification des stocks de ce produit
     * @param produitID le numéro du produit 
     * @param qte la quantité à ajouter ou retirer
     * @throws DAOException 
     */
    public void modificationStock (int produitID, int qte) throws DAOException{
        
        String rqtSql = "UPDATE PRODUCT "
                + "SET QUANTITY_ON_HAND = QUANTITY_ON_HAND + ?"
                + "WHERE PRODUCT_ID = ?";
        
        try (Connection connection = myDataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(rqtSql)) {
            
            pstmt.setInt(1, qte);
            pstmt.setInt(2, produitID);
            
            pstmt.executeUpdate();
            
        } catch(SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
            throw new DAOException("Modif stock : " + e.getMessage());
        }
    }

}
