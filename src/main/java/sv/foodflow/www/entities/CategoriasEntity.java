package sv.foodflow.www.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias", schema = "food_flow")
@NamedQueries({
        @NamedQuery(name="CategoriasEntity.findAll", query = "SELECT c FROM CategoriasEntity c"),
})
public class CategoriasEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idCategoria", nullable = false)
    private int idCategoria;
    @Basic
    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoriasEntity that = (CategoriasEntity) o;

        if (idCategoria != that.idCategoria) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCategoria;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }
}
