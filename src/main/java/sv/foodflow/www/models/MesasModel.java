package sv.foodflow.www.models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import sv.foodflow.www.entities.EmpleadosEntity;
import sv.foodflow.www.entities.MesasEntity;
import sv.foodflow.www.utils.JpaUtil;

import java.util.List;

public class MesasModel {
    public List<MesasEntity> listarMesa() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("MesasEntity.findAll");
            List<MesasEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public int insertarMesa(MesasEntity mesa) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            tran.begin();
            em.persist(mesa);
            tran.commit();
            em.close();
            return 1;
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }

    public int modificarMesa(MesasEntity mesa) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            tran.begin();
            em.merge(mesa);
            tran.commit();
            em.close();
            return 1;
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }

    public int eliminarMesa(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        int filasBorradas = 0;
        try {
            MesasEntity mesa = em.find(MesasEntity.class, id);
            if (mesa != null) {
                EntityTransaction tran = em.getTransaction();
                tran.begin();
                em.remove(mesa);
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
