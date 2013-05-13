/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessDaos;

import BusinessDaos.exceptions.IllegalOrphanException;
import BusinessDaos.exceptions.NonexistentEntityException;
import BusinessDaos.exceptions.RollbackFailureException;
import BusinessValueObject.CategoriaCombo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import BusinessValueObject.ComboProducto;
import java.util.ArrayList;
import java.util.Collection;
import BusinessValueObject.ProductoHasCategoriaCombo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author ACER
 */
public class CategoriaComboJpaController implements Serializable {

    public CategoriaComboJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CategoriaCombo categoriaCombo) throws RollbackFailureException, Exception {
        if (categoriaCombo.getComboProductoCollection() == null) {
            categoriaCombo.setComboProductoCollection(new ArrayList<ComboProducto>());
        }
        if (categoriaCombo.getProductoHasCategoriaComboCollection() == null) {
            categoriaCombo.setProductoHasCategoriaComboCollection(new ArrayList<ProductoHasCategoriaCombo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<ComboProducto> attachedComboProductoCollection = new ArrayList<ComboProducto>();
            for (ComboProducto comboProductoCollectionComboProductoToAttach : categoriaCombo.getComboProductoCollection()) {
                comboProductoCollectionComboProductoToAttach = em.getReference(comboProductoCollectionComboProductoToAttach.getClass(), comboProductoCollectionComboProductoToAttach.getIdComboProducto());
                attachedComboProductoCollection.add(comboProductoCollectionComboProductoToAttach);
            }
            categoriaCombo.setComboProductoCollection(attachedComboProductoCollection);
            Collection<ProductoHasCategoriaCombo> attachedProductoHasCategoriaComboCollection = new ArrayList<ProductoHasCategoriaCombo>();
            for (ProductoHasCategoriaCombo productoHasCategoriaComboCollectionProductoHasCategoriaComboToAttach : categoriaCombo.getProductoHasCategoriaComboCollection()) {
                productoHasCategoriaComboCollectionProductoHasCategoriaComboToAttach = em.getReference(productoHasCategoriaComboCollectionProductoHasCategoriaComboToAttach.getClass(), productoHasCategoriaComboCollectionProductoHasCategoriaComboToAttach.getProductoHasCategoriaComboPK());
                attachedProductoHasCategoriaComboCollection.add(productoHasCategoriaComboCollectionProductoHasCategoriaComboToAttach);
            }
            categoriaCombo.setProductoHasCategoriaComboCollection(attachedProductoHasCategoriaComboCollection);
            em.persist(categoriaCombo);
            for (ComboProducto comboProductoCollectionComboProducto : categoriaCombo.getComboProductoCollection()) {
                CategoriaCombo oldCategoriacomboidCategoriaComboOfComboProductoCollectionComboProducto = comboProductoCollectionComboProducto.getCategoriacomboidCategoriaCombo();
                comboProductoCollectionComboProducto.setCategoriacomboidCategoriaCombo(categoriaCombo);
                comboProductoCollectionComboProducto = em.merge(comboProductoCollectionComboProducto);
                if (oldCategoriacomboidCategoriaComboOfComboProductoCollectionComboProducto != null) {
                    oldCategoriacomboidCategoriaComboOfComboProductoCollectionComboProducto.getComboProductoCollection().remove(comboProductoCollectionComboProducto);
                    oldCategoriacomboidCategoriaComboOfComboProductoCollectionComboProducto = em.merge(oldCategoriacomboidCategoriaComboOfComboProductoCollectionComboProducto);
                }
            }
            for (ProductoHasCategoriaCombo productoHasCategoriaComboCollectionProductoHasCategoriaCombo : categoriaCombo.getProductoHasCategoriaComboCollection()) {
                CategoriaCombo oldCategoriaComboOfProductoHasCategoriaComboCollectionProductoHasCategoriaCombo = productoHasCategoriaComboCollectionProductoHasCategoriaCombo.getCategoriaCombo();
                productoHasCategoriaComboCollectionProductoHasCategoriaCombo.setCategoriaCombo(categoriaCombo);
                productoHasCategoriaComboCollectionProductoHasCategoriaCombo = em.merge(productoHasCategoriaComboCollectionProductoHasCategoriaCombo);
                if (oldCategoriaComboOfProductoHasCategoriaComboCollectionProductoHasCategoriaCombo != null) {
                    oldCategoriaComboOfProductoHasCategoriaComboCollectionProductoHasCategoriaCombo.getProductoHasCategoriaComboCollection().remove(productoHasCategoriaComboCollectionProductoHasCategoriaCombo);
                    oldCategoriaComboOfProductoHasCategoriaComboCollectionProductoHasCategoriaCombo = em.merge(oldCategoriaComboOfProductoHasCategoriaComboCollectionProductoHasCategoriaCombo);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
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

    public void edit(CategoriaCombo categoriaCombo) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CategoriaCombo persistentCategoriaCombo = em.find(CategoriaCombo.class, categoriaCombo.getIdCategoriaCombo());
            Collection<ComboProducto> comboProductoCollectionOld = persistentCategoriaCombo.getComboProductoCollection();
            Collection<ComboProducto> comboProductoCollectionNew = categoriaCombo.getComboProductoCollection();
            Collection<ProductoHasCategoriaCombo> productoHasCategoriaComboCollectionOld = persistentCategoriaCombo.getProductoHasCategoriaComboCollection();
            Collection<ProductoHasCategoriaCombo> productoHasCategoriaComboCollectionNew = categoriaCombo.getProductoHasCategoriaComboCollection();
            List<String> illegalOrphanMessages = null;
            for (ComboProducto comboProductoCollectionOldComboProducto : comboProductoCollectionOld) {
                if (!comboProductoCollectionNew.contains(comboProductoCollectionOldComboProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ComboProducto " + comboProductoCollectionOldComboProducto + " since its categoriacomboidCategoriaCombo field is not nullable.");
                }
            }
            for (ProductoHasCategoriaCombo productoHasCategoriaComboCollectionOldProductoHasCategoriaCombo : productoHasCategoriaComboCollectionOld) {
                if (!productoHasCategoriaComboCollectionNew.contains(productoHasCategoriaComboCollectionOldProductoHasCategoriaCombo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductoHasCategoriaCombo " + productoHasCategoriaComboCollectionOldProductoHasCategoriaCombo + " since its categoriaCombo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ComboProducto> attachedComboProductoCollectionNew = new ArrayList<ComboProducto>();
            for (ComboProducto comboProductoCollectionNewComboProductoToAttach : comboProductoCollectionNew) {
                comboProductoCollectionNewComboProductoToAttach = em.getReference(comboProductoCollectionNewComboProductoToAttach.getClass(), comboProductoCollectionNewComboProductoToAttach.getIdComboProducto());
                attachedComboProductoCollectionNew.add(comboProductoCollectionNewComboProductoToAttach);
            }
            comboProductoCollectionNew = attachedComboProductoCollectionNew;
            categoriaCombo.setComboProductoCollection(comboProductoCollectionNew);
            Collection<ProductoHasCategoriaCombo> attachedProductoHasCategoriaComboCollectionNew = new ArrayList<ProductoHasCategoriaCombo>();
            for (ProductoHasCategoriaCombo productoHasCategoriaComboCollectionNewProductoHasCategoriaComboToAttach : productoHasCategoriaComboCollectionNew) {
                productoHasCategoriaComboCollectionNewProductoHasCategoriaComboToAttach = em.getReference(productoHasCategoriaComboCollectionNewProductoHasCategoriaComboToAttach.getClass(), productoHasCategoriaComboCollectionNewProductoHasCategoriaComboToAttach.getProductoHasCategoriaComboPK());
                attachedProductoHasCategoriaComboCollectionNew.add(productoHasCategoriaComboCollectionNewProductoHasCategoriaComboToAttach);
            }
            productoHasCategoriaComboCollectionNew = attachedProductoHasCategoriaComboCollectionNew;
            categoriaCombo.setProductoHasCategoriaComboCollection(productoHasCategoriaComboCollectionNew);
            categoriaCombo = em.merge(categoriaCombo);
            for (ComboProducto comboProductoCollectionNewComboProducto : comboProductoCollectionNew) {
                if (!comboProductoCollectionOld.contains(comboProductoCollectionNewComboProducto)) {
                    CategoriaCombo oldCategoriacomboidCategoriaComboOfComboProductoCollectionNewComboProducto = comboProductoCollectionNewComboProducto.getCategoriacomboidCategoriaCombo();
                    comboProductoCollectionNewComboProducto.setCategoriacomboidCategoriaCombo(categoriaCombo);
                    comboProductoCollectionNewComboProducto = em.merge(comboProductoCollectionNewComboProducto);
                    if (oldCategoriacomboidCategoriaComboOfComboProductoCollectionNewComboProducto != null && !oldCategoriacomboidCategoriaComboOfComboProductoCollectionNewComboProducto.equals(categoriaCombo)) {
                        oldCategoriacomboidCategoriaComboOfComboProductoCollectionNewComboProducto.getComboProductoCollection().remove(comboProductoCollectionNewComboProducto);
                        oldCategoriacomboidCategoriaComboOfComboProductoCollectionNewComboProducto = em.merge(oldCategoriacomboidCategoriaComboOfComboProductoCollectionNewComboProducto);
                    }
                }
            }
            for (ProductoHasCategoriaCombo productoHasCategoriaComboCollectionNewProductoHasCategoriaCombo : productoHasCategoriaComboCollectionNew) {
                if (!productoHasCategoriaComboCollectionOld.contains(productoHasCategoriaComboCollectionNewProductoHasCategoriaCombo)) {
                    CategoriaCombo oldCategoriaComboOfProductoHasCategoriaComboCollectionNewProductoHasCategoriaCombo = productoHasCategoriaComboCollectionNewProductoHasCategoriaCombo.getCategoriaCombo();
                    productoHasCategoriaComboCollectionNewProductoHasCategoriaCombo.setCategoriaCombo(categoriaCombo);
                    productoHasCategoriaComboCollectionNewProductoHasCategoriaCombo = em.merge(productoHasCategoriaComboCollectionNewProductoHasCategoriaCombo);
                    if (oldCategoriaComboOfProductoHasCategoriaComboCollectionNewProductoHasCategoriaCombo != null && !oldCategoriaComboOfProductoHasCategoriaComboCollectionNewProductoHasCategoriaCombo.equals(categoriaCombo)) {
                        oldCategoriaComboOfProductoHasCategoriaComboCollectionNewProductoHasCategoriaCombo.getProductoHasCategoriaComboCollection().remove(productoHasCategoriaComboCollectionNewProductoHasCategoriaCombo);
                        oldCategoriaComboOfProductoHasCategoriaComboCollectionNewProductoHasCategoriaCombo = em.merge(oldCategoriaComboOfProductoHasCategoriaComboCollectionNewProductoHasCategoriaCombo);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categoriaCombo.getIdCategoriaCombo();
                if (findCategoriaCombo(id) == null) {
                    throw new NonexistentEntityException("The categoriaCombo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CategoriaCombo categoriaCombo;
            try {
                categoriaCombo = em.getReference(CategoriaCombo.class, id);
                categoriaCombo.getIdCategoriaCombo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaCombo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ComboProducto> comboProductoCollectionOrphanCheck = categoriaCombo.getComboProductoCollection();
            for (ComboProducto comboProductoCollectionOrphanCheckComboProducto : comboProductoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CategoriaCombo (" + categoriaCombo + ") cannot be destroyed since the ComboProducto " + comboProductoCollectionOrphanCheckComboProducto + " in its comboProductoCollection field has a non-nullable categoriacomboidCategoriaCombo field.");
            }
            Collection<ProductoHasCategoriaCombo> productoHasCategoriaComboCollectionOrphanCheck = categoriaCombo.getProductoHasCategoriaComboCollection();
            for (ProductoHasCategoriaCombo productoHasCategoriaComboCollectionOrphanCheckProductoHasCategoriaCombo : productoHasCategoriaComboCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CategoriaCombo (" + categoriaCombo + ") cannot be destroyed since the ProductoHasCategoriaCombo " + productoHasCategoriaComboCollectionOrphanCheckProductoHasCategoriaCombo + " in its productoHasCategoriaComboCollection field has a non-nullable categoriaCombo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(categoriaCombo);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
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

    public List<CategoriaCombo> findCategoriaComboEntities() {
        return findCategoriaComboEntities(true, -1, -1);
    }

    public List<CategoriaCombo> findCategoriaComboEntities(int maxResults, int firstResult) {
        return findCategoriaComboEntities(false, maxResults, firstResult);
    }

    private List<CategoriaCombo> findCategoriaComboEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CategoriaCombo.class));
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

    public CategoriaCombo findCategoriaCombo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CategoriaCombo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaComboCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CategoriaCombo> rt = cq.from(CategoriaCombo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
