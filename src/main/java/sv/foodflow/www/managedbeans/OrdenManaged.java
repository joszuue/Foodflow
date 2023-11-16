package sv.foodflow.www.managedbeans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import sv.foodflow.www.entities.OrdenEntity;
import sv.foodflow.www.models.OrdenModel;

import javax.swing.text.DateFormatter;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@ManagedBean
@SessionScoped
public class OrdenManaged {
    private OrdenEntity orden;
    private OrdenModel modelo = new OrdenModel();

    private double totalCarrito;

    private int totalCantidad;

    private double totalReporte;

    private String fech = "";

    private String fecha1;

    private String fecha2;

    private int idProdu = 0;

    private int anioo = 0;

    public OrdenManaged(){
        orden = new OrdenEntity();
        fecha1 = "no";
        fecha2 ="no";
    }

    public void carrito(String codigo, int idProdu, double precio, String tiempo) {
        orden.setFecha(fecha());
        orden.setEstado("Carrito");
        orden.setTotal(precio);
        orden.setTiempoEspera(converTime(tiempo, orden.getCantidad()));
        if (modelo.insertarOrden(orden, codigo, idProdu) != 1) {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se ha podido registrar el producto"));
        } else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "El producto se ha registrado correctamente"));
        }
        orden = new OrdenEntity();
    }

    public void realizarPedido(String codigo) {
        if (modelo.carritoOrden("Enviado", codigo, fecha()) != 1) {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "La orden ha sido enviada a cocina"));
        } else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al enviar la orden a cocina"));
        }
        orden = new OrdenEntity();
    }

    public void eliminarOrden(int id) {
        if (modelo.eliminarOrden(id) > 0){
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Se ha eliminado de tu orden."));
        }else{
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se ha podido eliminar"));
        }
    }

    public void modificarCarrito(OrdenEntity ordeen, String codigo, int idProdu){
        ordeen.setCantidad(orden.getCantidad());
        ordeen.setComentario(orden.getComentario());
        if (modelo.modificarOrden(ordeen,codigo, idProdu) == 1){
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Se ha modificado."));
        }else{
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se ha podido modificar"));
        }
        orden = new OrdenEntity();
    }

    //OTRAS FUNCIONES
    public static String fecha() {
        Date fechaHoraActual = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String formattedDate = formatter.format(fechaHoraActual);

        return formattedDate;
    }

    public LocalTime converTime(String timeString, int cant){
        try {
            // Define el formato de la cadena de tiempo
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            // Parsea la cadena y obtiene un objeto LocalTime
            LocalTime time = LocalTime.parse(timeString, formatter);

            // Multiplica el tiempo por la cantidad deseada
            LocalTime tiempoMultiplicado = time.plusHours(time.getHour() * (cant - 1))
                    .plusMinutes(time.getMinute() * (cant - 1))
                    .plusSeconds(time.getSecond() * (cant - 1));

            // Retorna el tiempo multiplicado
            return tiempoMultiplicado;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
            // Maneja la excepción según tus necesidades
        }
    }

    //FUNCIONES PARA EL INFORME

    public void sumar(double subtotal){totalCarrito += subtotal;}

    public void sumarCantidad(int cant){
        totalCantidad += cant;
    }

    public void totalRepor(double subtotal){
        totalReporte += subtotal;
    }

    public void reiniciarFecha(){fecha1 = "no"; fecha2 = "no";}
    public void reiniciarSuma(){
        totalCarrito = 0;
        totalCantidad = 0;
    }

    public List<OrdenEntity> listInforme(){
        if (fecha1.equals("no") && fecha2.equals("no")){
            return modelo.productosReporte();
        }else {
            fecha1 = ""+fecha1.replace("T", " ")+":00";
            fecha2 = ""+fecha2.replace("T", " ")+":00";
            return modelo.productosReporteFiltrado(fecha1, fecha2);
        }
    }

    public List<OrdenEntity> listDetalle(int id){
        if (fecha1.equals("no") && fecha2.equals("no")){
            return modelo.productosDetalle(id);
        }else if (id == 0){
            fecha1 = ""+fecha1.replace("T", " ")+":00";
            fecha2 = ""+fecha2.replace("T", " ")+":00";
            return modelo.informeDetalle(idProdu, fecha1, fecha2);
        }
        return null;
    }


    public List<OrdenEntity> reporte(int id){
        if (fecha1.equals("no") && fecha2.equals("no")){
            return modelo.reporte(id);
        }else{
            fecha1 = ""+fecha1.replace("T", " ")+":00";
            fecha2 = ""+fecha2.replace("T", " ")+":00";
            return modelo.reporte2(id, fecha1, fecha2);
        }
    }

    //INFORME MENSUAL

    public List<Object[]> informeMensual(){
        if (anioo == 0){
            return modelo.generarReporteMensual(anioActual());
        }else {
            return modelo.generarReporteMensual(anioo);
        }

    }

    public int anioActual(){
        LocalDate fechaActual = LocalDate.now();
        return fechaActual.getYear();
    }
    public void reiniciarSuma2(){
        totalReporte = 0;
    }

    public OrdenEntity getOrden() {
        return orden;
    }

    public void setOrden(OrdenEntity orden) {
        this.orden = orden;
    }

    public OrdenModel getModelo() {
        return modelo;
    }

    public void setModelo(OrdenModel modelo) {
        this.modelo = modelo;
    }

    public double getTotalCarrito() {
        return totalCarrito;
    }

    public void setTotalCarrito(double totalCarrito) {
        this.totalCarrito = totalCarrito;
    }

    public String getFech() {
        return fech;
    }

    public void setFech(String fech) {
        this.fech = fech;
    }

    public int getTotalCantidad() {
        return totalCantidad;
    }

    public void setTotalCantidad(int totalCantidad) {
        this.totalCantidad = totalCantidad;
    }

    public double getTotalReporte() {
        return totalReporte;
    }

    public void setTotalReporte(double totalReporte) {
        this.totalReporte = totalReporte;
    }

    public String getFecha1() {
        return fecha1;
    }

    public void setFecha1(String fecha1) {
        this.fecha1 = fecha1;
    }

    public String getFecha2() {
        return fecha2;
    }

    public void setFecha2(String fecha2) {
        this.fecha2 = fecha2;
    }

    public int getIdProdu() {
        return idProdu;
    }

    public void setIdProdu(int idProdu) {
        this.idProdu = idProdu;
    }

    public int getAnioo() {
        return anioo;
    }

    public void setAnioo(int anioo) {
        this.anioo = anioo;
    }
}
