package co.edu.poli.aplication.domain.model;

import java.time.LocalDate;

/**
 * Representa un apartamento dentro del dominio inmobiliario.
 * <p>
 * Un apartamento comparte la información básica de un inmueble y agrega la
 * referencia al piso en el que se encuentra la unidad.
 * </p>
 */
public class Apartamento extends Inmueble {
    private int numeroPiso;

    /**
     * Crea un apartamento vacío.
     */
    public Apartamento() {
    }

    /**
     * Crea un apartamento con sus datos principales y el número de piso.
     *
     * @param chip identificador único del apartamento
     * @param fechaCompra fecha de compra del apartamento
     * @param direccion dirección del apartamento
     * @param estado estado actual del apartamento
     * @param propietario propietario asociado
     * @param numeroPiso piso en el que se ubica el apartamento
     */
    public Apartamento(String chip, LocalDate fechaCompra, String direccion, String estado,
                       Propietario propietario, int numeroPiso) {
        super(chip, fechaCompra, direccion, estado, propietario);
        this.numeroPiso = numeroPiso;
    }

    /**
     * Obtiene el número de piso del apartamento.
     *
     * @return número del piso
     */
    public int getNumeroPiso() {
        return numeroPiso;
    }

    /**
     * Establece el número de piso del apartamento.
     *
     * @param numeroPiso nuevo número de piso
     */
    public void setNumeroPiso(int numeroPiso) {
        this.numeroPiso = numeroPiso;
    }
}
