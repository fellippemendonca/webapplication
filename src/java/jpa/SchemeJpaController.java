/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import Entities.Scheme;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
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
public class SchemeJpaController implements Serializable {

    public SchemeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Scheme create(Scheme scheme) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.persist(scheme);
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
        return scheme;
    }

    public void edit(Scheme scheme) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            scheme = em.merge(scheme);
        } catch (Exception ex) {
            try {
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = scheme.getIdScheme();
                if (findScheme(id) == null) {
                    throw new NonexistentEntityException("The scheme with id " + id + " no longer exists.");
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
            Scheme scheme;
            try {
                scheme = em.getReference(Scheme.class, id);
                scheme.getIdScheme();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The scheme with id " + id + " no longer exists.", enfe);
            }
            em.remove(scheme);
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

    public List<Scheme> findSchemeEntities() {
        return findSchemeEntities(true, -1, -1);
    }

    public List<Scheme> findSchemeEntities(int maxResults, int firstResult) {
        return findSchemeEntities(false, maxResults, firstResult);
    }

    private List<Scheme> findSchemeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Scheme.class));
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

    public Scheme findScheme(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Scheme.class, id);
        } finally {
            em.close();
        }
    }

    public int getSchemeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Scheme> rt = cq.from(Scheme.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Scheme find(Scheme scheme) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Scheme.findBySchemeValue");
        query.setParameter("schemeValue", scheme.getSchemeValue());
        List<Scheme> schemeList = (List<Scheme>) query.getResultList();
        try {
            if (schemeList.size() > 0) {
                return schemeList.get(0);
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }

    public Scheme findOrAdd(Scheme scheme) throws Exception {
        if (find(scheme) != null) {
            return find(scheme);
        } else {
            create(scheme);
            return find(scheme);
        }
    }
    
    public List<String> listSchemeEntities() {
        List<String> list = new ArrayList();
        for(Scheme m : findSchemeEntities(true, -1, -1)){
            list.add(m.getSchemeValue());
        }
        return list;
    }
    
}
