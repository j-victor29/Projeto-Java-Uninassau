# ♻️ PescaViva — Calculadora de Reciclagem de Tampinhas PET

![Java](https://img.shields.io/badge/Java-17+-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/GUI-Java%20Swing-F89820?style=for-the-badge&logo=java&logoColor=white)
![POO](https://img.shields.io/badge/Paradigma-POO-7C3AED?style=for-the-badge)
![Licença](https://img.shields.io/badge/Licença-Educacional-0EA5E9?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Concluído%20✓-10B981?style=for-the-badge)

> **Desafio — Reciclagem de Tampinhas PET.** Aplicativo desktop desenvolvido em Java com interface gráfica moderna (Dark Mode) que calcula projeções anuais de massa reciclável (kg) e retorno financeiro estimado (R$) a partir da coleta de tampinhas plásticas. O projeto promove a conscientização ecológica e demonstra boas práticas de programação orientada a objetos.

---

## 📋 Sumário

- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades](#-funcionalidades)
- [Regras de Negócio e Conversão](#-regras-de-negócio-e-conversão)
- [Arquitetura de Classes](#-arquitetura-de-classes)
- [Estrutura de Arquivos](#-estrutura-de-arquivos)
- [Fluxo de Funcionamento](#-fluxo-de-funcionamento)
- [Como Instalar e Executar](#️-como-instalar-e-executar)
- [Diferenciais Técnicos](#-diferenciais-técnicos)
- [Componentes do Grupo](#-componentes-do-grupo)

---

## 📖 Sobre o Projeto

O **PescaViva** é uma solução desktop desenvolvida como parte do **Desafio de Reciclagem de Tampinhas PET**, em um contexto acadêmico do curso de **Análise e Desenvolvimento de Sistemas (ADS)**.

O objetivo do software é permitir que residências, escolas, cooperativas ou qualquer pessoa insira a quantidade de tampinhas plásticas descartadas em um determinado período e obtenha instantaneamente uma **projeção anual** de:

1. 📦 **Massa total acumulada** — em quilogramas, com base em fatores de conversão oficiais de peso por unidade.
2. 💰 **Retorno financeiro potencial** — com base no preço de mercado por kg de plástico PET reciclado.

A aplicação foi projetada seguindo o princípio de **Separação de Responsabilidades (SoC)**: toda a lógica de cálculo reside em uma classe independente (`CalculadoraReciclagem`), completamente desacoplada da camada visual (`ReciclagemApp`), o que facilita manutenção, testes e evolução do código.

---

## ✅ Funcionalidades

| Recurso | Descrição |
|---------|-----------|
| 🔢 **Entrada por Categoria** | Campos separados para tampinhas de garrafas 2L, 1L e água mineral |
| 📅 **Frequência Customizável** | Seletor de período de coleta: Diário, Semanal, Mensal ou Anual |
| 📊 **Projeção Anual Automática** | Cálculo instantâneo da massa (kg) e valor financeiro (R$) ao longo de um ano |
| 🎨 **Interface Dark Mode** | Visual moderno com paleta Slate/Emerald/Cyan, cards com glow e bordas arredondadas |
| ✨ **Feedback Visual** | Efeito flash nos resultados após cada cálculo para confirmar a ação |
| 📝 **Placeholders Inteligentes** | Campos de entrada com exemplos que desaparecem ao clicar e reaparecem ao sair sem digitar |
| 🗑️ **Botão Limpar** | Reseta todos os campos e resultados ao estado inicial com um clique |
| ⌨️ **Atalho de Teclado** | Pressionar `Enter` em qualquer campo aciona automaticamente o cálculo |
| 🛡️ **Validação Robusta** | Proteção contra letras, caracteres especiais e valores negativos com alertas visuais |
| 💬 **Tooltips Informativos** | Dicas contextuais ao passar o mouse sobre cada campo de entrada |

---

## ⚖️ Regras de Negócio e Conversão

O sistema adota os seguintes padrões de conversão física e financeira:

| Tipo de Tampinha | Unidades por Kg | Fator |
|------------------|:--------------:|:-----:|
| Garrafas PET **1L e 2L** | 500 | `1 kg = 500 tampinhas` |
| Garrafas de **Água Mineral** | 1.000 | `1 kg = 1.000 tampinhas` |

| Parâmetro Financeiro | Valor |
|---------------------|:-----:|
| Preço por kg reciclado | **R$ 0,98** |

### Multiplicadores de Período

| Período | Multiplicador Anual | Descrição |
|---------|:-------------------:|-----------|
| Diário | × 365 | Valor diário projetado para 365 dias |
| Semanal | × 52 | Valor semanal projetado para 52 semanas |
| Mensal | × 12 | Valor mensal projetado para 12 meses |
| Anual | × 1 | Valor já representa o ano inteiro |

### Fórmula de Cálculo

```
pesoAnual = ((tampinhas2L + tampinhas1L) / 500 + tampinhasAgua / 1000) × multiplicadorPeriodo
valorAnual = pesoAnual × R$ 0,98
```

---

## 🏗️ Arquitetura de Classes

```
┌──────────────────────────────────┐
│         ReciclagemApp            │ ← Interface Gráfica (Swing)
│  ┌────────────────────────────┐  │
│  │  Campos de Entrada (3x)   │  │    Responsável por:
│  │  Seletor de Período       │  │    • Captura de dados do usuário
│  │  Botão Calcular / Limpar  │  │    • Validação visual e placeholders
│  │  Cards de Resultado (2x)  │  │    • Exibição formatada dos resultados
│  └─────────┬──────────────────┘  │    • Feedback visual (flash, hover, focus)
│            │ usa                  │
└────────────┼─────────────────────┘
             │
             ▼
┌──────────────────────────────────┐
│     CalculadoraReciclagem        │ ← Motor de Cálculo (Lógica Pura)
│  ┌────────────────────────────┐  │
│  │  Constantes de Conversão   │  │    Responsável por:
│  │  Enum PeriodoColeta        │  │    • Fatores de conversão (peso/valor)
│  │  calcularAnual()           │  │    • Multiplicadores temporais
│  │  Classe Resultado          │  │    • Cálculo matemático de projeção
│  └────────────────────────────┘  │    • Encapsulamento do resultado
└──────────────────────────────────┘
```

---

## 📁 Estrutura de Arquivos

```
projeto-java-alamyr/
│
├── CalculadoraReciclagem.java    # Lógica de negócio, constantes, enum e classe Resultado
├── ReciclagemApp.java            # Interface gráfica Swing, eventos e componentes visuais
└── README.md                     # Documentação completa do projeto
```

---

## 🔄 Fluxo de Funcionamento

```
┌─────────────────────────────────────────────────────┐
│                    USUÁRIO                           │
│                                                     │
│   1. Informa quantidade de tampinhas por categoria  │
│   2. Seleciona o período de coleta                  │
│   3. Clica em "Calcular Impacto" (ou pressiona ↵)  │
└────────────────────┬────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────┐
│              VALIDAÇÃO DE ENTRADA                    │
│                                                     │
│   • Verifica se são números inteiros válidos        │
│   • Rejeita valores negativos                       │
│   • Trata placeholders como valor zero              │
└────────────────────┬────────────────────────────────┘
                     │ ✅ Válido
                     ▼
┌─────────────────────────────────────────────────────┐
│             MOTOR DE CÁLCULO                         │
│                                                     │
│   pesoAnual  = (2L+1L)/500 + água/1000 × período   │
│   valorAnual = pesoAnual × R$ 0,98                  │
└────────────────────┬────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────┐
│             EXIBIÇÃO DE RESULTADOS                   │
│                                                     │
│   ⚖️ Card Massa:  "X,XXX kg"  (com flash visual)   │
│   💰 Card Valor:  "R$ X,XX"   (com flash visual)   │
└─────────────────────────────────────────────────────┘
```

---

## ⚙️ Como Instalar e Executar

### Pré-requisitos

| Ferramenta | Versão Mínima | Download |
|:----------:|:-------------:|:--------:|
| **JDK** | 17+ | [oracle.com/java](https://www.oracle.com/java/technologies/downloads/) |
| **Git** | qualquer | [git-scm.com](https://git-scm.com/downloads) |

> 💡 Verifique a instalação com `java -version` e `javac -version` no terminal.

### Passo a Passo

```bash
# 1. Clone o repositório
git clone https://github.com/j-victor29/projeto-java-alamyr.git
cd projeto-java-alamyr

# 2. Compile os arquivos-fonte
javac CalculadoraReciclagem.java ReciclagemApp.java

# 3. Execute a aplicação
java ReciclagemApp
```

> A janela do aplicativo abrirá automaticamente, centralizada na tela, com o tema escuro e pronta para uso.

---

## ⭐ Diferenciais Técnicos

| Diferencial | Descrição |
|:-----------:|-----------|
| 🏛️ **SoC (Separation of Concerns)** | Lógica de negócio 100% isolada da camada de apresentação — duas classes com responsabilidades distintas |
| 🔢 **Enums Estruturados** | `PeriodoColeta` encapsula multiplicadores e rótulos amigáveis com `toString()` customizado |
| 📦 **Resultado Imutável** | Classe `Resultado` com campos `final` e `toString()` para debug/logging |
| ⚡ **Validação em Duas Camadas** | Tratamento na UI (placeholder, foco) + `IllegalArgumentException` no motor de cálculo |
| 🎨 **Design System Consistente** | Paleta de cores, tipografia e espaçamentos definidos como constantes reutilizáveis |
| 🖌️ **Anti-Aliasing Global** | Renderização suave de fontes e bordas arredondadas com `Graphics2D` |
| ✨ **Micro-Interações** | Hover em botões, focus ring nos campos, flash nos resultados, tooltips contextuais |
| ⌨️ **Acessibilidade** | Atalho `Enter` para calcular, navegação por Tab entre campos, cursor pointer nos botões |
| 🧹 **Reset Completo** | Botão "Limpar" restaura todos os campos ao estado de placeholder original |
| 📐 **Borda Arredondada Custom** | Implementação própria de `AbstractBorder` com `RoundRectangle2D` e anti-aliasing |

---

## 👥 Componentes do Grupo

| Nome | Matrícula | Curso |
|:-----|:---------:|:-----:|
| **Eduardo Vinícius** | `01800450` | ADS |
| **Gabriel César** | `01813128` | ADS |
| **João Victor** | `01791125` | ADS |
| **Cauã Vital** | `01801792` | ADS |
| **Francisco Marques** | `01788403` | ADS |
| **Alex Ricardo** | `01822200` | ADS |

---

<p align="center">
  <em>Desenvolvido em 2026 para fins educacionais e ecológicos ♻️</em>
</p>