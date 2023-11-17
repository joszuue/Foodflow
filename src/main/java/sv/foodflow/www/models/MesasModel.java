package sv.foodflow.www.models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import sv.foodflow.www.entities.*;
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

    //MESAS PARA MESEROS Y RECEPCION
    public List<MesasEntity> mesasDisponibles() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("disponibles");
            List<MesasEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<MesasEntity> mesasDisponiblesSector(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("disponiblesFiltradas");
            consulta.setParameter("id", id);
            List<MesasEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    //FIN

    public int insertarMesa(MesasEntity mesa, int idSector) {
        SectorEntity sector;

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            //Encontrando las instancias de cada clave foranea
            sector = em.find(SectorEntity.class,idSector);
            tran.begin();//Iniciando transacción

            //Incluyendo en instancia de producto las instancias que corresponden a sus claves foraneas
            mesa.setSectorByIdSector(sector);

            //Persistiendo entidad producto con todos los datos posibles asignados
            em.persist(mesa); //Guardando el objeto en la BD
            tran.commit();//Confirmando la transacción
            em.close();
            return 1;
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }

    public int modificarMesa(MesasEntity mesa, int idSector) {
        SectorEntity sector;

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            //Encontrando las instancias de cada clave foranea
            sector = em.find(SectorEntity.class,idSector);
            tran.begin();//Iniciando transacción

            //Incluyendo en instancia de producto las instancias que corresponden a sus claves foraneas
            mesa.setSectorByIdSector(sector);

            //Persistiendo entidad producto con todos los datos posibles asignados
            em.merge(mesa); //Guardando el objeto en la BD
            tran.commit();//Confirmando la transacción
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
