# Imagen base de Java
FROM openjdk:17-jdk-alpine

# Copiar el archivo JAR de la aplicación
COPY target/themepark-0.0.1-SNAPSHOT.jar /app/app.jar

# Expone el puerto utilizado por la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación cuando se inicie el contenedor
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
