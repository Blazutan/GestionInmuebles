# Gestión de Inmuebles

## Descripción del proyecto

Este proyecto es una aplicación de escritorio para gestionar propiedades inmobiliarias. Permite registrar, consultar, actualizar y eliminar inmuebles, así como asociar la información del propietario y manejar datos específicos según el tipo de inmueble.

La aplicación está desarrollada en Java usando JavaFX para la interfaz gráfica y MySQL como motor de persistencia. El proyecto sigue una arquitectura orientada al dominio y casos de uso, separando la lógica de negocio de la infraestructura.

## ¿Qué hace la aplicación?

La aplicación permite:

- Registrar viviendas, apartamentos y otros inmuebles.
- Guardar información del propietario asociado.
- Consultar todos los inmuebles registrados.
- Buscar un inmueble por su CHIP.
- Actualizar los datos de un inmueble existente.
- Eliminar propiedades del sistema.
- Administrar información específica como:
  - número de piso para apartamentos
  - cantidad de pisos para casas
  - área y tipo de uso del sótano

## Tecnologías utilizadas

- Java 11
- Maven
- JavaFX 17.0.10
- MySQL 8
- dotenv-java
- JDBC

## Arquitectura del proyecto

El proyecto está organizado por capas:

- `co.edu.poli.aplication.domain.model` — entidades del dominio (Inmueble, Casa, Apartamento, Propietario, Sotano)
- `co.edu.poli.aplication.port.in` — casos de uso de entrada
- `co.edu.poli.aplication.port.out` — puerto de salida para persistencia
- `co.edu.poli.aplication.service` — implementación de la lógica de negocio
- `co.edu.poli.infraestructure.persistence` — acceso a base de datos y conexiones JDBC
- `co.edu.poli.infraestructure.ui` — controladores de la interfaz gráfica
- `co.edu.poli.vista` — punto de entrada de la aplicación

## Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- JDK 11 o superior
- Maven
- MySQL Server
- Git
- IDE compatible (IntelliJ IDEA, Eclipse o VS Code con soporte Java)

## Instalación

1. Clona el repositorio:

```bash
git clone https://github.com/Blazutan/GestionInmuebles.git
cd GestionInmuebles
```

2. Asegúrate de tener Maven disponible en tu sistema.

3. Compila el proyecto:

```bash
mvn clean compile
```

## Configuración de la base de datos

1. Inicia MySQL y crea la base de datos `gestion_inmuebles`.
2. Ejecuta el script de creación de tablas ubicado en:

```text
src/main/resources/db/schema.sql
```

3. En la raíz del proyecto crea un archivo `.env` con la siguiente estructura:

```env
DB_URL=jdbc:mysql://localhost:3306/gestion_inmuebles?useSSL=false&serverTimezone=UTC
DB_USER=tu_usuario
DB_PASSWORD=tu_contraseña
```

> El proyecto usa `dotenv-java` para leer automáticamente estas variables desde el archivo `.env`.

### Ejemplo de configuración local

```env
DB_URL=jdbc:mysql://localhost:3306/gestion_inmuebles?useSSL=false&serverTimezone=UTC
DB_USER=root
DB_PASSWORD=12345
```

## Ejecutar la aplicación

Desde la raíz del proyecto, ejecuta:

```bash
mvn javafx:run
```

También puedes ejecutar la clase principal desde tu IDE:

```text
co.edu.poli.vista.InmuebleApplication
```

## Operaciones CRUD

La aplicación soporta las siguientes operaciones:

| Operación | Descripción |
|---|---|
| Create | Registrar un nuevo inmueble junto con sus datos del propietario |
| Read | Consultar todos los inmuebles o buscar uno por CHIP |
| Update | Modificar la información de un inmueble existente |
| Delete | Eliminar un inmueble del sistema |

## Funcionalidades principales

- Crear inmuebles
- Consultar inmuebles
- Buscar por CHIP
- Actualizar información
- Eliminar registros
- Manejar propiedades de tipo casa y apartamento
- Guardar metadatos del propietario y del inmueble

## Estructura del proyecto

```text
GestionInmuebles/
├── pom.xml
├── README.md
├── .env
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── co/
│   │   │       └── edu/
│   │   │           └── poli/
│   │   │               ├── aplication/
│   │   │               │   ├── domain/
│   │   │               │   ├── port/
│   │   │               │   └── service/
│   │   │               ├── infraestructure/
│   │   │               │   ├── persistence/
│   │   │               │   └── ui/
│   │   │               └── vista/
│   │   ├── resources/
│   │   │   ├── co/edu/poli/infraestructure/ui/InmuebleView.fxml
│   │   │   └── db/schema.sql
│   └── test/
│       └── java/
├── target/
└── .gitignore
```

## Observaciones

- El proyecto se basa en una separación clara entre dominio, casos de uso y adaptadores.
- La lógica de acceso a datos está encapsulada en repositorios JDBC.
- La interfaz gráfica está diseñada para facilitar la administración de inmuebles sin requerir conocimientos de SQL.

## Autor

Proyecto desarrollado en Java para la gestión inmobiliaria con enfoque en arquitectura por capas y persistencia relacional.
