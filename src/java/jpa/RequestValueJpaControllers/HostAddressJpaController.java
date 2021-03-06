/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.RequestValueJpaControllers;

import Entities.ValueEntities.HostAddress;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import jpa.exceptions.NonexistentEntityException;
import jpa.exceptions.PreexistingEntityException;
import jpa.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class HostAddressJpaController implements Serializable {

    public HostAddressJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public HostAddress create(HostAddress hostAddress) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction etx = null;
        try {
            em = getEntityManager();
            etx = em.getTransaction();
            etx.begin();
            em.persist(hostAddress);
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
        return hostAddress;
    }

    public void edit(HostAddress hostAddress) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            hostAddress = em.merge(hostAddress);
        } catch (Exception ex) {
            try {
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = hostAddress.getIdHostAddress();
                if (findHostAddress(id) == null) {
                    throw new NonexistentEntityException("The hostAddress with id " + id + " no longer exists.");
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
            HostAddress hostAddress;
            try {
                hostAddress = em.getReference(HostAddress.class, id);
                hostAddress.getIdHostAddress();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hostAddress with id " + id + " no longer exists.", enfe);
            }
            em.remove(hostAddress);
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

    public List<HostAddress> findHostAddressEntities() {
        return findHostAddressEntities(true, -1, -1);
    }

    public List<HostAddress> findHostAddressEntities(int maxResults, int firstResult) {
        return findHostAddressEntities(false, maxResults, firstResult);
    }

    private List<HostAddress> findHostAddressEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HostAddress.class));
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

    public HostAddress findHostAddress(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HostAddress.class, id);
        } finally {
            em.close();
        }
    }

    public HostAddress find(HostAddress host) {
        EntityManager em = getEntityManager(); 
        Query query = em.createNamedQuery("HostAddress.findByHostAddressValue");
        query.setParameter("hostAddressValue", host.getHostAddressValue());
        List<HostAddress> hostList = (List<HostAddress>) query.getResultList();
        try {
            if (hostList.size() > 0) {
                return hostList.get(0);
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }

    public HostAddress findOrAdd(HostAddress host) throws RollbackFailureException, Exception {
        if (find(host) != null) {
            return find(host);
        } else {
            return create(host);//find(host);
        }
    }

    public int getHostAddressCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HostAddress> rt = cq.from(HostAddress.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<String> listHostAddressEntities() {
        List<String> list = new ArrayList();
        for (HostAddress m : findHostAddressEntities(true, -1, -1)) {
            list.add(m.getHostAddressValue());
        }
        return list;
    }
    
    public List<String> listEntitiesBasedOnCriteria(String criteria) {
        EntityManager em = getEntityManager(); 
        Query query = em.createQuery("select h from HostAddress h where h.idHostAddress in (select distinct r.idHostAddress from RequestReference r where r.idEnvironment in (select e.idEnvironment from Environment e where e.environmentName = '"+criteria+"'))",HostAddress.class);
        List<HostAddress> objectList = (List<HostAddress>) query.getResultList();
        List<String> list = new ArrayList();
        for (HostAddress m : objectList) {
            list.add(m.getHostAddressValue());
        }
        return list;
    }
}
