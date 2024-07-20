
# API REST DICARO BANK

Esta API REST proporciona los servicios backend necesarios para la aplicación móvil de Dicaro Bank, incluyendo la autenticación, registro de usuarios, y operaciones bancarias.

## Comenzando 🚀
Estas instrucciones te permitirán obtener una copia de la API en funcionamiento en tu máquina local para propósitos de desarrollo y pruebas.

## Pre-requisitos 📋
Necesitarás las siguientes herramientas y entornos instalados en tu máquina:

- Java Development Kit (JDK) 21.0.2
- Docker

### Instalación de Docker:
Para instalar Docker en tu máquina, ejecuta los siguientes comandos:

#### Para sistemas Debian/Ubuntu
```bash
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io
```
#### Para sistemas MacOS
```bash
brew install --cask docker
```
#### Para sistemas Windows
Sigue las instrucciones en la [página oficial de Docker](https://docs.docker.com/docker-for-windows/install/) para instalar Docker Desktop.

## Instalación 🔧
Sigue estos pasos para configurar el entorno de desarrollo:

1. Clonar el repositorio del proyecto:
```bash
git clone https://github.com/dicarodev/API-Rest-DicaroBank.git
cd API-Rest-DicaroBank
```
2. Configuración del contenedor Docker:
   Descargar la imagen de Oracle:
```bash
docker pull gvenzl/oracle-xe:21-slim
```
Crear y correr el contenedor:
```bash
docker run --name dicaro-db -d -p 1521:1521 -e ORACLE_PASSWORD=toor gvenzl/oracle-xe:21-slim
```
3. Configurar la base de datos:
   Conéctate a la base de datos Oracle y ejecuta el script para crear el esquema:
```bash
CREATE USER DICAROBANK IDENTIFIED BY toor 
DEFAULT TABLESPACE USERS 
TEMPORARY TABLESPACE TEMPFILE 
QUOTA UNLIMITED ON USERS;
GRANT CREATE SESSION, CREATE TABLE, CREATE SEQUENCE TO DICAROBANK;
```
4. Inicialización de las Tablas de la Base de Datos:
   Al iniciar por primera vez la API, se crearán las tablas y una serie de usuarios con sus respectivas cuentas y datos de ejemplo. Las contraseñas de estos usuarios serán cifradas para mayor seguridad. 
    
    Estos usuarios se inicializan a través de la implementación de CommandLineRunner y los podrás encontrar en el ficher `DicaroBankApp.java`.

## Construido con 🛠️
- Spring Boot - Framework para el backend
- Lombok - Reducción del código boilerplate en Java
- Oracle - Base de datos relacional
- Docker - Contenerización del servidor de base de datos

## Authors

- [@dicarodev](https://www.github.com/dicarodev)