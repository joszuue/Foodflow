package sv.foodflow.www.entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "categorias", schema = "food_flow")
@NamedQueries({
        @NamedQuery(name="CategoriasEntity.findAll", query = "SELECT c FROM CategoriasEntity c"),
        @NamedQuery(name="CategoriasEntity.ValidateCate", query = "SELECT c FROM CategoriasEntity c WHERE c.nombre = :nombre")
})
public class CategoriasEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idCategoria", nullable = false)
    private int idCategoria;
    @Basic
    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;
    @OneToMany(mappedBy = "categoriasByIdCategoria")
    private Collection<ProductosEntity> productosByIdCategoria;

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

    public Collection<ProductosEntity> getProductosByIdCategoria() {
        return productosByIdCategoria;
    }

    public void setProductosByIdCategoria(Collection<ProductosEntity> productosByIdCategoria) {
        this.productosByIdCategoria = productosByIdCategoria;
    }
}
