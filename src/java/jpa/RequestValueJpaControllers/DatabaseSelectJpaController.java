/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.RequestValueJpaControllers;

import Entities.ValueEntities.DatabaseSelect;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import jpa.exceptions.NonexistentEntityException;
import jpa.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class DatabaseSelectJpaController implements Serializable {

    public DatabaseSelectJpaController(EntityManagerFactory emf) {

        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public DatabaseSelect create(DatabaseSelect databaseSelect) throws RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            em.persist(databaseSelect);
            em.flush();
            etx.commit();
        } catch (Exception ex) {
            try {
                etx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return databaseSelect;
    }

    public void edit(DatabaseSelect databaseSelect) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {

            em = getEntityManager();
            databaseSelect = em.merge(databaseSelect);

        } catch (Exception ex) {
            try {

            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = databaseSelect.getIdDatabaseSelect();
                if (findDatabaseSelect(id) == null) {
                    throw new NonexistentEntityException("The databaseSelect with id " + id + " no longer exists.");
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
            DatabaseSelect databaseSelect;
            try {
                databaseSelect = em.getReference(DatabaseSelect.class, id);
                databaseSelect.getIdDatabaseSelect();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The databaseSelect with id " + id + " no longer exists.", enfe);
            }
            em.remove(databaseSelect);

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

    public List<DatabaseSelect> findDatabaseSelectEntities() {
        return findDatabaseSelectEntities(true, -1, -1);
    }

    public List<DatabaseSelect> findDatabaseSelectEntities(int maxResults, int firstResult) {
        return findDatabaseSelectEntities(false, maxResults, firstResult);
    }

    private List<DatabaseSelect> findDatabaseSelectEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DatabaseSelect.class));
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

    public DatabaseSelect findDatabaseSelect(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DatabaseSelect.class, id);
        } finally {
            em.close();
        }
    }

    public int getDatabaseSelectCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DatabaseSelect> rt = cq.from(DatabaseSelect.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public DatabaseSelect findOrAdd(DatabaseSelect object) throws Exception {
        create(object);
        return object;
    }
    
}
