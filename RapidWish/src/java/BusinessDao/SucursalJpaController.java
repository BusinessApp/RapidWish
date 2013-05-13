/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessDao;

import BusinessDao.exceptions.IllegalOrphanException;
import BusinessDao.exceptions.NonexistentEntityException;
import BusinessDao.exceptions.RollbackFailureException;
import BusinessObject.Sucursal;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import BusinessObject.Usuario;
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
public class SucursalJpaController implements Serializable {

    public SucursalJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sucursal sucursal) throws RollbackFailureException, Exception {
        if (sucursal.getUsuarioCollection() == null) {
            sucursal.setUsuarioCollection(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Usuario> attachedUsuarioCollection = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionUsuarioToAttach : sucursal.getUsuarioCollection()) {
                usuarioCollectionUsuarioToAttach = em.getReference(usuarioCollectionUsuarioToAttach.getClass(), usuarioCollectionUsuarioToAttach.getIdusuario());
                attachedUsuarioCollection.add(usuarioCollectionUsuarioToAttach);
            }
            sucursal.setUsuarioCollection(attachedUsuarioCollection);
            em.persist(sucursal);
            for (Usuario usuarioCollectionUsuario : sucursal.getUsuarioCollection()) {
                Sucursal oldSUCURSALidSucursalOfUsuarioCollectionUsuario = usuarioCollectionUsuario.getSUCURSALidSucursal();
                usuarioCollectionUsuario.setSUCURSALidSucursal(sucursal);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
                if (oldSUCURSALidSucursalOfUsuarioCollectionUsuario != null) {
                    oldSUCURSALidSucursalOfUsuarioCollectionUsuario.getUsuarioCollection().remove(usuarioCollectionUsuario);
                    oldSUCURSALidSucursalOfUsuarioCollectionUsuario = em.merge(oldSUCURSALidSucursalOfUsuarioCollectionUsuario);
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

    public void edit(Sucursal sucursal) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Sucursal persistentSucursal = em.find(Sucursal.class, sucursal.getIdSucursal());
            Collection<Usuario> usuarioCollectionOld = persistentSucursal.getUsuarioCollection();
            Collection<Usuario> usuarioCollectionNew = sucursal.getUsuarioCollection();
            List<String> illegalOrphanMessages = null;
            for (Usuario usuarioCollectionOldUsuario : usuarioCollectionOld) {
                if (!usuarioCollectionNew.contains(usuarioCollectionOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioCollectionOldUsuario + " since its SUCURSALidSucursal field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Usuario> attachedUsuarioCollectionNew = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionNewUsuarioToAttach : usuarioCollectionNew) {
                usuarioCollectionNewUsuarioToAttach = em.getReference(usuarioCollectionNewUsuarioToAttach.getClass(), usuarioCollectionNewUsuarioToAttach.getIdusuario());
                attachedUsuarioCollectionNew.add(usuarioCollectionNewUsuarioToAttach);
            }
            usuarioCollectionNew = attachedUsuarioCollectionNew;
            sucursal.setUsuarioCollection(usuarioCollectionNew);
            sucursal = em.merge(sucursal);
            for (Usuario usuarioCollectionNewUsuario : usuarioCollectionNew) {
                if (!usuarioCollectionOld.contains(usuarioCollectionNewUsuario)) {
                    Sucursal oldSUCURSALidSucursalOfUsuarioCollectionNewUsuario = usuarioCollectionNewUsuario.getSUCURSALidSucursal();
                    usuarioCollectionNewUsuario.setSUCURSALidSucursal(sucursal);
                    usuarioCollectionNewUsuario = em.merge(usuarioCollectionNewUsuario);
                    if (oldSUCURSALidSucursalOfUsuarioCollectionNewUsuario != null && !oldSUCURSALidSucursalOfUsuarioCollectionNewUsuario.equals(sucursal)) {
                        oldSUCURSALidSucursalOfUsuarioCollectionNewUsuario.getUsuarioCollection().remove(usuarioCollectionNewUsuario);
                        oldSUCURSALidSucursalOfUsuarioCollectionNewUsuario = em.merge(oldSUCURSALidSucursalOfUsuarioCollectionNewUsuario);
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
                Integer id = sucursal.getIdSucursal();
                if (findSucursal(id) == null) {
                    throw new NonexistentEntityException("The sucursal with id " + id + " no longer exists.");
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
            Sucursal sucursal;
            try {
                sucursal = em.getReference(Sucursal.class, id);
                sucursal.getIdSucursal();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sucursal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Usuario> usuarioCollectionOrphanCheck = sucursal.getUsuarioCollection();
            for (Usuario usuarioCollectionOrphanCheckUsuario : usuarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sucursal (" + sucursal + ") cannot be destroyed since the Usuario " + usuarioCollectionOrphanCheckUsuario + " in its usuarioCollection field has a non-nullable SUCURSALidSucursal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sucursal);
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

    public List<Sucursal> findSucursalEntities() {
        return findSucursalEntities(true, -1, -1);
    }

    public List<Sucursal> findSucursalEntities(int maxResults, int firstResult) {
        return findSucursalEntities(false, maxResults, firstResult);
    }

    private List<Sucursal> findSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sucursal.class));
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

    public Sucursal findSucursal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sucursal.class, id);
        } finally {
            em.close();
        }
    }

    public int getSucursalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sucursal> rt = cq.from(Sucursal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
