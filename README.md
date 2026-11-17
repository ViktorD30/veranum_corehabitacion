# Veranum Core Habitación

Microservicio encargado de la gestión de habitaciones del sistema hotelero **Veranum**.

Permite crear, consultar, actualizar, cambiar el estado y eliminar habitaciones mediante una API REST desarrollada con Spring Boot.

---

## Tecnologías utilizadas

- Java 17
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- Hibernate
- MySQL
- Jakarta Validation
- Maven
- Swagger / OpenAPI
- Postman
- Git / GitHub

---

## Arquitectura

El microservicio utiliza una arquitectura por capas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
JPA / Hibernate
    ↓
MySQL
```

Estructura principal:

```text
src/main/java/com/veranum/corehabitacion
│
├── controller
│   └── HabitacionController.java
│
├── dto
│   ├── ApiResponse.java
│   ├── EstadoHabitacionRequest.java
│   ├── HabitacionRequest.java
│   └── HabitacionResponse.java
│
├── exception
│   ├── BusinessException.java
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
│
├── model
│   └── Habitacion.java
│
├── repository
│   └── HabitacionRepository.java
│
├── service
│   └── HabitacionService.java
│
└── VeranumCorehabitacionApplication.java
```

---

## Puerto

El microservicio utiliza el puerto:

```text
8083
```

URL base:

```text
http://localhost:8083
```

---

## Base de datos

El microservicio utiliza una base de datos MySQL independiente:

```text
veranum_habitacion
```

Crear la base de datos:

```sql
CREATE DATABASE veranum_habitacion;
```

La tabla `habitaciones` es creada automáticamente por Hibernate mediante:

```properties
spring.jpa.hibernate.ddl-auto=update
```

---

## Configuración de MySQL

Archivo:

```text
src/main/resources/application.properties
```

Configuración principal:

```properties
spring.application.name=veranum_corehabitacion

server.port=8083

spring.datasource.url=jdbc:mysql://localhost:3306/veranum_habitacion?useSSL=false&serverTimezone=America/Santiago&allowPublicKeyRetrieval=true
spring.datasource.username=${DB_USER:root}
spring.datasource.password=${DB_PASSWORD:}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Si no se configuran variables de entorno, el proyecto utiliza:

```text
Usuario MySQL: root
Contraseña: vacía
```

---

## Modelo Habitacion

La entidad `Habitacion` contiene:

```text
id
numero
tipo
precioPorNoche
estado
```

Ejemplo:

```json
{
  "id": 1,
  "numero": "101",
  "tipo": "SIMPLE",
  "precioPorNoche": 45000,
  "estado": "DISPONIBLE"
}
```

---

## Tipos de habitación

El sistema admite los siguientes tipos:

```text
SIMPLE
DOBLE
FAMILIAR
```

En el sistema Veranum se consideran las siguientes capacidades:

```text
SIMPLE   → 1 huésped
DOBLE    → 2 huéspedes
FAMILIAR → 4 huéspedes
```

---

## Estados de habitación

Los estados permitidos son:

```text
DISPONIBLE
OCUPADA
MANTENIMIENTO
```

Las validaciones impiden registrar estados diferentes.

---

# Endpoints REST

URL base:

```text
http://localhost:8083/api/v1/habitaciones
```

## Crear habitación

```http
POST /api/v1/habitaciones
```

Ejemplo:

```json
{
  "numero": "304",
  "tipo": "FAMILIAR",
  "precioPorNoche": 95000,
  "estado": "DISPONIBLE"
}
```

Respuesta exitosa:

```text
201 Created
```

Ejemplo:

```json
{
  "codigo": 201,
  "mensaje": "Habitación creada correctamente",
  "error": false,
  "data": {
    "id": 4,
    "numero": "304",
    "tipo": "FAMILIAR",
    "precioPorNoche": 95000.0,
    "estado": "DISPONIBLE"
  }
}
```

---

## Listar habitaciones

```http
GET /api/v1/habitaciones
```

Respuesta:

```text
200 OK
```

---

## Buscar habitación por ID

```http
GET /api/v1/habitaciones/{id}
```

Ejemplo:

```text
GET /api/v1/habitaciones/2
```

Respuesta exitosa:

```text
200 OK
```

Si el ID no existe:

```text
404 Not Found
```

Ejemplo:

```json
{
  "codigo": 404,
  "mensaje": "Habitación no encontrada con ID: 3",
  "error": true,
  "data": null
}
```

---

## Listar habitaciones disponibles

```http
GET /api/v1/habitaciones/disponibles
```

Devuelve únicamente habitaciones cuyo estado sea:

```text
DISPONIBLE
```

---

## Actualizar habitación

```http
PUT /api/v1/habitaciones/{id}
```

Ejemplo:

```text
PUT /api/v1/habitaciones/2
```

Body:

```json
{
  "numero": "202",
  "tipo": "DOBLE",
  "precioPorNoche": 70000,
  "estado": "DISPONIBLE"
}
```

Respuesta:

```text
200 OK
```

---

## Cambiar estado

```http
PUT /api/v1/habitaciones/{id}/estado
```

Ejemplo:

```text
PUT /api/v1/habitaciones/2/estado
```

Body:

```json
{
  "estado": "MANTENIMIENTO"
}
```

Estados permitidos:

```text
DISPONIBLE
OCUPADA
MANTENIMIENTO
```

---

## Eliminar habitación

```http
DELETE /api/v1/habitaciones/{id}
```

Ejemplo:

```text
DELETE /api/v1/habitaciones/3
```

Respuesta:

```json
{
  "codigo": 200,
  "mensaje": "Habitación eliminada correctamente",
  "error": false,
  "data": null
}
```

---

# Validaciones

El microservicio valida:

