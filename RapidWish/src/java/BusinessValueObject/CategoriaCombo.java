/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessValueObject;

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
 * @author ACER
 */
@Entity
@Table(name = "categoria_combo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoriaCombo.findAll", query = "SELECT c FROM CategoriaCombo c"),
    @NamedQuery(name = "CategoriaCombo.findByIdCategoriaCombo", query = "SELECT c FROM CategoriaCombo c WHERE c.idCategoriaCombo = :idCategoriaCombo"),
    @NamedQuery(name = "CategoriaCombo.findByNombre", query = "SELECT c FROM CategoriaCombo c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "CategoriaCombo.findByValorCombo", query = "SELECT c FROM CategoriaCombo c WHERE c.valorCombo = :valorCombo"),
    @NamedQuery(name = "CategoriaCombo.findByImagen", query = "SELECT c FROM CategoriaCombo c WHERE c.imagen = :imagen"),
    @NamedQuery(name = "CategoriaCombo.findByFechaCreacion", query = "SELECT c FROM CategoriaCombo c WHERE c.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "CategoriaCombo.findByEstado", query = "SELECT c FROM CategoriaCombo c WHERE c.estado = :estado")})
public class CategoriaCombo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCategoria_Combo")
    private Integer idCategoriaCombo;
    @Size(max = 254)
    @Column(name = "nombre")
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_Combo")
    private Float valorCombo;
    @Size(max = 250)
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "estado")
    private Integer estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoriacomboidCategoriaCombo")
    private Collection<ComboProducto> comboProductoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoriaCombo")
    private Collection<ProductoHasCategoriaCombo> productoHasCategoriaComboCollection;

    public CategoriaCombo() {
    }

    public CategoriaCombo(Integer idCategoriaCombo) {
        this.idCategoriaCombo = idCategoriaCombo;
    }

    public Integer getIdCategoriaCombo() {
        return idCategoriaCombo;
    }

    public void setIdCategoriaCombo(Integer idCategoriaCombo) {
        this.idCategoriaCombo = idCategoriaCombo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getValorCombo() {
        return valorCombo;
    }

    public void setValorCombo(Float valorCombo) {
        this.valorCombo = valorCombo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<ComboProducto> getComboProductoCollection() {
        return comboProductoCollection;
    }

    public void setComboProductoCollection(Collection<ComboProducto> comboProductoCollection) {
        this.comboProductoCollection = comboProductoCollection;
    }

    @XmlTransient
    public Collection<ProductoHasCategoriaCombo> getProductoHasCategoriaComboCollection() {
        return productoHasCategoriaComboCollection;
    }

    public void setProductoHasCategoriaComboCollection(Collection<ProductoHasCategoriaCombo> productoHasCategoriaComboCollection) {
        this.productoHasCategoriaComboCollection = productoHasCategoriaComboCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCategoriaCombo != null ? idCategoriaCombo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaCombo)) {
            return false;
        }
        CategoriaCombo other = (CategoriaCombo) object;
        if ((this.idCategoriaCombo == null && other.idCategoriaCombo != null) || (this.idCategoriaCombo != null && !this.idCategoriaCombo.equals(other.idCategoriaCombo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BusinessValueObject.CategoriaCombo[ idCategoriaCombo=" + idCategoriaCombo + " ]";
    }
    
}
