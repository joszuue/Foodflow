package sv.foodflow.www.models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import sv.foodflow.www.entities.CategoriasEntity;
import sv.foodflow.www.entities.ComentarioEntity;
import sv.foodflow.www.entities.EmpleadosEntity;
import sv.foodflow.www.entities.ProductosEntity;
import sv.foodflow.www.utils.JpaUtil;

import java.util.List;

public class ProductoModel {

    public List<ProductosEntity> listarProductos(String opcion) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("ProductosEntity.findAll");
            consulta.setParameter("estado", opcion);
            List<ProductosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<ProductosEntity> eliminados() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("eliminado");
            List<ProductosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<ProductosEntity> menuCliente(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("produCliente");
            consulta.setParameter("id", id);
            List<ProductosEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<ComentarioEntity> listarComentarios(int idProdu) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("comentarios");
            consulta.setParameter("id", idProdu);
            List<ComentarioEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public int insertarProducto(ProductosEntity producto, int idCategoria) {
        CategoriasEntity cateTemporal;

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            //Encontrando las instancias de cada clave foranea
            cateTemporal = em.find(CategoriasEntity.class,idCategoria);
            tran.begin();//Iniciando transacción

            //Incluyendo en instancia de producto las instancias que corresponden a sus claves foraneas
            producto.setCategoriasByIdCategoria(cateTemporal);

            //Persistiendo entidad producto con todos los datos posibles asignados
            em.persist(producto); //Guardando el objeto en la BD
            tran.commit();//Confirmando la transacción
            em.close();
            return 1;
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }

    public int insertarComentario(ComentarioEntity comentario) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            tran.begin();
            em.persist(comentario);
            tran.commit();
            em.close();
            return 1;
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }

    public int modificarProducto(ProductosEntity producto, int idCategoria) {
        CategoriasEntity cateTemporal;

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            //Encontrando las instancias de cada clave foranea
            cateTemporal = em.find(CategoriasEntity.class,idCategoria);
            tran.begin();//Iniciando transacción

            //Incluyendo en instancia de producto las instancias que corresponden a sus claves foraneas
            producto.setCategoriasByIdCategoria(cateTemporal);

            //Persistiendo entidad producto con todos los datos posibles asignados
            em.merge(producto); //Guardando el objeto en la BD
            tran.commit();//Confirmando la transacción
            em.close();
            return 1;
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }

    public int validarProdu(String nombre) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<ProductosEntity> query = em.createNamedQuery("ProductosEntity.validateInsertProdu", ProductosEntity.class);
            query.setParameter("nombre", nombre);
            ProductosEntity producto = query.getSingleResult();
            em.close();
            return producto.getIdProducto();
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }


    public int eliminarProducto(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        int filasBorradas = 0;
        try {
            ProductosEntity producto = em.find(ProductosEntity.class, id);
            if (producto != null) {
                EntityTransaction tran = em.getTransaction();
                tran.begin();
                em.remove(producto);
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