- Número de habitación obligatorio.
- Número de habitación único.
- Tipo obligatorio.
- Tipo válido: `SIMPLE`, `DOBLE` o `FAMILIAR`.
- Precio por noche obligatorio.
- Precio mayor que cero.
- Estado obligatorio.
- Estado válido: `DISPONIBLE`, `OCUPADA` o `MANTENIMIENTO`.

Ejemplo de regla de negocio:

Si se intenta registrar nuevamente:

```text
numero = 101
```

el servidor responde:

```text
400 Bad Request
```

con:

```json
{
  "codigo": 400,
  "mensaje": "Ya existe una habitación con el número 101",
  "error": true,
  "data": null
}
```

---

# Formato de respuestas

La API utiliza una estructura estándar:

```json
{
  "codigo": 200,
  "mensaje": "Mensaje de respuesta",
  "error": false,
  "data": {}
}
```

Esto permite mantener respuestas uniformes entre los microservicios del sistema Veranum.

---

# Manejo de excepciones

El proyecto utiliza:

```text
BusinessException
ResourceNotFoundException
GlobalExceptionHandler
```

Principales respuestas HTTP:

```text
200 → operación correcta
201 → recurso creado
400 → validación o regla de negocio
404 → recurso no encontrado
500 → error interno
```

---

# Swagger / OpenAPI

Swagger se encuentra habilitado mediante Springdoc OpenAPI.

Interfaz:

```text
http://localhost:8083/swagger-ui.html
```

También puede accederse mediante:

```text
http://localhost:8083/swagger-ui/index.html
```

Especificación OpenAPI:

```text
http://localhost:8083/v3/api-docs
```

Swagger documenta los endpoints bajo:

```text
Gestión de Habitaciones
```

Incluyendo:

```text
Buscar habitación por ID
Actualizar habitación
Eliminar habitación
Cambiar estado de habitación
Listar habitaciones
Crear una habitación
Listar habitaciones disponibles
```

---

# Requisitos

Para ejecutar el proyecto se requiere:

```text
Java 17
Maven Wrapper
MySQL
Git
```

Comprobar Java:

```powershell
java -version
```

Debe mostrar Java 17.

---

# JAVA_HOME

Ejemplo utilizado durante el desarrollo:

```powershell
$env:JAVA_HOME = "C:\Users\Usuario\.jdks\ms-17.0.19"
$env:Path = "$env:JAVA_HOME\bin;" + $env:Path
```

Comprobar:

```powershell
$env:JAVA_HOME
java -version
```

---

# Clonar repositorio

```powershell
git clone https://github.com/ViktorD30/veranum_corehabitacion.git
```

Entrar al proyecto:

```powershell
cd veranum_corehabitacion
```

---

# Maven

Limpiar el proyecto:

```powershell
.\mvnw.cmd clean
```

Compilar:

```powershell
.\mvnw.cmd compile
```

Ejecutar pruebas:

```powershell
.\mvnw.cmd test
```

Generar JAR:

```powershell
.\mvnw.cmd package
```

Instalar en el repositorio Maven local:

```powershell
.\mvnw.cmd install
```

Durante las verificaciones del proyecto se obtuvo:

```text
Tests run: 1
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS
```

---

# Ejecutar con Spring Boot

```powershell
.\mvnw.cmd spring-boot:run
```

La aplicación debe iniciar en:

```text
http://localhost:8083
```

---

# Ejecutar el JAR

Después de:

```powershell
.\mvnw.cmd package
```

se genera:

```text
target/veranum_corehabitacion-0.0.1-SNAPSHOT.jar
```

Ejecutar:

```powershell
java -jar .\target\veranum_corehabitacion-0.0.1-SNAPSHOT.jar
```

La salida esperada incluye:

```text
Tomcat started on port 8083
Started VeranumCorehabitacionApplication
```

---

# Persistencia

La persistencia fue comprobada directamente en MySQL.

Ejemplo de registros:

```text
101 | SIMPLE   | 45000 | DISPONIBLE
202 | DOBLE    | 70000 | MANTENIMIENTO
304 | FAMILIAR | 95000 | DISPONIBLE
```

Las operaciones realizadas mediante la API se reflejan directamente en la tabla:

```text
habitaciones
```

---

# Pruebas realizadas con Postman

Se verificaron:

```text
POST   → 201 Created
GET    → 200 OK
GET ID → 200 OK
GET disponibles → 200 OK
PUT    → 200 OK
PUT estado → 200 OK
DELETE → 200 OK
GET ID inexistente → 404 Not Found
POST número duplicado → 400 Bad Request
```

---

# Estrategia Git

El proyecto utiliza las ramas:

```text
main
develop
feature/corehabitacion
```

Flujo:

```text
feature/corehabitacion
        ↓
     develop
        ↓
       main
```

Los cambios funcionales se desarrollan inicialmente en `feature/corehabitacion` antes de integrarse a las ramas superiores.

---

# Relación con Veranum Core

El sistema Veranum está compuesto por microservicios independientes:

```text
veranum_coreusuario      → puerto 8081
veranum_corereserva      → puerto 8082
veranum_corehabitacion   → puerto 8083
veranum_corepago         → puerto 8084
```

`veranum_corehabitacion` administra exclusivamente la información y estado de las habitaciones.

El microservicio de reservas puede consultar los datos de una habitación mediante API REST utilizando su identificador.

Ejemplo:

```text
veranum_corereserva
        │
        │ HTTP / REST
        ▼
veranum_corehabitacion
        │
        ▼
GET /api/v1/habitaciones/{id}
```

Esto permite mantener separadas las bases de datos y responsabilidades de cada microservicio.

---

# Autor

Proyecto académico desarrollado para el sistema hotelero **Veranum Core**.