package sv.foodflow.www.utils;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

public class Mensajes {
    public static void setErrorMessage(String titulo, String msg) {
        FacesMessage mensaje = new
                FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        FacesContext.getCurrentInstance().addMessage(titulo, mensaje);
    }

    public static void setInfoMessage(String titulo, String msg) {
        FacesMessage mensaje = new
                FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        FacesContext.getCurrentInstance().addMessage(titulo, mensaje);
    }
}
