# language: pt
Funcionalidade: Soluções Cielo

  Contexto:
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup

  @Solucoes @HappyPath
  Esquema do Cenário: Visualizar Soluções <SubElemento>
    Quando eu destaco e clico no elemento "Soluções"
    E eu interajo com o sub-elemento "<SubElemento>"
    Então eu tiro um screenshot do resultado "Positivo"

    Exemplos:
      | SubElemento             |
      | Escolha como Receber    |
      | Conversor de Moedas     |
      | Pix                     |
      | Minha Conta Cielo       |
      | Farol                   |
      | Cielo Store             |
      | Inteligência de Mercado |
      | Cielo Conciliador       |
