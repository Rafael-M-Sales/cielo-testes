FROM maven:3.9.6-eclipse-temurin-17-focal

WORKDIR /app

# Copiar arquivo de configuração de dependências
COPY pom.xml .

# Baixar dependências (para cachear esta camada se o pom não mudar)
RUN mvn dependency:go-offline

# Copiar código fonte
COPY src ./src

# Comando padrão para rodar os testes
ENTRYPOINT ["mvn", "test"]
