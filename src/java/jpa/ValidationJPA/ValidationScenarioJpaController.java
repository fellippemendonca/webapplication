/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.ValidationJPA;

import Entities.ValidationEntities.ValidationScenario;
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
import jpa.ValidationJPA.exceptions.PreexistingEntityException;
import jpa.ValidationJPA.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class ValidationScenarioJpaController implements Serializable {

    public ValidationScenarioJpaController(EntityManagerFactory emf) {
        //this.utx = utx;
        this.emf = emf;
    }
    //private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ValidationScenario validationScenario) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            em.persist(validationScenario);
            em.flush();
            etx.commit();
        } catch (Exception ex) {
            try {
                etx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findValidationScenario(validationScenario.getIdValidationScenario()) != null) {
                throw new PreexistingEntityException("ValidationScenario " + validationScenario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ValidationScenario validationScenario) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            validationScenario = em.merge(validationScenario);
            etx.commit();
        } catch (Exception ex) {
            try {
                etx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = validationScenario.getIdValidationScenario();
                if (findValidationScenario(id) == null) {
                    throw new NonexistentEntityException("The validationScenario with id " + id + " no longer exists.");
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
            ValidationScenario validationScenario;
            try {
                validationScenario = em.getReference(ValidationScenario.class, id);
                validationScenario.getIdValidationScenario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The validationScenario with id " + id + " no longer exists.", enfe);
            }
            em.remove(validationScenario);
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

    public List<ValidationScenario> findValidationScenarioEntities() {
        return findValidationScenarioEntities(true, -1, -1);
    }

    public List<ValidationScenario> findValidationScenarioEntities(int maxResults, int firstResult) {
        return findValidationScenarioEntities(false, maxResults, firstResult);
    }

    private List<ValidationScenario> findValidationScenarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ValidationScenario.class));
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

    public ValidationScenario findValidationScenario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ValidationScenario.class, id);
        } finally {
            em.close();
        }
    }

    public int getValidationScenarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ValidationScenario> rt = cq.from(ValidationScenario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<ValidationScenario> findByIdRequestReference(int id) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("ValidationScenario.findByIdRequestReference");
        query.setParameter("idRequestReference", id);
        List<ValidationScenario> validationScenarioList = (List<ValidationScenario>) query.getResultList();
        try {
            return validationScenarioList;
        } finally {
            em.close();
        }
    }
    
}
