/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import Entities.Method;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa.exceptions.NonexistentEntityException;
import jpa.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class MethodJpaController implements Serializable {

    public MethodJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Method create(Method method) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.persist(method);
            em.flush();
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
        return method;
    }

    public void edit(Method method) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            method = em.merge(method);
        } catch (Exception ex) {
            try {
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = method.getIdMethod();
                if (findMethod(id) == null) {
                    throw new NonexistentEntityException("The method with id " + id + " no longer exists.");
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
            Method method;
            try {
                method = em.getReference(Method.class, id);
                method.getIdMethod();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The method with id " + id + " no longer exists.", enfe);
            }
            em.remove(method);
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

    public List<Method> findMethodEntities() {
        return findMethodEntities(true, -1, -1);
    }

    public List<Method> findMethodEntities(int maxResults, int firstResult) {
        return findMethodEntities(false, maxResults, firstResult);
    }

    private List<Method> findMethodEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Method.class));
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

    public Method findMethod(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Method.class, id);
        } finally {
            em.close();
        }
    }

    public int getMethodCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Method> rt = cq.from(Method.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Method find(Method method) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Method.findByMethodValue");
        query.setParameter("methodValue", method.getMethodValue());
        List<Method> methodList = (List<Method>) query.getResultList();
        try {
            if (methodList.size() > 0) {
                return methodList.get(0);
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }

    public Method findOrAdd(Method method) throws Exception {
        if (find(method) != null) {
            return find(method);
        } else {
            
            return create(method);//find(method);
        }
    }
    
    public List<String> listMethodEntities() {
        List<String> list = new ArrayList();
        for(Method m : findMethodEntities(true, -1, -1)){
            list.add(m.getMethodValue());
        }
        return list;
    }
}
