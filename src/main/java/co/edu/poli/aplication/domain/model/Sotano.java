package co.edu.poli.aplication.domain.model;

public class Sotano {
    private double areaM2;
    private String tipoUso;

    public Sotano() {
    }

    public Sotano(double areaM2, String tipoUso) {
        this.areaM2 = areaM2;
        this.tipoUso = tipoUso;
    }

    public double getAreaM2() {
        return areaM2;
    }

    public void setAreaM2(double areaM2) {
        this.areaM2 = areaM2;
    }

    public String getTipoUso() {
        return tipoUso;
    }

    public void setTipoUso(String tipoUso) {
        this.tipoUso = tipoUso;
    }
}
