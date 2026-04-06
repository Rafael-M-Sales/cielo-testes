# language: pt
Funcionalidade: Cobertura Total de Elementos - Screen Scanning

  Contexto:
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup

  @Smoke @Mapping
  Cenário: Validar integridade visual e funcional de todos os elementos constantes
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup
    Então eu verifico todos os elementos da tela de "Página Inicial"
    
    Quando eu destaco e clico no elemento "Maquininhas"
    Então eu verifico todos os elementos da tela de "Maquininhas"
    
    Quando eu destaco e clico no elemento "E-commerce"
    Então eu verifico todos os elementos da tela de "E-commerce"

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
