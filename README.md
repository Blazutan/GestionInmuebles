# Gestión de Inmuebles

Sistema de gestión de inmuebles desarrollado en Java con JavaFX y MySQL. La aplicación permite registrar, consultar, actualizar y eliminar propiedades, así como asociarlas con sus propietarios y manejar información específica de cada tipo de inmueble.

## Descripción del proyecto

Este proyecto implementa un CRUD para gestionar inmuebles con una estructura basada en el modelo de dominio y una capa de infraestructura para acceso a datos.

Incluye soporte para:

- Casas
- Apartamentos
- Propietarios
- Búsqueda por CHIP
- Registro y actualización de datos específicos de cada tipo de inmueble
- Persistencia en base de datos MySQL

La aplicación presenta una interfaz gráfica con JavaFX y está organizada siguiendo una arquitectura orientada a casos de uso.

## Tecnologías utilizadas

- Java 11
- Maven
- JavaFX 17
- MySQL 8
- dotenv-java

## Arquitectura

El proyecto está estructurado por capas:

- `co.edu.poli.aplication.domain.model` — entidades del dominio
- `co.edu.poli.aplication.port.in` — casos de uso de entrada
- `co.edu.poli.aplication.service` — implementación de los servicios
- `co.edu.poli.infraestructure.persistence` — acceso a la base de datos
- `co.edu.poli.infraestructure.ui` — controladores y vista de la interfaz
- `co.edu.poli.vista` — punto de entrada de la aplicación

## Requisitos previos

Antes de ejecutar el proyecto asegúrate de tener instalado:

- JDK 11 o superior
- Maven
- MySQL Server
- Git

## Configuración de la base de datos

1. Crear la base de datos en MySQL.
2. Ejecutar el script SQL ubicado en:

   `src/main/resources/db/schema.sql`

3. Asegúrate de que exista una configuración de conexión con las siguientes variables de entorno en un archivo `.env` ubicado en la raíz del proyecto:

```env
DB_URL=jdbc:mysql://localhost:3306/gestion_inmuebles?useSSL=false&serverTimezone=UTC
DB_USER=tu_usuario
DB_PASSWORD=tu_password
```

> La aplicación utiliza `dotenv-java` para leer este archivo automáticamente.

## Ejecución

Desde la raíz del proyecto, ejecuta:

```bash
mvn clean compile
mvn javafx:run
```

También puedes ejecutar la clase principal desde tu IDE:

```text
co.edu.poli.vista.InmuebleApplication
```

## Funcionalidades principales

- Crear un inmueble
- Consultar todos los inmuebles
- Buscar un inmueble por CHIP
- Actualizar datos de un inmueble
- Eliminar un inmueble
- Registrar información del propietario asociado
- Administrar tipos de inmueble con atributos específicos

## Estructura del proyecto

```text
GestionInmuebles/
├── pom.xml
├── README.md
├── .env
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── co/edu/poli/
│   │   ├── resources/
│   │   │   ├── co/edu/poli/infraestructure/ui/InmuebleView.fxml
│   │   │   └── db/schema.sql
│   └── test/
└── target/
```

## Nota

Este proyecto está pensado como una aplicación de gestión académica y funcional para manejo de inmuebles, con enfoque en buen diseño de dominio y separación de responsabilidades.

## Autor

Proyecto desarrollado en Java para gestión de inmuebles con arquitectura orientada a casos de uso.
