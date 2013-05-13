/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessDao;

import BusinessDao.exceptions.NonexistentEntityException;
import BusinessDao.exceptions.PreexistingEntityException;
import BusinessDao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import BusinessObject.Producto;
import BusinessObject.CategoriaCombo;
import BusinessObject.ProductoHasCategoriaCombo;
import BusinessObject.ProductoHasCategoriaComboPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author ACER
 */
public class ProductoHasCategoriaComboJpaController implements Serializable {

    public ProductoHasCategoriaComboJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProductoHasCategoriaCombo productoHasCategoriaCombo) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (productoHasCategoriaCombo.getProductoHasCategoriaComboPK() == null) {
            productoHasCategoriaCombo.setProductoHasCategoriaComboPK(new ProductoHasCategoriaComboPK());
        }
        productoHasCategoriaCombo.getProductoHasCategoriaComboPK().setCategoriacomboidCategoriaCombo(productoHasCategoriaCombo.getCategoriaCombo().getIdCategoriaCombo());
        productoHasCategoriaCombo.getProductoHasCategoriaComboPK().setProductoidProducto(productoHasCategoriaCombo.getProducto().getIdProducto());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Producto producto = productoHasCategoriaCombo.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getIdProducto());
                productoHasCategoriaCombo.setProducto(producto);
            }
            CategoriaCombo categoriaCombo = productoHasCategoriaCombo.getCategoriaCombo();
            if (categoriaCombo != null) {
                categoriaCombo = em.getReference(categoriaCombo.getClass(), categoriaCombo.getIdCategoriaCombo());
                productoHasCategoriaCombo.setCategoriaCombo(categoriaCombo);
            }
            em.persist(productoHasCategoriaCombo);
            if (producto != null) {
                producto.getProductoHasCategoriaComboCollection().add(productoHasCategoriaCombo);
                producto = em.merge(producto);
            }
            if (categoriaCombo != null) {
                categoriaCombo.getProductoHasCategoriaComboCollection().add(productoHasCategoriaCombo);
                categoriaCombo = em.merge(categoriaCombo);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findProductoHasCategoriaCombo(productoHasCategoriaCombo.getProductoHasCategoriaComboPK()) != null) {
                throw new PreexistingEntityException("ProductoHasCategoriaCombo " + productoHasCategoriaCombo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProductoHasCategoriaCombo productoHasCategoriaCombo) throws NonexistentEntityException, RollbackFailureException, Exception {
        productoHasCategoriaCombo.getProductoHasCategoriaComboPK().setCategoriacomboidCategoriaCombo(productoHasCategoriaCombo.getCategoriaCombo().getIdCategoriaCombo());
        productoHasCategoriaCombo.getProductoHasCategoriaComboPK().setProductoidProducto(productoHasCategoriaCombo.getProducto().getIdProducto());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ProductoHasCategoriaCombo persistentProductoHasCategoriaCombo = em.find(ProductoHasCategoriaCombo.class, productoHasCategoriaCombo.getProductoHasCategoriaComboPK());
            Producto productoOld = persistentProductoHasCategoriaCombo.getProducto();
            Producto productoNew = productoHasCategoriaCombo.getProducto();
            CategoriaCombo categoriaComboOld = persistentProductoHasCategoriaCombo.getCategoriaCombo();
            CategoriaCombo categoriaComboNew = productoHasCategoriaCombo.getCategoriaCombo();
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getIdProducto());
                productoHasCategoriaCombo.setProducto(productoNew);
            }
            if (categoriaComboNew != null) {
                categoriaComboNew = em.getReference(categoriaComboNew.getClass(), categoriaComboNew.getIdCategoriaCombo());
                productoHasCategoriaCombo.setCategoriaCombo(categoriaComboNew);
            }
            productoHasCategoriaCombo = em.merge(productoHasCategoriaCombo);
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getProductoHasCategoriaComboCollection().remove(productoHasCategoriaCombo);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getProductoHasCategoriaComboCollection().add(productoHasCategoriaCombo);
                productoNew = em.merge(productoNew);
            }
            if (categoriaComboOld != null && !categoriaComboOld.equals(categoriaComboNew)) {
                categoriaComboOld.getProductoHasCategoriaComboCollection().remove(productoHasCategoriaCombo);
                categoriaComboOld = em.merge(categoriaComboOld);
            }
            if (categoriaComboNew != null && !categoriaComboNew.equals(categoriaComboOld)) {
                categoriaComboNew.getProductoHasCategoriaComboCollection().add(productoHasCategoriaCombo);
                categoriaComboNew = em.merge(categoriaComboNew);
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
                ProductoHasCategoriaComboPK id = productoHasCategoriaCombo.getProductoHasCategoriaComboPK();
                if (findProductoHasCategoriaCombo(id) == null) {
                    throw new NonexistentEntityException("The productoHasCategoriaCombo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ProductoHasCategoriaComboPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ProductoHasCategoriaCombo productoHasCategoriaCombo;
            try {
                productoHasCategoriaCombo = em.getReference(ProductoHasCategoriaCombo.class, id);
                productoHasCategoriaCombo.getProductoHasCategoriaComboPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productoHasCategoriaCombo with id " + id + " no longer exists.", enfe);
            }
            Producto producto = productoHasCategoriaCombo.getProducto();
            if (producto != null) {
                producto.getProductoHasCategoriaComboCollection().remove(productoHasCategoriaCombo);
                producto = em.merge(producto);
            }
            CategoriaCombo categoriaCombo = productoHasCategoriaCombo.getCategoriaCombo();
            if (categoriaCombo != null) {
                categoriaCombo.getProductoHasCategoriaComboCollection().remove(productoHasCategoriaCombo);
                categoriaCombo = em.merge(categoriaCombo);
            }
            em.remove(productoHasCategoriaCombo);
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

    public List<ProductoHasCategoriaCombo> findProductoHasCategoriaComboEntities() {
        return findProductoHasCategoriaComboEntities(true, -1, -1);
    }

    public List<ProductoHasCategoriaCombo> findProductoHasCategoriaComboEntities(int maxResults, int firstResult) {
        return findProductoHasCategoriaComboEntities(false, maxResults, firstResult);
    }

    private List<ProductoHasCategoriaCombo> findProductoHasCategoriaComboEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProductoHasCategoriaCombo.class));
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

    public ProductoHasCategoriaCombo findProductoHasCategoriaCombo(ProductoHasCategoriaComboPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProductoHasCategoriaCombo.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoHasCategoriaComboCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProductoHasCategoriaCombo> rt = cq.from(ProductoHasCategoriaCombo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
