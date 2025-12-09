
# language: pt
Funcionalidade: Contratação e Visualização de Maquininhas

  Contexto:
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup

  @Maquininhas @HappyPath
  Esquema do Cenário: Visualizar detalhes das Maquininhas <SubElemento>
    Quando eu destaco e clico no elemento "Maquininhas"
    E eu interajo com o sub-elemento "<SubElemento>"
    Então eu tiro um screenshot do resultado "Positivo"

    Exemplos:
      | SubElemento          |
      | Tap to Pay no iPhone |
      | Cielo ZIP            |
      | Cielo Flash          |
      | Cielo LIO On         |
      | Cielo Tap            |
      | Conheça todas        |
      | Cielo Aluguel        |

  @Maquininhas @HappyPath @Contratacao
  Esquema do Cenário: Contratar Maquininha com Sucesso <SubElemento>
    Quando eu destaco e clico no elemento "Maquininhas"
    E eu interajo com o sub-elemento "<SubElemento>"
    E eu clico no botão "Eu Quero"
    E eu preencho o CNPJ "<CNPJ>"
    E eu seleciono o checkbox de termos
    Então eu tiro um screenshot do resultado "Positivo"

    Exemplos:
      | SubElemento | CNPJ               |
      | Cielo ZIP   | 48.418.741/0001-13 |

  @Maquininhas @UnhappyPath
  Esquema do Cenário: Tentativa de Contratação com CNPJ Inválido
    Quando eu destaco e clico no elemento "Maquininhas"
    E eu interajo com o sub-elemento "<SubElemento>"
    E eu clico no botão "Eu Quero"
    E eu preencho o CNPJ "00.000.000/0000-00"
    E eu clico fora ou tab para validar
    Então eu vejo a mensagem de erro "CNPJ inválido"
    E eu tiro um screenshot do resultado "Erro_CNPJ"

    Exemplos:
      | SubElemento |
      | Cielo ZIP   |

  @Maquininhas @UnhappyPath
  Esquema do Cenário: Tentativa de Contratação sem Preencher Campos
    Quando eu destaco e clico no elemento "Maquininhas"
    E eu interajo com o sub-elemento "<SubElemento>"
    E eu clico no botão "Eu Quero"
    E eu clico no botão "Continuar" ou equivalente
    Então eu vejo a validação de campos obrigatórios
    E eu tiro um screenshot do resultado "Erro_CamposObrigatorios"

    Exemplos:
      | SubElemento |
      | Cielo ZIP   |
