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
 * @author ACER
 */
@Embeddable
public class ProductoHasComboProductoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "producto_idProducto")
    private int productoidProducto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMBO_PRODUCTO_idCombo_Producto")
    private int cOMBOPRODUCTOidComboProducto;

    public ProductoHasComboProductoPK() {
    }

    public ProductoHasComboProductoPK(int productoidProducto, int cOMBOPRODUCTOidComboProducto) {
        this.productoidProducto = productoidProducto;
        this.cOMBOPRODUCTOidComboProducto = cOMBOPRODUCTOidComboProducto;
    }

    public int getProductoidProducto() {
        return productoidProducto;
    }

    public void setProductoidProducto(int productoidProducto) {
        this.productoidProducto = productoidProducto;
    }

    public int getCOMBOPRODUCTOidComboProducto() {
        return cOMBOPRODUCTOidComboProducto;
    }

    public void setCOMBOPRODUCTOidComboProducto(int cOMBOPRODUCTOidComboProducto) {
        this.cOMBOPRODUCTOidComboProducto = cOMBOPRODUCTOidComboProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) productoidProducto;
        hash += (int) cOMBOPRODUCTOidComboProducto;
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
        if (this.cOMBOPRODUCTOidComboProducto != other.cOMBOPRODUCTOidComboProducto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BusinessValueObject.ProductoHasComboProductoPK[ productoidProducto=" + productoidProducto + ", cOMBOPRODUCTOidComboProducto=" + cOMBOPRODUCTOidComboProducto + " ]";
    }
    
}
