# language: pt
Funcionalidade: Começar a Vender

  Contexto:
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup

  @ComecarVender @HappyPath
  Esquema do Cenário: Navegar por Começar a Vender <SubElemento>
    Quando eu destaco e clico no elemento "Começar a vender"
    E eu interajo com o sub-elemento "<SubElemento>"
    Então eu tiro um screenshot do resultado "Resultado"

    Exemplos:
      | SubElemento   |
      | Ofertas       |
