# language: pt
Funcionalidade: Integração API Cielo - Saúde do Sistema

  @Smoke @Api
  Cenário: Consultar Endpoints Públicos da Cielo via API
    Dado que eu inicializo a requisição para a API da Cielo
    Quando eu disparo um GET para o endpoint de saúde do sistema
    Então eu recebo um status code 200
    E a mensagem de resposta deve ser válida
