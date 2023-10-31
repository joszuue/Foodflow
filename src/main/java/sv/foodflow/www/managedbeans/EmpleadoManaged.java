package sv.foodflow.www.managedbeans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import org.mindrot.jbcrypt.BCrypt;
import sv.foodflow.www.entities.EmpleadosEntity;
import sv.foodflow.www.models.EmpleadoModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private String contraTemp3;

    private ArrayList<String> departamentoList = new ArrayList<>();
    private ArrayList<String> municipioList = new ArrayList<>();
    private String muni;

    public EmpleadoManaged(){
        empleado = new EmpleadosEntity();
        departamentos();
    }

    public void guardarEmple() {

        if (modelo.validarEmple(empleado.getDui()) == null){
            empleado.setCodigo(generateCodigo());
            empleado.setContraseña(BCrypt.hashpw(empleado.getCodigo(), BCrypt.gensalt()));
            empleado.setEstado("no");
            empleado.setMunicipio(muni);
            if (modelo.insertarEmpleado(empleado) != 1) {
                FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                        FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El empleado no se ha podido registrar."));
            } else {
                FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                        FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "El empleado se ha registrado correctamente."));
            }
            empleado = new EmpleadosEntity();
        }else{
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Los nombres y los apellidos de este empleado coiciden con uno de los empleados."));
        }

    }

    public String generateCodigo(){
        Date fecha = new Date();
        SimpleDateFormat formatoAnio = new SimpleDateFormat("yy");

        Random random = new Random();
        String anio;
        int numero;

        anio = formatoAnio.format(fecha);
        numero = random.nextInt(9000) + 1000;

        String codigo = empleado.getApellido1().substring(0, 1).toUpperCase() + empleado.getApellido2().substring(0, 1).toUpperCase() + anio + numero;
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
        muni = emple.getMunicipio();
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

   /* public String cambiarContra(String codigo, String contraseña, String nombres, String apellidos, String rol){
        String pagina = "";
        if (BCrypt.checkpw(contraTemp,contraseña)){
            if (contraTemp2.equals(contraTemp3)){
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
            }else{
                FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                        FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las nuevas contraseñas no coiciden."));
            }
        }else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La contraseña actual no coicide con la que has digitado."));
            pagina = "/contraseña?faces-redirect=true";
        }
        return pagina;
    }*/

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

    public String getContraTemp3() {
        return contraTemp3;
    }

    public void setContraTemp3(String contraTemp3) {
        this.contraTemp3 = contraTemp3;
    }

    public ArrayList<String> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(ArrayList<String> departamentoList) {
        this.departamentoList = departamentoList;
    }

    public ArrayList<String> getMunicipioList() {
        return municipioList;
    }

    public void setMunicipioList(ArrayList<String> municipioList) {
        this.municipioList = municipioList;
    }

    public String getMuni() {
        return muni;
    }

    public void setMuni(String muni) {
        this.muni = muni;
    }

    public void departamentos(){
        //Llenando la lista de departamento
        departamentoList.add("Ahuachapán");
        departamentoList.add("Cabañas");
        departamentoList.add("Chalatenango");
        departamentoList.add("Cuscatlán");
        departamentoList.add("La Libertad");
        departamentoList.add("Morazán");
        departamentoList.add("La Paz");
        departamentoList.add("Santa Ana");
        departamentoList.add("San Miguel");
        departamentoList.add("San Salvador");
        departamentoList.add("San Vicente");
        departamentoList.add("Sonsonate");
        departamentoList.add("La Unión");
        departamentoList.add("Usulután");
        municipioList.add("Primero seleccione un departamento");
    }

    public void municipios(AjaxBehaviorEvent e){
        switch (empleado.getDepartamento()){
            case "Ahuachapán":
                municipioList.add("Ahuachapán Norte");
                municipioList.add("Ahuachapán Centro");
                municipioList.add("Ahuachapán Sur");
                break;
            case "Cabañas":
                municipioList.add("Cabañas Este");
                municipioList.add("Cabañas Oeste");
                break;
            case "Chalatenango":
                municipioList.add("Chalatenango Norte");
                municipioList.add("Chalatenango Centro");
                municipioList.add("Chalatenango Sur");
                break;
            case "Cuscatlán":
                municipioList.add("Cuscatlán Norte");
                municipioList.add("Cuscatlán Sur");
                break;
            case "La Libertad":
                municipioList.add("La Libertad Norte");
                municipioList.add("La Libertad Centro");
                municipioList.add("La Libertad Oeste");
                municipioList.add("La Libertad Este");
                municipioList.add("La Libertad Costa");
                municipioList.add("La Libertad Sur");
                break;
            case "Morazán":
                municipioList.add("Morazán Norte");
                municipioList.add("Morazán Sur");
                break;
            case "La Paz":
                municipioList.add("La Paz Oeste");
                municipioList.add("La Paz Centro");
                municipioList.add("La Paz Este");
                break;
            case "Santa Ana":
                municipioList.add("Santa Ana Norte");
                municipioList.add("Santa Ana Centro");
                municipioList.add("Santa Ana Este");
                municipioList.add("Santa Ana Oeste");
                break;
            case "San Miguel":
                municipioList.add("San Miguel Norte");
                municipioList.add("San Miguel Centro");
                municipioList.add("San Miguel Oeste");
                break;
            case "San Salvador":
                municipioList.add("San Salvador Norte");
                municipioList.add("San Salvador Oeste");
                municipioList.add("San Salvador Este");
                municipioList.add("San Salvador Centro");
                municipioList.add("San Salvador Sur");
                break;
            case "San Vicente":
                municipioList.add("San Vicente Norte");
                municipioList.add("San Vicente Sur");
                break;
            case "Sonsonate":
                municipioList.add("Sonsonate Norte");
                municipioList.add("Sonsonate Centro");
                municipioList.add("Sonsonate Este");
                break;
            case "La Unión":
                municipioList.add("La Unión Norte");
                municipioList.add("La Unión Sur");
                break;
            case "Usulután":
                municipioList.add("Usulután Norte");
                municipioList.add("Usulután Este");
                municipioList.add("Usulután Oeste");
                break;
            default:
                municipioList.add("Primero seleccione un departamento");
                break;
        }
    }

}
