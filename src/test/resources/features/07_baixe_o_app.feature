# language: pt
Funcionalidade: Baixe o App Cielo

  Contexto:
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup

  @Apps @HappyPath
  Esquema do Cenário: Redirecionamento para Lojas de App <Loja>
    Quando eu destaco e clico no elemento "Baixe o App"
    E eu interajo com o sub-elemento "<Loja>"
    Então eu sou redirecionado para a loja de aplicativos "<UrlEsperada>"

    Exemplos:
      | Loja        | UrlEsperada     |
      | App Store   | apple.com       |
      | Google Play | play.google.com |
