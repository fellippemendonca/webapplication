/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.RequestReferenceJpaControllers;

import Entities.ReferenceEntities.HeaderReference;
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
public class HeaderReferenceJpaController implements Serializable {

    public HeaderReferenceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public HeaderReference create(HeaderReference headerReference) throws RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            em.persist(headerReference);
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
        return headerReference;
    }

    public void edit(HeaderReference headerReference) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {

            em = getEntityManager();
            headerReference = em.merge(headerReference);

        } catch (Exception ex) {
            try {

            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = headerReference.getIdHeaderReference();
                if (findHeaderReference(id) == null) {
                    throw new NonexistentEntityException("The headerReference with id " + id + " no longer exists.");
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
            HeaderReference headerReference;
            try {
                headerReference = em.getReference(HeaderReference.class, id);
                headerReference.getIdHeaderReference();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The headerReference with id " + id + " no longer exists.", enfe);
            }
            em.remove(headerReference);

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

    public List<HeaderReference> findHeaderReferenceEntities() {
        return findHeaderReferenceEntities(true, -1, -1);
    }

    public List<HeaderReference> findHeaderReferenceEntities(int maxResults, int firstResult) {
        return findHeaderReferenceEntities(false, maxResults, firstResult);
    }

    private List<HeaderReference> findHeaderReferenceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HeaderReference.class));
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

    public HeaderReference findHeaderReference(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HeaderReference.class, id);
        } finally {
            em.close();
        }
    }

    public int getHeaderReferenceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HeaderReference> rt = cq.from(HeaderReference.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<HeaderReference> findByIdRequestReference(int id) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("HeaderReference.findByIdRequestReference");
        query.setParameter("idRequestReference", id);
        List<HeaderReference> headerReferenceList = (List<HeaderReference>) query.getResultList();
        try {
            return headerReferenceList;
        } finally {
            em.close();
        }
    }
    
}
