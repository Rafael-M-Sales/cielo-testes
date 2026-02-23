---
name: cielo-testing-core
description: Core knowledge and specialized patterns for automating the Cielo website.
---

# Cielo Testing Core Skill

Esta Skill centraliza o conhecimento técnico e os padrões de automação específicos para o site da Cielo. Ela deve ser consultada para garantir consistência em seletores, lógica de passos e estratégias de recuperação de erros.

## 📋 Arquitetura de Referência

- **Framework**: Selenium WebDriver + Cucumber (JUnit 5 + JUnit Suite).
- **Linguagem**: Java 25 (JDK Atualizado).
- **Mapeamento**: Centralizado em `pages.CommonPage` e auxiliado por `utils.ElementHelper`.
- **Relatórios**: Allure Framework + Cucumber HTML Report.

## 🚀 Padrões de Automação

### Idioma e Nomenclatura
- **Step Definitions**: Devem ser escritos em **Português do Brasil (pt-BR)** conforme a regra `.agent/rules/language.md`.
- **Tags Cucumber**: Usar PascalCase (ex: `@HappyPath`, `@Maquininhas`).

### Manipulação de Elementos
- **Popups/Cookies**: Sempre verificar a presença do popup de cookies (`glassbox`) antes de interagir com o menu principal.
- **Destaque (Highlight)**: Usar o método `highlightElement` do `ElementHelper` em ambiente de demonstração para facilitar o rastreio visual.
- **Wait Strategy**: Preferir `WebDriverWait` (ExpectedConditions) com o timeout padrão de 10s definido em `config.properties`.

## 🛠️ Guia de Troubleshooting (Debugging Interativo)

1. **Timeout em "Maquininhas"**: O menu da Cielo é dinâmico. Se houver falha de clique, verifique se o elemento está coberto pelo popup de consentimento ou se o seletor ID/CSS mudou.
2. **Erros 404 em API**: Ignorar falhas de recursos secundários de tracking (`go-mpulse`, `adobe`) se a funcionalidade principal não for afetada.
3. **Execução Headless**: Em ambientes CI, garantir que o `config.properties` esteja com `headless=true`. Para debugging local, usar `headless=false`.

## 📂 Estrutura da Skill
- `scripts/`: Scripts utilitários para análise de logs e resultados.
- `examples/`: Exemplos de features e steps otimizados.
