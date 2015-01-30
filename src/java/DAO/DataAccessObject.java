/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Entities.Store;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import jpa.StoreJpaController;

/**
 *
 * @author fellippe.mendonca
 */
public class DataAccessObject {

    @PersistenceContext protected EntityManager   em;
    @Resource           private   UserTransaction utx;   
     
    EntityManagerFactory emf;
    
    
    public DataAccessObject(){
        this.emf = javax.persistence.Persistence.createEntityManagerFactory("ServletStatelessPU");
    }
    
    public String getStore (int i){
        Store store;
        StoreJpaController jpa = new StoreJpaController(utx,this.emf);
        store = jpa.findStore(i);
        return store.getStoreName();
    }
    
    public List<Store> findStoreByID (String i){
        Store store;
        StoreJpaController jpa = new StoreJpaController(utx,this.emf);
        List<Store> stores = jpa.findStoreByID(i);
        return stores;
    }
    
    
    
}