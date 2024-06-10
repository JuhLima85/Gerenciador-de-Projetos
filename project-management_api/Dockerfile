# Etapa 1: Construir a imagem do builder
FROM openjdk:17-jdk-slim as builder

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR da aplicação para o diretório de trabalho
COPY target/projectmanagement-0.0.1-SNAPSHOT.jar /app/app.jar

# Etapa 2: Construir a imagem final
FROM openjdk:17-jdk-slim

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR da etapa de builder para o diretório de trabalho
COPY --from=builder /app/app.jar /app/app.jar

# Definir o comando de entrada para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Expor a porta 8080
EXPOSE 8080
