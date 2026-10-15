CREATE DATABASE IF NOT EXISTS gestion_inmuebles;
USE gestion_inmuebles;

CREATE TABLE IF NOT EXISTS propietario (
    identificacion INT PRIMARY KEY,
    nombre VARCHAR(150),
    correo VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS inmueble (
    chip VARCHAR(64) PRIMARY KEY,
    fecha_compra DATE,
    direccion VARCHAR(255),
    estado VARCHAR(50),
    tipo VARCHAR(20) NOT NULL,
    propietario_id INT NOT NULL,
    CONSTRAINT fk_inmueble_propietario
        FOREIGN KEY (propietario_id) REFERENCES propietario (identificacion)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS apartamento (
    inmueble_chip VARCHAR(64) PRIMARY KEY,
    numero_piso INT NOT NULL,
    CONSTRAINT fk_apartamento_inmueble
        FOREIGN KEY (inmueble_chip) REFERENCES inmueble (chip)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS casa (
    inmueble_chip VARCHAR(64) PRIMARY KEY,
    pisos INT NOT NULL,
    CONSTRAINT fk_casa_inmueble
        FOREIGN KEY (inmueble_chip) REFERENCES inmueble (chip)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS sotano (
    casa_chip VARCHAR(64) PRIMARY KEY,
    area_m2 DECIMAL(10, 2) NOT NULL,
    tipo_uso VARCHAR(100),
    CONSTRAINT fk_sotano_casa
        FOREIGN KEY (casa_chip) REFERENCES casa (inmueble_chip)
        ON DELETE CASCADE
);
