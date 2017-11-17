/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import entitys.MicroMarketEntity;
import entitys.ProductEntity;
import entitys.PurchaseOrderEntity;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author Eman
 */
public class DAO {
    
    private final DataSource myDataSource;

    /**
     *
     * @param dataSource la source de données à utiliser
     */
    public DAO(DataSource dataSource) {
            this.myDataSource = dataSource;
    }
    
    public void connexionClient(String id, String mdp){
        
    }
    
    public void ConnexionAdmin(String id, String mdp){
        
    }
    
    public void ajoutCommande(){//Revoir Arguments
        
    }
    
    public void modifCommande(){//Revoir Arguments
        
    }
    
    public void suppressionCommande(int orderNum){
        
    }
    
    public List<PurchaseOrderEntity> commandesClient(int customerId, String dateDebut, String dateFin){
        return null;
    }
    
    public List<PurchaseOrderEntity> allCommandes(String dateDebut, String dateFin){
        return null;
    }
    
    public List<PurchaseOrderEntity> commandesParProduit(int productId, String dateDebut, String dateFin){
        return null;
    }
    
    public List<PurchaseOrderEntity> commandesParZone(String zipCode, String dateDebut, String dateFin){
        return null;     
    }

}
