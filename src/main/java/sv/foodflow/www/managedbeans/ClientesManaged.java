package sv.foodflow.www.managedbeans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import sv.foodflow.www.entities.*;
import sv.foodflow.www.models.ClientesModel;
import sv.foodflow.www.models.MesasModel;
import sv.foodflow.www.models.OrdenModel;
import sv.foodflow.www.utils.EnviarCorreo;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ManagedBean
@RequestScoped
public class ClientesManaged {
    ClientesModel modelo = new ClientesModel();
    private ClientesEntity cliente;

    private String ape;

    private int idSector;

    private String codigoMesero;

    private List<OrdenEntity> factura;

    private String correo = "";

    public ClientesManaged(){
        cliente = new ClientesEntity();
    }

    public void guardarCliente(MesasEntity mesa, int idSector) {
        if (cliente.getApellido().isEmpty()){
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe de ingresar un apellido."));
        }else {
            cliente.setEstado("Activo");
            cliente.setCodigoClient(generarCodigo());
            if (modelo.insertarCliente(cliente, mesa.getIdMesa(), codigoMesero) != 1) {
                FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                        FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El cliente no se ha podido registrar."));
            } else {
                MesasModel mesasModel = new MesasModel();
                mesa.setEstado("Ocupada");
                mesasModel.modificarMesa(mesa, idSector);
                FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                        FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "El cliente se ha registrado correctamente. El código de cliente es: " + cliente.getCodigoClient()));
            }
        }
        cliente = new ClientesEntity();
    }

    public String generarCodigo() {
        String caracteresPermitidos = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int longitud = 8;
        SecureRandom random = new SecureRandom();
        StringBuilder codigo = new StringBuilder();

        for (int i = 0; i < longitud; i++) {
            int indice = random.nextInt(caracteresPermitidos.length());
            char caracterAleatorio = caracteresPermitidos.charAt(indice);
            codigo.append(caracterAleatorio);
        }
        return codigo.toString();
    }

    public List<ClientesEntity> mesasMesero(String codigo){
        if (idSector == 0){
            return modelo.listarMesaCliente(codigo);
        }else {
            return modelo.listarMesaSector(codigo,idSector);
        }

    }

    public List<ClientesEntity> listClientes(){
        return modelo.listarClientes();
    }

    public ClientesEntity obtenerClient(int idMesa){
        return modelo.obtenerCliente(idMesa);
    }

    public void modificarCliente(){
        if (modelo.modificarCliente(cliente) == 1){
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "El cliente se ha modificado correctamente."));
        }else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El cliente no se ha podido modificar."));
        }
        cliente = new ClientesEntity();
    }

    public void data(ClientesEntity client){
        cliente = client;
    }

    public void cerrarCuenta(ClientesEntity client, MesasEntity mesas, int idSector, Date fecha, String accion){
        client.setEstado("Inactivo");
        if (modelo.modificarCliente(client) == 1){
            MesasModel mesasModel = new MesasModel();
            OrdenModel ordenModel = new OrdenModel();
            if (!correo.isEmpty()){
                enviarFactura(client);
                correo = "";
            }
            mesas.setEstado("Disponible");
            mesasModel.modificarMesa(mesas, idSector);
            ordenModel.cerrarOrden("Finalizado", client.getCodigoClient());

            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "La cuenta del cliente ha sido cerrada correctamente."));
        }else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se ha podido cerrar la cuenta del cliente."));
        }
        cliente = new ClientesEntity();
    }

    public void limpiar(){
        if (factura != null){
            factura.clear();
        }
    }

    public void enviarFactura(ClientesEntity client){
        double total = 0;
        OrdenModel modelOrden = new OrdenModel();
        String asunto = "Factura Electrónica - "+ client.getCodigoClient();
        String contenido = "<center><img src='https://drive.google.com/uc?id=1DPULY6d7ITdJEzqXodn_b1eIFkV241vl' width='1000px' height='220px'/></center>"
                + "<h2>Hola Familia, <b>" + client.getApellido() + ".</b></h2>"
                + "<h2>Gracias por elegir FoodFlow. A continuación, te presentamos los detalles de tu factura.</h2>"
                + "<br><h2>Detalle de la factura:</h2>"
                + "<h2>-------------------------------------------</h2>"
                + "<h2>Codigo de cliente: "+ client.getCodigoClient()+"</h2>"
                + "<h2>Fecha y hora de emisión: "+ fecha()+"</h2>"
                + "<h2>Productos/Servicios:</h2>"
                + "  <table style=\"width: 40%; border-collapse: collapse; margin-bottom: 20px; font-family: Arial;\">\n" +
                "    <thead>\n" +
                "      <tr style=\"background-color: #f2f2f2;\">\n" +
                "        <th style=\"border: 1px solid #dddddd; text-align: left; padding: 8px;\">Producto</th>\n" +
                "        <th style=\"border: 1px solid #dddddd; text-align: left; padding: 8px;\">Cantidad</th>\n" +
                "        <th style=\"border: 1px solid #dddddd; text-align: left; padding: 8px;\">Precio</th>\n" +
                "        <th style=\"border: 1px solid #dddddd; text-align: left; padding: 8px;\">Subtotal</th>\n" +
                "      </tr>\n" +
                "    </thead>\n" +
                "    <tbody>";
        if (modelOrden.carrito(client.getCodigoClient(), "Entregado") != null) {
            for (OrdenEntity orden : modelOrden.carrito(client.getCodigoClient(), "Entregado")) {
                ProductosEntity productos = orden.getProductosByIdProducto();
                contenido +="<tr>";
                contenido += "<td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>"+productos.getNombre() + "</td>" +
                        "<td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>" + orden.getCantidad() + "</td>" +
                        "<td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>$" + orden.getTotal() + "</td>" +
                        "<td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>$" + orden.getCantidad() * orden.getTotal()+"</td>";
                contenido += "</tr>";
                total += orden.getCantidad() * orden.getTotal();
            }
        }
        contenido += "<tr><td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'> - </td>" +
                "<td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'><b>Propina: $" + total * 0.1 + "</b></td>" +
                "<td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'><b>SubTotal: $" + total + "</b></td>" +
                "<td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'><b>Total: $" + total + total * 0.1 +"</b></td></tr>"
                + "</tbody></table>"
                + "<img src='https://drive.google.com/uc?id=14amyQ84zTZ47460dx7vh3DC0wNSYkz9D'/>";
        EnviarCorreo correo2 = new EnviarCorreo(correo, asunto, contenido);
        correo2.createEmail();
        correo2.sendEmail();
    }

    public static String fecha() {
        Date fechaHoraActual = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String formattedDate = formatter.format(fechaHoraActual);

        return formattedDate;
    }

    public ClientesModel getModelo() {
        return modelo;
    }

    public void setModelo(ClientesModel modelo) {
        this.modelo = modelo;
    }

    public ClientesEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClientesEntity cliente) {
        this.cliente = cliente;
    }

    public String getApe() {
        return ape;
    }

    public void setApe(String ape) {
        this.ape = ape;
    }

    public int getIdSector() {
        return idSector;
    }

    public void setIdSector(int idSector) {
        this.idSector = idSector;
    }

    public String getCodigoMesero() {
        return codigoMesero;
    }

    public void setCodigoMesero(String codigoMesero) {
        this.codigoMesero = codigoMesero;
    }

    public List<OrdenEntity> getFactura() {
        return factura;
    }

    public void setFactura(List<OrdenEntity> factura) {
        this.factura = factura;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
