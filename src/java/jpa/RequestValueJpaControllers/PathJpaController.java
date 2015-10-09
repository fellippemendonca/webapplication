/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.RequestValueJpaControllers;

import Entities.ValueEntities.Path;
import java.io.Serializable;
import java.util.ArrayList;
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
public class PathJpaController implements Serializable {

    public PathJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Path create(Path path) throws RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            em.persist(path);
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
        return path;
    }

    public void edit(Path path) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            path = em.merge(path);
        } catch (Exception ex) {
            try {
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = path.getIdPath();
                if (findPath(id) == null) {
                    throw new NonexistentEntityException("The path with id " + id + " no longer exists.");
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
            Path path;
            try {
                path = em.getReference(Path.class, id);
                path.getIdPath();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The path with id " + id + " no longer exists.", enfe);
            }
            em.remove(path);
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

    public List<Path> findPathEntities() {
        return findPathEntities(true, -1, -1);
    }

    public List<Path> findPathEntities(int maxResults, int firstResult) {
        return findPathEntities(false, maxResults, firstResult);
    }

    private List<Path> findPathEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Path.class));
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

    public Path findPath(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Path.class, id);
        } finally {
            em.close();
        }
    }

    public int getPathCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Path> rt = cq.from(Path.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Path find(Path path) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Path.findByPathValue");
        query.setParameter("pathValue", path.getPathValue());
        List<Path> pathList = (List<Path>) query.getResultList();
        try {
            if (pathList.size() > 0) {
                return pathList.get(0);
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }

    public Path findOrAdd(Path path) throws Exception {
        if (find(path) != null) {
            return find(path);
        } else {
            return create(path);
        }
    }
    
    public List<String> listPathEntities() {
        List<String> list = new ArrayList();
        for(Path m : findPathEntities(true, -1, -1)){
            list.add(m.getPathValue());
        }
        return list;
    }
    
}
