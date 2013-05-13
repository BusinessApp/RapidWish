/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessDao;

import BusinessDao.exceptions.IllegalOrphanException;
import BusinessDao.exceptions.NonexistentEntityException;
import BusinessDao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import BusinessObject.CategoriaCombo;
import BusinessObject.ComboProducto;
import BusinessObject.ProductoHasComboProducto;
import java.util.ArrayList;
import java.util.Collection;
import BusinessObject.ComboProductoHasPedido;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author ACER
 */
public class ComboProductoJpaController implements Serializable {

    public ComboProductoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ComboProducto comboProducto) throws RollbackFailureException, Exception {
        if (comboProducto.getProductoHasComboProductoCollection() == null) {
            comboProducto.setProductoHasComboProductoCollection(new ArrayList<ProductoHasComboProducto>());
        }
        if (comboProducto.getComboProductoHasPedidoCollection() == null) {
            comboProducto.setComboProductoHasPedidoCollection(new ArrayList<ComboProductoHasPedido>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CategoriaCombo categoriacomboidCategoriaCombo = comboProducto.getCategoriacomboidCategoriaCombo();
            if (categoriacomboidCategoriaCombo != null) {
                categoriacomboidCategoriaCombo = em.getReference(categoriacomboidCategoriaCombo.getClass(), categoriacomboidCategoriaCombo.getIdCategoriaCombo());
                comboProducto.setCategoriacomboidCategoriaCombo(categoriacomboidCategoriaCombo);
            }
            Collection<ProductoHasComboProducto> attachedProductoHasComboProductoCollection = new ArrayList<ProductoHasComboProducto>();
            for (ProductoHasComboProducto productoHasComboProductoCollectionProductoHasComboProductoToAttach : comboProducto.getProductoHasComboProductoCollection()) {
                productoHasComboProductoCollectionProductoHasComboProductoToAttach = em.getReference(productoHasComboProductoCollectionProductoHasComboProductoToAttach.getClass(), productoHasComboProductoCollectionProductoHasComboProductoToAttach.getProductoHasComboProductoPK());
                attachedProductoHasComboProductoCollection.add(productoHasComboProductoCollectionProductoHasComboProductoToAttach);
            }
            comboProducto.setProductoHasComboProductoCollection(attachedProductoHasComboProductoCollection);
            Collection<ComboProductoHasPedido> attachedComboProductoHasPedidoCollection = new ArrayList<ComboProductoHasPedido>();
            for (ComboProductoHasPedido comboProductoHasPedidoCollectionComboProductoHasPedidoToAttach : comboProducto.getComboProductoHasPedidoCollection()) {
                comboProductoHasPedidoCollectionComboProductoHasPedidoToAttach = em.getReference(comboProductoHasPedidoCollectionComboProductoHasPedidoToAttach.getClass(), comboProductoHasPedidoCollectionComboProductoHasPedidoToAttach.getComboProductoHasPedidoPK());
                attachedComboProductoHasPedidoCollection.add(comboProductoHasPedidoCollectionComboProductoHasPedidoToAttach);
            }
            comboProducto.setComboProductoHasPedidoCollection(attachedComboProductoHasPedidoCollection);
            em.persist(comboProducto);
            if (categoriacomboidCategoriaCombo != null) {
                categoriacomboidCategoriaCombo.getComboProductoCollection().add(comboProducto);
                categoriacomboidCategoriaCombo = em.merge(categoriacomboidCategoriaCombo);
            }
            for (ProductoHasComboProducto productoHasComboProductoCollectionProductoHasComboProducto : comboProducto.getProductoHasComboProductoCollection()) {
                ComboProducto oldComboProductoOfProductoHasComboProductoCollectionProductoHasComboProducto = productoHasComboProductoCollectionProductoHasComboProducto.getComboProducto();
                productoHasComboProductoCollectionProductoHasComboProducto.setComboProducto(comboProducto);
                productoHasComboProductoCollectionProductoHasComboProducto = em.merge(productoHasComboProductoCollectionProductoHasComboProducto);
                if (oldComboProductoOfProductoHasComboProductoCollectionProductoHasComboProducto != null) {
                    oldComboProductoOfProductoHasComboProductoCollectionProductoHasComboProducto.getProductoHasComboProductoCollection().remove(productoHasComboProductoCollectionProductoHasComboProducto);
                    oldComboProductoOfProductoHasComboProductoCollectionProductoHasComboProducto = em.merge(oldComboProductoOfProductoHasComboProductoCollectionProductoHasComboProducto);
                }
            }
            for (ComboProductoHasPedido comboProductoHasPedidoCollectionComboProductoHasPedido : comboProducto.getComboProductoHasPedidoCollection()) {
                ComboProducto oldComboProductoOfComboProductoHasPedidoCollectionComboProductoHasPedido = comboProductoHasPedidoCollectionComboProductoHasPedido.getComboProducto();
                comboProductoHasPedidoCollectionComboProductoHasPedido.setComboProducto(comboProducto);
                comboProductoHasPedidoCollectionComboProductoHasPedido = em.merge(comboProductoHasPedidoCollectionComboProductoHasPedido);
                if (oldComboProductoOfComboProductoHasPedidoCollectionComboProductoHasPedido != null) {
                    oldComboProductoOfComboProductoHasPedidoCollectionComboProductoHasPedido.getComboProductoHasPedidoCollection().remove(comboProductoHasPedidoCollectionComboProductoHasPedido);
                    oldComboProductoOfComboProductoHasPedidoCollectionComboProductoHasPedido = em.merge(oldComboProductoOfComboProductoHasPedidoCollectionComboProductoHasPedido);
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

    public void edit(ComboProducto comboProducto) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ComboProducto persistentComboProducto = em.find(ComboProducto.class, comboProducto.getIdComboProducto());
            CategoriaCombo categoriacomboidCategoriaComboOld = persistentComboProducto.getCategoriacomboidCategoriaCombo();
            CategoriaCombo categoriacomboidCategoriaComboNew = comboProducto.getCategoriacomboidCategoriaCombo();
            Collection<ProductoHasComboProducto> productoHasComboProductoCollectionOld = persistentComboProducto.getProductoHasComboProductoCollection();
            Collection<ProductoHasComboProducto> productoHasComboProductoCollectionNew = comboProducto.getProductoHasComboProductoCollection();
            Collection<ComboProductoHasPedido> comboProductoHasPedidoCollectionOld = persistentComboProducto.getComboProductoHasPedidoCollection();
            Collection<ComboProductoHasPedido> comboProductoHasPedidoCollectionNew = comboProducto.getComboProductoHasPedidoCollection();
            List<String> illegalOrphanMessages = null;
            for (ProductoHasComboProducto productoHasComboProductoCollectionOldProductoHasComboProducto : productoHasComboProductoCollectionOld) {
                if (!productoHasComboProductoCollectionNew.contains(productoHasComboProductoCollectionOldProductoHasComboProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductoHasComboProducto " + productoHasComboProductoCollectionOldProductoHasComboProducto + " since its comboProducto field is not nullable.");
                }
            }
            for (ComboProductoHasPedido comboProductoHasPedidoCollectionOldComboProductoHasPedido : comboProductoHasPedidoCollectionOld) {
                if (!comboProductoHasPedidoCollectionNew.contains(comboProductoHasPedidoCollectionOldComboProductoHasPedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ComboProductoHasPedido " + comboProductoHasPedidoCollectionOldComboProductoHasPedido + " since its comboProducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (categoriacomboidCategoriaComboNew != null) {
                categoriacomboidCategoriaComboNew = em.getReference(categoriacomboidCategoriaComboNew.getClass(), categoriacomboidCategoriaComboNew.getIdCategoriaCombo());
                comboProducto.setCategoriacomboidCategoriaCombo(categoriacomboidCategoriaComboNew);
            }
            Collection<ProductoHasComboProducto> attachedProductoHasComboProductoCollectionNew = new ArrayList<ProductoHasComboProducto>();
            for (ProductoHasComboProducto productoHasComboProductoCollectionNewProductoHasComboProductoToAttach : productoHasComboProductoCollectionNew) {
                productoHasComboProductoCollectionNewProductoHasComboProductoToAttach = em.getReference(productoHasComboProductoCollectionNewProductoHasComboProductoToAttach.getClass(), productoHasComboProductoCollectionNewProductoHasComboProductoToAttach.getProductoHasComboProductoPK());
                attachedProductoHasComboProductoCollectionNew.add(productoHasComboProductoCollectionNewProductoHasComboProductoToAttach);
            }
            productoHasComboProductoCollectionNew = attachedProductoHasComboProductoCollectionNew;
            comboProducto.setProductoHasComboProductoCollection(productoHasComboProductoCollectionNew);
            Collection<ComboProductoHasPedido> attachedComboProductoHasPedidoCollectionNew = new ArrayList<ComboProductoHasPedido>();
            for (ComboProductoHasPedido comboProductoHasPedidoCollectionNewComboProductoHasPedidoToAttach : comboProductoHasPedidoCollectionNew) {
                comboProductoHasPedidoCollectionNewComboProductoHasPedidoToAttach = em.getReference(comboProductoHasPedidoCollectionNewComboProductoHasPedidoToAttach.getClass(), comboProductoHasPedidoCollectionNewComboProductoHasPedidoToAttach.getComboProductoHasPedidoPK());
                attachedComboProductoHasPedidoCollectionNew.add(comboProductoHasPedidoCollectionNewComboProductoHasPedidoToAttach);
            }
            comboProductoHasPedidoCollectionNew = attachedComboProductoHasPedidoCollectionNew;
            comboProducto.setComboProductoHasPedidoCollection(comboProductoHasPedidoCollectionNew);
            comboProducto = em.merge(comboProducto);
            if (categoriacomboidCategoriaComboOld != null && !categoriacomboidCategoriaComboOld.equals(categoriacomboidCategoriaComboNew)) {
                categoriacomboidCategoriaComboOld.getComboProductoCollection().remove(comboProducto);
                categoriacomboidCategoriaComboOld = em.merge(categoriacomboidCategoriaComboOld);
            }
            if (categoriacomboidCategoriaComboNew != null && !categoriacomboidCategoriaComboNew.equals(categoriacomboidCategoriaComboOld)) {
                categoriacomboidCategoriaComboNew.getComboProductoCollection().add(comboProducto);
                categoriacomboidCategoriaComboNew = em.merge(categoriacomboidCategoriaComboNew);
            }
            for (ProductoHasComboProducto productoHasComboProductoCollectionNewProductoHasComboProducto : productoHasComboProductoCollectionNew) {
                if (!productoHasComboProductoCollectionOld.contains(productoHasComboProductoCollectionNewProductoHasComboProducto)) {
                    ComboProducto oldComboProductoOfProductoHasComboProductoCollectionNewProductoHasComboProducto = productoHasComboProductoCollectionNewProductoHasComboProducto.getComboProducto();
                    productoHasComboProductoCollectionNewProductoHasComboProducto.setComboProducto(comboProducto);
                    productoHasComboProductoCollectionNewProductoHasComboProducto = em.merge(productoHasComboProductoCollectionNewProductoHasComboProducto);
                    if (oldComboProductoOfProductoHasComboProductoCollectionNewProductoHasComboProducto != null && !oldComboProductoOfProductoHasComboProductoCollectionNewProductoHasComboProducto.equals(comboProducto)) {
                        oldComboProductoOfProductoHasComboProductoCollectionNewProductoHasComboProducto.getProductoHasComboProductoCollection().remove(productoHasComboProductoCollectionNewProductoHasComboProducto);
                        oldComboProductoOfProductoHasComboProductoCollectionNewProductoHasComboProducto = em.merge(oldComboProductoOfProductoHasComboProductoCollectionNewProductoHasComboProducto);
                    }
                }
            }
            for (ComboProductoHasPedido comboProductoHasPedidoCollectionNewComboProductoHasPedido : comboProductoHasPedidoCollectionNew) {
                if (!comboProductoHasPedidoCollectionOld.contains(comboProductoHasPedidoCollectionNewComboProductoHasPedido)) {
                    ComboProducto oldComboProductoOfComboProductoHasPedidoCollectionNewComboProductoHasPedido = comboProductoHasPedidoCollectionNewComboProductoHasPedido.getComboProducto();
                    comboProductoHasPedidoCollectionNewComboProductoHasPedido.setComboProducto(comboProducto);
                    comboProductoHasPedidoCollectionNewComboProductoHasPedido = em.merge(comboProductoHasPedidoCollectionNewComboProductoHasPedido);
                    if (oldComboProductoOfComboProductoHasPedidoCollectionNewComboProductoHasPedido != null && !oldComboProductoOfComboProductoHasPedidoCollectionNewComboProductoHasPedido.equals(comboProducto)) {
                        oldComboProductoOfComboProductoHasPedidoCollectionNewComboProductoHasPedido.getComboProductoHasPedidoCollection().remove(comboProductoHasPedidoCollectionNewComboProductoHasPedido);
                        oldComboProductoOfComboProductoHasPedidoCollectionNewComboProductoHasPedido = em.merge(oldComboProductoOfComboProductoHasPedidoCollectionNewComboProductoHasPedido);
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
                Integer id = comboProducto.getIdComboProducto();
                if (findComboProducto(id) == null) {
                    throw new NonexistentEntityException("The comboProducto with id " + id + " no longer exists.");
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
            ComboProducto comboProducto;
            try {
                comboProducto = em.getReference(ComboProducto.class, id);
                comboProducto.getIdComboProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comboProducto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ProductoHasComboProducto> productoHasComboProductoCollectionOrphanCheck = comboProducto.getProductoHasComboProductoCollection();
            for (ProductoHasComboProducto productoHasComboProductoCollectionOrphanCheckProductoHasComboProducto : productoHasComboProductoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ComboProducto (" + comboProducto + ") cannot be destroyed since the ProductoHasComboProducto " + productoHasComboProductoCollectionOrphanCheckProductoHasComboProducto + " in its productoHasComboProductoCollection field has a non-nullable comboProducto field.");
            }
            Collection<ComboProductoHasPedido> comboProductoHasPedidoCollectionOrphanCheck = comboProducto.getComboProductoHasPedidoCollection();
            for (ComboProductoHasPedido comboProductoHasPedidoCollectionOrphanCheckComboProductoHasPedido : comboProductoHasPedidoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ComboProducto (" + comboProducto + ") cannot be destroyed since the ComboProductoHasPedido " + comboProductoHasPedidoCollectionOrphanCheckComboProductoHasPedido + " in its comboProductoHasPedidoCollection field has a non-nullable comboProducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            CategoriaCombo categoriacomboidCategoriaCombo = comboProducto.getCategoriacomboidCategoriaCombo();
            if (categoriacomboidCategoriaCombo != null) {
                categoriacomboidCategoriaCombo.getComboProductoCollection().remove(comboProducto);
                categoriacomboidCategoriaCombo = em.merge(categoriacomboidCategoriaCombo);
            }
            em.remove(comboProducto);
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

    public List<ComboProducto> findComboProductoEntities() {
        return findComboProductoEntities(true, -1, -1);
    }

    public List<ComboProducto> findComboProductoEntities(int maxResults, int firstResult) {
        return findComboProductoEntities(false, maxResults, firstResult);
    }

    private List<ComboProducto> findComboProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ComboProducto.class));
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

    public ComboProducto findComboProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ComboProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getComboProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ComboProducto> rt = cq.from(ComboProducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
