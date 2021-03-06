/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.RequestValueJpaControllers;

import Entities.ValueEntities.Parameter;
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
import jpa.exceptions.NonexistentEntityException;
import jpa.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class ParameterJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public ParameterJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Parameter create(Parameter parameter) throws RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            em.persist(parameter);
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
        return parameter;
    }

    public void edit(Parameter parameter) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {

            em = getEntityManager();
            parameter = em.merge(parameter);

        } catch (Exception ex) {
            try {

            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = parameter.getIdParameter();
                if (findParameter(id) == null) {
                    throw new NonexistentEntityException("The parameter with id " + id + " no longer exists.");
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
            Parameter parameter;
            try {
                parameter = em.getReference(Parameter.class, id);
                parameter.getIdParameter();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parameter with id " + id + " no longer exists.", enfe);
            }
            em.remove(parameter);

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

    public List<Parameter> findParameterEntities() {
        return findParameterEntities(true, -1, -1);
    }

    public List<Parameter> findParameterEntities(int maxResults, int firstResult) {
        return findParameterEntities(false, maxResults, firstResult);
    }

    private List<Parameter> findParameterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Parameter.class));
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

    public Parameter findParameter(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Parameter.class, id);
        } finally {
            em.close();
        }
    }

    public int getParameterCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Parameter> rt = cq.from(Parameter.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Parameter find(Parameter parameter) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Parameter.findByParameterNameAndValue");
        query.setParameter("parameterName", parameter.getParameterName());
        query.setParameter("parameterValue", parameter.getParameterValue());
        List<Parameter> parameterList = (List<Parameter>) query.getResultList();
        try {
            if (parameterList.size() > 0) {
                return parameterList.get(0);
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }

    public Parameter findOrAdd(Parameter parameter) throws Exception {
        if (find(parameter) != null) {
            return find(parameter);
        } else {

            return create(parameter);//find(parameter);
        }
    }

    public List<String> listParameterNameEntities() {
        List<String> list = new ArrayList();
        for (Parameter m : findParameterEntities(true, -1, -1)) {
            if (list.contains(m.getParameterName()) == false) {
                list.add(m.getParameterName());
            }
        }
        return list;
    }
    
    public List<String> listEntitiesBasedOnCriteria(String criteria) {
        EntityManager em = getEntityManager(); 
        Query query = em.createQuery("select p from Parameter p where p.idParameter in (select pr.idParameter from ParameterReference pr where pr.idRequestReference in (select r.idRequestReference from RequestReference r where r.idHostAddress in (select h.idHostAddress from HostAddress h where h.hostAddressValue = '"+criteria+"')))",Parameter.class);
        List<Parameter> objectList = (List<Parameter>) query.getResultList();  
        List<String> list = new ArrayList();
        for (Parameter m : objectList) {
            if(list.contains(m.getParameterName())){
                //disconsider duplicated
            } else {
                list.add(m.getParameterName());
            }
        }
        return list;
    }
    

    public List<String> listParameterValueEntities() {
        List<String> list = new ArrayList();
        for (Parameter m : findParameterEntities(true, -1, -1)) {
            if (list.contains(m.getParameterValue()) == false) {
                list.add(m.getParameterValue());
            }
        }
        return list;
    }
    
    public List<String> listValuesBasedOnName(String name) {
        EntityManager em = getEntityManager(); 
        Query query = em.createQuery("select p from Parameter p where p.parameterName = '"+name+"'", Parameter.class);
        List<Parameter> objectList = (List<Parameter>) query.getResultList();  
        List<String> list = new ArrayList();
        for (Parameter m : objectList) {
            list.add(m.getParameterValue());
        }
        return list;
    }

}
