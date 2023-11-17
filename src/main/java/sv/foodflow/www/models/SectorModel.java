package sv.foodflow.www.models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import sv.foodflow.www.entities.MesasEntity;
import sv.foodflow.www.entities.ProductosEntity;
import sv.foodflow.www.entities.SectorEntity;
import sv.foodflow.www.managedbeans.MesasManaged;
import sv.foodflow.www.utils.JpaUtil;

import java.util.List;

public class SectorModel {
    public List<SectorEntity> listarSectores() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("findAll");
            List<SectorEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<SectorEntity> listarSectoresMesas() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("showSector");
            List<SectorEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public int insertarSector(SectorEntity sector) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            tran.begin();
            em.persist(sector);
            tran.commit();
            em.close();
            return 1;
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }

    public int modificarSector(SectorEntity sector) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            tran.begin();
            em.merge(sector);
            tran.commit();
            em.close();
            return 1;
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }


    public int validar(String nombre) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<SectorEntity> query = em.createNamedQuery("validacion", SectorEntity.class);
            query.setParameter("nombre", nombre);
            SectorEntity sector = query.getSingleResult();
            em.close();
            return sector.getIdSector();
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }

    public int eliminarSector(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        int filasBorradas = 0;
        try {
            SectorEntity sector = em.find(SectorEntity.class, id);
            if (sector != null) {
                EntityTransaction tran = em.getTransaction();
                tran.begin();
                em.remove(sector);
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

    public List<MesasEntity> obtenerMesa(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("obtenerMesa");
            consulta.setParameter("id", id);
            List<MesasEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<Object[]> sectoresFiltrados() {
        EntityManager em = JpaUtil.getEntityManager();
        String nativeQuery = "SELECT s.* FROM sector s LEFT JOIN ( SELECT id_Sector, COUNT(*) as cantidad_mesas FROM mesas WHERE estado <> 'Eliminada' GROUP BY id_Sector ) m " +
                "ON s.id_Sector = m.id_Sector WHERE s.estado <> 'Eliminada' AND (m.cantidad_mesas IS NULL OR m.cantidad_mesas < s.capacidad)";
        Query query = em.createNativeQuery(nativeQuery);

        List<Object[]> result = query.getResultList();
        return result;
    }
}
