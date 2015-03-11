/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import Entities.Store;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa.exceptions.NonexistentEntityException;
import jpa.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class StoreJpaController implements Serializable {

    public StoreJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Store create(Store store) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.persist(store);
            em.flush();
        } catch (Exception ex) {
            try {
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return store;
    }

    public void edit(Store store) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            store = em.merge(store);
        } catch (Exception ex) {
            try {
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = store.getIdStore();
                if (findStore(id) == null) {
                    throw new NonexistentEntityException("The store with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Store store;
            try {
                store = em.getReference(Store.class, id);
                store.getIdStore();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The store with id " + id + " no longer exists.", enfe);
            }
            em.remove(store);
        } catch (Exception ex) {
            try {
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Store> findStoreEntities() {
        return findStoreEntities(true, -1, -1);
    }

    public List<Store> findStoreEntities(int maxResults, int firstResult) {
        return findStoreEntities(false, maxResults, firstResult);
    }

    private List<Store> findStoreEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Store.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Store findStore(Integer id) {
        EntityManager em = getEntityManager();
        em.createNamedQuery(null);
        try {
            return em.find(Store.class, id);
        } finally {
            em.close();
        }
    }
    
    public List<Store> findStoreByID(String id) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Store.findByStoreCode");
        query.setParameter("storeCode", id);
        List<Store> stores = (List<Store>) query.getResultList();
        try {
            return stores;
        } finally {
            em.close();
        }
    }
    
    public List<Store> findStoreByName(String id) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Store.findByStoreName");
        query.setParameter("storeCode", id);
        List<Store> stores = (List<Store>) query.getResultList();
        try {
            return stores;
        } finally {
            em.close();
        }
    }
    

    public int getStoreCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Store> rt = cq.from(Store.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Store find(Store store) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Store.findByStoreCode");
        query.setParameter("storeCode", store.getStoreCode());
        List<Store> hostList = (List<Store>) query.getResultList();
        try{
        if(hostList.size()>0){
            return hostList.get(0);
        } else{
            return null;
        }
        }
        finally {
            em.close();
        }
    }
    
    public Store findOrAdd(Store store) throws RollbackFailureException, Exception{
        if(find(store)!=null){
            return find(store);
        }else{
            create(store);
            return find(store);
        }
    }
}
