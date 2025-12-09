# language: pt
Funcionalidade: Seja Cielo

  Contexto:
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup

  @SejaCielo @HappyPath
  Esquema do Cenário: Navegar por Seja Cielo <SubElemento>
    Quando eu destaco e clico no elemento "Seja Cielo"
    E eu interajo com o sub-elemento "<SubElemento>"
    Então eu tiro um screenshot do resultado "Resultado"

    Exemplos:
      | SubElemento   |
      | Cadastre-se   |
