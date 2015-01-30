/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import Entities.TemplateReference;
import Entities.TemplateReferencePK;
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
public class TemplateReferenceJpaController implements Serializable {

    public TemplateReferenceJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TemplateReference templateReference) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (templateReference.getTemplateReferencePK() == null) {
            templateReference.setTemplateReferencePK(new TemplateReferencePK());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(templateReference);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTemplateReference(templateReference.getTemplateReferencePK()) != null) {
                throw new PreexistingEntityException("TemplateReference " + templateReference + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TemplateReference templateReference) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            templateReference = em.merge(templateReference);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TemplateReferencePK id = templateReference.getTemplateReferencePK();
                if (findTemplateReference(id) == null) {
                    throw new NonexistentEntityException("The templateReference with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TemplateReferencePK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TemplateReference templateReference;
            try {
                templateReference = em.getReference(TemplateReference.class, id);
                templateReference.getTemplateReferencePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The templateReference with id " + id + " no longer exists.", enfe);
            }
            em.remove(templateReference);
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

    public List<TemplateReference> findTemplateReferenceEntities() {
        return findTemplateReferenceEntities(true, -1, -1);
    }

    public List<TemplateReference> findTemplateReferenceEntities(int maxResults, int firstResult) {
        return findTemplateReferenceEntities(false, maxResults, firstResult);
    }

    private List<TemplateReference> findTemplateReferenceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TemplateReference.class));
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

    public TemplateReference findTemplateReference(TemplateReferencePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TemplateReference.class, id);
        } finally {
            em.close();
        }
    }

    public int getTemplateReferenceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TemplateReference> rt = cq.from(TemplateReference.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
