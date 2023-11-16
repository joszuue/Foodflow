package sv.foodflow.www.models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import sv.foodflow.www.entities.ClientesEntity;
import sv.foodflow.www.entities.EmpleadosEntity;
import sv.foodflow.www.utils.JpaUtil;

import java.util.List;

public class EmpleadoModel {

    public List<EmpleadosEntity> listarEmpleado() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("EmpleadosEntity.findAll");
            List<EmpleadosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<EmpleadosEntity> listarMeseros() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("meseros");
            List<EmpleadosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public EmpleadosEntity buscarCorreo(String correo) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("buscarCorreo");
            consulta.setParameter("correo", correo); // Agregar parámetro
            EmpleadosEntity empleado = (EmpleadosEntity) consulta.getSingleResult();
            em.close();
            return empleado;
        } catch (Exception e) {
            // Manejar otras excepciones
            em.close();
            return null;
        }
    }


    public int insertarEmpleado(EmpleadosEntity empleado) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            tran.begin();
            em.persist(empleado);
            tran.commit();
            em.close();
            return 1;
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }


    public EmpleadosEntity validarEmple(String dui) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<EmpleadosEntity> query = em.createNamedQuery("EmpleadosEntity.validateEmple", EmpleadosEntity.class);
            query.setParameter("dui", dui);
            EmpleadosEntity empleado = query.getSingleResult();
            em.close();
            return empleado;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public int modificarEmpleado(EmpleadosEntity empleado) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            tran.begin();
            em.merge(empleado);
            tran.commit();
            em.close();
            return 1;
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }

    public int eliminarEmpleado(String codigo) {
        EntityManager em = JpaUtil.getEntityManager();
        int filasBorradas = 0;
        try {
            EmpleadosEntity emple = em.find(EmpleadosEntity.class, codigo);
            if (emple != null) {
                EntityTransaction tran = em.getTransaction();
                tran.begin();
                em.remove(emple);
                tran.commit();
                filasBorradas = 1;
            }
            em.close();
            return filasBorradas;
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }


}
