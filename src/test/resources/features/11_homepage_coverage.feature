# language: pt
@homepage
Funcionalidade: Validar elementos da Homepage
  Como um visitante do site da Cielo
  Quero acessar as principais ações e links da página inicial
  Para conhecer os produtos e serviços

  Contexto:
    Dado que eu acesso a página inicial da Cielo
    E o popup de cookies ou configuração aparece
    E eu fecho o popup

  Cenario: Validar CTAs principais da Homepage
    Quando eu destaco e clico no elemento "Maquininha no celular"
    Entao eu vejo resultados relacionados a "Maquininha"
    
  Cenario: Validar CTA Link de Pagamento
    Quando eu destaco e clico no elemento "Link de Pagamento"
    Entao eu tiro um screenshot do resultado "Pagina_Link_Pagamento"

  Cenario: Validar CTA E-commerce
    Quando eu destaco e clico no elemento "CTA E-commerce"
    Entao eu tiro um screenshot do resultado "Pagina_Ecommerce_CTA"

  Cenario: Validar Links do Rodapé - Produtos Cielo
    Quando eu destaco e clico no elemento "Produtos Cielo"
    Entao eu tiro um screenshot do resultado "Footer_Produtos_Cielo"

  Cenario: Validar Links do Rodapé - Desenvolvedores
    Quando eu destaco e clico no elemento "Portal de Desenvolvedores"
    # Portais externos podem abrir nova aba, CieloSteps lida com isso se configurado, 
    # mas por padrão 'destaco e clico' não muda foco automaticamente a menos que seja sub-elemento
    # Vamos usar 'eu interajo com o sub-elemento' ou apenas validar o clique se for mesma janela
    Entao eu tiro um screenshot do resultado "Footer_Devs"

  Cenario: Validar Links do Rodapé - Trabalhe Conosco
    Quando eu destaco e clico no elemento "Trabalhe conosco"
    Entao eu tiro um screenshot do resultado "Footer_Trabalhe_Conosco"
