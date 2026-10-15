package co.edu.poli.aplication.domain.model;

/**
 * Representa al propietario de uno o varios inmuebles.
 * <p>
 * La entidad registra la identificación, nombre y correo del titular de la
 * propiedad para asociarlo a cada inmueble del sistema.
 * </p>
 */
public class Propietario {
    private int identificacion;
    private String nombre;
    private String correo;

    /**
     * Crea un propietario vacío.
     */
    public Propietario() {
    }

    /**
     * Crea un propietario con su información personal.
     *
     * @param identificacion número de identificación del propietario
     * @param nombre nombre completo del propietario
     * @param correo correo electrónico de contacto
     */
    public Propietario(int identificacion, String nombre, String correo) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.correo = correo;
    }

    /**
     * Obtiene la identificación del propietario.
     *
     * @return número de identificación
     */
    public int getIdentificacion() {
        return identificacion;
    }

    /**
     * Establece la identificación del propietario.
     *
     * @param identificacion nueva identificación
     */
    public void setIdentificacion(int identificacion) {
        this.identificacion = identificacion;
    }

    /**
     * Obtiene el nombre del propietario.
     *
     * @return nombre completo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del propietario.
     *
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el correo electrónico del propietario.
     *
     * @return correo electrónico
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del propietario.
     *
     * @param correo nuevo correo electrónico
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
