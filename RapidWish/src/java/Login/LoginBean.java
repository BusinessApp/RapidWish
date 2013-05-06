/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;


/**
 *
 * @author Sierra
 */
@ManagedBean
@SessionScoped
public class LoginBean {

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {}
    
    private String usuario;
    private String contrasena;

    public String getUsername() {
        return usuario;
    }

    public void setUsername(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return contrasena;
    }

    public void setPassword(String contrasena) {
        this.contrasena = contrasena;
    }

    public void login(ActionEvent actionEvent) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
        System.out.println(usuario +" pass" + contrasena);
        if (usuario.equals("admin") && contrasena.equals("admin")) {
            loggedIn = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", usuario);
        } else {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje Inicio", "Credenciales Inválidas");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);
    }

}
