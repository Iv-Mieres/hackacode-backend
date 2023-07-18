`Versión 0.0.1` <div align="left"> [![CodeFactor](https://www.codefactor.io/repository/github/iv-mieres/hackacode-backend/badge)](https://www.codefactor.io/repository/github/iv-mieres/hackacode-backend) </div>

<div align="center">
    <img width="50%" src="https://github.com/Iv-Mieres/hackacode-backend/assets/103857812/ba9afcde-567d-4fa1-838f-2350b4381b76.png"</img>
</div>


***[La Hackacode](https://hackacode.todocodeacademy.com/) es un evento organizado por [Todo Code Academy](https://todocodeacademy.com/) con la finalidad de ayudar en la creación de proyectos prácticos a programadores en formación o en búsqueda laboral activa, sea tanto para la creación de un portfolio como para la puesta en práctica de los conceptos aprendidos.***


<div align="center">
    <img width="30%" src="https://github.com/Iv-Mieres/hackacode-backend/assets/103857812/d916800f-8592-499a-8207-699b47bbe873.png"</img>
</div>

***Fantasy Kingdom es un sistema empresarial para el registro de clientes, empleados y ventas, basado en un parque de diversiones. Cuenta además con un registro de juegos junto con un sistema de estadisticas sobre las ganancias de las ventas de entradas y la actividad de los clientes.***


  # Información Técnica del proyecto
  
  ### <sub> - Tecnologías aplicadas - </sub>
  
  - Java 17
  - Spring Boot 3
  - Maven
  - Spring Security/JWT
  - JPA/Hibernate
  - Java Mail
  - Sping Validation
  - JUnit/Mockito
  - MySql

  ### <sub> - Herramientas utilizadas - </sub>

  - IntelliJ Idea
  - Postman
  - Swagger
  - Docker

### <sub> - Servicio Cloud - </sub>
  - DonWeb

  ## Diagrama UML 
  <div align="left">
    <img width="40%" src="https://github.com/Iv-Mieres/hackacode-backend/assets/103857812/5d69fd5e-5998-464b-8968-7abb0dd26d8e.png"</img>
</div> 

## Tests Unitarios: JUnit/Mockito
 >Los tests unitarios se realizaron sobre la capa service utilizado mocks para la simulación del comportamiento de la persistencia, intentando abarcar la mayoria de métodos y escenarios posibles.
> Este proyecto no cuenta con test de integración aplicado con JUnit/Mockito. Todas las pruebas de integración se hicieron de forma manual desde postman y la interfaz gráfica.

 <p align="left">
  <img src=https://github.com/Iv-Mieres/hackacode-backend/assets/103857812/422e2a7b-2459-486d-b03c-476d1924d737.gif alt="Descripción del GIF" width="800" height="250" />
</p>

 <p align="left">
  <img src=https://github.com/Iv-Mieres/hackacode-backend/assets/103857812/a293074d-9577-42bb-8b6e-2b4efe936b5d.png alt="Descripción del GIF" width="750" height="460" />
</p>

## Docker
 >Se utilizó Docker para la creación de contenedores con `docker-compose` y realizar el despliegue al servicio cloud de DonWeb con `Java` `Angular` `MySQL`

## Comentarios finales
El objetivo principal del back y del proyecto general fue lograr construir un sistema que tuviera la mayor robustes posible. Intentando mantener los posibles errores al mínimo, incluso aplicando válidaciones propias de Spring como lógicas sobre el código. 
 Las posibles excepciones que pudiera arrojar el programa fueron capturadas y controladas de forma global con `@RestControllerAdvice` y `@ExceptionHandler`. De esta forma se logró mantener cierta personalización a la hora de enviar los statuscode y mensajes de errores al cliente. 
 Por ultimo, tambien se pensó en la seguridad de la aplicación y por eso se decidió implementar `Spring Security/JWT` con el cual se puede gestionar un sistema de roles para la autenticación y autorización de los diferentes endpoints del sistema. Encriptando información sensible como contraseñas con `PasswordEncoder` 

 

## Agradecimientos

Primeramente a Todo Code Academy por habernos permitido participar de este evento para mejorar cada día nuestras habilidades técnicas y blandas y segundo a mis compañeros de equipo que se desvelaron cada día para que este proyecto salga adelante. Estoy muy orgulloso de todo lo que hicimos y sé que mis compañeros tambien, asi que muchas gracias.

## Equipo de desarrollo : DriftTeam

- Federico Silva ([@federicosilva](https://github.com/federico42o))
- David Thomen ([@davidthomen](https://github.com/DavidLG89))
- Ivan Mieres 
