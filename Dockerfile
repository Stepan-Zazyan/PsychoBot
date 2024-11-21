# Этап 1: сборка проекта с использованием OpenJDK 19 и Maven
FROM openjdk:19-jdk-slim AS build

# Устанавливаем Maven
RUN apt-get update && apt-get install -y wget unzip && \
    wget https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz && \
    tar -xzvf apache-maven-3.9.6-bin.tar.gz -C /opt && \
    ln -s /opt/apache-maven-3.9.6/bin/mvn /usr/local/bin/mvn

WORKDIR /app
COPY . .
RUN mvn clean package

# Проверим, что файл существует в каталоге /app/target
RUN ls -l /app/target

# Этап 2: запуск приложения с использованием OpenJDK 19
FROM openjdk:19-jdk-slim
WORKDIR /app

# Копируем JAR-файл (укажите правильное имя)
COPY --from=build /app/target/PsychoBot-0.0.1-SNAPSHOT.jar app.jar

# Убедимся, что файл скопирован и доступен
RUN ls -l /app

# Открываем порт 10000
EXPOSE 10000

ENTRYPOINT ["java", "-jar", "/app/app.jar"]