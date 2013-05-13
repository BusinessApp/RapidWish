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
import BusinessObject.ComboProducto;
import BusinessObject.ProductoHasComboProducto;
import BusinessObject.ProductoHasComboProductoPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author ACER
 */
public class ProductoHasComboProductoJpaController implements Serializable {

    public ProductoHasComboProductoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProductoHasComboProducto productoHasComboProducto) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (productoHasComboProducto.getProductoHasComboProductoPK() == null) {
            productoHasComboProducto.setProductoHasComboProductoPK(new ProductoHasComboProductoPK());
        }
        productoHasComboProducto.getProductoHasComboProductoPK().setCOMBOPRODUCTOidComboProducto(productoHasComboProducto.getComboProducto().getIdComboProducto());
        productoHasComboProducto.getProductoHasComboProductoPK().setProductoidProducto(productoHasComboProducto.getProducto().getIdProducto());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Producto producto = productoHasComboProducto.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getIdProducto());
                productoHasComboProducto.setProducto(producto);
            }
            ComboProducto comboProducto = productoHasComboProducto.getComboProducto();
            if (comboProducto != null) {
                comboProducto = em.getReference(comboProducto.getClass(), comboProducto.getIdComboProducto());
                productoHasComboProducto.setComboProducto(comboProducto);
            }
            em.persist(productoHasComboProducto);
            if (producto != null) {
                producto.getProductoHasComboProductoCollection().add(productoHasComboProducto);
                producto = em.merge(producto);
            }
            if (comboProducto != null) {
                comboProducto.getProductoHasComboProductoCollection().add(productoHasComboProducto);
                comboProducto = em.merge(comboProducto);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findProductoHasComboProducto(productoHasComboProducto.getProductoHasComboProductoPK()) != null) {
                throw new PreexistingEntityException("ProductoHasComboProducto " + productoHasComboProducto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProductoHasComboProducto productoHasComboProducto) throws NonexistentEntityException, RollbackFailureException, Exception {
        productoHasComboProducto.getProductoHasComboProductoPK().setCOMBOPRODUCTOidComboProducto(productoHasComboProducto.getComboProducto().getIdComboProducto());
        productoHasComboProducto.getProductoHasComboProductoPK().setProductoidProducto(productoHasComboProducto.getProducto().getIdProducto());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ProductoHasComboProducto persistentProductoHasComboProducto = em.find(ProductoHasComboProducto.class, productoHasComboProducto.getProductoHasComboProductoPK());
            Producto productoOld = persistentProductoHasComboProducto.getProducto();
            Producto productoNew = productoHasComboProducto.getProducto();
            ComboProducto comboProductoOld = persistentProductoHasComboProducto.getComboProducto();
            ComboProducto comboProductoNew = productoHasComboProducto.getComboProducto();
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getIdProducto());
                productoHasComboProducto.setProducto(productoNew);
            }
            if (comboProductoNew != null) {
                comboProductoNew = em.getReference(comboProductoNew.getClass(), comboProductoNew.getIdComboProducto());
                productoHasComboProducto.setComboProducto(comboProductoNew);
            }
            productoHasComboProducto = em.merge(productoHasComboProducto);
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getProductoHasComboProductoCollection().remove(productoHasComboProducto);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getProductoHasComboProductoCollection().add(productoHasComboProducto);
                productoNew = em.merge(productoNew);
            }
            if (comboProductoOld != null && !comboProductoOld.equals(comboProductoNew)) {
                comboProductoOld.getProductoHasComboProductoCollection().remove(productoHasComboProducto);
                comboProductoOld = em.merge(comboProductoOld);
            }
            if (comboProductoNew != null && !comboProductoNew.equals(comboProductoOld)) {
                comboProductoNew.getProductoHasComboProductoCollection().add(productoHasComboProducto);
                comboProductoNew = em.merge(comboProductoNew);
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
                ProductoHasComboProductoPK id = productoHasComboProducto.getProductoHasComboProductoPK();
                if (findProductoHasComboProducto(id) == null) {
                    throw new NonexistentEntityException("The productoHasComboProducto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ProductoHasComboProductoPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ProductoHasComboProducto productoHasComboProducto;
            try {
                productoHasComboProducto = em.getReference(ProductoHasComboProducto.class, id);
                productoHasComboProducto.getProductoHasComboProductoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productoHasComboProducto with id " + id + " no longer exists.", enfe);
            }
            Producto producto = productoHasComboProducto.getProducto();
            if (producto != null) {
                producto.getProductoHasComboProductoCollection().remove(productoHasComboProducto);
                producto = em.merge(producto);
            }
            ComboProducto comboProducto = productoHasComboProducto.getComboProducto();
            if (comboProducto != null) {
                comboProducto.getProductoHasComboProductoCollection().remove(productoHasComboProducto);
                comboProducto = em.merge(comboProducto);
            }
            em.remove(productoHasComboProducto);
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

    public List<ProductoHasComboProducto> findProductoHasComboProductoEntities() {
        return findProductoHasComboProductoEntities(true, -1, -1);
    }

    public List<ProductoHasComboProducto> findProductoHasComboProductoEntities(int maxResults, int firstResult) {
        return findProductoHasComboProductoEntities(false, maxResults, firstResult);
    }

    private List<ProductoHasComboProducto> findProductoHasComboProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProductoHasComboProducto.class));
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

    public ProductoHasComboProducto findProductoHasComboProducto(ProductoHasComboProductoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProductoHasComboProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoHasComboProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProductoHasComboProducto> rt = cq.from(ProductoHasComboProducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
