/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.QueryReportJpaControllers;

import Entities.QueryReportEntities.QueryReportLog;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class QueryReportLogJpaController implements Serializable {

    public QueryReportLogJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public QueryReportLog create(QueryReportLog queryReportLog) throws RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();
            em.persist(queryReportLog);
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
        return queryReportLog;
    }

    public QueryReportLog edit(QueryReportLog queryReportLog) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();
            queryReportLog = em.merge(queryReportLog);
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
                Integer id = queryReportLog.getId();
                if (findQueryReportLog(id) == null) {
                    throw new NonexistentEntityException("The queryReportLog with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return queryReportLog;
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();
            QueryReportLog queryReportLog;
            try {
                queryReportLog = em.getReference(QueryReportLog.class, id);
                queryReportLog.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The queryReportLog with id " + id + " no longer exists.", enfe);
            }
            em.remove(queryReportLog);
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

    public List<QueryReportLog> findQueryReportLogEntities() {
        return findQueryReportLogEntities(true, -1, -1);
    }

    public List<QueryReportLog> findQueryReportLogEntities(int maxResults, int firstResult) {
        return findQueryReportLogEntities(false, maxResults, firstResult);
    }

    private List<QueryReportLog> findQueryReportLogEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(QueryReportLog.class));
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

    public QueryReportLog findQueryReportLog(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(QueryReportLog.class, id);
        } finally {
            em.close();
        }
    }
    
    
    public List<QueryReportLog> findQueryReportId(int queryReportId, String executionDate) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = null;
        try {
            convertedDate = (Date) formatter.parse(executionDate);
        } catch (ParseException ex) {
            Logger.getLogger(QueryReportLogJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("QueryReportLog.findByQueryReportId");
        query.setParameter("queryReportId", queryReportId);
        query.setParameter("executionDateTime", convertedDate);
        try {
            List<QueryReportLog> queryReportLogList = (List<QueryReportLog>) query.getResultList();
            return queryReportLogList;
        } catch(Exception e){
            System.out.println("failed to retrieve QueryReportLog.findByQueryReportId, "+e);
            return null;
        }
        finally {
            em.close();
        }
    }
    

    public int getQueryReportLogCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<QueryReportLog> rt = cq.from(QueryReportLog.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}