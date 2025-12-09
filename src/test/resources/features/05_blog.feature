# language: pt
Funcionalidade: Blog Cielo

  Contexto:
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup

  @Blog @HappyPath
  Esquema do Cenário: Navegar pelo Blog <SubElemento>
    Quando eu destaco e clico no elemento "Blog"
    E eu interajo com o sub-elemento "<SubElemento>"
    Então eu tiro um screenshot do resultado "Resultado"

    Exemplos:
      | SubElemento   |
      | Artigos       |
