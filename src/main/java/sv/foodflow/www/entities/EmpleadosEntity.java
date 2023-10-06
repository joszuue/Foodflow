package sv.foodflow.www.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "empleados", schema = "food_flow")
@NamedQueries({
        @NamedQuery(name="EmpleadosEntity.findAll", query = "SELECT e FROM EmpleadosEntity e"),
})
public class EmpleadosEntity {
    @Id
    @Column(name = "codigo", nullable = false, length = 8)
    private String codigo;
    @Basic
    @Column(name = "contraseña", nullable = false, length = 200)
    private String contraseña;
    @Basic
    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;
    @Basic
    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;
    @Basic
    @Column(name = "rol", nullable = false, length = 25)
    private String rol;
    @Basic
    @Column(name = "estado", nullable = false, length = 25)
    private String estado;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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

        EmpleadosEntity that = (EmpleadosEntity) o;

        if (codigo != null ? !codigo.equals(that.codigo) : that.codigo != null) return false;
        if (contraseña != null ? !contraseña.equals(that.contraseña) : that.contraseña != null) return false;
        if (nombres != null ? !nombres.equals(that.nombres) : that.nombres != null) return false;
        if (apellidos != null ? !apellidos.equals(that.apellidos) : that.apellidos != null) return false;
        if (rol != null ? !rol.equals(that.rol) : that.rol != null) return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigo != null ? codigo.hashCode() : 0;
        result = 31 * result + (contraseña != null ? contraseña.hashCode() : 0);
        result = 31 * result + (nombres != null ? nombres.hashCode() : 0);
        result = 31 * result + (apellidos != null ? apellidos.hashCode() : 0);
        result = 31 * result + (rol != null ? rol.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        return result;
    }
}
