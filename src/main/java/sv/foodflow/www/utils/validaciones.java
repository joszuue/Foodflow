package sv.foodflow.www.utils;


import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validaciones implements Validator {
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String componentId = component.getId();

        //Valida que todos los campos que ingresen no esten vacíos
        if (value == null || value.toString().trim().isEmpty()) {
            String nombre = componentId.replace("_", " ");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El campo " + nombre + " no puede quedar vacío");
            throw new ValidatorException(message);
        }

    }
}
