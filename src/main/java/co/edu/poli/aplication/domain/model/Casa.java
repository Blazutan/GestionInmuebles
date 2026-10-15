package co.edu.poli.aplication.domain.model;

import java.time.LocalDate;

public class Casa extends Inmueble {
    private int pisos;
    private Sotano sotano;

    public Casa() {
    }

    public Casa(String chip, LocalDate fechaCompra, String direccion, String estado,
                Propietario propietario, int pisos, Sotano sotano) {
        super(chip, fechaCompra, direccion, estado, propietario);
        this.pisos = pisos;
        this.sotano = sotano;
    }

    public int getPisos() {
        return pisos;
    }

    public void setPisos(int pisos) {
        this.pisos = pisos;
    }

    public Sotano getSotano() {
        return sotano;
    }

    public void setSotano(Sotano sotano) {
        this.sotano = sotano;
    }
}
