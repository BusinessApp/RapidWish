/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessObject;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author croa
 */
@Entity
@Table(name = "sugerencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sugerencia.findAll", query = "SELECT s FROM Sugerencia s"),
    @NamedQuery(name = "Sugerencia.findByIdSugerencia", query = "SELECT s FROM Sugerencia s WHERE s.idSugerencia = :idSugerencia"),
    @NamedQuery(name = "Sugerencia.findByFecha", query = "SELECT s FROM Sugerencia s WHERE s.fecha = :fecha")})
public class Sugerencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sugerencia")
    private Integer idSugerencia;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "id_cliente", referencedColumnName = "idCliente")
    @ManyToOne(optional = false)
    private Cliente idCliente;

    public Sugerencia() {
    }

    public Sugerencia(Integer idSugerencia) {
        this.idSugerencia = idSugerencia;
    }

    public Integer getIdSugerencia() {
        return idSugerencia;
    }

    public void setIdSugerencia(Integer idSugerencia) {
        this.idSugerencia = idSugerencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSugerencia != null ? idSugerencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sugerencia)) {
            return false;
        }
        Sugerencia other = (Sugerencia) object;
        if ((this.idSugerencia == null && other.idSugerencia != null) || (this.idSugerencia != null && !this.idSugerencia.equals(other.idSugerencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BusinessObject.Sugerencia[ idSugerencia=" + idSugerencia + " ]";
    }
    
}
