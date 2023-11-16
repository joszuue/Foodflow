package sv.foodflow.www.utils;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.donut.DonutChartOptions;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.scatter.ScatterChartModel;
import sv.foodflow.www.entities.OrdenEntity;
import sv.foodflow.www.entities.ProductosEntity;
import sv.foodflow.www.models.OrdenModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Named
@RequestScoped
public class grafica implements Serializable {


    private DonutChartModel donutModel;

    private BarChartModel mixedModel;
    private String fecha1 = "no";
    private String fecha2 = "no";

    private List<OrdenEntity> listInforme;

    private List<OrdenEntity> lisProductos;

    private OrdenModel modelo = new OrdenModel();

    List<Number> values = new ArrayList<>();
    List<String> labels = new ArrayList<>();

    @PostConstruct
    public void init() {
        createDonutModel();
        createMixedModel();
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

    //GRAFICAS PRINCIPALES DE VENTAS

    public void lista(double total, String nombre){
        values.add(total);
        labels.add(nombre);
    }

    public void limpiarLista(){
        values.clear();
        labels.clear();
    }
    public void createDonutModel() {
        donutModel = new DonutChartModel();
        ChartData data = new ChartData();
        DonutChartOptions options = new DonutChartOptions();
        options.setMaintainAspectRatio(false);
        donutModel.setOptions(options);

        DonutChartDataSet dataSet = new DonutChartDataSet();

        List<Number> copy = new ArrayList<>(values);
        Collections.sort(copy, Collections.reverseOrder());
        List<Number> topNNumbers = copy.subList(0, Math.min(4, copy.size()));

        dataSet.setData(topNNumbers);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255, 205, 86)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        data.setLabels(labels);

        donutModel.setData(data);
    }

    public void createMixedModel() {
        mixedModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet dataSet = new BarChartDataSet();
        values.add(10);
        values.add(20);
        values.add(30);
        values.add(40);
        dataSet.setData(values);
        dataSet.setLabel("Bar Dataset");
        dataSet.setBorderColor("rgb(255, 99, 132)");
        dataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");

        LineChartDataSet dataSet2 = new LineChartDataSet();
        List<Object> values2 = new ArrayList<>();
        values2.add(50);
        values2.add(50);
        values2.add(50);
        values2.add(50);
        dataSet2.setData(values2);
        dataSet2.setLabel("Line Dataset");
        dataSet2.setFill(false);
        dataSet2.setBorderColor("rgb(54, 162, 235)");

        data.addChartDataSet(dataSet);
        data.addChartDataSet(dataSet2);

        List<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        data.setLabels(labels);

        mixedModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        linearAxes.setBeginAtZero(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        linearAxes.setTicks(ticks);

        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
        mixedModel.setOptions(options);
    }

    public DonutChartModel getDonutModel() {
        return donutModel;
    }

    public void setDonutModel(DonutChartModel donutModel) {
        this.donutModel = donutModel;
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

    public List<OrdenEntity> getListInforme() {
        return listInforme;
    }

    public void setListInforme(List<OrdenEntity> listInforme) {
        this.listInforme = listInforme;
    }

    public List<OrdenEntity> getLisProductos() {
        return lisProductos;
    }

    public void setLisProductos(List<OrdenEntity> lisProductos) {
        this.lisProductos = lisProductos;
    }

    public BarChartModel getMixedModel() {
        return mixedModel;
    }

    public void setMixedModel(BarChartModel mixedModel) {
        this.mixedModel = mixedModel;
    }
}
