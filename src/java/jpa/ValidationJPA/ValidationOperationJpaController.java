/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.ValidationJPA;

import Entities.ValidationEntities.ValidationOperation;
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
public class ValidationOperationJpaController implements Serializable {

    public ValidationOperationJpaController(EntityManagerFactory emf) {
        //this.utx = utx;
        this.emf = emf;
    }
    //private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ValidationOperation validationOperation) throws RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            em.persist(validationOperation);
            em.flush();
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

    public void edit(ValidationOperation validationOperation) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            validationOperation = em.merge(validationOperation);
            etx.commit();
        } catch (Exception ex) {
            try {
                etx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = validationOperation.getIdValidationOperation();
                if (findValidationOperation(id) == null) {
                    throw new NonexistentEntityException("The validationOperation with id " + id + " no longer exists.");
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
            ValidationOperation validationOperation;
            try {
                validationOperation = em.getReference(ValidationOperation.class, id);
                validationOperation.getIdValidationOperation();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The validationOperation with id " + id + " no longer exists.", enfe);
            }
            em.remove(validationOperation);
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

    public List<ValidationOperation> findValidationOperationEntities() {
        return findValidationOperationEntities(true, -1, -1);
    }

    public List<ValidationOperation> findValidationOperationEntities(int maxResults, int firstResult) {
        return findValidationOperationEntities(false, maxResults, firstResult);
    }

    private List<ValidationOperation> findValidationOperationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ValidationOperation.class));
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

    public ValidationOperation findValidationOperation(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ValidationOperation.class, id);
        } finally {
            em.close();
        }
    }
    
    public ValidationOperation find(ValidationOperation operation) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("ValidationOperation.findByName");
        query.setParameter("name", operation.getName());
        List<ValidationOperation> operationList = (List<ValidationOperation>) query.getResultList();
        try{
        if(operationList.size()>0){
            return operationList.get(0);
        } else {
            return null;
        }
        }
        finally {
            em.close();
        }
    }

    public int getValidationOperationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ValidationOperation> rt = cq.from(ValidationOperation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
