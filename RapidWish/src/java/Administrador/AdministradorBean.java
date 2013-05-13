package Administrador;

import BusinessDaos.CategoriaComboJpaController;
import BusinessValueObject.CategoriaCombo;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

@ManagedBean
@RequestScoped
public class AdministradorBean {

    private String nombreCategoria;
    private float valorCombo;
    private String imageCategoria;
    private Date fechaCreacion;
    private int estado;

    public AdministradorBean() {
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public float getValorCombo() {
        return valorCombo;
    }

    public void setValorCombo(float valorCombo) {
        this.valorCombo = valorCombo;
    }

    public String getImageCategoria() {
        return imageCategoria;
    }

    public void setImageCategoria(String imageCategoria) {
        this.imageCategoria = imageCategoria;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
   public void ingesarCategoria(ActionEvent actionEvent)
   {
       
       System.out.println("Nombre"+nombreCategoria);
   }
}
