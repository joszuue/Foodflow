package sv.foodflow.www.entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "productos", schema = "food_flow")
@NamedQueries({
        @NamedQuery(name="ProductosEntity.findAll", query = "SELECT p FROM ProductosEntity p"),
})
public class ProductosEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idProducto", nullable = false)
    private int idProducto;
    @Basic
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Basic
    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;
    @Basic
    @Column(name = "precio", nullable = false, precision = 0)
    private double precio;
    @Basic
    @Column(name = "imagen", nullable = false, length = 100)
    private String imagen;
    @Basic
    @Column(name = "estado", nullable = false, length = 25)
    private String estado;
    @OneToMany(mappedBy = "productosByIdProducto")
    private Collection<OrdenEntity> ordensByIdProducto;
    @ManyToOne
    @JoinColumn(name = "idCategoria", referencedColumnName = "idCategoria", nullable = false)
    private CategoriasEntity categoriasByIdCategoria;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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

        ProductosEntity that = (ProductosEntity) o;

        if (idProducto != that.idProducto) return false;
        if (Double.compare(that.precio, precio) != 0) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        if (imagen != null ? !imagen.equals(that.imagen) : that.imagen != null) return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idProducto;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        temp = Double.doubleToLongBits(precio);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (imagen != null ? imagen.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        return result;
    }

    public Collection<OrdenEntity> getOrdensByIdProducto() {
        return ordensByIdProducto;
    }

    public void setOrdensByIdProducto(Collection<OrdenEntity> ordensByIdProducto) {
        this.ordensByIdProducto = ordensByIdProducto;
    }

    public CategoriasEntity getCategoriasByIdCategoria() {
        return categoriasByIdCategoria;
    }

    public void setCategoriasByIdCategoria(CategoriasEntity categoriasByIdCategoria) {
        this.categoriasByIdCategoria = categoriasByIdCategoria;
    }
}
