package co.edu.poli.aplication.domain.model;

import java.time.LocalDate;

public class Inmueble {
    private String chip;
    private LocalDate fechaCompra;
    private String direccion;
    private String estado;
    private Propietario propietario;

    public Inmueble() {
    }

    public Inmueble(String chip, LocalDate fechaCompra, String direccion, String estado,
                    Propietario propietario) {
        this.chip = chip;
        this.fechaCompra = fechaCompra;
        this.direccion = direccion;
        this.estado = estado;
        this.propietario = propietario;
    }

    public String getChip() {
        return chip;
    }

    public void setChip(String chip) {
        this.chip = chip;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }
}
