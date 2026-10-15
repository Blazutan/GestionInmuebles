package co.edu.poli.aplication.domain.model;

import java.time.LocalDate;

/**
 * Representa una casa en el dominio inmobiliario.
 * <p>
 * Además de la información común de todo inmueble, una casa incluye la
 * cantidad de pisos y, opcionalmente, una referencia al sótano asociado.
 * </p>
 */
public class Casa extends Inmueble {
    private int pisos;
    private Sotano sotano;

    /**
     * Crea una casa vacía.
     */
    public Casa() {
    }

    /**
     * Crea una casa con sus datos principales y atributos específicos.
     *
     * @param chip identificador único de la casa
     * @param fechaCompra fecha de compra de la casa
     * @param direccion dirección de la casa
     * @param estado estado actual de la casa
     * @param propietario propietario asociado
     * @param pisos cantidad de pisos de la vivienda
     * @param sotano información del sótano, si existe
     */
    public Casa(String chip, LocalDate fechaCompra, String direccion, String estado,
                Propietario propietario, int pisos, Sotano sotano) {
        super(chip, fechaCompra, direccion, estado, propietario);
        this.pisos = pisos;
        this.sotano = sotano;
    }

    /**
     * Obtiene la cantidad de pisos de la casa.
     *
     * @return número de pisos
     */
    public int getPisos() {
        return pisos;
    }

    /**
     * Establece la cantidad de pisos de la casa.
     *
     * @param pisos nueva cantidad de pisos
     */
    public void setPisos(int pisos) {
        this.pisos = pisos;
    }

    /**
     * Obtiene la información del sótano asociado.
     *
     * @return sótano de la casa o {@code null} si no existe
     */
    public Sotano getSotano() {
        return sotano;
    }

    /**
     * Establece la información del sótano asociado.
     *
     * @param sotano nuevo sótano
     */
    public void setSotano(Sotano sotano) {
        this.sotano = sotano;
    }
}
