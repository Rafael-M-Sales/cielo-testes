# language: pt
Funcionalidade: Login Cielo

  Contexto:
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup

  @Login @HappyPath
  Esquema do Cenário: Validar Acesso ao Login <SubElemento>
    Quando eu destaco e clico no elemento "Login"
    E eu interajo com o sub-elemento "<SubElemento>"
    Então eu sou redirecionado para a loja de aplicativos "cielo.com.br"
    E eu tiro um screenshot do resultado "Pagina_Login"

    Exemplos:
      | SubElemento         |
      | Minha Conta Cielo   |
