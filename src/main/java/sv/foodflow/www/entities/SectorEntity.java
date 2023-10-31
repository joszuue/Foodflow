package sv.foodflow.www.entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "sector", schema = "food_flow")
@NamedQueries({
        @NamedQuery(name="findAll", query = "SELECT s FROM SectorEntity s"),
        @NamedQuery(name="validacion", query = "SELECT s FROM SectorEntity s WHERE s.nombre = :nombre")
})
public class SectorEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_Sector", nullable = false)
    private int idSector;
    @Basic
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    @Basic
    @Column(name = "capacidad", nullable = false)
    private int capacidad;
    @Basic
    @Column(name = "descripcion", nullable = false, length = 300)
    private String descripcion;
    @Basic
    @Column(name = "estado", nullable = false, length = 50)
    private String estado;
    @OneToMany(mappedBy = "sectorByIdSector")
    private Collection<MesasEntity> mesasByIdSector;

    public int getIdSector() {
        return idSector;
    }

    public void setIdSector(int idSector) {
        this.idSector = idSector;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

        SectorEntity that = (SectorEntity) o;

        if (idSector != that.idSector) return false;
        if (capacidad != that.capacidad) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSector;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + capacidad;
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        return result;
    }

    public Collection<MesasEntity> getMesasByIdSector() {
        return mesasByIdSector;
    }

    public void setMesasByIdSector(Collection<MesasEntity> mesasByIdSector) {
        this.mesasByIdSector = mesasByIdSector;
    }
}
