package sv.foodflow.www.models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import sv.foodflow.www.entities.CategoriasEntity;
import sv.foodflow.www.entities.ClientesEntity;
import sv.foodflow.www.entities.OrdenEntity;
import sv.foodflow.www.entities.ProductosEntity;
import sv.foodflow.www.utils.JpaUtil;

import java.util.Date;
import java.util.List;

public class OrdenModel {

    public List<OrdenEntity> carrito(String codigoCliente) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("carrito");
            consulta.setParameter("id", codigoCliente);
            List<OrdenEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public int insertarOrden(OrdenEntity orden, String codigoClient, int idProducto) {
        ClientesEntity clientes;
        ProductosEntity producto;

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            //Encontrando las instancias de cada clave foranea
            producto = em.find(ProductosEntity.class, idProducto);
            clientes = em.find(ClientesEntity.class,codigoClient);
            tran.begin();//Iniciando transacción

            //Incluyendo en instancia de producto las instancias que corresponden a sus claves foraneas
            orden.setClientesByCodigoClient(clientes);
            orden.setProductosByIdProducto(producto);

            //Persistiendo entidad producto con todos los datos posibles asignados
            em.persist(orden); //Guardando el objeto en la BD
            tran.commit();//Confirmando la transacción
            em.close();
            return 1;
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }

    public int modificarOrden(OrdenEntity orden, String codigoClient, int idProducto) {
        ClientesEntity clientes;
        ProductosEntity producto;

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            //Encontrando las instancias de cada clave foranea
            producto = em.find(ProductosEntity.class, idProducto);
            clientes = em.find(ClientesEntity.class,codigoClient);
            tran.begin();//Iniciando transacción

            //Incluyendo en instancia de producto las instancias que corresponden a sus claves foraneas
            orden.setClientesByCodigoClient(clientes);
            orden.setProductosByIdProducto(producto);

            //Persistiendo entidad producto con todos los datos posibles asignados
            em.merge(orden); //Guardando el objeto en la BD
            tran.commit();//Confirmando la transacción
            em.close();
            return 1;
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }

    public int carritoOrden(String estado, String codigoCliente, Date fecha) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            tran.begin();//Iniciando transacción
            Query query = em.createNamedQuery("update");
            query.setParameter("estado", estado);
            query.setParameter("codigo", codigoCliente);
            query.setParameter("fecha", fecha);
            int filasActualizadas = query.executeUpdate();

            tran.commit(); // Confirmando la transacción
            em.close();
            return filasActualizadas; // Devuelve la cantidad de filas actualizadas
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }

    /*public int carritoOrden(OrdenEntity orden, String codigoCliente, int idProducto) {
        ClientesEntity clientes;
        ProductosEntity producto;

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            //Encontrando las instancias de cada clave foranea
            producto = em.find(ProductosEntity.class, idProducto);
            clientes = em.find(ClientesEntity.class,codigoCliente);
            tran.begin();//Iniciando transacción

            //Incluyendo en instancia de producto las instancias que corresponden a sus claves foraneas
            orden.setClientesByCodigoClient(clientes);
            orden.setProductosByIdProducto(producto);

            Query query = em.createNamedQuery("update");
            query.setParameter("orden", orden);
            query.setParameter("codigo", codigoCliente);

            int filasActualizadas = query.executeUpdate();

            tran.commit(); // Confirmando la transacción
            em.close();
            return filasActualizadas; // Devuelve la cantidad de filas actualizadas
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }*/
}
