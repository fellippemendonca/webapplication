/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.ValidationJPA;

import Entities.ValidationEntities.ValidationElement;
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
import jpa.ValidationJPA.exceptions.NonexistentEntityException;
import jpa.ValidationJPA.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class ValidationElementJpaController implements Serializable {

    public ValidationElementJpaController(EntityManagerFactory emf) {
        //this.utx = utx;
        this.emf = emf;
    }
    //private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ValidationElement validationElement) throws RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            em.persist(validationElement);
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
    }

    public void edit(ValidationElement validationElement) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            validationElement = em.merge(validationElement);
            etx.commit();
        } catch (Exception ex) {
            try {
                etx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = validationElement.getIdResponseValidationElement();
                if (findValidationElement(id) == null) {
                    throw new NonexistentEntityException("The validationElement with id " + id + " no longer exists.");
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
            em = getEntityManager();
            ValidationElement validationElement;
            try {
                validationElement = em.getReference(ValidationElement.class, id);
                validationElement.getIdResponseValidationElement();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The validationElement with id " + id + " no longer exists.", enfe);
            }
            em.remove(validationElement);
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

    public List<ValidationElement> findValidationElementEntities() {
        return findValidationElementEntities(true, -1, -1);
    }

    public List<ValidationElement> findValidationElementEntities(int maxResults, int firstResult) {
        return findValidationElementEntities(false, maxResults, firstResult);
    }

    private List<ValidationElement> findValidationElementEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ValidationElement.class));
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

    public ValidationElement findValidationElement(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ValidationElement.class, id);
        } finally {
            em.close();
        }
    }

    public int getValidationElementCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ValidationElement> rt = cq.from(ValidationElement.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<ValidationElement> findByIdValidationScenario(int id) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("ValidationElement.findByIdValidationScenario");
        query.setParameter("idValidationScenario", id);
        List<ValidationElement> validationElementList = (List<ValidationElement>) query.getResultList();
        try {
            return validationElementList;
        } finally {
            em.close();
        }
    }
    
}
