package sv.foodflow.www.managedbeans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import org.mindrot.jbcrypt.BCrypt;
import sv.foodflow.www.entities.EmpleadosEntity;
import sv.foodflow.www.models.EmpleadoModel;
import sv.foodflow.www.utils.EnviarCorreo;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

@ManagedBean
@SessionScoped
public class EmpleadoManaged {
    EmpleadoModel modelo = new EmpleadoModel();
    private EmpleadosEntity empleado;

    private String contraTemp;

    private String contraTemp2;

    private String contraTemp3;

    private String codigoCorreo;

    private String codigoCorreo2;

    private String correoContra;

    private ArrayList<String> departamentoList = new ArrayList<>();
    private ArrayList<String> municipioList = new ArrayList<>();

    public EmpleadoManaged(){
        empleado = new EmpleadosEntity();
        departamentos();
    }

    public void guardarEmple() {
        if (modelo.validarEmple(empleado.getDui()) == null){
            empleado.setCodigo(generateCodigo());
            empleado.setContraseña(BCrypt.hashpw(empleado.getCodigo(), BCrypt.gensalt()));
            empleado.setEstado("no");
            if (modelo.insertarEmpleado(empleado) != 1) {
                FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                        FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El empleado no se ha podido registrar."));
            } else {
                enviarCorreo();
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

    public void eliminarEmpleado(EmpleadosEntity emple){
        emple.setEstado("Eliminado");
        if (modelo.modificarEmpleado(emple) == 1){
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "El empleado se ha eliminado correctamente."));
        }else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El empleado no se ha podido eliminar."));
        }
        empleado = new EmpleadosEntity();
    }

    //OTRAS FUNCIONES
    public void data(EmpleadosEntity emple){
        empleado = emple;
    }

    public void dataUsu(){
        // Obtiene el objeto completo almacenado en sessionScope.emple
        empleado = (EmpleadosEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("emple");
    }

    public void cancelar(){
        empleado = new EmpleadosEntity();
    }

    public String cambiarContra(){
        String pagina = "";
        empleado = (EmpleadosEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("emple");
        if (BCrypt.checkpw(contraTemp,empleado.getContraseña())){
            if (contraTemp2.equals(contraTemp3)){
                empleado.setContraseña(BCrypt.hashpw(contraTemp2, BCrypt.gensalt()));
                empleado.setEstado("si");
                if (modelo.modificarEmpleado(empleado) == 1){
                    FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                            FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "La contraseña se ha cambiado correctamente."));
                    switch (empleado.getRol()){
                        case "Administrador":
                            pagina = "/admin/inicio?faces-redirect=true";
                            break;
                        case "Jefe de cocina":
                            pagina = "/jefeCocina/inicio?faces-redirect=true";
                            break;
                        case "Mesero":
                            pagina = "/mesero/inicio?faces-redirect=true";
                            break;
                        case "Recepcionista":
                            pagina = "/recepcion/inicio?faces-redirect=true";
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
            empleado = new EmpleadosEntity();
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La contraseña actual no coicide con la que has digitado."));
            pagina = "/contraseña?faces-redirect=true";
        }
        return pagina;
    }

    public String enviarCodigo(){
        if (modelo.buscarCorreo(correoContra) == null){
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El correo no se encuentra registrado."));
            return null;
        }else {
            codigoCorreo = generarCodigo();
            String asunto = "Solicitud para restablecer contraseña de FoodFlow";
            String contenido = "<center><img src='https://drive.google.com/uc?id=1DPULY6d7ITdJEzqXodn_b1eIFkV241vl' width='1000px' height='220px'/></center>"
                    + "<h2>Hola, <b>" + correoContra + ".</b></h2>"
                    + "<h2>Recibimos una solicitud para restablecer tu contraseña de FoodFlow.\n" +
                    "Ingresa el siguiente código para restablecer la contraseña:</h2>"
                    +"<center><h1>" + codigoCorreo +"</h1></center>"
                    + "<img src='https://drive.google.com/uc?id=14amyQ84zTZ47460dx7vh3DC0wNSYkz9D'/>";
            EnviarCorreo correo = new EnviarCorreo(correoContra, asunto, contenido);
            correo.createEmail();
            correo.sendEmail();
            return "/confirmarCodigo?faces-redirect=true";
        }
    }

    public String confirmarCodigo(){
        if (codigoCorreo.equals(codigoCorreo2)){
            return "/restablecerContra?faces-redirect=true";
        }else{
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El código no coicide."));
            return null;
        }
    }

    public String restablecerContra(){
        String pagina = "";
        empleado = modelo.buscarCorreo(correoContra);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("emple", empleado);
        if (contraTemp2.equals(contraTemp3)){
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
        return pagina;
    }

    public String generarCodigo() {
        String caracteresPermitidos = "0123456789";
        int longitud = 6;
        SecureRandom random = new SecureRandom();
        StringBuilder codigo = new StringBuilder();

        for (int i = 0; i < longitud; i++) {
            int indice = random.nextInt(caracteresPermitidos.length());
            char caracterAleatorio = caracteresPermitidos.charAt(indice);
            codigo.append(caracterAleatorio);
        }
        return codigo.toString();
    }


    public void enviarCorreo() {
        String asunto = "Credenciales para el acceso a FoodFlow";
        String contenido = "<center><h1>Bienvenido/a a FoodFlow!!</h1></center>"
                + "<center><img src='https://drive.google.com/uc?id=1DPULY6d7ITdJEzqXodn_b1eIFkV241vl' width='1000px' height='220px'/></center>"
                + "<h2>Estimado/a <b>" + empleado.getNombre1() + " " + empleado.getNombre2() + " " + empleado.getApellido1() + " " + empleado.getApellido2() + ".</b></h2>"
                + "<h2>Con el cargo de: <b>"+ empleado.getRol() +"</b></h2>"
                + "<h2>Nos complace confirmar que tu registro en FoodFlow ha sido exitoso."
                + "Bienvenido/a a nuestra comunidad de FoodFlow! A continuaci&oacute;n, encontrar&aacute;s "
                + "tus credenciales temporales de inicio de sesi&oacute;n, posteriormente podras cambiar la contrase&ntilde;a:</h2>"
                + "<h2><b>C&oacute;digo: </b>"+ empleado.getCodigo() +"</h2>"
                + "<h2><b>Contrase&ntilde;a: </b>" + empleado.getCodigo() +"</h2>"
                + "<img src='https://drive.google.com/uc?id=14amyQ84zTZ47460dx7vh3DC0wNSYkz9D'/>";
        EnviarCorreo correo = new EnviarCorreo(empleado.getCorreo(), asunto, contenido);
        correo.createEmail();
        correo.sendEmail();
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

    public String getCorreoContra() {
        return correoContra;
    }

    public void setCorreoContra(String correoContra) {
        this.correoContra = correoContra;
    }

    public String getCodigoCorreo() {
        return codigoCorreo;
    }

    public void setCodigoCorreo(String codigoCorreo) {
        this.codigoCorreo = codigoCorreo;
    }

    public String getCodigoCorreo2() {
        return codigoCorreo2;
    }

    public void setCodigoCorreo2(String codigoCorreo2) {
        this.codigoCorreo2 = codigoCorreo2;
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
        municipioList.clear();
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
