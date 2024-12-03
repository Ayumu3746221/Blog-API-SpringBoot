# ベースイメージとして公式のMavenイメージを使用
FROM eclipse-temurin:21-jdk as build

# 作業ディレクトリを設定
WORKDIR /app

# Mavenの依存関係をキャッシュするために、まずpom.xmlとmaven-wrapper.propertiesをコピー
COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./

# 依存関係をダウンロード
RUN ./mvnw dependency:go-offline

# ソースコードをコピー
COPY src ./src

# アプリケーションをビルド
RUN ./mvnw clean package -DskipTests

# 実行ステージ
FROM openjdk:21-jdk-slim

# 作業ディレクトリを設定
WORKDIR /app

# ビルドステージからJARファイルをコピー
COPY --from=build /app/target/*.jar app.jar

# ポートを公開
EXPOSE 8080

# アプリケーションを実行
ENTRYPOINT ["java", "-jar", "app.jar"]