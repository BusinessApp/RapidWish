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
public class ComboProductoHasPedidoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "combo_producto_idCombo_Producto")
    private int comboproductoidComboProducto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pedido_idPedido")
    private int pedidoidPedido;

    public ComboProductoHasPedidoPK() {
    }

    public ComboProductoHasPedidoPK(int comboproductoidComboProducto, int pedidoidPedido) {
        this.comboproductoidComboProducto = comboproductoidComboProducto;
        this.pedidoidPedido = pedidoidPedido;
    }

    public int getComboproductoidComboProducto() {
        return comboproductoidComboProducto;
    }

    public void setComboproductoidComboProducto(int comboproductoidComboProducto) {
        this.comboproductoidComboProducto = comboproductoidComboProducto;
    }

    public int getPedidoidPedido() {
        return pedidoidPedido;
    }

    public void setPedidoidPedido(int pedidoidPedido) {
        this.pedidoidPedido = pedidoidPedido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) comboproductoidComboProducto;
        hash += (int) pedidoidPedido;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComboProductoHasPedidoPK)) {
            return false;
        }
        ComboProductoHasPedidoPK other = (ComboProductoHasPedidoPK) object;
        if (this.comboproductoidComboProducto != other.comboproductoidComboProducto) {
            return false;
        }
        if (this.pedidoidPedido != other.pedidoidPedido) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BusinessValueObject.ComboProductoHasPedidoPK[ comboproductoidComboProducto=" + comboproductoidComboProducto + ", pedidoidPedido=" + pedidoidPedido + " ]";
    }
    
}
