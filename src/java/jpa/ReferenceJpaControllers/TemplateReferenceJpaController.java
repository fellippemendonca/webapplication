/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.ReferenceJpaControllers;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.RollbackFailureException;
import Entities.ReferenceEntities.TemplateReference;
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
public class TemplateReferenceJpaController implements Serializable {

    public TemplateReferenceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TemplateReference create(TemplateReference templateReference) throws RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            em.persist(templateReference);
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
        return templateReference;
    }

    public void edit(TemplateReference templateReference) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {

            em = getEntityManager();
            templateReference = em.merge(templateReference);

        } catch (Exception ex) {
            try {

            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = templateReference.getIdTemplateReference();
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

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {

            em = getEntityManager();
            TemplateReference templateReference;
            try {
                templateReference = em.getReference(TemplateReference.class, id);
                templateReference.getIdTemplateReference();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The templateReference with id " + id + " no longer exists.", enfe);
            }
            em.remove(templateReference);

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

    public TemplateReference findTemplateReference(Integer id) {
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
    
    public List<TemplateReference> findByIdRequestReference(int id) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("TemplateReference.findByIdRequestReference");
        query.setParameter("idRequestReference", id);
        List<TemplateReference> templateReferenceList = (List<TemplateReference>) query.getResultList();
        try {
            return templateReferenceList;
        } finally {
            em.close();
        }
    }
    
}
