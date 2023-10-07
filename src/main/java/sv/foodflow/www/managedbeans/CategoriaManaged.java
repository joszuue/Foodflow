package sv.foodflow.www.managedbeans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.context.FacesContext;
import sv.foodflow.www.entities.CategoriasEntity;
import sv.foodflow.www.entities.MesasEntity;
import sv.foodflow.www.models.CategoriaModel;

import java.util.List;

@ManagedBean
@RequestScoped
public class CategoriaManaged {
    CategoriaModel modelo = new CategoriaModel();
    private CategoriasEntity categoria;

    public CategoriaManaged(){
        categoria = new CategoriasEntity();
    }

    public String guardarCategoria() {
        if (modelo.insertarCategoria(categoria) != 1) {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La categoria no se ha podido registrar."));
        } else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "La categoria se ha registrado correctamente."));
        }
        return "/jefeCocina/categorias?faces-redirect=true";
    }

    public List<CategoriasEntity> listCategorias(){
        return modelo.listarCategorias();
    }

    public String modificarCategoria(){
        if (modelo.modificarCategoria(categoria) == 1){
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "La categoria se ha modificado correctamente."));
        }else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La categoria no se ha podido modificar."));
        }
        return "/jefeCocina/categorias?faces-redirect=true";
    }

    public void data(CategoriasEntity cate){
        categoria = cate;
    }

    public String eliminarCategoria(int id){
        if (modelo.eliminarCategoria(id) > 0){
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "La categoria se ha eliminado correctamente."));
        }else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La categoria no se ha podido eliminar."));
        }
        return "/jefeCocina/categorias?faces-redirect=true";
    }

    public CategoriaModel getModelo() {
        return modelo;
    }

    public void setModelo(CategoriaModel modelo) {
        this.modelo = modelo;
    }

    public CategoriasEntity getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriasEntity categoria) {
        this.categoria = categoria;
    }
}
