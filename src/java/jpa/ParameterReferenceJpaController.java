/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import Entities.ParameterReference;
import Entities.ParameterReferencePK;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import jpa.exceptions.NonexistentEntityException;
import jpa.exceptions.PreexistingEntityException;
import jpa.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class ParameterReferenceJpaController implements Serializable {

    public ParameterReferenceJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ParameterReference parameterReference) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (parameterReference.getParameterReferencePK() == null) {
            parameterReference.setParameterReferencePK(new ParameterReferencePK());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(parameterReference);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findParameterReference(parameterReference.getParameterReferencePK()) != null) {
                throw new PreexistingEntityException("ParameterReference " + parameterReference + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ParameterReference parameterReference) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            parameterReference = em.merge(parameterReference);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ParameterReferencePK id = parameterReference.getParameterReferencePK();
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

    public void destroy(ParameterReferencePK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ParameterReference parameterReference;
            try {
                parameterReference = em.getReference(ParameterReference.class, id);
                parameterReference.getParameterReferencePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parameterReference with id " + id + " no longer exists.", enfe);
            }
            em.remove(parameterReference);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
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

    public ParameterReference findParameterReference(ParameterReferencePK id) {
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
    
}
