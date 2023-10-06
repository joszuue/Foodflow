package sv.foodflow.www.managedbeans;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import org.mindrot.jbcrypt.BCrypt;
import sv.foodflow.www.entities.EmpleadosEntity;
import sv.foodflow.www.models.EmpleadoModel;
import sv.foodflow.www.utils.Mensajes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@ManagedBean
@RequestScoped
public class EmpleadoManaged {
    EmpleadoModel modelo = new EmpleadoModel();
    private EmpleadosEntity empleado;

    public EmpleadoManaged(){
        empleado = new EmpleadosEntity();
    }

    public String guardarEmple() {
        empleado.setCodigo(generateCodigo());
        empleado.setContraseña(BCrypt.hashpw(empleado.getCodigo(), BCrypt.gensalt()));
        empleado.setEstado("Activo");
        if (modelo.insertarEmpleado(empleado) != 1) {
            Mensajes.setErrorMessage("Error", "El empleado no se ha podido registrar.");
        } else {
            Mensajes.setInfoMessage("Éxito", "El empleado se ha registrado correctamente.");
        }
        return "/admin/empleados?faces-redirect=true";
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
            String letra1 = String.valueOf(apellido.charAt(indiceEspacio - 1));
            String letra2 = String.valueOf(apellido.charAt(indiceEspacio + 1));

            codigo = letra1.toUpperCase() + letra2.toUpperCase() + anio + numero;
        } else {
            String letra = apellido.substring(0, 2);
            codigo = letra.toUpperCase() + anio + numero;
        }
        return codigo;
    }

    public List<EmpleadosEntity> listEmpleados(){
        return modelo.listarEmpleado();
    }

    public String modificarEmpleado(){
        if (modelo.modificarEmpleado(empleado) == 1){
            Mensajes.setInfoMessage("Éxito", "El empleado se ha modificado correctamente.");
        }else {
            Mensajes.setErrorMessage("Error", "El empleado no se ha podido modificar.");
        }
        return "/admin/empleados?faces-redirect=true";
    }

    public void data(EmpleadosEntity emple){
        empleado = emple;
    }

    public String eliminarEmpleado(String codigo){
        if (modelo.eliminarEmpleado(codigo) > 0){
            Mensajes.setInfoMessage("Éxito", "El empleado se ha eliminado correctamente.");
        }else {
            Mensajes.setErrorMessage("Error", "El empleado no se ha podido eliminar.");
        }
        return "/admin/empleados?faces-redirect=true";
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
}
