/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.Scheduled;

import Entities.Scheduled.ScheduledRequest;
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
import jpa.Scheduled.exceptions.NonexistentEntityException;
import jpa.Scheduled.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class ScheduledRequestJpaController implements Serializable {

    public ScheduledRequestJpaController(/*UserTransaction utx,*/ EntityManagerFactory emf) {
        //this.utx = utx;
        this.emf = emf;
    }
    //private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ScheduledRequest create(ScheduledRequest scheduledRequest) throws RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            em.persist(scheduledRequest);
            em.flush();
            etx.commit();
        } catch (Exception ex) {
            try {
                etx.rollback();
            } catch (Exception re) {
                throw new jpa.exceptions.RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return scheduledRequest;
    }

    public void edit(ScheduledRequest scheduledRequest) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            scheduledRequest = em.merge(scheduledRequest);
            etx.commit();
        } catch (Exception ex) {
            try {
                etx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = scheduledRequest.getIdScheduledRequest();
                if (findScheduledRequest(id) == null) {
                    throw new NonexistentEntityException("The scheduledRequest with id " + id + " no longer exists.");
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
            ScheduledRequest scheduledRequest;
            try {
                scheduledRequest = em.getReference(ScheduledRequest.class, id);
                scheduledRequest.getIdScheduledRequest();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The scheduledRequest with id " + id + " no longer exists.", enfe);
            }
            em.remove(scheduledRequest);
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

    public List<ScheduledRequest> findScheduledRequestEntities() {
        return findScheduledRequestEntities(true, -1, -1);
    }

    public List<ScheduledRequest> findScheduledRequestEntities(int maxResults, int firstResult) {
        return findScheduledRequestEntities(false, maxResults, firstResult);
    }

    private List<ScheduledRequest> findScheduledRequestEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ScheduledRequest.class));
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

    public ScheduledRequest findScheduledRequest(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ScheduledRequest.class, id);
        } finally {
            em.close();
        }
    }
    
    public ScheduledRequest find(ScheduledRequest env) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("ScheduledRequest.findByIdRequestReference");
        query.setParameter("idRequestReference", env.getIdRequestReference());
        List<ScheduledRequest> scheduledRequestList = (List<ScheduledRequest>) query.getResultList();
        try{
        if(scheduledRequestList.size()>0){
            return scheduledRequestList.get(0);
        } else {
            return null;
        }
        }
        finally {
            em.close();
        }
    }
    
    public ScheduledRequest findOrAdd(ScheduledRequest scheduledRequest) throws Exception{
        if(find(scheduledRequest)!=null){
            return find(scheduledRequest);
        }else{
            return create(scheduledRequest);//find(environment);
        }
    }

    public int getScheduledRequestCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ScheduledRequest> rt = cq.from(ScheduledRequest.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
