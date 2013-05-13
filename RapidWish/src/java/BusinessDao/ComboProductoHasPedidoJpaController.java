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
import BusinessObject.Pedido;
import BusinessObject.ComboProducto;
import BusinessObject.ComboProductoHasPedido;
import BusinessObject.ComboProductoHasPedidoPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author ACER
 */
public class ComboProductoHasPedidoJpaController implements Serializable {

    public ComboProductoHasPedidoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ComboProductoHasPedido comboProductoHasPedido) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (comboProductoHasPedido.getComboProductoHasPedidoPK() == null) {
            comboProductoHasPedido.setComboProductoHasPedidoPK(new ComboProductoHasPedidoPK());
        }
        comboProductoHasPedido.getComboProductoHasPedidoPK().setPedidoidPedido(comboProductoHasPedido.getPedido().getIdPedido());
        comboProductoHasPedido.getComboProductoHasPedidoPK().setComboproductoidComboProducto(comboProductoHasPedido.getComboProducto().getIdComboProducto());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Pedido pedido = comboProductoHasPedido.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getIdPedido());
                comboProductoHasPedido.setPedido(pedido);
            }
            ComboProducto comboProducto = comboProductoHasPedido.getComboProducto();
            if (comboProducto != null) {
                comboProducto = em.getReference(comboProducto.getClass(), comboProducto.getIdComboProducto());
                comboProductoHasPedido.setComboProducto(comboProducto);
            }
            em.persist(comboProductoHasPedido);
            if (pedido != null) {
                pedido.getComboProductoHasPedidoCollection().add(comboProductoHasPedido);
                pedido = em.merge(pedido);
            }
            if (comboProducto != null) {
                comboProducto.getComboProductoHasPedidoCollection().add(comboProductoHasPedido);
                comboProducto = em.merge(comboProducto);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findComboProductoHasPedido(comboProductoHasPedido.getComboProductoHasPedidoPK()) != null) {
                throw new PreexistingEntityException("ComboProductoHasPedido " + comboProductoHasPedido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ComboProductoHasPedido comboProductoHasPedido) throws NonexistentEntityException, RollbackFailureException, Exception {
        comboProductoHasPedido.getComboProductoHasPedidoPK().setPedidoidPedido(comboProductoHasPedido.getPedido().getIdPedido());
        comboProductoHasPedido.getComboProductoHasPedidoPK().setComboproductoidComboProducto(comboProductoHasPedido.getComboProducto().getIdComboProducto());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ComboProductoHasPedido persistentComboProductoHasPedido = em.find(ComboProductoHasPedido.class, comboProductoHasPedido.getComboProductoHasPedidoPK());
            Pedido pedidoOld = persistentComboProductoHasPedido.getPedido();
            Pedido pedidoNew = comboProductoHasPedido.getPedido();
            ComboProducto comboProductoOld = persistentComboProductoHasPedido.getComboProducto();
            ComboProducto comboProductoNew = comboProductoHasPedido.getComboProducto();
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getIdPedido());
                comboProductoHasPedido.setPedido(pedidoNew);
            }
            if (comboProductoNew != null) {
                comboProductoNew = em.getReference(comboProductoNew.getClass(), comboProductoNew.getIdComboProducto());
                comboProductoHasPedido.setComboProducto(comboProductoNew);
            }
            comboProductoHasPedido = em.merge(comboProductoHasPedido);
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                pedidoOld.getComboProductoHasPedidoCollection().remove(comboProductoHasPedido);
                pedidoOld = em.merge(pedidoOld);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                pedidoNew.getComboProductoHasPedidoCollection().add(comboProductoHasPedido);
                pedidoNew = em.merge(pedidoNew);
            }
            if (comboProductoOld != null && !comboProductoOld.equals(comboProductoNew)) {
                comboProductoOld.getComboProductoHasPedidoCollection().remove(comboProductoHasPedido);
                comboProductoOld = em.merge(comboProductoOld);
            }
            if (comboProductoNew != null && !comboProductoNew.equals(comboProductoOld)) {
                comboProductoNew.getComboProductoHasPedidoCollection().add(comboProductoHasPedido);
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
                ComboProductoHasPedidoPK id = comboProductoHasPedido.getComboProductoHasPedidoPK();
                if (findComboProductoHasPedido(id) == null) {
                    throw new NonexistentEntityException("The comboProductoHasPedido with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ComboProductoHasPedidoPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ComboProductoHasPedido comboProductoHasPedido;
            try {
                comboProductoHasPedido = em.getReference(ComboProductoHasPedido.class, id);
                comboProductoHasPedido.getComboProductoHasPedidoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comboProductoHasPedido with id " + id + " no longer exists.", enfe);
            }
            Pedido pedido = comboProductoHasPedido.getPedido();
            if (pedido != null) {
                pedido.getComboProductoHasPedidoCollection().remove(comboProductoHasPedido);
                pedido = em.merge(pedido);
            }
            ComboProducto comboProducto = comboProductoHasPedido.getComboProducto();
            if (comboProducto != null) {
                comboProducto.getComboProductoHasPedidoCollection().remove(comboProductoHasPedido);
                comboProducto = em.merge(comboProducto);
            }
            em.remove(comboProductoHasPedido);
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

    public List<ComboProductoHasPedido> findComboProductoHasPedidoEntities() {
        return findComboProductoHasPedidoEntities(true, -1, -1);
    }

    public List<ComboProductoHasPedido> findComboProductoHasPedidoEntities(int maxResults, int firstResult) {
        return findComboProductoHasPedidoEntities(false, maxResults, firstResult);
    }

    private List<ComboProductoHasPedido> findComboProductoHasPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ComboProductoHasPedido.class));
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

    public ComboProductoHasPedido findComboProductoHasPedido(ComboProductoHasPedidoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ComboProductoHasPedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getComboProductoHasPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ComboProductoHasPedido> rt = cq.from(ComboProductoHasPedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
