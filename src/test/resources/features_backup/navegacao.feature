# language: pt
Funcionalidade: Navegação Geral

  Contexto:
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup

  @Navegacao @HappyPath
  Esquema do Cenário: Navegar pelos Menus Principais <SubElemento>
    Quando eu destaco e clico no elemento "<Elemento>"
    E eu interajo com o sub-elemento "<SubElemento>"
    Então eu tiro um screenshot do resultado "Resultado"

    Exemplos:
      | Elemento         | SubElemento   |
      | Login            | Criar conta   |
      | Seja Cielo       | Cadastre-se   |
      | Blog             | Artigos       |
      | Começar a vender | Ofertas       |
