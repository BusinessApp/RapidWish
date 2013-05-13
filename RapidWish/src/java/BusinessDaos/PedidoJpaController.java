/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessDaos;

import BusinessDaos.exceptions.IllegalOrphanException;
import BusinessDaos.exceptions.NonexistentEntityException;
import BusinessDaos.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import BusinessValueObject.EstadoPedido;
import BusinessValueObject.Cliente;
import BusinessValueObject.ComboProductoHasPedido;
import BusinessValueObject.Pedido;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author ACER
 */
public class PedidoJpaController implements Serializable {

    public PedidoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pedido pedido) throws RollbackFailureException, Exception {
        if (pedido.getComboProductoHasPedidoCollection() == null) {
            pedido.setComboProductoHasPedidoCollection(new ArrayList<ComboProductoHasPedido>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EstadoPedido estadopedidoidEstadoPedido = pedido.getEstadopedidoidEstadoPedido();
            if (estadopedidoidEstadoPedido != null) {
                estadopedidoidEstadoPedido = em.getReference(estadopedidoidEstadoPedido.getClass(), estadopedidoidEstadoPedido.getIdEstadoPedido());
                pedido.setEstadopedidoidEstadoPedido(estadopedidoidEstadoPedido);
            }
            Cliente clienteidCliente = pedido.getClienteidCliente();
            if (clienteidCliente != null) {
                clienteidCliente = em.getReference(clienteidCliente.getClass(), clienteidCliente.getIdCliente());
                pedido.setClienteidCliente(clienteidCliente);
            }
            Collection<ComboProductoHasPedido> attachedComboProductoHasPedidoCollection = new ArrayList<ComboProductoHasPedido>();
            for (ComboProductoHasPedido comboProductoHasPedidoCollectionComboProductoHasPedidoToAttach : pedido.getComboProductoHasPedidoCollection()) {
                comboProductoHasPedidoCollectionComboProductoHasPedidoToAttach = em.getReference(comboProductoHasPedidoCollectionComboProductoHasPedidoToAttach.getClass(), comboProductoHasPedidoCollectionComboProductoHasPedidoToAttach.getComboProductoHasPedidoPK());
                attachedComboProductoHasPedidoCollection.add(comboProductoHasPedidoCollectionComboProductoHasPedidoToAttach);
            }
            pedido.setComboProductoHasPedidoCollection(attachedComboProductoHasPedidoCollection);
            em.persist(pedido);
            if (estadopedidoidEstadoPedido != null) {
                estadopedidoidEstadoPedido.getPedidoCollection().add(pedido);
                estadopedidoidEstadoPedido = em.merge(estadopedidoidEstadoPedido);
            }
            if (clienteidCliente != null) {
                clienteidCliente.getPedidoCollection().add(pedido);
                clienteidCliente = em.merge(clienteidCliente);
            }
            for (ComboProductoHasPedido comboProductoHasPedidoCollectionComboProductoHasPedido : pedido.getComboProductoHasPedidoCollection()) {
                Pedido oldPedidoOfComboProductoHasPedidoCollectionComboProductoHasPedido = comboProductoHasPedidoCollectionComboProductoHasPedido.getPedido();
                comboProductoHasPedidoCollectionComboProductoHasPedido.setPedido(pedido);
                comboProductoHasPedidoCollectionComboProductoHasPedido = em.merge(comboProductoHasPedidoCollectionComboProductoHasPedido);
                if (oldPedidoOfComboProductoHasPedidoCollectionComboProductoHasPedido != null) {
                    oldPedidoOfComboProductoHasPedidoCollectionComboProductoHasPedido.getComboProductoHasPedidoCollection().remove(comboProductoHasPedidoCollectionComboProductoHasPedido);
                    oldPedidoOfComboProductoHasPedidoCollectionComboProductoHasPedido = em.merge(oldPedidoOfComboProductoHasPedidoCollectionComboProductoHasPedido);
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

    public void edit(Pedido pedido) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Pedido persistentPedido = em.find(Pedido.class, pedido.getIdPedido());
            EstadoPedido estadopedidoidEstadoPedidoOld = persistentPedido.getEstadopedidoidEstadoPedido();
            EstadoPedido estadopedidoidEstadoPedidoNew = pedido.getEstadopedidoidEstadoPedido();
            Cliente clienteidClienteOld = persistentPedido.getClienteidCliente();
            Cliente clienteidClienteNew = pedido.getClienteidCliente();
            Collection<ComboProductoHasPedido> comboProductoHasPedidoCollectionOld = persistentPedido.getComboProductoHasPedidoCollection();
            Collection<ComboProductoHasPedido> comboProductoHasPedidoCollectionNew = pedido.getComboProductoHasPedidoCollection();
            List<String> illegalOrphanMessages = null;
            for (ComboProductoHasPedido comboProductoHasPedidoCollectionOldComboProductoHasPedido : comboProductoHasPedidoCollectionOld) {
                if (!comboProductoHasPedidoCollectionNew.contains(comboProductoHasPedidoCollectionOldComboProductoHasPedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ComboProductoHasPedido " + comboProductoHasPedidoCollectionOldComboProductoHasPedido + " since its pedido field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (estadopedidoidEstadoPedidoNew != null) {
                estadopedidoidEstadoPedidoNew = em.getReference(estadopedidoidEstadoPedidoNew.getClass(), estadopedidoidEstadoPedidoNew.getIdEstadoPedido());
                pedido.setEstadopedidoidEstadoPedido(estadopedidoidEstadoPedidoNew);
            }
            if (clienteidClienteNew != null) {
                clienteidClienteNew = em.getReference(clienteidClienteNew.getClass(), clienteidClienteNew.getIdCliente());
                pedido.setClienteidCliente(clienteidClienteNew);
            }
            Collection<ComboProductoHasPedido> attachedComboProductoHasPedidoCollectionNew = new ArrayList<ComboProductoHasPedido>();
            for (ComboProductoHasPedido comboProductoHasPedidoCollectionNewComboProductoHasPedidoToAttach : comboProductoHasPedidoCollectionNew) {
                comboProductoHasPedidoCollectionNewComboProductoHasPedidoToAttach = em.getReference(comboProductoHasPedidoCollectionNewComboProductoHasPedidoToAttach.getClass(), comboProductoHasPedidoCollectionNewComboProductoHasPedidoToAttach.getComboProductoHasPedidoPK());
                attachedComboProductoHasPedidoCollectionNew.add(comboProductoHasPedidoCollectionNewComboProductoHasPedidoToAttach);
            }
            comboProductoHasPedidoCollectionNew = attachedComboProductoHasPedidoCollectionNew;
            pedido.setComboProductoHasPedidoCollection(comboProductoHasPedidoCollectionNew);
            pedido = em.merge(pedido);
            if (estadopedidoidEstadoPedidoOld != null && !estadopedidoidEstadoPedidoOld.equals(estadopedidoidEstadoPedidoNew)) {
                estadopedidoidEstadoPedidoOld.getPedidoCollection().remove(pedido);
                estadopedidoidEstadoPedidoOld = em.merge(estadopedidoidEstadoPedidoOld);
            }
            if (estadopedidoidEstadoPedidoNew != null && !estadopedidoidEstadoPedidoNew.equals(estadopedidoidEstadoPedidoOld)) {
                estadopedidoidEstadoPedidoNew.getPedidoCollection().add(pedido);
                estadopedidoidEstadoPedidoNew = em.merge(estadopedidoidEstadoPedidoNew);
            }
            if (clienteidClienteOld != null && !clienteidClienteOld.equals(clienteidClienteNew)) {
                clienteidClienteOld.getPedidoCollection().remove(pedido);
                clienteidClienteOld = em.merge(clienteidClienteOld);
            }
            if (clienteidClienteNew != null && !clienteidClienteNew.equals(clienteidClienteOld)) {
                clienteidClienteNew.getPedidoCollection().add(pedido);
                clienteidClienteNew = em.merge(clienteidClienteNew);
            }
            for (ComboProductoHasPedido comboProductoHasPedidoCollectionNewComboProductoHasPedido : comboProductoHasPedidoCollectionNew) {
                if (!comboProductoHasPedidoCollectionOld.contains(comboProductoHasPedidoCollectionNewComboProductoHasPedido)) {
                    Pedido oldPedidoOfComboProductoHasPedidoCollectionNewComboProductoHasPedido = comboProductoHasPedidoCollectionNewComboProductoHasPedido.getPedido();
                    comboProductoHasPedidoCollectionNewComboProductoHasPedido.setPedido(pedido);
                    comboProductoHasPedidoCollectionNewComboProductoHasPedido = em.merge(comboProductoHasPedidoCollectionNewComboProductoHasPedido);
                    if (oldPedidoOfComboProductoHasPedidoCollectionNewComboProductoHasPedido != null && !oldPedidoOfComboProductoHasPedidoCollectionNewComboProductoHasPedido.equals(pedido)) {
                        oldPedidoOfComboProductoHasPedidoCollectionNewComboProductoHasPedido.getComboProductoHasPedidoCollection().remove(comboProductoHasPedidoCollectionNewComboProductoHasPedido);
                        oldPedidoOfComboProductoHasPedidoCollectionNewComboProductoHasPedido = em.merge(oldPedidoOfComboProductoHasPedidoCollectionNewComboProductoHasPedido);
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
                Integer id = pedido.getIdPedido();
                if (findPedido(id) == null) {
                    throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.");
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
            Pedido pedido;
            try {
                pedido = em.getReference(Pedido.class, id);
                pedido.getIdPedido();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ComboProductoHasPedido> comboProductoHasPedidoCollectionOrphanCheck = pedido.getComboProductoHasPedidoCollection();
            for (ComboProductoHasPedido comboProductoHasPedidoCollectionOrphanCheckComboProductoHasPedido : comboProductoHasPedidoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pedido (" + pedido + ") cannot be destroyed since the ComboProductoHasPedido " + comboProductoHasPedidoCollectionOrphanCheckComboProductoHasPedido + " in its comboProductoHasPedidoCollection field has a non-nullable pedido field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            EstadoPedido estadopedidoidEstadoPedido = pedido.getEstadopedidoidEstadoPedido();
            if (estadopedidoidEstadoPedido != null) {
                estadopedidoidEstadoPedido.getPedidoCollection().remove(pedido);
                estadopedidoidEstadoPedido = em.merge(estadopedidoidEstadoPedido);
            }
            Cliente clienteidCliente = pedido.getClienteidCliente();
            if (clienteidCliente != null) {
                clienteidCliente.getPedidoCollection().remove(pedido);
                clienteidCliente = em.merge(clienteidCliente);
            }
            em.remove(pedido);
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

    public List<Pedido> findPedidoEntities() {
        return findPedidoEntities(true, -1, -1);
    }

    public List<Pedido> findPedidoEntities(int maxResults, int firstResult) {
        return findPedidoEntities(false, maxResults, firstResult);
    }

    private List<Pedido> findPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pedido.class));
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

    public Pedido findPedido(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pedido> rt = cq.from(Pedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
