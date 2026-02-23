# language: pt
Funcionalidade: Seja Cielo

  Contexto:
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup

  @SejaCielo @HappyPath
  Esquema do Cenário: Navegar por Seja Cielo <SubElemento>
    Quando eu destaco e clico no elemento "Seja Cielo"
    E eu interajo com o sub-elemento "<SubElemento>"
    Então eu tiro um screenshot do resultado "Resultado"

    Exemplos:
      | SubElemento   |
      | Cadastre-se   |

  @SejaCielo @UnhappyPath
  Esquema do Cenário: Tentativa de Cadastro com CNPJ Inválido
    Quando eu destaco e clico no elemento "Seja Cielo"
    E eu interajo com o sub-elemento "Cadastre-se"
    E eu preencho o CNPJ "00.000.000/0000-00"
    E eu clico fora ou tab para validar
    Então eu vejo a validação de campos obrigatórios
    E eu tiro um screenshot do resultado "Erro_CNPJ"
