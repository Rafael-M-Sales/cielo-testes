# language: pt
Funcionalidade: Começar a Vender

  Contexto:
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup

  @ComecarAVender @HappyPath
  Esquema do Cenário: Visualizar Ofertas <SubElemento>
    Quando eu clico no botão "Começar a vender"
    E eu interajo com o sub-elemento "<SubElemento>"
    Então eu tiro um screenshot do resultado "Ofertas_Visualizadas"

    Exemplos:
      | SubElemento   |
      | Ofertas       |
