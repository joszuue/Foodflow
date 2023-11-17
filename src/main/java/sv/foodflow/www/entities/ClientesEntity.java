package sv.foodflow.www.entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "clientes", schema = "food_flow")
@NamedQueries({
        @NamedQuery(name = "ClientesEntity.findAll", query = "SELECT DISTINCT c FROM ClientesEntity c JOIN OrdenEntity o ON c.codigoClient = o.clientesByCodigoClient.codigoClient WHERE c.estado = 'Activo' AND o.estado = 'Enviado'"),
        @NamedQuery(name="clientesMesa", query = "SELECT c FROM ClientesEntity c WHERE c.estado = 'Activo' AND c.empleadoByCodigo.codigo = :codigo"),
        @NamedQuery(name="mesaSector", query = "SELECT c FROM ClientesEntity c WHERE c.estado = 'Activo' AND c.empleadoByCodigo.codigo = :codigo AND c.mesasByIdMesa.sectorByIdSector.idSector = :id"),
        @NamedQuery(name="ClientesEntity.findByMesa", query = "SELECT c FROM ClientesEntity c WHERE c.mesasByIdMesa = :idMesa AND c.estado = 'Activo'"),
        @NamedQuery(name="ClientesEntity.findByCodigoyEstado", query = "SELECT c FROM ClientesEntity c WHERE c.estado = 'Activo' AND c.codigoClient = :codigo")
})
public class ClientesEntity {
    @Id
    @Column(name = "codigoClient", nullable = false, length = 8)
    private String codigoClient;
    @Basic
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;
    @Basic
    @Column(name = "estado", nullable = false, length = 25)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "idMesa", referencedColumnName = "idMesa", nullable = false)
    private MesasEntity mesasByIdMesa;
    @OneToMany(mappedBy = "clientesByCodigoClient")
    private Collection<OrdenEntity> ordensByCodigoClient;

    @ManyToOne
    @JoinColumn(name = "codigo", referencedColumnName = "codigo", nullable = false)
    private EmpleadosEntity empleadoByCodigo;

    @Basic
    @Column(name = "tiempo", nullable = true, length = 25)
    private String tiempo;

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

        ClientesEntity that = (ClientesEntity) o;

        if (apellido != null ? !apellido.equals(that.apellido) : that.apellido != null) return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = apellido != null ? apellido.hashCode() : 0;
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        return result;
    }

    public MesasEntity getMesasByIdMesa() {
        return mesasByIdMesa;
    }

    public void setMesasByIdMesa(MesasEntity mesasByIdMesa) {
        this.mesasByIdMesa = mesasByIdMesa;
    }

    public Collection<OrdenEntity> getOrdensByCodigoClient() {
        return ordensByCodigoClient;
    }

    public void setOrdensByCodigoClient(Collection<OrdenEntity> ordensByCodigoClient) {
        this.ordensByCodigoClient = ordensByCodigoClient;
    }

    public String getCodigoClient() {
        return codigoClient;
    }

    public void setCodigoClient(String codigoClient) {
        this.codigoClient = codigoClient;
    }

    public EmpleadosEntity getEmpleadoByCodigo() {
        return empleadoByCodigo;
    }

    public void setEmpleadoByCodigo(EmpleadosEntity empleadoByCodigo) {
        this.empleadoByCodigo = empleadoByCodigo;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }
}
