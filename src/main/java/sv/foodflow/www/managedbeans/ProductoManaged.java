package sv.foodflow.www.managedbeans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.Part;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import sv.foodflow.www.entities.CategoriasEntity;
import sv.foodflow.www.entities.ProductosEntity;
import sv.foodflow.www.models.ProductoModel;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@ManagedBean
@RequestScoped
public class ProductoManaged {
    ProductoModel modelo = new ProductoModel();
    private int idCategoria;
    private ProductosEntity producto;

    private Part nombreTempImg;


    public ProductoManaged(){
        producto = new ProductosEntity();
    }

    public void guardarProducto() {
        if (nombreTempImg != null){
            if (guardarImagen() != null){
                producto.setEstado("Pendiente");
                producto.setImagen(guardarImagen());
                if (modelo.insertarProducto(producto, idCategoria) != 1) {
                    FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                            FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El producto no se ha podido registrar."));
                } else {
                    FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                            FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "El producto se ha registrado correctamente."));
                }
            }else{
                FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                        FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El formato debe de ser de imagen png, jpg o jpeg."));
            }
        }else{
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El campo imagen no puede quedar vacío."));
        }

        producto = new ProductosEntity();
    }

    public String guardarImagen(){
        try (InputStream input = nombreTempImg.getInputStream()) {
            // Obtener el nombre del archivo
            String fileName = nombreTempImg.getSubmittedFileName();
            String nuevoNombre = generateCodigo() + obtenerExtension(fileName);
            if (obtenerExtension(fileName).equals(".jpg") ||  obtenerExtension(fileName).equals(".png") || obtenerExtension(fileName).equals(".jpeg")){
                // Guardar la imagen en la carpeta www de wamp
                OutputStream output = new FileOutputStream(new File("C:\\wamp64\\www\\FoodFlow_img\\", nuevoNombre));
                IOUtils.copy(input, output);
                return nuevoNombre;
            }else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String generateCodigo(){
        String codigo = "";
        Date fecha = new Date();
        SimpleDateFormat formatoAnio = new SimpleDateFormat("yy");

        Random random = new Random();
        String anio;
        int numero;

        anio = formatoAnio.format(fecha);
        numero = random.nextInt(9000) + 1000;

        codigo = "IMG" + anio + numero;
        return codigo;
    }

    private String obtenerExtension(String nombreArchivo) {
        int lastIndex = nombreArchivo.lastIndexOf(".");
        if (lastIndex == -1) {
            // No se encontró una extensión, puedes manejar este caso de manera diferente si es necesario
            return "";
        }
        return nombreArchivo.substring(lastIndex);
    }

    public List<ProductosEntity> listProductos(){
        return modelo.listarProductos();
    }

    public void modificarProducto(){
        producto.setImagen(guardarImagen());
        if (modelo.modificarProducto(producto, idCategoria) == 1){
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "El producto se ha modificado correctamente."));
        }else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El producto no se ha podido modificar."));
        }
        producto = new ProductosEntity();
    }

    public void data(ProductosEntity produ) throws FileNotFoundException {
        producto = produ;
        String rutaImagen = "C:\\wamp64\\www\\FoodFlow_img\\" + produ.getImagen();

    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void eliminarProducto(int id){
        if (modelo.eliminarProducto(id) > 0){
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "El producto se ha eliminado correctamente."));
        }else {
            FacesContext.getCurrentInstance().addMessage("SEVERITY_ERROR", new
                    FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El producto no se ha podido eliminar."));
        }
        producto = new ProductosEntity();
    }

    public ProductoModel getModelo() {
        return modelo;
    }

    public void setModelo(ProductoModel modelo) {
        this.modelo = modelo;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public ProductosEntity getProducto() {
        return producto;
    }

    public void setProducto(ProductosEntity producto) {
        this.producto = producto;
    }

    public Part getNombreTempImg() {
        return nombreTempImg;
    }

    public void setNombreTempImg(Part nombreTempImg) {
        this.nombreTempImg = nombreTempImg;
    }
}
