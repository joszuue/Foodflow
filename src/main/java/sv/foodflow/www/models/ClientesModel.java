package sv.foodflow.www.models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import sv.foodflow.www.entities.*;
import sv.foodflow.www.utils.JpaUtil;

import java.util.List;

public class ClientesModel {

    public List<ClientesEntity> listarClientes() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("ClientesEntity.findAll");
            List<ClientesEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public ClientesEntity obtenerCliente(int idMesa) {
        MesasEntity mesaTemporal;

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            //Encontrando las instancias de cada clave foranea
            mesaTemporal = em.find(MesasEntity.class,idMesa);
            tran.begin();//Iniciando transacción

            TypedQuery<ClientesEntity> query = em.createNamedQuery("ClientesEntity.findByMesa", ClientesEntity.class);
            query.setParameter("idMesa", mesaTemporal);
            ClientesEntity cliente = query.getSingleResult();
            em.close();
            return cliente;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<ClientesEntity> listarMesaCliente(String codigo) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("clientesMesa");
            consulta.setParameter("codigo", codigo);
            List<ClientesEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public List<ClientesEntity> listarMesaSector(String codigo, int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query consulta = em.createNamedQuery("mesaSector");
            consulta.setParameter("codigo", codigo);
            consulta.setParameter("id", id);
            List<ClientesEntity> lista = consulta.getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public int insertarCliente(ClientesEntity cliente, int idMesa, String codigo) {
        MesasEntity mesaTemporal;
        EmpleadosEntity empleTemporal;

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            //Encontrando las instancias de cada clave foranea
            mesaTemporal = em.find(MesasEntity.class,idMesa);
            empleTemporal = em.find(EmpleadosEntity.class, codigo);
            tran.begin();//Iniciando transacción

            //Incluyendo en instancia de LibroEntity las instancias que corresponden a sus claves foraneas
            cliente.setMesasByIdMesa(mesaTemporal);
            cliente.setEmpleadoByCodigo(empleTemporal);
            //Persistiendo entidad libro con todos los datos posibles asignados
            em.persist(cliente); //Guardando el objeto en la BD
            tran.commit();//Confirmando la transacción
            em.close();
            return 1;
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }

    public int modificarCliente(ClientesEntity cliente) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        try {
            tran.begin();
            em.merge(cliente);
            tran.commit();
            em.close();
            return 1;
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }

    public int eliminarCliente(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        int filasBorradas = 0;
        try {
            ClientesEntity cliente = em.find(ClientesEntity.class, id);
            if (cliente != null) {
                EntityTransaction tran = em.getTransaction();
                tran.begin();
                em.remove(cliente);
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
