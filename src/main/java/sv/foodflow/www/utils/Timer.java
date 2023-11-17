package sv.foodflow.www.utils;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Named
@RequestScoped
public class Timer implements Serializable {
    private int tiempoRestante;

    @PostConstruct
    public void init() {
        tiempoRestante = 300; // 5 minutos en segundos

        // Configurar un ScheduledExecutorService que disminuirá el tiempo restante cada segundo
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            if (tiempoRestante > 0) {
                tiempoRestante--;
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    public int getTiempoRestante() {
        return tiempoRestante;
    }

    public void setTiempoRestante(int tiempoRestante) {
        this.tiempoRestante = tiempoRestante;
    }
}
