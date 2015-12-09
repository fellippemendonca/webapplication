/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.RequestValueJpaControllers;

import Entities.ValueEntities.RequestDescription;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa.RequestValueJpaControllers.exceptions.NonexistentEntityException;
import jpa.RequestValueJpaControllers.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class RequestDescriptionJpaController implements Serializable {

    public RequestDescriptionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public RequestDescription create(RequestDescription requestDescription) throws RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            em.persist(requestDescription);
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
        return requestDescription;
    }

    public void edit(RequestDescription requestDescription) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            requestDescription = em.merge(requestDescription);
            etx.commit();
        } catch (Exception ex) {
            try {
                etx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = requestDescription.getIdRequestDescription();
                if (findRequestDescription(id) == null) {
                    throw new NonexistentEntityException("The requestDescription with id " + id + " no longer exists.");
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
            RequestDescription requestDescription;
            try {
                requestDescription = em.getReference(RequestDescription.class, id);
                requestDescription.getIdRequestDescription();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The requestDescription with id " + id + " no longer exists.", enfe);
            }
            em.remove(requestDescription);
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

    public List<RequestDescription> findRequestDescriptionEntities() {
        return findRequestDescriptionEntities(true, -1, -1);
    }

    public List<RequestDescription> findRequestDescriptionEntities(int maxResults, int firstResult) {
        return findRequestDescriptionEntities(false, maxResults, firstResult);
    }

    private List<RequestDescription> findRequestDescriptionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RequestDescription.class));
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

    public RequestDescription findRequestDescription(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RequestDescription.class, id);
        } finally {
            em.close();
        }
    }
    
    public RequestDescription find(RequestDescription description) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("RequestDescription.findByRequestDescription");
        query.setParameter("requestDescription", description.getRequestDescription());
        List<RequestDescription> requestNameList = (List<RequestDescription>) query.getResultList();
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
    
    public RequestDescription findOrAdd(RequestDescription requestDescription) throws Exception {
        if (find(requestDescription) != null) {
            return find(requestDescription);
        } else {
            return create(requestDescription);
        }
    }

    public int getRequestDescriptionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RequestDescription> rt = cq.from(RequestDescription.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
