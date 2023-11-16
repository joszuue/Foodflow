package sv.foodflow.www.entities;

import jakarta.persistence.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "orden", schema = "food_flow")
@NamedQueries({
        @NamedQuery(name="ordenes", query = "SELECT o FROM OrdenEntity o WHERE o.estado = :estado AND o.clientesByCodigoClient.codigoClient = :id"),
        @NamedQuery(name="pedidos", query = "SELECT o FROM OrdenEntity o WHERE o.estado = :estado"),
        @NamedQuery(name="todos", query = "SELECT o FROM OrdenEntity o WHERE o.estado <> 'Finalizado' AND o.clientesByCodigoClient.codigoClient = :id"),
        @NamedQuery(name="update", query = "UPDATE OrdenEntity o SET o.estado = :estado, o.fecha = :fecha WHERE o.clientesByCodigoClient.codigoClient = :codigo AND o.estado = 'Carrito'"),
        @NamedQuery(name = "populares", query = "SELECT o FROM OrdenEntity o WHERE o.estado = 'Enviado' OR o.estado = 'Entregado' OR o.estado = 'Finalizado' GROUP BY o.productosByIdProducto.idProducto ORDER BY SUM(o.cantidad) DESC"),
        @NamedQuery(name="cerrarOrden", query = "UPDATE OrdenEntity o SET o.estado = :estado WHERE o.clientesByCodigoClient.codigoClient = :codigo AND o.estado = 'Entregado'"),
        @NamedQuery(name="productosReporte", query = "SELECT o FROM OrdenEntity o WHERE o.estado = 'Finalizado' AND o.fecha >= :fecha1 AND o.fecha <= :fecha2 GROUP BY o.productosByIdProducto.idProducto"),
        @NamedQuery(name="productosReporte2", query = "SELECT o FROM OrdenEntity o WHERE o.estado = 'Finalizado' GROUP BY o.productosByIdProducto.idProducto"),
        @NamedQuery(name="detalle", query = "SELECT o FROM OrdenEntity o WHERE o.estado = 'Finalizado' AND o.fecha >= :fecha1 AND o.fecha <= :fecha2 AND o.productosByIdProducto.idProducto = :id"),
        @NamedQuery(name="detalle2", query = "SELECT o FROM OrdenEntity o WHERE o.estado = 'Finalizado' AND o.productosByIdProducto.idProducto = :id"),
        @NamedQuery(name="reporte", query = "SELECT o FROM OrdenEntity o WHERE o.productosByIdProducto.idProducto = :id AND o.estado = 'Finalizado'"),
        @NamedQuery(name="reporte2", query = "SELECT o FROM OrdenEntity o WHERE o.productosByIdProducto.idProducto = :id AND o.fecha >= :fecha1 AND o.fecha <= :fecha2 AND o.estado = 'Finalizado'")
})
public class OrdenEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idOrden", nullable = false)
    private int idOrden;
    @Basic
    @Column(name = "cantidad", nullable = false)
    private int cantidad;
    @Basic
    @Column(name = "fecha", nullable = false)
    private String fecha;
    @Basic
    @Column(name = "tiempoEspera", nullable = true)
    private LocalTime tiempoEspera;
    @Basic
    @Column(name = "total", nullable = false, precision = 0)
    private double total;
    @Basic
    @Column(name = "estado", nullable = false, length = 25)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "codigoClient", referencedColumnName = "codigoClient", nullable = false)
    private ClientesEntity clientesByCodigoClient;
    @ManyToOne
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto", nullable = false)
    private ProductosEntity productosByIdProducto;
    @Basic
    @Column(name = "comentario", nullable = false, length = 200)
    private String comentario;

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public LocalTime getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoEspera(LocalTime tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrdenEntity that = (OrdenEntity) o;

        if (idOrden != that.idOrden) return false;
        if (cantidad != that.cantidad) return false;
        if (total != that.total) return false;
        if (fecha != null ? !fecha.equals(that.fecha) : that.fecha != null) return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;
        if (comentario != null ? !comentario.equals(that.comentario) : that.comentario != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = idOrden;
        result = 31 * result + cantidad;
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        result = 31 * result + (tiempoEspera != null ? tiempoEspera.hashCode() : 0);
        result = (int) (31 * result + total);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        result = 31 * result + (comentario != null ? comentario.hashCode() : 0);
        return result;
    }

    public ClientesEntity getClientesByCodigoClient() {
        return clientesByCodigoClient;
    }

    public void setClientesByCodigoClient(ClientesEntity clientesByCodigoClient) {
        this.clientesByCodigoClient = clientesByCodigoClient;
    }

    public ProductosEntity getProductosByIdProducto() {
        return productosByIdProducto;
    }

    public void setProductosByIdProducto(ProductosEntity productosByIdProducto) {
        this.productosByIdProducto = productosByIdProducto;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
