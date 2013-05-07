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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author croa
 */
@Entity
@Table(name = "pedido")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pedido.findAll", query = "SELECT p FROM Pedido p"),
    @NamedQuery(name = "Pedido.findByIdPedido", query = "SELECT p FROM Pedido p WHERE p.idPedido = :idPedido"),
    @NamedQuery(name = "Pedido.findByFehcaPedido", query = "SELECT p FROM Pedido p WHERE p.fehcaPedido = :fehcaPedido"),
    @NamedQuery(name = "Pedido.findByCodRadicacion", query = "SELECT p FROM Pedido p WHERE p.codRadicacion = :codRadicacion")})
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPedido")
    private Integer idPedido;
    @Column(name = "fehca_Pedido")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fehcaPedido;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "cod_radicacion")
    private String codRadicacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido")
    private Collection<ComboProductoHasPedido> comboProductoHasPedidoCollection;
    @JoinColumn(name = "cliente_idCliente", referencedColumnName = "idCliente")
    @ManyToOne(optional = false)
    private Cliente clienteidCliente;
    @JoinColumn(name = "estado_pedido_idEstado_Pedido", referencedColumnName = "idEstado_Pedido")
    @ManyToOne(optional = false)
    private EstadoPedido estadopedidoidEstadoPedido;

    public Pedido() {
    }

    public Pedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Pedido(Integer idPedido, String codRadicacion) {
        this.idPedido = idPedido;
        this.codRadicacion = codRadicacion;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Date getFehcaPedido() {
        return fehcaPedido;
    }

    public void setFehcaPedido(Date fehcaPedido) {
        this.fehcaPedido = fehcaPedido;
    }

    public String getCodRadicacion() {
        return codRadicacion;
    }

    public void setCodRadicacion(String codRadicacion) {
        this.codRadicacion = codRadicacion;
    }

    @XmlTransient
    public Collection<ComboProductoHasPedido> getComboProductoHasPedidoCollection() {
        return comboProductoHasPedidoCollection;
    }

    public void setComboProductoHasPedidoCollection(Collection<ComboProductoHasPedido> comboProductoHasPedidoCollection) {
        this.comboProductoHasPedidoCollection = comboProductoHasPedidoCollection;
    }

    public Cliente getClienteidCliente() {
        return clienteidCliente;
    }

    public void setClienteidCliente(Cliente clienteidCliente) {
        this.clienteidCliente = clienteidCliente;
    }

    public EstadoPedido getEstadopedidoidEstadoPedido() {
        return estadopedidoidEstadoPedido;
    }

    public void setEstadopedidoidEstadoPedido(EstadoPedido estadopedidoidEstadoPedido) {
        this.estadopedidoidEstadoPedido = estadopedidoidEstadoPedido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPedido != null ? idPedido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedido)) {
            return false;
        }
        Pedido other = (Pedido) object;
        if ((this.idPedido == null && other.idPedido != null) || (this.idPedido != null && !this.idPedido.equals(other.idPedido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BusinessObject.Pedido[ idPedido=" + idPedido + " ]";
    }
    
}
