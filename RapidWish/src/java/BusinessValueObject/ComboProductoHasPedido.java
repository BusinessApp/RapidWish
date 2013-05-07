/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessValueObject;

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
@Table(name = "combo_producto_has_pedido")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComboProductoHasPedido.findAll", query = "SELECT c FROM ComboProductoHasPedido c"),
    @NamedQuery(name = "ComboProductoHasPedido.findByComboproductoidComboProducto", query = "SELECT c FROM ComboProductoHasPedido c WHERE c.comboProductoHasPedidoPK.comboproductoidComboProducto = :comboproductoidComboProducto"),
    @NamedQuery(name = "ComboProductoHasPedido.findByPedidoidPedido", query = "SELECT c FROM ComboProductoHasPedido c WHERE c.comboProductoHasPedidoPK.pedidoidPedido = :pedidoidPedido"),
    @NamedQuery(name = "ComboProductoHasPedido.findByCantidadCombo", query = "SELECT c FROM ComboProductoHasPedido c WHERE c.cantidadCombo = :cantidadCombo")})
public class ComboProductoHasPedido implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComboProductoHasPedidoPK comboProductoHasPedidoPK;
    @Column(name = "cantidad_Combo")
    private Integer cantidadCombo;
    @JoinColumn(name = "pedido_idPedido", referencedColumnName = "idPedido", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pedido pedido;
    @JoinColumn(name = "combo_producto_idCombo_Producto", referencedColumnName = "idCombo_Producto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ComboProducto comboProducto;

    public ComboProductoHasPedido() {
    }

    public ComboProductoHasPedido(ComboProductoHasPedidoPK comboProductoHasPedidoPK) {
        this.comboProductoHasPedidoPK = comboProductoHasPedidoPK;
    }

    public ComboProductoHasPedido(int comboproductoidComboProducto, int pedidoidPedido) {
        this.comboProductoHasPedidoPK = new ComboProductoHasPedidoPK(comboproductoidComboProducto, pedidoidPedido);
    }

    public ComboProductoHasPedidoPK getComboProductoHasPedidoPK() {
        return comboProductoHasPedidoPK;
    }

    public void setComboProductoHasPedidoPK(ComboProductoHasPedidoPK comboProductoHasPedidoPK) {
        this.comboProductoHasPedidoPK = comboProductoHasPedidoPK;
    }

    public Integer getCantidadCombo() {
        return cantidadCombo;
    }

    public void setCantidadCombo(Integer cantidadCombo) {
        this.cantidadCombo = cantidadCombo;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
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
        hash += (comboProductoHasPedidoPK != null ? comboProductoHasPedidoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComboProductoHasPedido)) {
            return false;
        }
        ComboProductoHasPedido other = (ComboProductoHasPedido) object;
        if ((this.comboProductoHasPedidoPK == null && other.comboProductoHasPedidoPK != null) || (this.comboProductoHasPedidoPK != null && !this.comboProductoHasPedidoPK.equals(other.comboProductoHasPedidoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BusinessObject.ComboProductoHasPedido[ comboProductoHasPedidoPK=" + comboProductoHasPedidoPK + " ]";
    }
    
}
