package sv.foodflow.www.managedbeans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.context.FacesContext;
import sv.foodflow.www.entities.CategoriasEntity;
import sv.foodflow.www.entities.ClientesEntity;
import sv.foodflow.www.entities.MesasEntity;
import sv.foodflow.www.models.ClientesModel;
import sv.foodflow.www.models.MesasModel;
import sv.foodflow.www.models.OrdenModel;

import java.security.SecureRandom;
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

    public void cerrarCuenta(ClientesEntity client, MesasEntity mesas, int idSector, Date fecha){
        client.setEstado("Inactivo");
        if (modelo.modificarCliente(client) == 1){
            MesasModel mesasModel = new MesasModel();
            OrdenModel ordenModel = new OrdenModel();

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

}
