# language: pt
Funcionalidade: Soluções Cielo

  Contexto:
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup

  @Solucoes @HappyPath
  Esquema do Cenário: Navegar pelas Soluções <SubElemento>
    Quando eu destaco e clico no elemento "Soluções"
    E eu interajo com o sub-elemento "<SubElemento>"
    Então eu tiro um screenshot do resultado "Resultado"

    Exemplos:
      | SubElemento            |
      | Escolha como Receber   |
      | Conversor de Moedas    |
      | Minha Conta Cielo      |
      | Cielo Store            |
      | Inteligência de Mercado|
      | Cielo Conciliador      |

  @Solucoes @Conteudo
  Cenário: Validar Home da Página Soluções
    Quando eu destaco e clico no elemento "Soluções"
    # Clicar novamente em "Soluções" ou um link "Ver todos" se o menu for apenas hover
    # Assumindo que o link principal "Soluções" é clicável e leva à home da seção
    E eu clico no botão "Soluções" ou equivalente
    Então eu vejo o elemento "Título Soluções"
    E eu vejo o elemento "Para Vender"
    E eu vejo o elemento "Para Gerir"
