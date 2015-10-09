/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.RequestValueJpaControllers;

import Entities.ValueEntities.DynamicInputData;
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
public class DynamicInputDataJpaController implements Serializable {

    public DynamicInputDataJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public DynamicInputData create(DynamicInputData dynamicInputData) throws RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            em.persist(dynamicInputData);
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
        return dynamicInputData;
    }

    public void edit(DynamicInputData dynamicInputData) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            em = getEntityManager();
            dynamicInputData = em.merge(dynamicInputData);
            em.flush();
            etx.commit();
        } catch (Exception ex) {
            try {
                etx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dynamicInputData.getIdDynamicInputData();
                if (findDynamicInputData(id) == null) {
                    throw new NonexistentEntityException("The dynamicInputData with id " + id + " no longer exists.");
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
            DynamicInputData dynamicInputData;
            try {
                dynamicInputData = em.getReference(DynamicInputData.class, id);
                dynamicInputData.getIdDynamicInputData();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dynamicInputData with id " + id + " no longer exists.", enfe);
            }
            em.remove(dynamicInputData);
            //em.flush();
            etx.commit();
        } catch (NonexistentEntityException ex) {
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

    public List<DynamicInputData> findDynamicInputDataEntities() {
        return findDynamicInputDataEntities(true, -1, -1);
    }

    public List<DynamicInputData> findDynamicInputDataEntities(int maxResults, int firstResult) {
        return findDynamicInputDataEntities(false, maxResults, firstResult);
    }

    private List<DynamicInputData> findDynamicInputDataEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DynamicInputData.class));
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

    public DynamicInputData findDynamicInputData(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DynamicInputData.class, id);
        } finally {
            em.close();
        }
    }

    public int getDynamicInputDataCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DynamicInputData> rt = cq.from(DynamicInputData.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public DynamicInputData findByIdRequestReference(int id) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("DynamicInputData.findByIdRequestReference");
        query.setParameter("idRequestReference", id);
        List<DynamicInputData> dynamicInputDataList = (List<DynamicInputData>) query.getResultList();
        try {
            if (dynamicInputDataList.size() > 0) {
                return dynamicInputDataList.get(0);
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }
}
