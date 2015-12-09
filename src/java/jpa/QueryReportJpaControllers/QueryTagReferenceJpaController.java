/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.QueryReportJpaControllers;

import Entities.QueryReportEntities.QueryTagReference;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import jpa.QueryReportJpaControllers.exceptions.NonexistentEntityException;
import jpa.QueryReportJpaControllers.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class QueryTagReferenceJpaController implements Serializable {

    public QueryTagReferenceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public QueryTagReference create(QueryTagReference queryTagReference) throws RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();
            em.persist(queryTagReference);
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
        return queryTagReference;
    }

    public QueryTagReference edit(QueryTagReference queryTagReference) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();
            queryTagReference = em.merge(queryTagReference);
            em.flush();
            etx.commit();
        } catch (Exception ex) {
            try {
                etx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = queryTagReference.getId();
                if (findQueryTagReference(id) == null) {
                    throw new NonexistentEntityException("The queryTagReference with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return queryTagReference;
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();
            QueryTagReference queryTagReference;
            try {
                queryTagReference = em.getReference(QueryTagReference.class, id);
                queryTagReference.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The queryTagReference with id " + id + " no longer exists.", enfe);
            }
            em.remove(queryTagReference);
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
    }

    public boolean destroyByIdQueryReport(int id) {
        boolean success = false;
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("QueryTagReference.findByIdQueryReport");
        query.setParameter("idQueryReport", id);
        List<QueryTagReference> queryTagReferenceList = (List<QueryTagReference>) query.getResultList();
        try {
            for (QueryTagReference dead : queryTagReferenceList) {
                try {
                    destroy(dead.getId());
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(QueryTagReferenceJpaController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(QueryTagReferenceJpaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            success = true;
        } finally {
            em.close();
        }
        return success;
    }

    public List<QueryTagReference> findQueryTagReferenceEntities() {
        return findQueryTagReferenceEntities(true, -1, -1);
    }

    public List<QueryTagReference> findQueryTagReferenceEntities(int maxResults, int firstResult) {
        return findQueryTagReferenceEntities(false, maxResults, firstResult);
    }

    private List<QueryTagReference> findQueryTagReferenceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(QueryTagReference.class));
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

    public QueryTagReference findQueryTagReference(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(QueryTagReference.class, id);
        } finally {
            em.close();
        }
    }

    public int getQueryTagReferenceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<QueryTagReference> rt = cq.from(QueryTagReference.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<QueryTagReference> findByIdQueryReport(int id) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("QueryTagReference.findByIdQueryReport");
        query.setParameter("idQueryReport", id);
        List<QueryTagReference> queryTagReferenceList = (List<QueryTagReference>) query.getResultList();
        try {
            return queryTagReferenceList;
        } finally {
            em.close();
        }
    }

}
