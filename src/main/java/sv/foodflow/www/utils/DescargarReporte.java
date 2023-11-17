package sv.foodflow.www.utils;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ManagedBean
@SessionScoped
public class DescargarReporte {
    private List<List<String>> informeProductos = new ArrayList<>();

    private List<List<String>> informeMes = new ArrayList<>();

    private List<List<String>> informeAnio = new ArrayList<>();

    public void limpiarLista(){
        if (informeProductos != null ) {
            informeProductos.clear();
        }
    }

    public void limpiarLista1(){
        if (informeAnio != null) {
            informeAnio.clear();
        }
    }

    public void limpiarLista2(){
        if (informeMes != null) {
            informeMes.clear();
        }
    }

    public void ListaProductos(int num, String producto, String fecha, double precio, int cantidad, double subtotal){
        if (informeProductos == null) {
            informeProductos = new ArrayList<>();
        }
        informeProductos.add(Arrays.asList(""+num,producto,fecha,"$"+precio,""+cantidad,"$"+subtotal));
    }

    public void listaDetalle(int num, String producto, String fecha, double precio, int cantidad, double subtotal){
        if (informeProductos == null) {
            informeProductos = new ArrayList<>();
        }
        informeProductos.add(Arrays.asList(""+num,producto,fecha,"$"+precio,""+cantidad,"$"+subtotal));
    }

    public void listaMes(int num, String mes, double subtotal){
        if (informeMes == null) {
            informeMes = new ArrayList<>();
        }
        informeMes.add(Arrays.asList(""+num,mes,"$"+subtotal));
    }

    public void listaAnio(int num, int anioo, double subtotal){
        if (informeAnio == null) {
            informeAnio = new ArrayList<>();
        }
        informeAnio.add(Arrays.asList(""+num,""+anioo,"$"+subtotal));
    }

    public void descargarInforme(String accion, int anio) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        String nombre = "";
        Workbook workbook = new XSSFWorkbook();

        CellStyle encabezado = workbook.createCellStyle();

