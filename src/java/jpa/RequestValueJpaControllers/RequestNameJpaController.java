/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.RequestValueJpaControllers;

import Entities.ValueEntities.RequestName;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import jpa.exceptions.NonexistentEntityException;
import jpa.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class RequestNameJpaController implements Serializable {

    public RequestNameJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public RequestName create(RequestName requestName) throws RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            em.persist(requestName);
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
        return requestName;
    }

    public void edit(RequestName requestName) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            requestName = em.merge(requestName);
            etx.commit();
        } catch (Exception ex) {
            try {
                etx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = requestName.getIdRequestName();
                if (findRequestName(id) == null) {
                    throw new NonexistentEntityException("The requestName with id " + id + " no longer exists.");
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
            
            RequestName requestName;
            try {
                requestName = em.getReference(RequestName.class, id);
                requestName.getIdRequestName();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The requestName with id " + id + " no longer exists.", enfe);
            }
            em.remove(requestName);
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

    public List<RequestName> findRequestNameEntities() {
        return findRequestNameEntities(true, -1, -1);
    }

    public List<RequestName> findRequestNameEntities(int maxResults, int firstResult) {
        return findRequestNameEntities(false, maxResults, firstResult);
    }

    private List<RequestName> findRequestNameEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RequestName.class));
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

    public RequestName findRequestName(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RequestName.class, id);
        } finally {
            em.close();
        }
    }

    public RequestName find(RequestName name) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("RequestName.findByRequestName");
        query.setParameter("requestName", name.getRequestName());
        List<RequestName> requestNameList = (List<RequestName>) query.getResultList();
        try {
            if (requestNameList.size() > 0) {
                return requestNameList.get(0);
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }

    public RequestName findOrAdd(RequestName requestName) throws Exception {
        if (find(requestName) != null) {
            return find(requestName);
        } else {
            return create(requestName);//find(environment);
        }
    }

    public int getRequestNameCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RequestName> rt = cq.from(RequestName.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
