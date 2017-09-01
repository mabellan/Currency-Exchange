package edu.lasalle.pprog2.ac4.model;

/**
 * Created by miquelabellan on 26/4/17.
 */

public class CanviMoneda {

    private String lastUpdate;
    private String moneda;
    private double diners;
    private double diferencia;

    public CanviMoneda () {
        moneda = "";
        diners = 0;
        diferencia = 0;
    }
    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public double getDiners() {
        return diners;
    }

    public void setDiners(double diners) {
        this.diners = diners;
    }

    public double getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(double diferencia) {
        this.diferencia = diferencia;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
