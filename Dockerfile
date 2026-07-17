# 1. ビルド用ステージ（コードをコンパイルしてjarファイルを作る）
FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app
COPY . .
RUN ./gradlew bootJar --no-daemon

# 2. 実行用ステージ（作られたjarファイルだけを動かす軽量な環境）
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /app/build/libs/*SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]