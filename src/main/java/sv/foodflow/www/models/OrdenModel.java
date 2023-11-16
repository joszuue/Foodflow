package sv.foodflow.www.models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import sv.foodflow.www.entities.*;
import sv.foodflow.www.utils.JpaUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class OrdenModel {

    public List<OrdenEntity> carrito(String codigoCliente, String estado) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("ordenes");
            consulta.setParameter("estado", estado);
            consulta.setParameter("id", codigoCliente);
            List<OrdenEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<OrdenEntity> pedidosTotal(String codigoCliente) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("todos");
            consulta.setParameter("id", codigoCliente);
            List<OrdenEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<OrdenEntity> productosReporte() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("productosReporte2");
            List<OrdenEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    //MOSTRAR EL DETALLE DEL PRODUCTO
    public List<OrdenEntity> productosDetalle(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("detalle2");
            consulta.setParameter("id", id);
            List<OrdenEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }


    //MOSTRAR LA EL DETALLE DEL PRODUCTO FILTRADO POR LA FECHA
    public List<OrdenEntity> informeDetalle(int id, String fecha1, String fecha2) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("detalle");
            consulta.setParameter("fecha1", fecha1);
            consulta.setParameter("fecha2", fecha2);
            consulta.setParameter("id", id);
            List<OrdenEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }


    public List<OrdenEntity> productosReporteFiltrado(String fecha1, String fecha2) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("productosReporte");
            consulta.setParameter("fecha1", fecha1);
            consulta.setParameter("fecha2", fecha2);
            List<OrdenEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<OrdenEntity> reporte(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("reporte");
            consulta.setParameter("id", id);
            List<OrdenEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<OrdenEntity> reporte2(int id, String fecha1, String fecha2) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("reporte2");
            consulta.setParameter("id", id);
            consulta.setParameter("fecha1", fecha1);
            consulta.setParameter("fecha2", fecha2);
            List<OrdenEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<OrdenEntity> populares() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("populares");
            consulta.setFirstResult(0); // La posición inicial (0 para el primer resultado)
            consulta.setMaxResults(6);
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

    public int carritoOrden(String estado, String codigoCliente, String fecha) {
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

    public int cerrarOrden(String estado, String codigoCliente) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            tran.begin();//Iniciando transacción
            Query query = em.createNamedQuery("cerrarOrden");
            query.setParameter("estado", estado);
            query.setParameter("codigo", codigoCliente);
            int filasActualizadas = query.executeUpdate();

            tran.commit(); // Confirmando la transacción
            em.close();
            return filasActualizadas; // Devuelve la cantidad de filas actualizadas
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }

    public int eliminarOrden(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        int filasBorradas = 0;
        try {
            OrdenEntity orden = em.find(OrdenEntity.class, id);
            if (orden != null) {
                EntityTransaction tran = em.getTransaction();
                tran.begin();
                em.remove(orden);
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


    //REPORTE MENSUAL
    public List<Object[]> generarReporteMensual(int anio) {
        EntityManager em = JpaUtil.getEntityManager();
        String nativeQuery = "SELECT Meses.NombreMes AS Mes,\n" +
                "       COALESCE(SUM(cantidad * total), 0) AS TotalVendido\n" +
                "FROM (\n" +
                "    SELECT 1 AS NumeroMes, 'Enero' AS NombreMes\n" +
                "    UNION SELECT 2, 'Febrero'\n" +
                "    UNION SELECT 3, 'Marzo'\n" +
                "    UNION SELECT 4, 'Abril'\n" +
                "    UNION SELECT 5, 'Mayo'\n" +
                "    UNION SELECT 6, 'Junio'\n" +
                "    UNION SELECT 7, 'Julio'\n" +
                "    UNION SELECT 8, 'Agosto'\n" +
                "    UNION SELECT 9, 'Septiembre'\n" +
                "    UNION SELECT 10, 'Octubre'\n" +
                "    UNION SELECT 11, 'Noviembre'\n" +
                "    UNION SELECT 12, 'Diciembre'\n" +
                ") Meses\n" +
                "LEFT JOIN orden ON Meses.NumeroMes = MONTH(fecha) AND estado = 'Finalizado' AND YEAR(fecha) = " +anio+" GROUP BY Mes";
        Query query = em.createNativeQuery(nativeQuery);
        @SuppressWarnings("unchecked")
        List<Object[]> result = query.getResultList();
        return result;
    }

    public List<Object[]> informeAnual() {
        EntityManager em = JpaUtil.getEntityManager();
        String nativeQuery = "SELECT YEAR(orden.fecha) AS Anio, COALESCE(SUM(cantidad * total), 0) AS TotalVendido " +
                "FROM ( SELECT DISTINCT YEAR(fecha) AS Anio FROM orden WHERE estado = 'Finalizado' ) " +
                "Anios LEFT JOIN orden ON YEAR(orden.fecha) = Anios.Anio AND estado = 'Finalizado' GROUP BY Anio";
        Query query = em.createNativeQuery(nativeQuery);
        @SuppressWarnings("unchecked")
        List<Object[]> result = query.getResultList();
        return result;
    }

    public List<Object[]> años() {
        EntityManager em = JpaUtil.getEntityManager();
        String nativeQuery = "SELECT DISTINCT YEAR(orden.fecha) AS Anio FROM orden WHERE orden.estado = 'Finalizado'";
        Query query = em.createNativeQuery(nativeQuery);

        @SuppressWarnings("unchecked")
        List<Object[]> result = query.getResultList();
        return result;
    }

}
