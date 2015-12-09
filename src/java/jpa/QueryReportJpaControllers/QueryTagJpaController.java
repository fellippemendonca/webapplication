/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.QueryReportJpaControllers;

import Entities.QueryReportEntities.QueryTag;
import JsonObjects.Tags.JsonTag;
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
import jpa.QueryReportJpaControllers.exceptions.NonexistentEntityException;
import jpa.QueryReportJpaControllers.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class QueryTagJpaController implements Serializable {

    public QueryTagJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public QueryTag create(QueryTag queryTag) throws RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();
            em.persist(queryTag);
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
        return queryTag;
    }

    public QueryTag edit(QueryTag queryTag) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();
            queryTag = em.merge(queryTag);
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
                Integer id = queryTag.getId();
                if (findQueryTag(id) == null) {
                    throw new NonexistentEntityException("The queryTag with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return queryTag;
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction etx = em.getTransaction();
        try {
            etx.begin();
            QueryTag queryTag;
            try {
                queryTag = em.getReference(QueryTag.class, id);
                queryTag.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The queryTag with id " + id + " no longer exists.", enfe);
            }
            em.remove(queryTag);
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

    public List<QueryTag> findQueryTagEntities() {
        return findQueryTagEntities(true, -1, -1);
    }

    public List<QueryTag> findQueryTagEntities(int maxResults, int firstResult) {
        return findQueryTagEntities(false, maxResults, firstResult);
    }

    private List<QueryTag> findQueryTagEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(QueryTag.class));
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

    public QueryTag findQueryTag(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(QueryTag.class, id);
        } finally {
            em.close();
        }
    }
    
    public int getQueryTagCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<QueryTag> rt = cq.from(QueryTag.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public QueryTag find(QueryTag queryTag) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("QueryTag.findByName");
        query.setParameter("name", queryTag.getName());
        List<QueryTag> queryTagList = (List<QueryTag>) query.getResultList();
        try {
            if (queryTagList.size() > 0) {
                return queryTagList.get(0);
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }

    public QueryTag findOrAdd(QueryTag queryTag) throws Exception {
        if (find(queryTag) != null) {
            return find(queryTag);
        } else {
            return create(queryTag);
        }
    }

    public List<String> listQueryTagEntities() {
        List<String> list = new ArrayList();
        for(QueryTag m : findQueryTagEntities(true, -1, -1)){
            list.add(m.getName());
        }
        return list;
    }
    
    public List<JsonTag> listJsonQueryTagEntities() {
        List<JsonTag> list = new ArrayList();
        for(QueryTag m : findQueryTagEntities(true, -1, -1)){
            list.add(new JsonTag(m.getId(), m.getName()));
        }
        return list;
    }
    
    public List<JsonTag> listFilteredJsonQueryTagEntities(List<String> nameList) {
        List<JsonTag> list = new ArrayList();
        for(String tagName: nameList){
            QueryTag m = find(new QueryTag(0,tagName));
            if(m != null){
                list.add(new JsonTag(m.getId(), m.getName()));
            }
        }
        return list;
    }
    
}
