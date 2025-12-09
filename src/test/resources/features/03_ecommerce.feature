# language: pt
Funcionalidade: Soluções de E-commerce

  Contexto:
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup

  @Ecommerce @HappyPath
  Esquema do Cenário: Visualizar Soluções de E-commerce <SubElemento>
    Quando eu destaco e clico no elemento "E-commerce"
    E eu interajo com o sub-elemento "<SubElemento>"
    Então eu tiro um screenshot do resultado "Positivo"

    Exemplos:
      | SubElemento          |
      | Link de Pagamento    |
      | Cielo Checkout       |
      | API E-commerce       |
      | Split de Pagamento   |
      | Gestão de Risco      |
      | Gateway de Pagamento |

  @Ecommerce @HappyPath @Contratacao
  Esquema do Cenário: Contratar Solução E-commerce com Sucesso <SubElemento>
    Quando eu destaco e clico no elemento "E-commerce"
    E eu interajo com o sub-elemento "<SubElemento>"
    E eu clico no botão "Eu Quero" ou equivalente
    E eu preencho o CNPJ "<CNPJ>"
    E eu seleciono o checkbox de termos
    Então eu tiro um screenshot do resultado "Positivo"

    Exemplos:
      | SubElemento          | CNPJ               |
      | Link de Pagamento    | 48.418.741/0001-13 |
      | Cielo Checkout       | 48.418.741/0001-13 |

  @Ecommerce @UnhappyPath
  Esquema do Cenário: Tentativa de Contratação E-commerce com CNPJ Inválido
    Quando eu destaco e clico no elemento "E-commerce"
    E eu interajo com o sub-elemento "<SubElemento>"
    E eu clico no botão "Eu Quero" ou equivalente
    E eu preencho o CNPJ "00.000.000/0000-00"
    E eu clico fora ou tab para validar
    Então eu vejo a mensagem de erro "CNPJ inválido"
    E eu tiro um screenshot do resultado "Erro_CNPJ"

    Exemplos:
      | SubElemento       |
      | Link de Pagamento |

  @Ecommerce @UnhappyPath
  Esquema do Cenário: Tentativa de Contratação sem Preencher Campos
    Quando eu destaco e clico no elemento "E-commerce"
    E eu interajo com o sub-elemento "<SubElemento>"
    E eu clico no botão "Eu Quero" ou equivalente
    E eu clico no botão "Continuar" ou equivalente
    Então eu vejo a validação de campos obrigatórios
    E eu tiro um screenshot do resultado "Erro_CamposObrigatorios"

    Exemplos:
      | SubElemento       |
      | Link de Pagamento |
