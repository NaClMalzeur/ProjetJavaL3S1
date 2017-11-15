/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

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
    
    // TODO FAIRE METHODE DE LOGIN USER
    // TODO FAIRE METHODE DE LOGIN ADMIN
    
    // TODO FAIRE METHODES DE COMMANDE

}
