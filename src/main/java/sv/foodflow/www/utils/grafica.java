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
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(205, 97, 85)");
        bgColors.add("rgb(241, 148, 138)");
        bgColors.add("rgb(175, 122, 197)");
        bgColors.add("rgb(142, 68, 173)");
        bgColors.add("rgb(84, 153, 199)");
        bgColors.add("rgb(133, 193, 233)");
        bgColors.add("rgb(22, 160, 133)");
        bgColors.add("rgb(72, 201, 176)");
        bgColors.add("rgb(82, 190, 128)");
        bgColors.add("rgb(244, 208, 63)");
        bgColors.add("rgb(243, 156, 18)");
        bgColors.add("rgb(220, 118, 51)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        data.setLabels(labels);

        donutModel.setData(data);
    }

    public void createMixedModel() {
        mixedModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet dataSet = new BarChartDataSet();
        dataSet.setData(values);
        dataSet.setLabel("Ganancia $");
        dataSet.setBorderColor("rgb(255, 99, 132)");
        dataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");

        data.addChartDataSet(dataSet);
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
