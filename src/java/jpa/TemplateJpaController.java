/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import Entities.Template;
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
public class TemplateJpaController implements Serializable {

    public TemplateJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Template create(Template template) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.persist(template);
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
        return template;
    }

    public void edit(Template template) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            template = em.merge(template);
        } catch (Exception ex) {
            try {
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = template.getIdTemplate();
                if (findTemplate(id) == null) {
                    throw new NonexistentEntityException("The template with id " + id + " no longer exists.");
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
            Template template;
            try {
                template = em.getReference(Template.class, id);
                template.getIdTemplate();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The template with id " + id + " no longer exists.", enfe);
            }
            em.remove(template);
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

    public List<Template> findTemplateEntities() {
        return findTemplateEntities(true, -1, -1);
    }

    public List<Template> findTemplateEntities(int maxResults, int firstResult) {
        return findTemplateEntities(false, maxResults, firstResult);
    }

    private List<Template> findTemplateEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Template.class));
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

    public Template findTemplate(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Template.class, id);
        } finally {
            em.close();
        }
    }

    public int getTemplateCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Template> rt = cq.from(Template.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Template find(Template template) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Template.findByTemplateValue");
        query.setParameter("templateValue", template.getTemplateValue());
        List<Template> templateList = (List<Template>) query.getResultList();
        try {
            if (templateList.size() > 0) {
                return templateList.get(0);
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }

    public Template findOrAdd(Template template) throws Exception {
        if (find(template) != null) {
            return find(template);
        } else {
            create(template);
            return find(template);
        }
    }
    
    public List<String> listTemplateEntities() {
        List<String> list = new ArrayList();
        for(Template m : findTemplateEntities(true, -1, -1)){
            list.add(m.getTemplateValue());
        }
        return list;
    }
    
}
