/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessObject;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author croa
 */
@Entity
@Table(name = "combo_producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComboProducto.findAll", query = "SELECT c FROM ComboProducto c"),
    @NamedQuery(name = "ComboProducto.findByIdComboProducto", query = "SELECT c FROM ComboProducto c WHERE c.idComboProducto = :idComboProducto"),
    @NamedQuery(name = "ComboProducto.findByNombre", query = "SELECT c FROM ComboProducto c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ComboProducto.findByDescripcion", query = "SELECT c FROM ComboProducto c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "ComboProducto.findByCodigoPromocion", query = "SELECT c FROM ComboProducto c WHERE c.codigoPromocion = :codigoPromocion"),
    @NamedQuery(name = "ComboProducto.findByFechaCreacion", query = "SELECT c FROM ComboProducto c WHERE c.fechaCreacion = :fechaCreacion")})
public class ComboProducto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCombo_Producto")
    private Integer idComboProducto;
    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 254)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 100)
    @Column(name = "codigo_Promocion")
    private String codigoPromocion;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comboProducto")
    private Collection<ProductoHasComboProducto> productoHasComboProductoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comboProducto")
    private Collection<ComboProductoHasPedido> comboProductoHasPedidoCollection;
    @JoinColumn(name = "categoria_combo_idCategoria_Combo", referencedColumnName = "idCategoria_Combo")
    @ManyToOne(optional = false)
    private CategoriaCombo categoriacomboidCategoriaCombo;

    public ComboProducto() {
    }

    public ComboProducto(Integer idComboProducto) {
        this.idComboProducto = idComboProducto;
    }

    public Integer getIdComboProducto() {
        return idComboProducto;
    }

    public void setIdComboProducto(Integer idComboProducto) {
        this.idComboProducto = idComboProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoPromocion() {
        return codigoPromocion;
    }

    public void setCodigoPromocion(String codigoPromocion) {
        this.codigoPromocion = codigoPromocion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @XmlTransient
    public Collection<ProductoHasComboProducto> getProductoHasComboProductoCollection() {
        return productoHasComboProductoCollection;
    }

    public void setProductoHasComboProductoCollection(Collection<ProductoHasComboProducto> productoHasComboProductoCollection) {
        this.productoHasComboProductoCollection = productoHasComboProductoCollection;
    }

    @XmlTransient
    public Collection<ComboProductoHasPedido> getComboProductoHasPedidoCollection() {
        return comboProductoHasPedidoCollection;
    }

    public void setComboProductoHasPedidoCollection(Collection<ComboProductoHasPedido> comboProductoHasPedidoCollection) {
        this.comboProductoHasPedidoCollection = comboProductoHasPedidoCollection;
    }

    public CategoriaCombo getCategoriacomboidCategoriaCombo() {
        return categoriacomboidCategoriaCombo;
    }

    public void setCategoriacomboidCategoriaCombo(CategoriaCombo categoriacomboidCategoriaCombo) {
        this.categoriacomboidCategoriaCombo = categoriacomboidCategoriaCombo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComboProducto != null ? idComboProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComboProducto)) {
            return false;
        }
        ComboProducto other = (ComboProducto) object;
        if ((this.idComboProducto == null && other.idComboProducto != null) || (this.idComboProducto != null && !this.idComboProducto.equals(other.idComboProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BusinessObject.ComboProducto[ idComboProducto=" + idComboProducto + " ]";
    }
    
}
