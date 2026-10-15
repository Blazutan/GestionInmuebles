package co.edu.poli.aplication.domain.model;

import java.time.LocalDate;

public class Apartamento extends Inmueble {
    private int numeroPiso;

    public Apartamento() {
    }

    public Apartamento(String chip, LocalDate fechaCompra, String direccion, String estado,
                       Propietario propietario, int numeroPiso) {
        super(chip, fechaCompra, direccion, estado, propietario);
        this.numeroPiso = numeroPiso;
    }

    public int getNumeroPiso() {
        return numeroPiso;
    }

    public void setNumeroPiso(int numeroPiso) {
        this.numeroPiso = numeroPiso;
    }
}
