/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessDaos;

import BusinessDaos.exceptions.IllegalOrphanException;
import BusinessDaos.exceptions.NonexistentEntityException;
import BusinessDaos.exceptions.RollbackFailureException;
import BusinessValueObject.Producto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import BusinessValueObject.ProductoHasComboProducto;
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
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) throws RollbackFailureException, Exception {
        if (producto.getProductoHasComboProductoCollection() == null) {
            producto.setProductoHasComboProductoCollection(new ArrayList<ProductoHasComboProducto>());
        }
        if (producto.getProductoHasCategoriaComboCollection() == null) {
            producto.setProductoHasCategoriaComboCollection(new ArrayList<ProductoHasCategoriaCombo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<ProductoHasComboProducto> attachedProductoHasComboProductoCollection = new ArrayList<ProductoHasComboProducto>();
            for (ProductoHasComboProducto productoHasComboProductoCollectionProductoHasComboProductoToAttach : producto.getProductoHasComboProductoCollection()) {
                productoHasComboProductoCollectionProductoHasComboProductoToAttach = em.getReference(productoHasComboProductoCollectionProductoHasComboProductoToAttach.getClass(), productoHasComboProductoCollectionProductoHasComboProductoToAttach.getProductoHasComboProductoPK());
                attachedProductoHasComboProductoCollection.add(productoHasComboProductoCollectionProductoHasComboProductoToAttach);
            }
            producto.setProductoHasComboProductoCollection(attachedProductoHasComboProductoCollection);
            Collection<ProductoHasCategoriaCombo> attachedProductoHasCategoriaComboCollection = new ArrayList<ProductoHasCategoriaCombo>();
            for (ProductoHasCategoriaCombo productoHasCategoriaComboCollectionProductoHasCategoriaComboToAttach : producto.getProductoHasCategoriaComboCollection()) {
                productoHasCategoriaComboCollectionProductoHasCategoriaComboToAttach = em.getReference(productoHasCategoriaComboCollectionProductoHasCategoriaComboToAttach.getClass(), productoHasCategoriaComboCollectionProductoHasCategoriaComboToAttach.getProductoHasCategoriaComboPK());
                attachedProductoHasCategoriaComboCollection.add(productoHasCategoriaComboCollectionProductoHasCategoriaComboToAttach);
            }
            producto.setProductoHasCategoriaComboCollection(attachedProductoHasCategoriaComboCollection);
            em.persist(producto);
            for (ProductoHasComboProducto productoHasComboProductoCollectionProductoHasComboProducto : producto.getProductoHasComboProductoCollection()) {
                Producto oldProductoOfProductoHasComboProductoCollectionProductoHasComboProducto = productoHasComboProductoCollectionProductoHasComboProducto.getProducto();
                productoHasComboProductoCollectionProductoHasComboProducto.setProducto(producto);
                productoHasComboProductoCollectionProductoHasComboProducto = em.merge(productoHasComboProductoCollectionProductoHasComboProducto);
                if (oldProductoOfProductoHasComboProductoCollectionProductoHasComboProducto != null) {
                    oldProductoOfProductoHasComboProductoCollectionProductoHasComboProducto.getProductoHasComboProductoCollection().remove(productoHasComboProductoCollectionProductoHasComboProducto);
                    oldProductoOfProductoHasComboProductoCollectionProductoHasComboProducto = em.merge(oldProductoOfProductoHasComboProductoCollectionProductoHasComboProducto);
                }
            }
            for (ProductoHasCategoriaCombo productoHasCategoriaComboCollectionProductoHasCategoriaCombo : producto.getProductoHasCategoriaComboCollection()) {
                Producto oldProductoOfProductoHasCategoriaComboCollectionProductoHasCategoriaCombo = productoHasCategoriaComboCollectionProductoHasCategoriaCombo.getProducto();
                productoHasCategoriaComboCollectionProductoHasCategoriaCombo.setProducto(producto);
                productoHasCategoriaComboCollectionProductoHasCategoriaCombo = em.merge(productoHasCategoriaComboCollectionProductoHasCategoriaCombo);
                if (oldProductoOfProductoHasCategoriaComboCollectionProductoHasCategoriaCombo != null) {
                    oldProductoOfProductoHasCategoriaComboCollectionProductoHasCategoriaCombo.getProductoHasCategoriaComboCollection().remove(productoHasCategoriaComboCollectionProductoHasCategoriaCombo);
                    oldProductoOfProductoHasCategoriaComboCollectionProductoHasCategoriaCombo = em.merge(oldProductoOfProductoHasCategoriaComboCollectionProductoHasCategoriaCombo);
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

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Producto persistentProducto = em.find(Producto.class, producto.getIdProducto());
            Collection<ProductoHasComboProducto> productoHasComboProductoCollectionOld = persistentProducto.getProductoHasComboProductoCollection();
            Collection<ProductoHasComboProducto> productoHasComboProductoCollectionNew = producto.getProductoHasComboProductoCollection();
            Collection<ProductoHasCategoriaCombo> productoHasCategoriaComboCollectionOld = persistentProducto.getProductoHasCategoriaComboCollection();
            Collection<ProductoHasCategoriaCombo> productoHasCategoriaComboCollectionNew = producto.getProductoHasCategoriaComboCollection();
            List<String> illegalOrphanMessages = null;
            for (ProductoHasComboProducto productoHasComboProductoCollectionOldProductoHasComboProducto : productoHasComboProductoCollectionOld) {
                if (!productoHasComboProductoCollectionNew.contains(productoHasComboProductoCollectionOldProductoHasComboProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductoHasComboProducto " + productoHasComboProductoCollectionOldProductoHasComboProducto + " since its producto field is not nullable.");
                }
            }
            for (ProductoHasCategoriaCombo productoHasCategoriaComboCollectionOldProductoHasCategoriaCombo : productoHasCategoriaComboCollectionOld) {
                if (!productoHasCategoriaComboCollectionNew.contains(productoHasCategoriaComboCollectionOldProductoHasCategoriaCombo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductoHasCategoriaCombo " + productoHasCategoriaComboCollectionOldProductoHasCategoriaCombo + " since its producto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ProductoHasComboProducto> attachedProductoHasComboProductoCollectionNew = new ArrayList<ProductoHasComboProducto>();
            for (ProductoHasComboProducto productoHasComboProductoCollectionNewProductoHasComboProductoToAttach : productoHasComboProductoCollectionNew) {
                productoHasComboProductoCollectionNewProductoHasComboProductoToAttach = em.getReference(productoHasComboProductoCollectionNewProductoHasComboProductoToAttach.getClass(), productoHasComboProductoCollectionNewProductoHasComboProductoToAttach.getProductoHasComboProductoPK());
                attachedProductoHasComboProductoCollectionNew.add(productoHasComboProductoCollectionNewProductoHasComboProductoToAttach);
            }
            productoHasComboProductoCollectionNew = attachedProductoHasComboProductoCollectionNew;
            producto.setProductoHasComboProductoCollection(productoHasComboProductoCollectionNew);
            Collection<ProductoHasCategoriaCombo> attachedProductoHasCategoriaComboCollectionNew = new ArrayList<ProductoHasCategoriaCombo>();
            for (ProductoHasCategoriaCombo productoHasCategoriaComboCollectionNewProductoHasCategoriaComboToAttach : productoHasCategoriaComboCollectionNew) {
                productoHasCategoriaComboCollectionNewProductoHasCategoriaComboToAttach = em.getReference(productoHasCategoriaComboCollectionNewProductoHasCategoriaComboToAttach.getClass(), productoHasCategoriaComboCollectionNewProductoHasCategoriaComboToAttach.getProductoHasCategoriaComboPK());
                attachedProductoHasCategoriaComboCollectionNew.add(productoHasCategoriaComboCollectionNewProductoHasCategoriaComboToAttach);
            }
            productoHasCategoriaComboCollectionNew = attachedProductoHasCategoriaComboCollectionNew;
            producto.setProductoHasCategoriaComboCollection(productoHasCategoriaComboCollectionNew);
            producto = em.merge(producto);
            for (ProductoHasComboProducto productoHasComboProductoCollectionNewProductoHasComboProducto : productoHasComboProductoCollectionNew) {
                if (!productoHasComboProductoCollectionOld.contains(productoHasComboProductoCollectionNewProductoHasComboProducto)) {
                    Producto oldProductoOfProductoHasComboProductoCollectionNewProductoHasComboProducto = productoHasComboProductoCollectionNewProductoHasComboProducto.getProducto();
                    productoHasComboProductoCollectionNewProductoHasComboProducto.setProducto(producto);
                    productoHasComboProductoCollectionNewProductoHasComboProducto = em.merge(productoHasComboProductoCollectionNewProductoHasComboProducto);
                    if (oldProductoOfProductoHasComboProductoCollectionNewProductoHasComboProducto != null && !oldProductoOfProductoHasComboProductoCollectionNewProductoHasComboProducto.equals(producto)) {
                        oldProductoOfProductoHasComboProductoCollectionNewProductoHasComboProducto.getProductoHasComboProductoCollection().remove(productoHasComboProductoCollectionNewProductoHasComboProducto);
                        oldProductoOfProductoHasComboProductoCollectionNewProductoHasComboProducto = em.merge(oldProductoOfProductoHasComboProductoCollectionNewProductoHasComboProducto);
                    }
                }
            }
            for (ProductoHasCategoriaCombo productoHasCategoriaComboCollectionNewProductoHasCategoriaCombo : productoHasCategoriaComboCollectionNew) {
                if (!productoHasCategoriaComboCollectionOld.contains(productoHasCategoriaComboCollectionNewProductoHasCategoriaCombo)) {
                    Producto oldProductoOfProductoHasCategoriaComboCollectionNewProductoHasCategoriaCombo = productoHasCategoriaComboCollectionNewProductoHasCategoriaCombo.getProducto();
                    productoHasCategoriaComboCollectionNewProductoHasCategoriaCombo.setProducto(producto);
                    productoHasCategoriaComboCollectionNewProductoHasCategoriaCombo = em.merge(productoHasCategoriaComboCollectionNewProductoHasCategoriaCombo);
                    if (oldProductoOfProductoHasCategoriaComboCollectionNewProductoHasCategoriaCombo != null && !oldProductoOfProductoHasCategoriaComboCollectionNewProductoHasCategoriaCombo.equals(producto)) {
                        oldProductoOfProductoHasCategoriaComboCollectionNewProductoHasCategoriaCombo.getProductoHasCategoriaComboCollection().remove(productoHasCategoriaComboCollectionNewProductoHasCategoriaCombo);
                        oldProductoOfProductoHasCategoriaComboCollectionNewProductoHasCategoriaCombo = em.merge(oldProductoOfProductoHasCategoriaComboCollectionNewProductoHasCategoriaCombo);
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
                Integer id = producto.getIdProducto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getIdProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ProductoHasComboProducto> productoHasComboProductoCollectionOrphanCheck = producto.getProductoHasComboProductoCollection();
            for (ProductoHasComboProducto productoHasComboProductoCollectionOrphanCheckProductoHasComboProducto : productoHasComboProductoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the ProductoHasComboProducto " + productoHasComboProductoCollectionOrphanCheckProductoHasComboProducto + " in its productoHasComboProductoCollection field has a non-nullable producto field.");
            }
            Collection<ProductoHasCategoriaCombo> productoHasCategoriaComboCollectionOrphanCheck = producto.getProductoHasCategoriaComboCollection();
            for (ProductoHasCategoriaCombo productoHasCategoriaComboCollectionOrphanCheckProductoHasCategoriaCombo : productoHasCategoriaComboCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the ProductoHasCategoriaCombo " + productoHasCategoriaComboCollectionOrphanCheckProductoHasCategoriaCombo + " in its productoHasCategoriaComboCollection field has a non-nullable producto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(producto);
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

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
