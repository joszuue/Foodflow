package sv.foodflow.www.models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import sv.foodflow.www.entities.ClientesEntity;
import sv.foodflow.www.entities.EmpleadosEntity;
import sv.foodflow.www.entities.MesasEntity;
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

    public ClientesEntity obtenerCliente(String codigo) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<ClientesEntity> query = em.createNamedQuery("ClientesEntity.findByCodigoyEstado", ClientesEntity.class);
            query.setParameter("codigo", codigo);
            ClientesEntity cliente = query.getSingleResult();
            em.close();
            return cliente;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }
}
