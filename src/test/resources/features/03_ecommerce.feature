# language: pt
Funcionalidade: E-commerce Cielo

  Contexto:
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup

  @Ecommerce @HappyPath
  Esquema do Cenário: Navegar pelo E-commerce <SubElemento>
    Quando eu destaco e clico no elemento "E-commerce"
    E eu interajo com o sub-elemento "<SubElemento>"
    Então eu tiro um screenshot do resultado "Resultado"

    Exemplos:
      | SubElemento          |
      | Cielo Checkout       |
      | Split de Pagamento   |
      | Gestão de Risco      |
      | Gateway de Pagamento |
      | API E-commerce       |

  @Ecommerce @Conteudo
  Cenário: Validar Home da Página E-commerce
    Quando eu destaco e clico no elemento "E-commerce"
    E eu clico no botão "E-commerce" ou equivalente
    Então eu vejo o elemento "Título E-commerce"
    E eu vejo o elemento "Cielo Checkout" |
