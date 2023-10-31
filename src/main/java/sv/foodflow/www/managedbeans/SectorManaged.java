package sv.foodflow.www.managedbeans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.context.FacesContext;
import sv.foodflow.www.entities.SectorEntity;
import sv.foodflow.www.models.SectorModel;

import java.util.List;

@ManagedBean
@RequestScoped
public class SectorManaged {
    private SectorEntity sector;
    SectorModel modelo = new SectorModel();

    public SectorManaged(){
        sector = new SectorEntity();
    }

    public void guardarSector() {
        if (modelo.validar(sector.getNombre()) == 0){
            sector.setEstado("activa");
            if (modelo.insertarSector(sector) != 1) {
                FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                        FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El Sector no se ha podido registrar."));
            } else {
                FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                        FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "El Sector se ha registrado correctamente."));
            }
            sector = new SectorEntity();
        }else{
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El nombre coicide con uno ya registrado."));
        }

    }

    public List<SectorEntity> listSector(){
        return modelo.listarSectores();
    }

    public void modificarSector(){
        if (modelo.validar(sector.getNombre()) == sector.getIdSector() || modelo.validar(sector.getNombre()) == 0){
            if (modelo.modificarSector(sector) == 1){
                FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                        FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "El Sector se ha modificado correctamente."));
            }else {
                FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                        FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El Sector no se ha podido modificar."));
            }
            sector = new SectorEntity();
        }else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El nombre coicide con uno ya registrado."));
        }
    }

    public void data(SectorEntity sectoor){
        sector = sectoor;
    }

    public void eliminarSector(int id){
        if (modelo.eliminarSector(id) > 0){
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "El sector se ha eliminado correctamente."));
        }else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El sector no se ha podido eliminar porque posee mesas asociadas"));
        }
        sector = new SectorEntity();
    }

    public SectorEntity getSector() {
        return sector;
    }

    public void setSector(SectorEntity sector) {
        this.sector = sector;
    }

    public SectorModel getModelo() {
        return modelo;
    }

    public void setModelo(SectorModel modelo) {
        this.modelo = modelo;
    }
}
