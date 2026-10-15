package co.edu.poli.aplication.domain.model;

import java.time.LocalDate;

/**
 * Representa un inmueble base dentro del dominio de la aplicación.
 * <p>
 * Esta clase encapsula la información común a todos los tipos de propiedad
 * gestionados por el sistema, como el chip identificador, la fecha de compra,
 * la dirección, el estado y el propietario asociado.
 * </p>
 *
 * @author Copilot
 * @version 1.0
 */
public class Inmueble {
    private String chip;
    private LocalDate fechaCompra;
    private String direccion;
    private String estado;
    private Propietario propietario;

    /**
     * Crea una instancia vacía del inmueble.
     */
    public Inmueble() {
    }

    /**
     * Crea un inmueble con la información base.
     *
     * @param chip identificador único del inmueble
     * @param fechaCompra fecha en la que se adquirió el inmueble
     * @param direccion dirección física del inmueble
     * @param estado estado actual del inmueble
     * @param propietario dueño registrado del inmueble
     */
    public Inmueble(String chip, LocalDate fechaCompra, String direccion, String estado,
                    Propietario propietario) {
        this.chip = chip;
        this.fechaCompra = fechaCompra;
        this.direccion = direccion;
        this.estado = estado;
        this.propietario = propietario;
    }

    /**
     * Obtiene el identificador único del inmueble.
     *
     * @return chip del inmueble
     */
    public String getChip() {
        return chip;
    }

    /**
     * Establece el identificador único del inmueble.
     *
     * @param chip nuevo chip del inmueble
     */
    public void setChip(String chip) {
        this.chip = chip;
    }

    /**
     * Obtiene la fecha de compra del inmueble.
     *
     * @return fecha de compra o {@code null} si no está registrada
     */
    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    /**
     * Establece la fecha de compra del inmueble.
     *
     * @param fechaCompra fecha de compra
     */
    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    /**
     * Obtiene la dirección del inmueble.
     *
     * @return dirección física
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del inmueble.
     *
     * @param direccion nueva dirección
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el estado actual del inmueble.
     *
     * @return estado del inmueble
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado actual del inmueble.
     *
     * @param estado nuevo estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el propietario asociado al inmueble.
     *
     * @return propietario del inmueble
     */
    public Propietario getPropietario() {
        return propietario;
    }

    /**
     * Establece el propietario asociado al inmueble.
     *
     * @param propietario nuevo propietario
     */
    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }
}
