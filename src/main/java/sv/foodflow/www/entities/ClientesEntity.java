package sv.foodflow.www.entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "clientes", schema = "food_flow", catalog = "")
public class ClientesEntity {
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
}
