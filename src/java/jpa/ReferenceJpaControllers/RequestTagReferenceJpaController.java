/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.ReferenceJpaControllers;

import Entities.ReferenceEntities.RequestTagReference;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import jpa.ReferenceJpaControllers.exceptions.NonexistentEntityException;
import jpa.ReferenceJpaControllers.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class RequestTagReferenceJpaController implements Serializable {

    public RequestTagReferenceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RequestTagReference requestTagReference) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {

            em = getEntityManager();
            em.persist(requestTagReference);
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
    }

    public void edit(RequestTagReference requestTagReference) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {

            em = getEntityManager();
            requestTagReference = em.merge(requestTagReference);

        } catch (Exception ex) {
            try {

            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = requestTagReference.getIdrequestTagReference();
                if (findRequestTagReference(id) == null) {
                    throw new NonexistentEntityException("The requestTagReference with id " + id + " no longer exists.");
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
            RequestTagReference requestTagReference;
            try {
                requestTagReference = em.getReference(RequestTagReference.class, id);
                requestTagReference.getIdrequestTagReference();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The requestTagReference with id " + id + " no longer exists.", enfe);
            }
            em.remove(requestTagReference);
  
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

    public List<RequestTagReference> findRequestTagReferenceEntities() {
        return findRequestTagReferenceEntities(true, -1, -1);
    }

    public List<RequestTagReference> findRequestTagReferenceEntities(int maxResults, int firstResult) {
        return findRequestTagReferenceEntities(false, maxResults, firstResult);
    }

    private List<RequestTagReference> findRequestTagReferenceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RequestTagReference.class));
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

    public RequestTagReference findRequestTagReference(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RequestTagReference.class, id);
        } finally {
            em.close();
        }
    }

    public int getRequestTagReferenceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RequestTagReference> rt = cq.from(RequestTagReference.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<RequestTagReference> findByIdRequestReference(int id) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("RequestTagReference.findByIdRequestReference");
        query.setParameter("idRequestReference", id);
        List<RequestTagReference> requestTagReferenceList = (List<RequestTagReference>) query.getResultList();
        try {
            return requestTagReferenceList;
        } finally {
            em.close();
        }
    }
    
}
