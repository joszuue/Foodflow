package sv.foodflow.www.entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "empleados", schema = "food_flow")
@NamedQueries({
        @NamedQuery(name="EmpleadosEntity.findAll", query = "SELECT e FROM EmpleadosEntity e"),
        @NamedQuery(name="EmpleadosEntity.validateEmple", query = "SELECT e FROM EmpleadosEntity e WHERE e.dui = :dui")
})
public class EmpleadosEntity {
    @Id
    @Column(name = "codigo", nullable = false, length = 8)
    private String codigo;
    @Basic
    @Column(name = "contraseña", nullable = false, length = 200)
    private String contraseña;

    @Basic
    @Column(name = "rol", nullable = false, length = 25)
    private String rol;
    @Basic
    @Column(name = "estado", nullable = false, length = 25)
    private String estado;
    @Basic
    @Column(name = "correo", nullable = false, length = 200)
    private String correo;
    @Basic
    @Column(name = "telefono", nullable = false, length = 10)
    private String telefono;
    @Basic
    @Column(name = "dui", nullable = false, length = 10)
    private String dui;
    @Basic
    @Column(name = "fechaNac", nullable = false)
    private String fechaNac;
    @Basic
    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;
    @Basic
    @Column(name = "municipio", nullable = false, length = 200)
    private String municipio;
    @Basic
    @Column(name = "departamento", nullable = false, length = 200)
    private String departamento;
    @Basic
    @Column(name = "sexo", nullable = false, length = 20)
    private String sexo;
    @Basic
    @Column(name = "estadoCivil", nullable = false, length = 50)
    private String estadoCivil;
    @Basic
    @Column(name = "nombre1", nullable = false, length = 100)
    private String nombre1;
    @Basic
    @Column(name = "nombre2", nullable = false, length = 100)
    private String nombre2;
    @Basic
    @Column(name = "apellido1", nullable = false, length = 100)
    private String apellido1;
    @Basic
    @Column(name = "apellido2", nullable = false, length = 100)
    private String apellido2;

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
        if (rol != null ? !rol.equals(that.rol) : that.rol != null) return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigo != null ? codigo.hashCode() : 0;
        result = 31 * result + (contraseña != null ? contraseña.hashCode() : 0);
        result = 31 * result + (nombre1 != null ? nombre1.hashCode() : 0);
        result = 31 * result + (nombre2 != null ? nombre1.hashCode() : 0);
        result = 31 * result + (apellido1 != null ? apellido1.hashCode() : 0);
        result = 31 * result + (apellido2 != null ? apellido1.hashCode() : 0);
        result = 31 * result + (rol != null ? rol.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        return result;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }
}
