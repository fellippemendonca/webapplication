/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import Entities.Environment;
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
public class EnvironmentJpaController implements Serializable {

    public EnvironmentJpaController(EntityManagerFactory emf) {

        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Environment create(Environment environment) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {

            em = getEntityManager();
            em.persist(environment);
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
        return environment;
    }

    public void edit(Environment environment) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            environment = em.merge(environment);
        } catch (Exception ex) {
            try {
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = environment.getIdEnvironment();
                if (findEnvironment(id) == null) {
                    throw new NonexistentEntityException("The environment with id " + id + " no longer exists.");
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
            Environment environment;
            try {
                environment = em.getReference(Environment.class, id);
                environment.getIdEnvironment();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The environment with id " + id + " no longer exists.", enfe);
            }
            em.remove(environment);
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

    public List<Environment> findEnvironmentEntities() {
        return findEnvironmentEntities(true, -1, -1);
    }

    public List<Environment> findEnvironmentEntities(int maxResults, int firstResult) {
        return findEnvironmentEntities(false, maxResults, firstResult);
    }

    private List<Environment> findEnvironmentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Environment.class));
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

    public Environment findEnvironment(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Environment.class, id);
        } finally {
            em.close();
        }
    }
    
    public Environment find(Environment env) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Environment.findByEnvironmentName");
        query.setParameter("environmentName", env.getEnvironmentName());
        List<Environment> environment = (List<Environment>) query.getResultList();
        try{
        if(environment.size()>0){
            return environment.get(0);
        } else {
            return null;
        }
        }
        finally {
            em.close();
        }
    }
    
    public Environment findOrAdd(Environment environment) throws Exception{
        if(find(environment)!=null){
            return find(environment);
        }else{
            create(environment);
            return find(environment);
        }
    }

    public int getEnvironmentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Environment> rt = cq.from(Environment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<String> listEnvironmentEntities() {
        List<String> list = new ArrayList();
        for(Environment m : findEnvironmentEntities(true, -1, -1)){
            list.add(m.getEnvironmentName());
        }
        return list;
    }
    
}
