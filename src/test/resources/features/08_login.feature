# language: pt
Funcionalidade: Login Cielo

  Contexto:
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup

  @Login @HappyPath
  Esquema do Cenário: Navegar pelo Login <SubElemento>
    Quando eu destaco e clico no elemento "Login"
    E eu interajo com o sub-elemento "<SubElemento>"
    Então eu tiro um screenshot do resultado "Resultado"

    Exemplos:
      | SubElemento   |
      | Criar conta   |
