# Mercado Express — Sistema de Gestão (Spring MVC)

> **Checkpoint 4 · Parte 2 · FIAP**  
> Disciplina: Análise e Desenvolvimento de Sistemas (TDS)

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-Auth-6DB33F?style=flat-square&logo=springsecurity&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-Template%20Engine-005F0F?style=flat-square&logo=thymeleaf&logoColor=white)
![Oracle](https://img.shields.io/badge/Oracle-SQL-F80000?style=flat-square&logo=oracle&logoColor=white)

Sistema web para gerenciamento do catálogo de um mercado express, desenvolvido com arquitetura **Model-View-Controller (MVC)**. Diferente da Parte 1 (API RESTful), esta etapa realiza **server-side rendering**: as páginas HTML são renderizadas diretamente no servidor via Thymeleaf e entregues prontas ao navegador.

O sistema contempla gerenciamento completo de produtos (CRUD), persistência em banco de dados Oracle e controle de acesso às operações de escrita via Spring Security.
 
---

## Índice

- [Integrantes](#integrantes)
- [Arquitetura da Solução](#arquitetura-da-solução)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Configuração e Execução Local](#configuração-e-execução-local)
- [Credenciais de Acesso](#credenciais-de-acesso)
- [Funcionalidades e Operações CRUD](#funcionalidades-e-operações-crud)
- [Deploy em Produção](#deploy-em-produção)
---


## Integrantes

| Nome | RM |
|:---|:---|
| João Victor Vendrameto | 563665 |
| Gabriel Saraiva Ambrosio | 566552 |
 
---

## Arquitetura da Solução

```
Navegador (Browser)
      │
      │  HTTP GET / POST
      ▼
ProdutoController  (Spring MVC — intercepta requisições)
      │
      ├── GET  /produtos          → findAll()   → produtos/lista.html
      ├── GET  /produtos/novo     → new Produto → produtos/form.html
      ├── POST /produtos/salvar   → save()      → redirect:/produtos
      ├── GET  /produtos/editar/{id} → findById() → produtos/form.html
      └── GET  /produtos/deletar/{id} → deleteById() → redirect:/produtos
      │
      ▼
ProdutoRepository  (Spring Data JPA)
      │
      ▼
Oracle Database  (FIAP — acesso via VPN)
      └── Tabela: TDS_MVC_TB_MERCADO
```

### Camadas MVC

| Camada | Componente | Responsabilidade |
|:---|:---|:---|
| **Model** | `Produto.java` | Entidade JPA mapeada para `TDS_MVC_TB_MERCADO`. Lombok gera getters, setters e construtores automaticamente |
| **View** | Templates Thymeleaf | Renderização server-side com controle de sessão — botões de escrita só aparecem para usuários autenticados |
| **Controller** | `ProdutoController.java` | Intercepta requisições HTTP (GET/POST), aciona o repositório e retorna a view correspondente |
 
---

## Tecnologias Utilizadas

| Tecnologia | Versão / Descrição |
|:---|:---|
| Java | 21 |
| Spring Boot | 4.1.1 |
| Spring Web | Mapeamento de rotas HTTP (MVC) |
| Spring Data JPA | Abstração de acesso ao banco de dados via repositório |
| Spring Security | Controle de autenticação e autorização de rotas |
| Spring Boot DevTools | Hot reload durante o desenvolvimento |
| Thymeleaf | Template engine server-side com integração nativa ao Spring Security |
| Oracle SQL Developer | Banco de dados relacional hospedado no servidor FIAP |
| Bootstrap 5 + Icons | Estilização e componentes de interface |
| Lombok | Geração automática de boilerplate (getters, setters, construtores) |
| IntelliJ IDEA | IDE utilizada no desenvolvimento |
 
---

## Configuração e Execução Local

### Pré-requisitos

- JDK 21 instalado e configurado no `PATH`
- IntelliJ IDEA (ou outra IDE com suporte a Maven)
- Conexão com a **VPN da FIAP** — obrigatória para alcançar o banco Oracle
> Sem a VPN ativa, a aplicação lançará `Connection Timeout` ao tentar conectar ao banco de dados da instituição.
 
---
### Spring Initializr ###

<img width="1223" height="918" alt="springini" src="https://github.com/user-attachments/assets/0b1c1551-2158-43a5-a2d2-cf626c19e7f4">

### Passo 1 — Importar o Projeto

Abra o IntelliJ IDEA, selecione **File → Open** e aponte para a raiz do projeto. Aguarde o Maven resolver todas as dependências do `pom.xml`.
 
---

### Passo 2 — Configurar Credenciais do Banco

Abra o arquivo `src/main/resources/application.properties` e substitua os valores abaixo com seu RM e senha:

```properties
# src/main/resources/application.properties
 
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
spring.datasource.username=rmXXXXXX
spring.datasource.password=SuaSenha
 
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
```

<img width="1917" height="1030" alt="application properties" src="https://github.com/user-attachments/assets/ae5225fb-903e-4a41-be66-45bfe1dce3a3">


> **Nunca suba o arquivo `application.properties` com credenciais reais para o repositório.** Adicione-o ao `.gitignore` ou utilize variáveis de ambiente.
 
---

### Passo 3 — Executar a Aplicação

Execute a classe `Application.java` pelo IntelliJ (botão Run) ou via terminal:

```bash
./mvnw spring-boot:run
```

<img width="1915" height="1032" alt="Application java" src="https://github.com/user-attachments/assets/de55e290-adb6-45cb-b8c8-ec4240a596a5">


O servidor Tomcat embutido será iniciado na porta `8082`.
 
---

### Passo 4 — Acessar no Navegador

```
http://localhost:8082/produtos
```
 <img width="1912" height="995" alt="localhost_2" src="https://github.com/user-attachments/assets/0f0b6280-998b-4dba-8c8e-5d1bdd10cc47" >
<img width="1917" height="991" alt="locashotst_1" src="https://github.com/user-attachments/assets/2fa27e7d-9fd0-42be-837c-334624008d49" >

---

## Credenciais de Acesso

A aplicação segmenta rotas públicas (leitura) e rotas privadas (escrita) via **Spring Security**.

| Perfil | Acesso | Usuário | Senha |
|:---|:---|:---|:---|
| Visitante | Listagem de produtos (`GET`) | — | — |
| Administrador | Criar, editar e excluir produtos | `admin` | `1234567890` |

> As credenciais são armazenadas em memória (`InMemoryUserDetailsManager`). Em produção, substitua por autenticação baseada em banco de dados.
 
---

## Funcionalidades e Operações CRUD

### Rotas Disponíveis

| Método | Rota | Acesso | Descrição |
|:---|:---|:---|:---|
| `GET` | `/produtos` | Público | Lista todos os produtos |
| `GET` | `/produtos/novo` | Autenticado | Exibe formulário de criação |
| `POST` | `/produtos/salvar` | Autenticado | Persiste novo produto ou atualização |
| `GET` | `/produtos/editar/{id}` | Autenticado | Carrega formulário pré-preenchido |
| `GET` | `/produtos/deletar/{id}` | Autenticado | Remove o registro pelo ID |
 
---

### 1. Read — Listagem de Produtos

Qualquer visitante pode acessar `/produtos`. O Spring Data JPA executa `findAll()` e o Thymeleaf renderiza os dados em uma tabela Bootstrap com as colunas: **ID, Nome, Tipo, Setor, Tamanho e Preço**.

```java
// ProdutoController.java
@GetMapping("/produtos")
public String listar(Model model) {
    model.addAttribute("produtos", produtoRepository.findAll());
    return "produtos/lista";
}
```

<img width="1915" height="992" alt="read" src="https://github.com/user-attachments/assets/16883253-efdb-493e-bbaf-ac074c4fef04" >
 
---

### 2. Create — Criação de Produto

Disponível apenas para usuários autenticados. O botão "Novo Produto" redireciona para `/produtos/novo`. Os dados submetidos via `POST` são persistidos no banco Oracle via `save()`.

**Exemplo de registro:** `Detergente Neutro | Limpeza | Corredor 4 | R$ 20,00`

```java
// ProdutoController.java
@PostMapping("/produtos/salvar")
public String salvar(@ModelAttribute Produto produto) {
    produtoRepository.save(produto);
    return "redirect:/produtos";
}
```
<img width="1912" height="992" alt="create" src="https://github.com/user-attachments/assets/655e86e8-ff3c-4d69-9ee1-5ca5a31038c1" >
 
---

### 3. Update — Edição de Produto

O botão "Editar" captura o `id` do produto e carrega suas informações em um formulário pré-preenchido via `findById()`. A submissão reutiliza o mesmo endpoint `POST /produtos/salvar`, sobrescrevendo o registro existente pelo ID.

```java
// ProdutoController.java
@GetMapping("/produtos/editar/{id}")
public String editar(@PathVariable Long id, Model model) {
    model.addAttribute("produto", produtoRepository.findById(id).orElseThrow());
    return "produtos/form";
}
```

<img width="1915" height="992" alt="read" src="https://github.com/user-attachments/assets/7061b267-8820-457b-875b-835da16329ee" >
 
---

### 4. Delete — Exclusão de Produto

O botão "Excluir" aciona um alerta de confirmação em JavaScript. Após confirmado, a requisição é enviada para `/produtos/deletar/{id}` e o registro é removido permanentemente via `deleteById()`.

```java
// ProdutoController.java
@GetMapping("/produtos/deletar/{id}")
public String deletar(@PathVariable Long id) {
    produtoRepository.deleteById(id);
    return "redirect:/produtos";
}
```
<img width="1915" height="992" alt="delete" src="https://github.com/user-attachments/assets/c5b6797a-cdbf-4110-9ae9-3fcb38f665f8" >
 
---

## Deploy em Produção

| Campo | Valor |
|:---|:---|
| **URL de Produção** | [Inserir link do deploy aqui] |
| **Plataforma** | [Inserir plataforma — ex: Render, Fly.io, Railway] |

<img width="1912" height="1032" alt="deploy" src="https://github.com/user-attachments/assets/62df933b-69d8-45c9-bd57-a20d40530cf2" >

> Ao realizar o deploy, certifique-se de configurar as variáveis de ambiente do banco de dados na plataforma escolhida, sem expor credenciais no repositório.


### CRUD Completo ###

<img width="1912" height="1032" alt="crud_complete" src="https://github.com/user-attachments/assets/884c5c80-60e8-484d-a9dc-d557d88ba41e" >

---


Obrigado!
