# language: pt
Funcionalidade: Cobertura da Homepage e Footer

  Contexto:
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup

  @Homepage @CTAs
  Esquema do Cenário: Validar CTAs da Homepage <Elemento>
    Quando eu clico no botão "<Elemento>"
    Então eu tiro um screenshot do resultado "Redirecionamento_CTA"

    Exemplos:
      | Elemento                 |
      | Maquininha no celular    |
      | Soluções personalizadas  |
      | CTA E-commerce           |
      | Começar a vender         |

  @Homepage @Footer
  Esquema do Cenário: Navegar pelo Footer <Link>
    Quando eu clico no botão "<Link>" ou equivalente
    Então eu tiro um screenshot do resultado "Resultado_Footer"

    Exemplos:
      | Link                    |
      | Produtos Cielo          |
      | Conheça a Cielo         |
      | Fale conosco            |
      | Trabalhe conosco        |
      | Portal de Desenvolvedores |
      | ICVA e Release          |
