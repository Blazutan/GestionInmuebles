package co.edu.poli.aplication.domain.model;

/**
 * Representa la información específica del sótano de una casa.
 * <p>
 * Esta entidad almacena el área disponible en metros cuadrados y el uso
 * principal que se le da al espacio subterráneo.
 * </p>
 */
public class Sotano {
    private double areaM2;
    private String tipoUso;

    /**
     * Crea un sótano vacío.
     */
    public Sotano() {
    }

    /**
     * Crea un sótano con sus datos principales.
     *
     * @param areaM2 área en metros cuadrados del sótano
     * @param tipoUso uso principal del sótano
     */
    public Sotano(double areaM2, String tipoUso) {
        this.areaM2 = areaM2;
        this.tipoUso = tipoUso;
    }

    /**
     * Obtiene el área del sótano en metros cuadrados.
     *
     * @return área del sótano
     */
    public double getAreaM2() {
        return areaM2;
    }

    /**
     * Establece el área del sótano.
     *
     * @param areaM2 nueva área en metros cuadrados
     */
    public void setAreaM2(double areaM2) {
        this.areaM2 = areaM2;
    }

    /**
     * Obtiene el uso principal del sótano.
     *
     * @return tipo de uso
     */
    public String getTipoUso() {
        return tipoUso;
    }

    /**
     * Establece el uso principal del sótano.
     *
     * @param tipoUso nuevo tipo de uso
     */
    public void setTipoUso(String tipoUso) {
        this.tipoUso = tipoUso;
    }
}
