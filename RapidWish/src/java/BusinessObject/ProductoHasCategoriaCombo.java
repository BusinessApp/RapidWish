/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessObject;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author croa
 */
@Entity
@Table(name = "producto_has_categoria_combo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductoHasCategoriaCombo.findAll", query = "SELECT p FROM ProductoHasCategoriaCombo p"),
    @NamedQuery(name = "ProductoHasCategoriaCombo.findByProductoidProducto", query = "SELECT p FROM ProductoHasCategoriaCombo p WHERE p.productoHasCategoriaComboPK.productoidProducto = :productoidProducto"),
    @NamedQuery(name = "ProductoHasCategoriaCombo.findByCategoriacomboidCategoriaCombo", query = "SELECT p FROM ProductoHasCategoriaCombo p WHERE p.productoHasCategoriaComboPK.categoriacomboidCategoriaCombo = :categoriacomboidCategoriaCombo"),
    @NamedQuery(name = "ProductoHasCategoriaCombo.findByCantidaPermitida", query = "SELECT p FROM ProductoHasCategoriaCombo p WHERE p.cantidaPermitida = :cantidaPermitida")})
public class ProductoHasCategoriaCombo implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProductoHasCategoriaComboPK productoHasCategoriaComboPK;
    @Column(name = "cantida_Permitida")
    private Integer cantidaPermitida;
    @JoinColumn(name = "categoria_combo_idCategoria_Combo", referencedColumnName = "idCategoria_Combo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CategoriaCombo categoriaCombo;
    @JoinColumn(name = "producto_idProducto", referencedColumnName = "idProducto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;

    public ProductoHasCategoriaCombo() {
    }

    public ProductoHasCategoriaCombo(ProductoHasCategoriaComboPK productoHasCategoriaComboPK) {
        this.productoHasCategoriaComboPK = productoHasCategoriaComboPK;
    }

    public ProductoHasCategoriaCombo(int productoidProducto, int categoriacomboidCategoriaCombo) {
        this.productoHasCategoriaComboPK = new ProductoHasCategoriaComboPK(productoidProducto, categoriacomboidCategoriaCombo);
    }

    public ProductoHasCategoriaComboPK getProductoHasCategoriaComboPK() {
        return productoHasCategoriaComboPK;
    }

    public void setProductoHasCategoriaComboPK(ProductoHasCategoriaComboPK productoHasCategoriaComboPK) {
        this.productoHasCategoriaComboPK = productoHasCategoriaComboPK;
    }

    public Integer getCantidaPermitida() {
        return cantidaPermitida;
    }

    public void setCantidaPermitida(Integer cantidaPermitida) {
        this.cantidaPermitida = cantidaPermitida;
    }

    public CategoriaCombo getCategoriaCombo() {
        return categoriaCombo;
    }

    public void setCategoriaCombo(CategoriaCombo categoriaCombo) {
        this.categoriaCombo = categoriaCombo;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoHasCategoriaComboPK != null ? productoHasCategoriaComboPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoHasCategoriaCombo)) {
            return false;
        }
        ProductoHasCategoriaCombo other = (ProductoHasCategoriaCombo) object;
        if ((this.productoHasCategoriaComboPK == null && other.productoHasCategoriaComboPK != null) || (this.productoHasCategoriaComboPK != null && !this.productoHasCategoriaComboPK.equals(other.productoHasCategoriaComboPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BusinessObject.ProductoHasCategoriaCombo[ productoHasCategoriaComboPK=" + productoHasCategoriaComboPK + " ]";
    }
    
}
