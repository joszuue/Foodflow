package sv.foodflow.www.models;

import jakarta.persistence.EntityManager;
import sv.foodflow.www.entities.EmpleadosEntity;
import sv.foodflow.www.utils.JpaUtil;

public class LoginModel {

    public String obtenerPass(String codigo) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            //Recupero el objeto desde la BD a través del método find
            EmpleadosEntity empleado = em.find(EmpleadosEntity.class, codigo);
            em.close();
            return empleado.getContraseña();
        } catch (Exception e) {
            return null;
        }
    }

    public EmpleadosEntity datosSession(String codigo) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            //Recupero el objeto desde la BD a través del método find
            EmpleadosEntity empleado = em.find(EmpleadosEntity.class, codigo);
            em.close();
            return empleado;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }
}
