/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.ReferenceJpaControllers;

import Entities.ReferenceEntities.RequestReference;
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
import jpa.ReferenceJpaControllers.exceptions.NonexistentEntityException;
import jpa.ReferenceJpaControllers.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class RequestReferenceJpaController implements Serializable {

    public RequestReferenceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public RequestReference create(RequestReference requestReference) throws RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            em.persist(requestReference);
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
        return requestReference;
    }

    public void edit(RequestReference requestReference) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            requestReference = em.merge(requestReference);
            etx.commit();
        } catch (Exception ex) {
            try {
                etx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = requestReference.getIdRequestReference();
                if (findRequestReference(id) == null) {
                    throw new NonexistentEntityException("The requestReference with id " + id + " no longer exists.");
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
            RequestReference requestReference;
            try {
                requestReference = em.getReference(RequestReference.class, id);
                requestReference.getIdRequestReference();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The requestReference with id " + id + " no longer exists.", enfe);
            }
            em.remove(requestReference);
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

    public List<RequestReference> findRequestReferenceEntities() {
        return findRequestReferenceEntities(true, -1, -1);
    }

    public List<RequestReference> findRequestReferenceEntities(int maxResults, int firstResult) {
        return findRequestReferenceEntities(false, maxResults, firstResult);
    }

    private List<RequestReference> findRequestReferenceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RequestReference.class));
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

    public RequestReference findRequestReference(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RequestReference.class, id);
        } finally {
            em.close();
        }
    }

    public int getRequestReferenceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RequestReference> rt = cq.from(RequestReference.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
