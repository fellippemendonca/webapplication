/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.ReferenceJpaControllers;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.RollbackFailureException;
import Entities.ReferenceEntities.ParameterReference;
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

/**
 *
 * @author fellippe.mendonca
 */
public class ParameterReferenceJpaController implements Serializable {

    public ParameterReferenceJpaController(EntityManagerFactory emf) {

        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ParameterReference create(ParameterReference parameterReference) throws RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            em.persist(parameterReference);
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
        return parameterReference;
    }

    public void edit(ParameterReference parameterReference) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {

            em = getEntityManager();
            parameterReference = em.merge(parameterReference);
  
        } catch (Exception ex) {
            try {
    
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = parameterReference.getIdParameterReference();
                if (findParameterReference(id) == null) {
                    throw new NonexistentEntityException("The parameterReference with id " + id + " no longer exists.");
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
            ParameterReference parameterReference;
            try {
                parameterReference = em.getReference(ParameterReference.class, id);
                parameterReference.getIdParameterReference();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parameterReference with id " + id + " no longer exists.", enfe);
            }
            em.remove(parameterReference);

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

    public List<ParameterReference> findParameterReferenceEntities() {
        return findParameterReferenceEntities(true, -1, -1);
    }

    public List<ParameterReference> findParameterReferenceEntities(int maxResults, int firstResult) {
        return findParameterReferenceEntities(false, maxResults, firstResult);
    }

    private List<ParameterReference> findParameterReferenceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ParameterReference.class));
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

    public ParameterReference findParameterReference(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ParameterReference.class, id);
        } finally {
            em.close();
        }
    }

    public int getParameterReferenceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ParameterReference> rt = cq.from(ParameterReference.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<ParameterReference> findByIdRequestReference(int id) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("ParameterReference.findByIdRequestReference");
        query.setParameter("idRequestReference", id);
        List<ParameterReference> parameterReferenceList = (List<ParameterReference>) query.getResultList();
        try {
            return parameterReferenceList;
        } finally {
            em.close();
        }
    }
    
}
