package sv.foodflow.www.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "comentario", schema = "food_flow")
public class ComentarioEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Basic
    @Column(name = "idComentario", nullable = false)
    private int idComentario;
    @Basic
    @Column(name = "comentario", nullable = false, length = 500)
    private String comentario;
    @Basic
    @Column(name = "idProducto", nullable = false)
    private int idProducto;

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComentarioEntity that = (ComentarioEntity) o;

        if (idComentario != that.idComentario) return false;
        if (idProducto != that.idProducto) return false;
        if (comentario != null ? !comentario.equals(that.comentario) : that.comentario != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idComentario;
        result = 31 * result + (comentario != null ? comentario.hashCode() : 0);
        result = 31 * result + idProducto;
        return result;
    }
}
