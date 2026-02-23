# language: pt
Funcionalidade: Maquininhas Cielo

  Contexto:
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup

  @Maquininhas @HappyPath
  Esquema do Cenário: Navegar pelas Maquininhas <SubElemento>
    Quando eu destaco e clico no elemento "Maquininhas"
    E eu interajo com o sub-elemento "<SubElemento>"
    Então eu tiro um screenshot do resultado "Resultado"

    Exemplos:
      | SubElemento          |
      | Cielo LIO            |
      | Cielo LIO On         |
      | Cielo ZIP            |
      | Cielo Flash          |
      | Tap to Pay no iPhone |
      | Cielo Tap            |
      | Conheça todas        |
      | Cielo Aluguel        |

  @Maquininhas @Conteudo
  Cenário: Validar Home da Página Maquininhas
    Quando eu destaco e clico no elemento "Maquininhas"
    E eu clico no botão "Conheça todas" ou equivalente
    Então eu vejo o elemento "Banner Maquininhas"
    E eu vejo o elemento "Taxas" |
