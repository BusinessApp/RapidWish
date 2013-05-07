/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessObject;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author croa
 */
@Entity
@Table(name = "producto_has_combo_producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductoHasComboProducto.findAll", query = "SELECT p FROM ProductoHasComboProducto p"),
    @NamedQuery(name = "ProductoHasComboProducto.findByProductoidProducto", query = "SELECT p FROM ProductoHasComboProducto p WHERE p.productoHasComboProductoPK.productoidProducto = :productoidProducto"),
    @NamedQuery(name = "ProductoHasComboProducto.findByComboproductoidComboProducto", query = "SELECT p FROM ProductoHasComboProducto p WHERE p.productoHasComboProductoPK.comboproductoidComboProducto = :comboproductoidComboProducto"),
    @NamedQuery(name = "ProductoHasComboProducto.findByEstado", query = "SELECT p FROM ProductoHasComboProducto p WHERE p.estado = :estado"),
    @NamedQuery(name = "ProductoHasComboProducto.findByCantidad", query = "SELECT p FROM ProductoHasComboProducto p WHERE p.cantidad = :cantidad")})
public class ProductoHasComboProducto implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProductoHasComboProductoPK productoHasComboProductoPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private int estado;
    @Column(name = "cantidad")
    private Integer cantidad;
    @JoinColumn(name = "producto_idProducto", referencedColumnName = "idProducto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;
    @JoinColumn(name = "combo_producto_idCombo_Producto", referencedColumnName = "idCombo_Producto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ComboProducto comboProducto;

    public ProductoHasComboProducto() {
    }

    public ProductoHasComboProducto(ProductoHasComboProductoPK productoHasComboProductoPK) {
        this.productoHasComboProductoPK = productoHasComboProductoPK;
    }

    public ProductoHasComboProducto(ProductoHasComboProductoPK productoHasComboProductoPK, int estado) {
        this.productoHasComboProductoPK = productoHasComboProductoPK;
        this.estado = estado;
    }

    public ProductoHasComboProducto(int productoidProducto, int comboproductoidComboProducto) {
        this.productoHasComboProductoPK = new ProductoHasComboProductoPK(productoidProducto, comboproductoidComboProducto);
    }

    public ProductoHasComboProductoPK getProductoHasComboProductoPK() {
        return productoHasComboProductoPK;
    }

    public void setProductoHasComboProductoPK(ProductoHasComboProductoPK productoHasComboProductoPK) {
        this.productoHasComboProductoPK = productoHasComboProductoPK;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public ComboProducto getComboProducto() {
        return comboProducto;
    }

    public void setComboProducto(ComboProducto comboProducto) {
        this.comboProducto = comboProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoHasComboProductoPK != null ? productoHasComboProductoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoHasComboProducto)) {
            return false;
        }
        ProductoHasComboProducto other = (ProductoHasComboProducto) object;
        if ((this.productoHasComboProductoPK == null && other.productoHasComboProductoPK != null) || (this.productoHasComboProductoPK != null && !this.productoHasComboProductoPK.equals(other.productoHasComboProductoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BusinessObject.ProductoHasComboProducto[ productoHasComboProductoPK=" + productoHasComboProductoPK + " ]";
    }
    
}
