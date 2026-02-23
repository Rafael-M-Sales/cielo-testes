# cielo-testes
Automação com Java Selenium para o site Cielo.
Este projeto foi feito através da AI "Antigravity", tudo que havia necessidade para o início e ajustes do projeto foram feitos através de prompts na IDE desse ecossitema da Google.
Desde a descrição da construção inicial, até o pedido de subida do projeto- Java 17
- Maven
- Docker and Docker Compose (optional, for containerized execution)

## Execução com Docker

Este projeto está pronto para ser executado em containers.

### Utilizando o Docker Compose
O arquivo `docker-compose.yaml` levanta um nó Selenium Standalone Chrome e o container de testes.

```bash
docker-compose up --build --abort-on-container-exit --exit-code-from tests
```

### Variáveis de Ambiente
O código suporta as seguintes variáveis de ambiente (que podem ser passadas via terminal ou Jenkins):
- `SELENIUM_REMOTE_URL`: URL do Selenium Grid (ex: `http://localhost:4444/wd/hub`). Se definida, o projeto usará `RemoteWebDriver`.
- `browser`: Define o navegador (padrão: `chrome`).
- `headless`: `true` ou `false`.

## Relatórios
```
