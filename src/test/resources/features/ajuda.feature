# language: pt
Funcionalidade: Central de Ajuda Cielo

  Contexto:
    Dado que eu acesso a página inicial da Cielo
    E eu fecho o popup

  @Ajuda @HappyPath
  Esquema do Cenário: Visualizar Canais de Ajuda <SubElemento>
    Quando eu destaco e clico no elemento "Ajuda"
    E eu interajo com o sub-elemento "<SubElemento>"
    Então eu tiro um screenshot do resultado "Positivo"

    Exemplos:
      | SubElemento               |
      | Acompanhar pedido         |
      | Central de atendimento    |
      | Atendimento pelo WhatsApp |
      | Atendimento em Libras     |
      | Status Cielo              |

  @Ajuda @Search
  Esquema do Cenário: Buscar na Central de Ajuda com Sucesso
    Quando eu destaco e clico no elemento "Ajuda"
    E eu realizo uma busca por "<Termo>"
    Então eu vejo resultados relacionados a "<Termo>"
    E eu tiro um screenshot do resultado "Busca_Sucesso"

    Exemplos:
      | Termo       |
      | Segunda via |
      | Cancelamento|

  @Ajuda @Search @UnhappyPath
  Esquema do Cenário: Buscar na Central de Ajuda sem Resultados
    Quando eu destaco e clico no elemento "Ajuda"
    E eu realizo uma busca por "xpto1234invalidterm"
    Então eu vejo a mensagem de nenhum resultado encontrado
    E eu tiro um screenshot do resultado "Busca_SemResultados"
