# Backlog do Projeto

Este documento contém as histórias de usuário derivadas da modelagem de classes.

---

## História #01

**Título:** Cadastro de Produto Químico

> **Como** gestor de estoque, **desejo** poder cadastrar produtos químicos com nível de risco, **para** garantir que informações críticas estejam disponíveis e restrições sejam respeitadas.

**Critérios de Aceitação:**
- Deve ser possível registrar perigos à saúde.
- A classe deve permitir avaliar se o produto pode ou não ser restrito.

**Status:** Feito  
**Tipo:** Funcionalidade

<!-- <p align="center">
  <img src="link_para_imagem_01.png" alt="Cadastro Produto Químico">
</p> -->

---

## História #02

**Título:** Cadastro de Farmacêutico

> **Como** gestor de estoque, **desejo** cadastrar medicamentos com composição, tarja e se exigem receita, **para** controlar a venda e uso de medicamentos conforme legislação.

**Critérios de Aceitação:**
- Deve conter campos para tarja, composição, genérico, receita e retenção de receita.
- Deve permitir validar se o medicamento é restrito ou não.

**Status:** Feito  
**Tipo:** Funcionalidade

<!-- <p align="center">
  <img src="link.png" alt="Cadastro Farmacêutico">
</p> -->

---

## História #03

**Título:** Restringir ou Liberar Itens

> **Como** gestor, **desejo** restringir ou liberar um item conforme regras de segurança, **para** garantir que itens perigosos não estejam acessíveis indevidamente para clientes que não devam obtê-los.

**Critérios de Aceitação:**
- Um item deve poder ser marcado como restrito.
- Um item deve poder ser marcado como não restrito.

**Status:** Feito  
**Tipo:** Funcionalidade

<!-- <p align="center">
  <img src="link.png" alt="Restrições de Itens">
</p> -->

---

## História #04

**Título:** Listagem de Características Básicas

> **Como** usuário do sistema, **quero** ver as informações básicas de cada item, **para** entender rapidamente nome, valor e categoria.

**Critérios de Aceitação:**
- Para todo item, é possível ver seu nome, categoria e valor.

**Status:** Feito  
**Tipo:** Funcionalidade

<!-- <p align="center">
  <img src="link.png" alt="Características Básicas">
</p> -->

---

## História #05

**Título:** Atualização de Itens

> **Como** operador de estoque, **quero** conseguir atualizar os dados de um item existente, **para** manter o controle de estoque correto e atualizado.

**Critérios de Aceitação:**
- Permitir atualização de nome, quantidade, valor, categoria e ID.
- Validar a existência do item antes da alteração.

**Status:** Feito  
**Tipo:** Funcionalidade

<!-- <p align="center">
  <img src="link.png" alt="Atualização de Itens">
</p> -->

---

## História #06

**Título:** Cadastro de Filial

> **Como** administrador da empresa, **quero** cadastrar uma filial com nome, local e ID, **para** representar a estrutura organizacional da empresa.

**Critérios de Aceitação:**
- Deve armazenar corretamente as informações da filial.
- É possível adicionar itens ao estoque da filial.

**Status:** Feito  
**Tipo:** Funcionalidade

<!-- <p align="center">
  <img src="link.png" alt="Cadastro de Filial">
</p> -->

---

## História #07

**Título:** Controle de Estoque da Filial

> **Como** funcionário da filial, **desejo** adicionar e remover itens do estoque da minha filial, **para** manter o controle do inventário.

**Critérios de Aceitação:**
- Deve permitir adicionar e remover itens.
- Deve retornar o estoque completo da filial.

**Status:** Feito  
**Tipo:** Funcionalidade

<!-- <p align="center">
  <img src="link.png" alt="Estoque da Filial">
</p> -->

---

## História #08

**Título:** Cadastro de Empresa

> **Como** administrador geral, **quero** cadastrar uma empresa e suas filiais, **para** organizar as operações por localidade.

**Critérios de Aceitação:**
- Deve ser possível cadastrar uma nova empresa, com nome da empresa.
- Deve ser possível adicionar e remover filiais da empresa.

**Status:** Feito  
**Tipo:** Funcionalidade

<!-- <p align="center">
  <img src="link.png" alt="Cadastro de Empresa">
</p> -->

---

## História #09

**Título:** Busca de Itens por ID

> **Como** operador, **quero** buscar um item pelo seu ID, **para** localizá-lo rapidamente no sistema.

**Critérios de Aceitação:**
- Deve funcionar em qualquer estoque (filial, empresa, etc.).
- Deve retornar o item correspondente ou erro se não existir.

**Status:** A Fazer  
**Tipo:** Funcionalidade

<!-- <p align="center">
  <img src="link.png" alt="Busca por ID">
</p> -->

---

## História #10

**Título:** Busca de Item por Nome

> **Como** operador de estoque, **quero** buscar um item pelo nome, **para** localizá-lo mesmo sem saber seu ID.

**Critérios de Aceitação:**
- Deve ser possível realizar busca parcial.
- Deve ignorar letras maiúsculas e minúsculas.
- Deve conseguir buscar por substrings.
- Deve retornar todos os itens correspondentes.

**Status:** A Fazer  
**Tipo:** Funcionalidade

<!-- <p align="center">
  <img src="link.png" alt="Busca por Nome">
</p> -->

---

## História #11

**Título:** Visualizar Todos os Itens

> **Como** administrador, **quero** ver todos os itens do sistema, **para** ter uma visão geral do estoque da empresa.

**Critérios de Aceitação:**
- Deve exibir lista com nome, categoria, quantidade, valor e filial.
- Deve exibir o estoque completo da empresa.
- Pode incluir ordenação alfabética ou por quantidade.

**Status:** Feito 
**Tipo:** Funcionalidade

<!-- <p align="center">
  <img src="link.png" alt="Listagem Geral de Itens">
</p> -->

---

## História #12

**Título:** Itens com Estoque Zerado

> **Como** supervisor de estoque, **quero** listar os itens com estoque zerado de uma filial, **para** identificar o que precisa ser reabastecido.

**Critérios de Aceitação:**
- Deve retornar apenas os itens com quantidade = 0.
- Deve filtrar por filial específica.

**Status:** Feito  
**Tipo:** Funcionalidade

<!-- <p align="center">
  <img src="link.png" alt="Estoque Zerado">
</p> -->

---