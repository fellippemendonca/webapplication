/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.QueryReportJpaControllers;

import Entities.QueryReportEntities.QueryReport;
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
import jpa.QueryReportJpaControllers.exceptions.NonexistentEntityException;
import jpa.QueryReportJpaControllers.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class QueryReportJpaController implements Serializable {

    public QueryReportJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public QueryReport create(QueryReport queryReport) throws RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();
            em.persist(queryReport);
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
        return queryReport;
    }

    public QueryReport edit(QueryReport queryReport) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();
            queryReport = em.merge(queryReport);
            etx.commit();
        } catch (Exception ex) {
            try {
                etx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = queryReport.getId();
                if (findQueryReport(id) == null) {
                    throw new NonexistentEntityException("The queryReport with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return queryReport;
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();
            QueryReport queryReport;
            try {
                queryReport = em.getReference(QueryReport.class, id);
                queryReport.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The queryReport with id " + id + " no longer exists.", enfe);
            }
            em.remove(queryReport);
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

    public List<QueryReport> findQueryReportEntities() {
        return findQueryReportEntities(true, -1, -1);
    }

    public List<QueryReport> findQueryReportEntities(int maxResults, int firstResult) {
        return findQueryReportEntities(false, maxResults, firstResult);
    }

    private List<QueryReport> findQueryReportEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(QueryReport.class));
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

    public QueryReport findQueryReport(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(QueryReport.class, id);
        } finally {
            em.close();
        }
    }

    public int getQueryReportCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<QueryReport> rt = cq.from(QueryReport.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}