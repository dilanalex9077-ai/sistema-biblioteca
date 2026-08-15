# Sistema de Biblioteca

Aplicación de escritorio desarrollada en Java Swing para administrar libros, usuarios y préstamos de una biblioteca. Utiliza MySQL para la persistencia de datos y Maven para gestionar dependencias.

## Funcionalidades

- Registro, consulta, actualización y eliminación de libros.
- Administración de usuarios.
- Registro y seguimiento de préstamos.
- Creación automática de la base de datos y sus tablas.
- Interfaz gráfica de escritorio con Java Swing.

## Tecnologías

- Java 21
- Java Swing
- MySQL
- JDBC
- Maven

## Requisitos

- JDK 21 o superior.
- Maven 3.9 o superior.
- MySQL 8 o superior ejecutándose en `localhost:3306`.

## Configuración segura

La aplicación no guarda contraseñas dentro del código. Antes de ejecutarla, define las credenciales de MySQL en la sesión actual de PowerShell:

```powershell
$env:BIBLIOTECA_DB_USUARIO = "root"
$env:BIBLIOTECA_DB_PASSWORD = "tu_contraseña"
```

No agregues archivos `.env`, contraseñas ni credenciales al repositorio.

## Compilar y ejecutar

```powershell
mvn clean package
mvn exec:java -Dexec.mainClass="Main"
```

También puedes importar el proyecto en IntelliJ IDEA o Apache NetBeans y ejecutar `Main.java`.

## Estructura

- `conexion`: conexión e inicialización de MySQL.
- `dao`: operaciones de acceso a datos.
- `modelo`: entidades del dominio.
- `vista`: ventanas y componentes de la interfaz gráfica.

## Autor

Dilan Alejandro Martínez Mercado  
Universidad Tecnológica de Tula-Tepeji
