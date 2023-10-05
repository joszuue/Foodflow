package sv.foodflow.www.entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "mesas", schema = "food_flow", catalog = "")
public class MesasEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idMesa", nullable = false)
    private int idMesa;
    @Basic
    @Column(name = "Capacidad", nullable = false)
    private int capacidad;
    @Basic
    @Column(name = "Estado", nullable = false, length = 25)
    private String estado;
    @OneToMany(mappedBy = "mesasByIdMesa")
    private Collection<ClientesEntity> clientesByIdMesa;

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
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

        MesasEntity that = (MesasEntity) o;

        if (idMesa != that.idMesa) return false;
        if (capacidad != that.capacidad) return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idMesa;
        result = 31 * result + capacidad;
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        return result;
    }

    public Collection<ClientesEntity> getClientesByIdMesa() {
        return clientesByIdMesa;
    }

    public void setClientesByIdMesa(Collection<ClientesEntity> clientesByIdMesa) {
        this.clientesByIdMesa = clientesByIdMesa;
    }
}