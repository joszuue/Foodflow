package sv.foodflow.www.managedbeans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.context.FacesContext;
import org.mindrot.jbcrypt.BCrypt;
import sv.foodflow.www.entities.EmpleadosEntity;
import sv.foodflow.www.models.EmpleadoModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@ManagedBean
@RequestScoped
public class EmpleadoManaged {
    EmpleadoModel modelo = new EmpleadoModel();
    private EmpleadosEntity empleado;

    private String contraTemp;

    private String contraTemp2;

    public EmpleadoManaged(){
        empleado = new EmpleadosEntity();
    }

    public void guardarEmple() {
        empleado.setCodigo(generateCodigo());
        empleado.setContraseña(BCrypt.hashpw(empleado.getCodigo(), BCrypt.gensalt()));
        empleado.setEstado("no");
        if (modelo.insertarEmpleado(empleado) != 1) {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El empleado no se ha podido registrar."));
        } else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "El empleado se ha registrado correctamente."));
        }
        empleado = new EmpleadosEntity();
    }

    public String generateCodigo(){
        String apellido = empleado.getApellidos();
        int indiceEspacio = apellido.indexOf(' '); // Encuentra la posición del primer espacio
        String codigo = "";
        Date fecha = new Date();
        SimpleDateFormat formatoAnio = new SimpleDateFormat("yy");

        Random random = new Random();
        String anio;
        int numero;

        anio = formatoAnio.format(fecha);
        numero = random.nextInt(9000) + 1000;

        if (indiceEspacio != -1 && indiceEspacio < apellido.length() - 1) {
            String letra2 = String.valueOf(apellido.charAt(indiceEspacio + 1));

            codigo = apellido.substring(0, 1).toUpperCase() + letra2.toUpperCase() + anio + numero;
        } else {
            String letra = apellido.substring(0, 2);
            codigo = letra.toUpperCase() + anio + numero;
        }
        return codigo;
    }

    public List<EmpleadosEntity> listEmpleados(){
        return modelo.listarEmpleado();
    }

    public void modificarEmpleado(){
        if (modelo.modificarEmpleado(empleado) == 1){
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "El empleado se ha modificado correctamente."));
        }else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El empleado no se ha podido modificar."));
        }
        empleado = new EmpleadosEntity();
    }

    public void data(EmpleadosEntity emple){
        empleado = emple;
    }

    public void eliminarEmpleado(String codigo){
        if (modelo.eliminarEmpleado(codigo) > 0){
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "El empleado se ha eliminado correctamente."));
        }else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El empleado no se ha podido eliminar."));
        }
        empleado = new EmpleadosEntity();
    }

    public String cambiarContra(String codigo, String contraseña, String nombres, String apellidos, String rol){
        String pagina = "";
        if (BCrypt.checkpw(contraTemp,contraseña)){
            empleado.setCodigo(codigo);
            empleado.setNombres(nombres);
            empleado.setApellidos(apellidos);
            empleado.setRol(rol);
            empleado.setContraseña(BCrypt.hashpw(contraTemp2, BCrypt.gensalt()));
            empleado.setEstado("si");
            if (modelo.modificarEmpleado(empleado) == 1){
                FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                        FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "La contraseña se ha cambiado correctamente."));
                switch (empleado.getRol()){
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
            }else {
                FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                        FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ha ocurrido un error la contraseña no ha sido modificada."));
            }
        }else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La contraseña actual no coicide con la que has digitado."));
            pagina = "/contraseña?faces-redirect=true";
        }
        return pagina;
    }

    public EmpleadoModel getModelo() {
        return modelo;
    }

    public void setModelo(EmpleadoModel modelo) {
        this.modelo = modelo;
    }

    public EmpleadosEntity getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadosEntity empleado) {
        this.empleado = empleado;
    }

    public String getContraTemp() {
        return contraTemp;
    }

    public void setContraTemp(String contraTemp) {
        this.contraTemp = contraTemp;
    }

    public String getContraTemp2() {
        return contraTemp2;
    }

    public void setContraTemp2(String contraTemp2) {
        this.contraTemp2 = contraTemp2;
    }
}
