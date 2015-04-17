/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import Entities.RequestTag;
import JsonObjects.Tags.JsonTag;
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
public class RequestTagJpaController implements Serializable {

    public RequestTagJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public RequestTag create(RequestTag requestTag) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.persist(requestTag);
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
        return requestTag; 
    }

    public void edit(RequestTag requestTag) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {

            em = getEntityManager();
            requestTag = em.merge(requestTag);

        } catch (Exception ex) {
            try {

            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = requestTag.getIdRequestTag();
                if (findRequestTag(id) == null) {
                    throw new NonexistentEntityException("The requestTag with id " + id + " no longer exists.");
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
            RequestTag requestTag;
            try {
                requestTag = em.getReference(RequestTag.class, id);
                requestTag.getIdRequestTag();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The requestTag with id " + id + " no longer exists.", enfe);
            }
            em.remove(requestTag);

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

    public List<RequestTag> findRequestTagEntities() {
        return findRequestTagEntities(true, -1, -1);
    }

    public List<RequestTag> findRequestTagEntities(int maxResults, int firstResult) {
        return findRequestTagEntities(false, maxResults, firstResult);
    }

    private List<RequestTag> findRequestTagEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RequestTag.class));
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

    public RequestTag findRequestTag(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RequestTag.class, id);
        } finally {
            em.close();
        }
    }

    public int getRequestTagCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RequestTag> rt = cq.from(RequestTag.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public RequestTag find(RequestTag requestTag) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("RequestTag.findByTagValue");
        query.setParameter("tagValue", requestTag.getTagValue());
        List<RequestTag> requestTagList = (List<RequestTag>) query.getResultList();
        try {
            if (requestTagList.size() > 0) {
                return requestTagList.get(0);
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }

    public RequestTag findOrAdd(RequestTag requestTag) throws Exception {
        if (find(requestTag) != null) {
            return find(requestTag);
        } else {
            
            return create(requestTag);//find(requestTag);
        }
    }

    public List<String> listRequestTagEntities() {
        List<String> list = new ArrayList();
        for(RequestTag m : findRequestTagEntities(true, -1, -1)){
            list.add(m.getTagValue());
        }
        return list;
    }
    
    public List<JsonTag> listJsonTagEntities() {
        List<JsonTag> list = new ArrayList();
        for(RequestTag m : findRequestTagEntities(true, -1, -1)){
            list.add(new JsonTag(m.getIdRequestTag(), m.getTagValue()));
        }
        return list;
    }
    
    public List<JsonTag> listFilteredJsonTagEntities(List<String> nameList) {
        List<JsonTag> list = new ArrayList();
        for(String tagName: nameList){
            RequestTag m = find(new RequestTag(0,tagName));
            if(m != null){
                list.add(new JsonTag(m.getIdRequestTag(), m.getTagValue()));
            }
        }
        return list;
    }
}
