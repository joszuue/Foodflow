package sv.foodflow.www.models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import sv.foodflow.www.entities.CategoriasEntity;
import sv.foodflow.www.entities.EmpleadosEntity;
import sv.foodflow.www.entities.MesasEntity;
import sv.foodflow.www.utils.JpaUtil;

import java.util.List;

public class CategoriaModel {
    public List<CategoriasEntity> listarCategorias() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("CategoriasEntity.findAll");
            List<CategoriasEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<CategoriasEntity> categoriaClientes() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("showCategoria");
            List<CategoriasEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public int insertarCategoria(CategoriasEntity categoria) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            tran.begin();
            em.persist(categoria);
            tran.commit();
            em.close();
            return 1;
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }

    public int modificarCategoria(CategoriasEntity categoria) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            tran.begin();
            em.merge(categoria);
            tran.commit();
            em.close();
            return 1;
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }

    public int validarCate(String nombre) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<CategoriasEntity> query = em.createNamedQuery("CategoriasEntity.ValidateCate", CategoriasEntity.class);
            query.setParameter("nombre", nombre);
            CategoriasEntity categoria = query.getSingleResult();
            em.close();
            return categoria.getIdCategoria();
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }

    public int eliminarCategoria(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        int filasBorradas = 0;
        try {
            CategoriasEntity categoria = em.find(CategoriasEntity.class, id);
            if (categoria != null) {
                EntityTransaction tran = em.getTransaction();
                tran.begin();
                em.remove(categoria);
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
