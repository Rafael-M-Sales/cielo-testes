# language: pt
Funcionalidade: Interação com o Site da Cielo

  Esquema do Cenário: Interagir com Botão do Popup <Elemento>
    Dado que eu acesso a página inicial da Cielo
    Quando o popup de cookies ou configuração aparece
    Então eu destaco e clico no elemento "<Elemento>"
    E eu interajo com o sub-elemento "<SubElemento>"
    E eu tiro um screenshot do resultado "Positivo"

    Exemplos:
      | Elemento             | SubElemento          |
      | Configurar           | Salvar preferências  |
      | Recusar              |                      |
      | Aceitar tudo         |                      |
      | Aviso de privacidade | Voltar               |

  Esquema do Cenário: Interagir com Elemento da Página Inicial <Elemento>
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup
    Então eu destaco e clico no elemento "<Elemento>"
    E eu interajo com o sub-elemento "<SubElemento>"
    E eu tiro um screenshot do resultado "Positivo"

    Exemplos:
      | Elemento         | SubElemento          |
      | Maquininhas      | Tap to Pay no iPhone      |
      | Maquininhas      | Cielo ZIP                 |
      | Maquininhas      | Cielo Flash               |
      | Maquininhas      | Cielo LIO On              |
      | Maquininhas      | Cielo Tap                 |
      | Maquininhas      | Cielo Aluguel             |
      | E-commerce       | Link de Pagamento         |
      | E-commerce       | Cielo Checkout            |
      | E-commerce       | API E-commerce            |
      | E-commerce       | Split de Pagamento        |
      | E-commerce       | Gestão de Risco           |
      | E-commerce       | Gateway de Pagamento      |
      | Soluções         | Escolha como Receber      |
      | Soluções         | Conversor de Moedas       |
      | Soluções         | Pix                       |
      | Soluções         | Minha Conta Cielo         |
      | Soluções         | Farol                     |
      | Soluções         | Cielo Store               |
      | Soluções         | Inteligência de Mercado   |
      | Soluções         | Cielo Conciliador         |
      | Ajuda            | Acompanhar pedido         |
      | Ajuda            | Central de atendimento    |
      | Ajuda            | Atendimento pelo WhatsApp |
      | Ajuda            | Atendimento em Libras     |
      | Ajuda            | Status Cielo              |
      | Baixe o App      | Google Play               |
      | Baixe o App      | App Store                 |
      | Login            | Criar conta               |
      | Seja Cielo       | Cadastre-se               |
      | Blog             | Artigos                   |
      | Começar a vender | Ofertas                   |



  Esquema do Cenário: Contratar Maquininha <Elemento>
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup
    Quando eu destaco e clico no elemento "<Elemento>"
    E eu interajo com o sub-elemento "<SubElemento>"
    E eu clico no botão "Eu Quero"
    E eu preencho o CNPJ "<CNPJ>"
    E eu seleciono o checkbox de termos
    Então eu tiro um screenshot do resultado "Positivo"

    Exemplos:
      | Elemento    | SubElemento | CNPJ                 |
      | Maquininhas | Cielo ZIP   | 48.418.741/0001-13   |
