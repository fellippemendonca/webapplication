/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import Entities.Header;
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
public class HeaderJpaController implements Serializable {

    public HeaderJpaController(EntityManagerFactory emf) {

        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Header create(Header header) throws RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            em.persist(header);
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
        return header;
    }

    public void edit(Header header) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            header = em.merge(header);
        } catch (Exception ex) {
            try {
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = header.getIdHeader();
                if (findHeader(id) == null) {
                    throw new NonexistentEntityException("The header with id " + id + " no longer exists.");
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
            Header header;
            try {
                header = em.getReference(Header.class, id);
                header.getIdHeader();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The header with id " + id + " no longer exists.", enfe);
            }
            em.remove(header);
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

    public List<Header> findHeaderEntities() {
        return findHeaderEntities(true, -1, -1);
    }

    public List<Header> findHeaderEntities(int maxResults, int firstResult) {
        return findHeaderEntities(false, maxResults, firstResult);
    }

    private List<Header> findHeaderEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Header.class));
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

    public Header findHeader(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Header.class, id);
        } finally {
            em.close();
        }
    }
    
    public Header find(Header object) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Header.findByHeaderNameAndValue");
        query.setParameter("headerName", object.getHeaderName());
        query.setParameter("headerValue", object.getHeaderValue());
        List<Header> objectList = (List<Header>) query.getResultList();
        try {
            if (objectList.size() > 0) {
                return objectList.get(0);
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }

    public Header findOrAdd(Header object) throws Exception {
        if (find(object) != null) {
            return find(object);
        } else {
            
            return create(object);//find(object);
        }
    }

    public int getHeaderCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Header> rt = cq.from(Header.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<String> listHeaderNameEntities() {
        List<String> list = new ArrayList();
        for(Header m : findHeaderEntities(true, -1, -1)){
            if (list.contains(m.getHeaderName()) == false) {
                list.add(m.getHeaderName());
            }
        }
        return list;
    }
    public List<String> listHeaderValueEntities() {
        List<String> list = new ArrayList();
        for(Header m : findHeaderEntities(true, -1, -1)){
            if (list.contains(m.getHeaderValue()) == false) {
                list.add(m.getHeaderValue());
            }
        }
        return list;
    }
    
}
