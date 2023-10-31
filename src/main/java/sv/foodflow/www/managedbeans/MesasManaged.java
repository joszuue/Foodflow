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

    private int idSector;

    public void guardarMesa() {
        mesa.setEstado("Disponible");
        if (modelo.insertarMesa(mesa, idSector) != 1) {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La mesa no se ha podido registrar."));
        } else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "La mesa se ha registrado correctamente."));
        }
        mesa = new MesasEntity();
    }

    public List<MesasEntity> listMesas(){
        return modelo.listarMesa();
    }

    public void modificarMesa(){
        if (modelo.modificarMesa(mesa, idSector) == 1){
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "La mesa se ha modificado correctamente."));
        }else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La mesa no se ha podido modificar."));
        }
        mesa = new MesasEntity();
    }

    public void data(MesasEntity mesaa, int id){
        mesa = mesaa;
        idSector = id;
    }

    public void eliminarMesa(int id){
        if (modelo.eliminarMesa(id) > 0){
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "La mesa se ha eliminado correctamente."));
        }else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La mesa no se ha podido eliminar."));
        }
        mesa = new MesasEntity();
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

    public int getIdSector() {
        return idSector;
    }

    public void setIdSector(int idSector) {
        this.idSector = idSector;
    }
}
