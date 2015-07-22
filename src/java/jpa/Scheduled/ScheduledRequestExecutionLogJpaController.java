/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.Scheduled;

import Entities.Scheduled.ScheduledRequestExecutionLog;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa.Scheduled.exceptions.NonexistentEntityException;
import jpa.Scheduled.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class ScheduledRequestExecutionLogJpaController implements Serializable {

    public ScheduledRequestExecutionLogJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ScheduledRequestExecutionLog scheduledRequestExecutionLog) throws RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            em.persist(scheduledRequestExecutionLog);
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
    }

    public void edit(ScheduledRequestExecutionLog scheduledRequestExecutionLog) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            scheduledRequestExecutionLog = em.merge(scheduledRequestExecutionLog);
            etx.commit();
        } catch (Exception ex) {
            try {
                etx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = scheduledRequestExecutionLog.getIdScheduledRequestExecutionLog();
                if (findScheduledRequestExecutionLog(id) == null) {
                    throw new NonexistentEntityException("The scheduledRequestExecutionLog with id " + id + " no longer exists.");
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
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            ScheduledRequestExecutionLog scheduledRequestExecutionLog;
            try {
                scheduledRequestExecutionLog = em.getReference(ScheduledRequestExecutionLog.class, id);
                scheduledRequestExecutionLog.getIdScheduledRequestExecutionLog();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The scheduledRequestExecutionLog with id " + id + " no longer exists.", enfe);
            }
            em.remove(scheduledRequestExecutionLog);
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

    public List<ScheduledRequestExecutionLog> findScheduledRequestExecutionLogEntities() {
        return findScheduledRequestExecutionLogEntities(true, -1, -1);
    }

    public List<ScheduledRequestExecutionLog> findScheduledRequestExecutionLogEntities(int maxResults, int firstResult) {
        return findScheduledRequestExecutionLogEntities(false, maxResults, firstResult);
    }

    private List<ScheduledRequestExecutionLog> findScheduledRequestExecutionLogEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ScheduledRequestExecutionLog.class));
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

    public ScheduledRequestExecutionLog findScheduledRequestExecutionLog(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ScheduledRequestExecutionLog.class, id);
        } finally {
            em.close();
        }
    }

    public List<ScheduledRequestExecutionLog> findByIdAndDate(int idRequestReference, String executionDate) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = (Date) formatter.parse(executionDate);

        Calendar c = Calendar.getInstance();
        c.setTime(convertedDate);
        c.add(Calendar.DATE, 1);
        Date dayAfter = c.getTime();

        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("ScheduledRequestExecutionLog.findByExecutionDateAndId");
        query.setParameter("idRequestReference", idRequestReference);
        query.setParameter("executionDate", convertedDate);
        query.setParameter("dayAfter", dayAfter);

        List<ScheduledRequestExecutionLog> scheduledRequestExecutionLogList = (List<ScheduledRequestExecutionLog>) query.getResultList();
        try {
            if (scheduledRequestExecutionLogList.size() > 0) {
                return scheduledRequestExecutionLogList;
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }
    
    public ScheduledRequestExecutionLog findLastExecutionByIdAndDate(int idRequestReference, String executionDate){
        try {
            return findByIdAndDate(idRequestReference, executionDate).get(0);
        } catch (ParseException ex) {
            Logger.getLogger(ScheduledRequestExecutionLogJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getScheduledRequestExecutionLogCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ScheduledRequestExecutionLog> rt = cq.from(ScheduledRequestExecutionLog.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