        encabezado.setWrapText(true);//Texto se ajuste a la celda
        encabezado.setAlignment(HorizontalAlignment.CENTER);// Establecer la alineación horizontal del texto como centrado
        encabezado.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());//Color de fondo encabezado
        encabezado.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //Color y texto en negrita
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        boldFont.setColor(IndexedColors.WHITE.getIndex());
        boldFont.setFontName("Arial");
        boldFont.setFontHeightInPoints((short) 11);

        encabezado.setFont(boldFont);

        encabezado.setBorderTop(BorderStyle.MEDIUM);
        encabezado.setBorderBottom(BorderStyle.MEDIUM);
        encabezado.setBorderLeft(BorderStyle.MEDIUM);
        encabezado.setBorderRight(BorderStyle.MEDIUM);

        encabezado.setTopBorderColor(IndexedColors.WHITE.getIndex());
        encabezado.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        encabezado.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        encabezado.setRightBorderColor(IndexedColors.WHITE.getIndex());


        CellStyle general = workbook.createCellStyle();

        // Crear bordes
        general.setBorderTop(BorderStyle.MEDIUM);
        general.setBorderBottom(BorderStyle.MEDIUM);
        general.setBorderLeft(BorderStyle.MEDIUM);
        general.setBorderRight(BorderStyle.MEDIUM);

        //NOMBRE HOJA
        Sheet sheet1 = workbook.createSheet("INFORME");

        // Establecer la alineación horizontal del texto como centrado
        general.setAlignment(HorizontalAlignment.CENTER);

        List<List<String>> datos = new ArrayList<>();
        List<List<String>> informeGeneral = new ArrayList<>();
        if (datos != null) {
            datos.clear();
        }

        if (informeGeneral != null) {
            informeGeneral.clear();
        }
        switch (accion){
            case "productos":
                datos.add(Arrays.asList("#","Producto","Fecha y hora", "Precio Inicial", "Cantidad", "Subtotal"));
                nombre = "Informe General - Productos";
                informeGeneral = informeProductos;
                break;
            case "mes":
                datos.add(Arrays.asList("#","Mes","Venta del mes"));
                nombre = "Informe Mensual";
                informeGeneral = informeMes;
                break;
            case "anioooo":
                datos.add(Arrays.asList("#","Año","Venta del mes"));
                nombre = "Informe Anual - " + anio;
                 informeGeneral = informeAnio;
                break;
            case "detalle":
                datos.add(Arrays.asList("#","Producto","Fecha y hora", "Precio Inicial", "Cantidad", "Subtotal"));
                nombre = "Detalle de Producto";
                informeGeneral = informeProductos;
                break;
        }
        Row row = sheet1.createRow(0);
        int cellNum = 0;
        for (List<String> fila : datos) {
            for (String dato : fila) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(dato);
                cell.setCellStyle(encabezado);
                sheet1.autoSizeColumn(cellNum);
                cellNum++;
            }
        }

        //IMPRESION DE RESULTADOS
        if (informeGeneral != null) {
            for (int i = 0; i < informeGeneral.size(); i++) {
                Row row6 = sheet1.createRow(1+i);
                List<String> fila6 = informeGeneral.get(i);
                for (int j = 0; j < fila6.size(); j++) {
                    Cell cell6 = row6.createCell(j);
                    cell6.setCellValue(fila6.get(j));
                    cell6.setCellStyle(general);
                    sheet1.autoSizeColumn(j);
                }
            }
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename="+ nombre +".xlsx");

        // Obtener el flujo de salida de la respuesta HTTP
        OutputStream outStream = response.getOutputStream();

        // Escribir el libro de trabajo en el flujo de salida
        workbook.write(outStream);

        // Limpiar y cerrar el flujo de salida
        outStream.flush();
        outStream.close();

        // Finalizar la respuesta
        facesContext.responseComplete();
    }

    public void descargarTodo() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        String nombre = "";
        Workbook workbook = new XSSFWorkbook();

        CellStyle encabezado = workbook.createCellStyle();

        encabezado.setWrapText(true);//Texto se ajuste a la celda
        encabezado.setAlignment(HorizontalAlignment.CENTER);// Establecer la alineación horizontal del texto como centrado
        encabezado.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());//Color de fondo encabezado
        encabezado.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //Color y texto en negrita
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        boldFont.setColor(IndexedColors.WHITE.getIndex());
        boldFont.setFontName("Arial");
        boldFont.setFontHeightInPoints((short) 11);

        encabezado.setFont(boldFont);

        encabezado.setBorderTop(BorderStyle.MEDIUM);
        encabezado.setBorderBottom(BorderStyle.MEDIUM);
        encabezado.setBorderLeft(BorderStyle.MEDIUM);
        encabezado.setBorderRight(BorderStyle.MEDIUM);

        encabezado.setTopBorderColor(IndexedColors.WHITE.getIndex());
        encabezado.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        encabezado.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        encabezado.setRightBorderColor(IndexedColors.WHITE.getIndex());


        CellStyle general = workbook.createCellStyle();

        // Crear bordes
        general.setBorderTop(BorderStyle.MEDIUM);
        general.setBorderBottom(BorderStyle.MEDIUM);
        general.setBorderLeft(BorderStyle.MEDIUM);
        general.setBorderRight(BorderStyle.MEDIUM);
        // Establecer la alineación horizontal del texto como centrado
        general.setAlignment(HorizontalAlignment.CENTER);

        //PRIMERA HOJA
        Sheet sheet1 = workbook.createSheet("INFORME POR PRODUCTOS");

        List<List<String>> datos = new ArrayList<>();
        datos.add(Arrays.asList("#","Producto","Fecha y hora", "Precio Inicial", "Cantidad", "Subtotal"));

        Row row = sheet1.createRow(0);
        int cellNum = 0;
        for (List<String> fila : datos) {
            for (String dato : fila) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(dato);
                cell.setCellStyle(encabezado);
                sheet1.autoSizeColumn(cellNum);
                cellNum++;
            }
        }

        //IMPRESION DE RESULTADOS
        if (informeProductos != null) {
            for (int i = 0; i < informeProductos.size(); i++) {
                Row row3 = sheet1.createRow(1+i);
                List<String> fila3 = informeProductos.get(i);
                for (int j = 0; j < fila3.size(); j++) {
                    Cell cell3 = row3.createCell(j);
                    cell3.setCellValue(fila3.get(j));
                    cell3.setCellStyle(general);
                    sheet1.autoSizeColumn(j);
                }
            }
        }

        //HOJA 2
        Sheet sheet2 = workbook.createSheet("INFORME MENSUAL");
        List<List<String>> datos2 = new ArrayList<>();
        datos2.add(Arrays.asList("#","Mes","Venta del mes"));

        Row row2 = sheet2.createRow(0);
        int cellNum2 = 0;
        for (List<String> fila2 : datos2) {
            for (String dato2 : fila2) {
                Cell cell2 = row2.createCell(cellNum2);
                cell2.setCellValue(dato2);
                cell2.setCellStyle(encabezado);
                sheet1.autoSizeColumn(cellNum2);
                cellNum2++;
            }
        }

        //IMPRESION DE RESULTADOS
        if (informeMes != null) {
            for (int i = 0; i < informeMes.size(); i++) {
                Row row5 = sheet2.createRow(1+i);
                List<String> fila5 = informeMes.get(i);
                for (int j = 0; j < fila5.size(); j++) {
                    Cell cell3 = row5.createCell(j);
                    cell3.setCellValue(fila5.get(j));
                    cell3.setCellStyle(general);
                    sheet2.autoSizeColumn(j);
                }
            }
        }

        //HOJA 3
        Sheet sheet3 = workbook.createSheet("INFORME ANUAL");
        List<List<String>> datos6 = new ArrayList<>();
        datos6.add(Arrays.asList("#","Año","Venta del año"));

        Row row6 = sheet3.createRow(0);
        int cellNum6 = 0;
        for (List<String> fila6 : datos6) {
            for (String dato6 : fila6) {
                Cell cell6 = row6.createCell(cellNum6);
                cell6.setCellValue(dato6);
                cell6.setCellStyle(encabezado);
                sheet3.autoSizeColumn(cellNum6);
                cellNum6++;
            }
        }

        //IMPRESION DE RESULTADOS
        if (informeAnio != null) {
            for (int i = 0; i < informeAnio.size(); i++) {
                Row row7 = sheet3.createRow(1+i);
                List<String> fila6 = informeAnio.get(i);
                for (int j = 0; j < fila6.size(); j++) {
                    Cell cell7 = row7.createCell(j);
                    cell7.setCellValue(fila6.get(j));
                    cell7.setCellStyle(general);
                    sheet3.autoSizeColumn(j);
                }
            }
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=INFORME GENERAL.xlsx");

        // Obtener el flujo de salida de la respuesta HTTP
        OutputStream outStream = response.getOutputStream();

        // Escribir el libro de trabajo en el flujo de salida
        workbook.write(outStream);

        // Limpiar y cerrar el flujo de salida
        outStream.flush();
        outStream.close();

        // Finalizar la respuesta
        facesContext.responseComplete();
    }

    public List<List<String>> getInformeProductos() {
        return informeProductos;
    }

    public void setInformeProductos(List<List<String>> informeProductos) {
        this.informeProductos = informeProductos;
    }

    public List<List<String>> getInformeMes() {
        return informeMes;
    }

    public void setInformeMes(List<List<String>> informeMes) {
        this.informeMes = informeMes;
    }

    public List<List<String>> getInformeAnio() {
        return informeAnio;
    }

    public void setInformeAnio(List<List<String>> informeAnio) {
        this.informeAnio = informeAnio;
    }

}
