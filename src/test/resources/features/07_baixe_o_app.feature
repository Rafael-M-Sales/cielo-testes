# language: pt
Funcionalidade: Links para Aplicativos Móveis

  Contexto:
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup

  @Apps @HappyPath @Redirection
  Esquema do Cenário: Validar Redirecionamento para Loja de Apps <SubElemento>
    Quando eu destaco e clico no elemento "Baixe o App"
    E eu interajo com o sub-elemento "<SubElemento>"
    Então eu sou redirecionado para a loja de aplicativos "<Loja>"
    E eu tiro um screenshot do resultado "Redirecionamento_App"

    Exemplos:
      | SubElemento | Loja        |
      | Google Play | google.com  |
      | App Store   | apple.com   |
