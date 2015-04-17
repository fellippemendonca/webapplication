/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import Entities.Payload;
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
public class PayloadJpaController implements Serializable {

    public PayloadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Payload create(Payload payload) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {

            em = getEntityManager();
            em.persist(payload);
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
        return payload;
    }

    public void edit(Payload payload) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {

            em = getEntityManager();
            payload = em.merge(payload);

        } catch (Exception ex) {
            try {

            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = payload.getIdPayload();
                if (findPayload(id) == null) {
                    throw new NonexistentEntityException("The payload with id " + id + " no longer exists.");
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
            Payload payload;
            try {
                payload = em.getReference(Payload.class, id);
                payload.getIdPayload();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The payload with id " + id + " no longer exists.", enfe);
            }
            em.remove(payload);

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

    public List<Payload> findPayloadEntities() {
        return findPayloadEntities(true, -1, -1);
    }

    public List<Payload> findPayloadEntities(int maxResults, int firstResult) {
        return findPayloadEntities(false, maxResults, firstResult);
    }

    private List<Payload> findPayloadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Payload.class));
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

    public Payload findPayload(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Payload.class, id);
        } finally {
            em.close();
        }
    }

    public int getPayloadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Payload> rt = cq.from(Payload.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Payload find(Payload payload) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Payload.findByPayloadValue");
        query.setParameter("payloadValue", payload.getPayloadValue());
        List<Payload> payloadList = (List<Payload>) query.getResultList();
        try {
            if (payloadList.size() > 0) {
                System.out.println("payloadValue Encontrado");
                return payloadList.get(0);
            } else {
                System.out.println("payloadValue Não Encontrado");
                return null;
            }
        } finally {
            em.close();
        }
    }

    public Payload findOrAdd(Payload payload) throws Exception {
        if (find(payload) != null) {
            return find(payload);
        } else {
            
            return create(payload);//find(payload);
        }
    }
    
    public List<String> listPayloadEntities() {
        List<String> list = new ArrayList();
        for(Payload m : findPayloadEntities(true, -1, -1)){
            list.add(m.getPayloadValue());
        }
        return list;
    }
    
}
