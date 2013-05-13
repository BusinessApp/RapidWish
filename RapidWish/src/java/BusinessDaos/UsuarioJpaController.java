/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessDaos;

import BusinessDaos.exceptions.NonexistentEntityException;
import BusinessDaos.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import BusinessValueObject.Sucursal;
import BusinessValueObject.Rol;
import BusinessValueObject.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author ACER
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Sucursal SUCURSALidSucursal = usuario.getSUCURSALidSucursal();
            if (SUCURSALidSucursal != null) {
                SUCURSALidSucursal = em.getReference(SUCURSALidSucursal.getClass(), SUCURSALidSucursal.getIdSucursal());
                usuario.setSUCURSALidSucursal(SUCURSALidSucursal);
            }
            Rol ROLidRol = usuario.getROLidRol();
            if (ROLidRol != null) {
                ROLidRol = em.getReference(ROLidRol.getClass(), ROLidRol.getIdRol());
                usuario.setROLidRol(ROLidRol);
            }
            em.persist(usuario);
            if (SUCURSALidSucursal != null) {
                SUCURSALidSucursal.getUsuarioCollection().add(usuario);
                SUCURSALidSucursal = em.merge(SUCURSALidSucursal);
            }
            if (ROLidRol != null) {
                ROLidRol.getUsuarioCollection().add(usuario);
                ROLidRol = em.merge(ROLidRol);
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

    public void edit(Usuario usuario) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdusuario());
            Sucursal SUCURSALidSucursalOld = persistentUsuario.getSUCURSALidSucursal();
            Sucursal SUCURSALidSucursalNew = usuario.getSUCURSALidSucursal();
            Rol ROLidRolOld = persistentUsuario.getROLidRol();
            Rol ROLidRolNew = usuario.getROLidRol();
            if (SUCURSALidSucursalNew != null) {
                SUCURSALidSucursalNew = em.getReference(SUCURSALidSucursalNew.getClass(), SUCURSALidSucursalNew.getIdSucursal());
                usuario.setSUCURSALidSucursal(SUCURSALidSucursalNew);
            }
            if (ROLidRolNew != null) {
                ROLidRolNew = em.getReference(ROLidRolNew.getClass(), ROLidRolNew.getIdRol());
                usuario.setROLidRol(ROLidRolNew);
            }
            usuario = em.merge(usuario);
            if (SUCURSALidSucursalOld != null && !SUCURSALidSucursalOld.equals(SUCURSALidSucursalNew)) {
                SUCURSALidSucursalOld.getUsuarioCollection().remove(usuario);
                SUCURSALidSucursalOld = em.merge(SUCURSALidSucursalOld);
            }
            if (SUCURSALidSucursalNew != null && !SUCURSALidSucursalNew.equals(SUCURSALidSucursalOld)) {
                SUCURSALidSucursalNew.getUsuarioCollection().add(usuario);
                SUCURSALidSucursalNew = em.merge(SUCURSALidSucursalNew);
            }
            if (ROLidRolOld != null && !ROLidRolOld.equals(ROLidRolNew)) {
                ROLidRolOld.getUsuarioCollection().remove(usuario);
                ROLidRolOld = em.merge(ROLidRolOld);
            }
            if (ROLidRolNew != null && !ROLidRolNew.equals(ROLidRolOld)) {
                ROLidRolNew.getUsuarioCollection().add(usuario);
                ROLidRolNew = em.merge(ROLidRolNew);
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
                Integer id = usuario.getIdusuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            utx.begin();
            em = getEntityManager();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdusuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            Sucursal SUCURSALidSucursal = usuario.getSUCURSALidSucursal();
            if (SUCURSALidSucursal != null) {
                SUCURSALidSucursal.getUsuarioCollection().remove(usuario);
                SUCURSALidSucursal = em.merge(SUCURSALidSucursal);
            }
            Rol ROLidRol = usuario.getROLidRol();
            if (ROLidRol != null) {
                ROLidRol.getUsuarioCollection().remove(usuario);
                ROLidRol = em.merge(ROLidRol);
            }
            em.remove(usuario);
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

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
