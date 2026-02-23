# language: pt
Funcionalidade: Ajuda Cielo

  Contexto:
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup

  @Ajuda @HappyPath
  Esquema do Cenário: Navegar pela Ajuda <SubElemento>
    Quando eu destaco e clico no elemento "Ajuda"
    E eu interajo com o sub-elemento "<SubElemento>"
    Então eu tiro um screenshot do resultado "Resultado"

    Exemplos:
      | SubElemento             |
      | Atendimento pelo WhatsApp |
      | Atendimento em Libras   |
      | Status Cielo            |
      | Central de atendimento  |
      | Dúvidas frequentes      |
      | Acompanhar pedido       |

  @Ajuda @Busca
  Cenário: Realizar busca na Central de Ajuda
    Quando eu destaco e clico no elemento "Ajuda"
    E eu realizo uma busca por "Taxas"
    Então eu vejo resultados relacionados a "Taxas"
