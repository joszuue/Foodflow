package sv.foodflow.www.managedbeans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.FacesContext;
import sv.foodflow.www.entities.OrdenEntity;
import sv.foodflow.www.models.OrdenModel;

import java.util.Date;

@ManagedBean
@SessionScoped
public class OrdenManaged {
    private OrdenEntity orden;
    private OrdenModel modelo = new OrdenModel();

    public OrdenManaged(){
        orden = new OrdenEntity();
    }

    public void carrito(String codigo, int idProdu, double precio) {
        orden.setFecha(fecha());
        orden.setEstado("Carrito");
        orden.setTotal(precio);
        if (modelo.insertarOrden(orden, codigo, idProdu) != 1) {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se ha podido registrar el producto"));
        } else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "El producto se ha registrado correctamente"));
        }
        orden = new OrdenEntity();
    }

    public void realizarPedido(String codigo) {
        if (modelo.carritoOrden("Enviado", codigo, fecha()) != 1) {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se ha podido registrar el producto"));
        } else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "El producto se ha registrado correctamente"));
        }
        orden = new OrdenEntity();
    }

    public Date fecha(){
        Date fechaHoraActual = new Date();
        return fechaHoraActual;
    }

    public OrdenEntity getOrden() {
        return orden;
    }

    public void setOrden(OrdenEntity orden) {
        this.orden = orden;
    }

    public OrdenModel getModelo() {
        return modelo;
    }

    public void setModelo(OrdenModel modelo) {
        this.modelo = modelo;
    }
}
