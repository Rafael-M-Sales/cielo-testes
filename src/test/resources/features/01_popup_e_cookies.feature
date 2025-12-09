# language: pt
Funcionalidade: Smoke Test - Carregamento Inicial

  Esquema do Cenário: Interagir com Botão do Popup <Elemento>
    Dado que eu acesso a página inicial da Cielo
    Quando o popup de cookies ou configuração aparece
    Então eu destaco e clico no elemento "<Elemento>"
    E eu interajo com o sub-elemento "<SubElemento>"
    E eu tiro um screenshot do resultado "Positivo"

    Exemplos:
      | Elemento             | SubElemento          |
      | Configurar           | Salvar preferências  |
      | Recusar              |                      |
      | Aceitar tudo         |                      |
      | Aviso de privacidade | Voltar               |




