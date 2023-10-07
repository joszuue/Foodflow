package sv.foodflow.www.managedbeans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.context.FacesContext;
import org.mindrot.jbcrypt.BCrypt;
import sv.foodflow.www.entities.EmpleadosEntity;
import sv.foodflow.www.entities.MesasEntity;
import sv.foodflow.www.models.MesasModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@ManagedBean
@RequestScoped
public class MesasManaged {
    private MesasEntity mesa;
    MesasModel modelo = new MesasModel();

    public MesasManaged(){
        mesa = new MesasEntity();
    }

    public String guardarMesa() {
        mesa.setEstado("Disponible");
        if (modelo.insertarMesa(mesa) != 1) {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La mesa no se ha podido registrar."));
        } else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "La mesa se ha registrado correctamente."));
        }
        return "/admin/mesas?faces-redirect=true";
    }

    public List<MesasEntity> listMesas(){
        return modelo.listarMesa();
    }

    public String modificarMesa(){
        if (modelo.modificarMesa(mesa) == 1){
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "La mesa se ha modificado correctamente."));
        }else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La mesa no se ha podido modificar."));
        }
        return "/admin/mesas?faces-redirect=true";
    }

    public void data(MesasEntity mesaa){
        mesa = mesaa;
    }

    public String eliminarMesa(int id){
        if (modelo.eliminarMesa(id) > 0){
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "La mesa se ha eliminado correctamente."));
        }else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La mesa no se ha podido eliminar."));
        }
        return "/admin/mesas?faces-redirect=true";
    }

    public MesasEntity getMesa() {
        return mesa;
    }

    public void setMesa(MesasEntity mesa) {
        this.mesa = mesa;
    }

    public MesasModel getModelo() {
        return modelo;
    }

    public void setModelo(MesasModel modelo) {
        this.modelo = modelo;
    }
}
