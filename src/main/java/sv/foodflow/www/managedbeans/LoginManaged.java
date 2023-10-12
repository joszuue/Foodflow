package sv.foodflow.www.managedbeans;


import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.context.FacesContext;
import org.mindrot.jbcrypt.BCrypt;


import sv.foodflow.www.entities.ClientesEntity;
import sv.foodflow.www.entities.EmpleadosEntity;
import sv.foodflow.www.models.LoginModel;

import java.io.IOException;

@ManagedBean
@RequestScoped
public class LoginManaged {
    private EmpleadosEntity empleado;

    private ClientesEntity cliente;
    LoginModel model = new LoginModel();

    public LoginManaged(){
        empleado = new EmpleadosEntity();
        cliente = new ClientesEntity();
    }

    public String login() {
        boolean verify = autenticando();
        String pagina = null;
        if (verify){
            EmpleadosEntity empleLogin = model.datosSession(empleado.getCodigo());
            if (empleLogin != null){
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("emple", empleLogin);
                if (empleLogin.getEstado().equals("no")){
                    pagina = "/contra?faces-redirect=true";
                }else{
                    switch (empleLogin.getRol()){
                        case "Admin":
                            pagina = "/admin/inicio?faces-redirect=true";
                            break;
                        case "Cocina":
                            pagina = "/jefeCocina/inicio?faces-redirect=true";
                            break;
                        case "Mesero":
                            pagina = "/mesero/inicio?faces-redirect=true";
                            break;
                    }
                }
            }else {
                FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                        FacesMessage(FacesMessage.SEVERITY_ERROR, "Creedenciales incorrectas", "El código de empleado o la contraseña es incorrecta."));
            }
        }else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Creedenciales incorrectas", "El código de empleado o la contraseña es incorrecta."));
        }
        empleado = new EmpleadosEntity();
        return pagina;
    }

    public String loginClient(){
        ClientesEntity clientLogin = model.obtenerCliente(cliente.getCodigoClient());
        if (clientLogin != null){
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cliente", clientLogin);
            return "/clientes/inicio?faces-redirect=true";
        }else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El código que ha proporcionado no existe."));
            return null;
        }
    }

    public String logOutCliente() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/loginCliente?faces-redirect=true";
    }


    public Boolean autenticando(){
        String passBdd = model.obtenerPass(empleado.getCodigo());
        if (passBdd != null && BCrypt.checkpw(empleado.getContraseña(), passBdd)){
            return true;
        }else {
            return false;
        }
    }

    public String logOut() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/loginEmple?faces-redirect=true";
    }

    public EmpleadosEntity getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadosEntity empleado) {
        this.empleado = empleado;
    }

    public LoginModel getModel() {
        return model;
    }

    public void setModel(LoginModel model) {
        this.model = model;
    }

    public ClientesEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClientesEntity cliente) {
        this.cliente = cliente;
    }
}
