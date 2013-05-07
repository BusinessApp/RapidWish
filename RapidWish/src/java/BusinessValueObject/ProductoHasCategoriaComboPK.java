/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessValueObject;

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
public class ProductoHasCategoriaComboPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "producto_idProducto")
    private int productoidProducto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "categoria_combo_idCategoria_Combo")
    private int categoriacomboidCategoriaCombo;

    public ProductoHasCategoriaComboPK() {
    }

    public ProductoHasCategoriaComboPK(int productoidProducto, int categoriacomboidCategoriaCombo) {
        this.productoidProducto = productoidProducto;
        this.categoriacomboidCategoriaCombo = categoriacomboidCategoriaCombo;
    }

    public int getProductoidProducto() {
        return productoidProducto;
    }

    public void setProductoidProducto(int productoidProducto) {
        this.productoidProducto = productoidProducto;
    }

    public int getCategoriacomboidCategoriaCombo() {
        return categoriacomboidCategoriaCombo;
    }

    public void setCategoriacomboidCategoriaCombo(int categoriacomboidCategoriaCombo) {
        this.categoriacomboidCategoriaCombo = categoriacomboidCategoriaCombo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) productoidProducto;
        hash += (int) categoriacomboidCategoriaCombo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoHasCategoriaComboPK)) {
            return false;
        }
        ProductoHasCategoriaComboPK other = (ProductoHasCategoriaComboPK) object;
        if (this.productoidProducto != other.productoidProducto) {
            return false;
        }
        if (this.categoriacomboidCategoriaCombo != other.categoriacomboidCategoriaCombo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BusinessObject.ProductoHasCategoriaComboPK[ productoidProducto=" + productoidProducto + ", categoriacomboidCategoriaCombo=" + categoriacomboidCategoriaCombo + " ]";
    }
    
}
