/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessObject;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author croa
 */
@Embeddable
public class ProductoHasComboProductoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "producto_idProducto")
    private int productoidProducto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "combo_producto_idCombo_Producto")
    private int comboproductoidComboProducto;

    public ProductoHasComboProductoPK() {
    }

    public ProductoHasComboProductoPK(int productoidProducto, int comboproductoidComboProducto) {
        this.productoidProducto = productoidProducto;
        this.comboproductoidComboProducto = comboproductoidComboProducto;
    }

    public int getProductoidProducto() {
        return productoidProducto;
    }

    public void setProductoidProducto(int productoidProducto) {
        this.productoidProducto = productoidProducto;
    }

    public int getComboproductoidComboProducto() {
        return comboproductoidComboProducto;
    }

    public void setComboproductoidComboProducto(int comboproductoidComboProducto) {
        this.comboproductoidComboProducto = comboproductoidComboProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) productoidProducto;
        hash += (int) comboproductoidComboProducto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoHasComboProductoPK)) {
            return false;
        }
        ProductoHasComboProductoPK other = (ProductoHasComboProductoPK) object;
        if (this.productoidProducto != other.productoidProducto) {
            return false;
        }
        if (this.comboproductoidComboProducto != other.comboproductoidComboProducto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BusinessObject.ProductoHasComboProductoPK[ productoidProducto=" + productoidProducto + ", comboproductoidComboProducto=" + comboproductoidComboProducto + " ]";
    }
    
}
