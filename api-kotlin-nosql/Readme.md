# Criando uma API com Spring Boot, MongoDB e Kotlin

Este projeto é uma API básica construída usando Kotlin & SpringBoot. Ele serve como exemplo para uso em sala de aula, fornecendo uma introdução à construção de APIs com SpringBoot e apresentando vários recursos e potencialidades das tecnologias envolvidas.

**Visão Geral do Projeto**

Vamos construir uma API que:

✅ **Cadastra** indicações ao Oscar

✅ **Lista** todas as indicações

✅ **Filtra** por ano, categoria, vencedores etc.

✅ **Atualiza** e **deleta** registros

## Spring e Spring Boot?

Spring é um framework de desenvolvimento de aplicações Java voltado para a construção de sistemas corporativos robustos e escaláveis. Ele fornece um conjunto abrangente de recursos e bibliotecas que facilitam o desenvolvimento, a configuração e a integração de aplicativos.

Spring Boot, por sua vez, é uma extensão do Spring Framework que simplifica ainda mais o processo de criação de aplicativos Java. Ele oferece convenções de configuração inteligentes e um conjunto de bibliotecas pré-configuradas para facilitar o desenvolvimento de aplicativos independentes e prontos para produção.

A relação entre Spring e Spring Boot é que o Spring Boot é construído em cima do Spring Framework, aproveitando muitos de seus recursos e aprimorando a produtividade do desenvolvedor. O Spring Boot simplifica a configuração e a inicialização de aplicativos Spring, fornecendo padrões de configuração inteligentes e um modelo de programação "convenção sobre configuração". Com o Spring Boot, os desenvolvedores podem criar aplicativos Java de forma mais rápida e eficiente, aproveitando os recursos poderosos do Spring Framework.

## **Passo 1: Criando o Projeto**

### **No [start.spring.io](https://start.spring.io/), selecione:**

- **Project:** Gradle
- **Language:** Kotlin
- **Dependencies:**
    - **Spring Web** (para API REST)
    - **Spring Data MongoDB** (para o banco)

👉 **Importe o projeto no IntelliJ IDEA** (ou sua IDE favorita).

![image.png](image.png)

**Estrutura do Projeto que iremos criar. Se quiserem, já podemos criar os arquivos que não existirem.** 

```
📂 projeto/
├── 📂 src/main/kotlin/
│   ├── 📄 OscarApiApplication.kt (Inicia a aplicação)
│   ├── 📂 model/
│   │   └── 📄 IndicacaoOscar.kt (Define a estrutura dos dados)
│   ├── 📂 repository/
│   │   └── 📄 IndicacaoOscarRepository.kt (Conversa com o banco)
│   └── 📂 controller/
│       └── 📄 OscarController.kt (Recebe as requisições HTTP)
├── 📂 src/main/resources/
│   └── 📄 application.properties (Configura o banco de dados)
└── 📄 build.gradle.kts (Lista as dependências)
```

### Criando a estrutura de pastas.

Procure o caminho ***src/main/kotlin/com/example/projeto*** Dentro da pasta “**projeto**” (os nomes das pastas podem variar de acordo com os nomes foram informados na criação do projeto dentro do portal [start.spring.io](https://start.spring.io/)).

Dentro dessa pasta você deve ter um arquivo chamado OscarApplication.java. Essa classe contém o método principal da sua classe. Não é necessário alterá-lo.

1. Agora é o momento de criar três pastas para nossa arquitetura: models, controllers e repositories.

*Dentro da estrutura de uma API, as classes são geralmente chamadas de "models", "controllers" e "repositories" devido à arquitetura do padrão MVC (Model-View-Controller) e ao uso do Spring Framework.*

A estrutura abaixo segue o princípio da separação de responsabilidades, onde cada classe tem um papel específico. Os models representam as entidades e os dados da aplicação, os controllers lidam com as requisições e respostas HTTP, e os repositories tratam da persistência dos dados. Essa abordagem ajuda a manter o código organizado, modular e facilita a manutenção e a evolução da API ao longo do tempo.

1. **Models**:
   As classes chamadas de "models" representam as entidades de negócio do seu sistema. Essas classes modelam os dados e geralmente correspondem às tabelas em um banco de dados relacional. Os modelos encapsulam os atributos e comportamentos relacionados a uma entidade específica, como um usuário, produto, pedido etc. Eles são responsáveis por representar os dados e fornecer métodos para acessá-los e manipulá-los.

2. **Controllers**:
   As classes chamadas de "controllers" são responsáveis por receber as solicitações HTTP dos clientes e processá-las. Os controllers lidam com a lógica da aplicação, roteando as solicitações para os métodos apropriados e retornando as respostas apropriadas. Eles atuam como intermediários entre as requisições do cliente e as operações a serem realizadas nos modelos e nos serviços. Os controllers geralmente contêm métodos que são anotados com @RequestMapping ou outras anotações do Spring para mapear os endpoints da API e definir o comportamento esperado.

3. **Repositories**:
   As classes chamadas de "repositories" são responsáveis pela persistência dos dados. Elas são usadas para interagir com o banco de dados ou qualquer outro mecanismo de armazenamento de dados. Os repositories fornecem métodos para criar, recuperar, atualizar e excluir dados no banco de dados. Eles encapsulam a lógica de acesso aos dados e oferecem uma camada de abstração para as operações de leitura e gravação. Os repositories são tipicamente implementados usando frameworks ORM (Object-Relational Mapping), como o Spring Data JPA, que simplificam a interação com o banco de dados.

A estrutura do seu projeto deve ser similar a essa:

**Diagrama de Funcionamento**

```mermaid
flowchart LR
    Cliente -->|GET /api/indicacoes| Controller
    Controller -->|repository.findAll()| Repository
    Repository -->|Busca no banco| MongoDB
    MongoDB -->|Retorna dados| Repository
    Repository -->|Retorna lista| Controller
    Controller -->|JSON| Cliente
```

🔹 **Explicação:**

1. O **cliente** (navegador/software/aplicacao) faz uma requisição.
2. O **controller** recebe e pede dados ao **repository**.
3. O **repository** busca no **MongoDB** e devolve.
4. O **controller** transforma em **JSON** e envia de volta.

## **Passo 2: Configurando o Banco de Dados (MongoDB)**

**No arquivo `application.properties` (em `src/main/resources`):**

```
spring.data.mongodb.uri=mongodb://localhost:27017/oscar
```

Se for MongoDB Atlas (nuvem), use: 

```
spring.data.mongodb.uri=mongodb+srv://USUARIO:SENHA@CLUSTER.mongodb.net/NOMEDOBANCO
```

🔹 **O que isso faz?**

- Define 

- **onde o Spring deve buscar os dados**: MongoDB.
- **`oscar`** é o nome do banco de dados.
- **`usuario`**
- **`senha`**
- **`cluster`**
- **`O nome do banco de dados`**

Você pode encontrar todas essas informações dentro do MongoDB Atlas. 

## **Passo 3: Criando a "Tabela"**

**Arquivo: `model/IndicacaoOscar.kt`**

```kotlin
@Document(collection = "indicacoes") // Nome da coleção no MongoDB  
data class IndicacaoOscar(  
    @Id val id: String? = null, // ID único (gerado pelo MongoDB)  
    val idRegistro: Int,  
    val anoFilmagem: Int,  
    val anoCerimonia: Int,  
    val cerimonia: Int,  
    val categoria: String,  
    val nomeDoIndicado: String,  
    val nomeDoFilme: String,  
    val vencedor: Boolean  
)  
```

## **Passo 4: Criando o "Consultor do Banco"**

### **Arquivo: `repository/IndicacaoOscarRepository.kt`**

```jsx
@Repository  
interface IndicacaoOscarRepository : MongoRepository<IndicacaoOscar, String> {  
    fun findByAnoCerimonia(ano: Int): List<IndicacaoOscar>  
    fun findByCategoria(categoria: String): List<IndicacaoOscar>  
    fun findByVencedor(vencedor: Boolean): List<IndicacaoOscar>  
}  
```

🔹 **O que isso faz?**

- **Conversa com o banco** (busca, salva, deleta).
- **`findByAnoCerimonia(2020)`** → Retorna indicações de 2020.
- O Spring **cria automaticamente** essas consultas! ✨

## **Passo 5: Criando o "Atendente"**

**Passo 5: Criando o "Atendente"** 

```jsx
@RestController  
@RequestMapping("/api/indicacoes")  
class OscarController(private val repository: IndicacaoOscarRepository) {  

    // Lista todas as indicações  
    @GetMapping  
    fun listarTodas(): List<IndicacaoOscar> = repository.findAll()  

    // Busca por ID  
    @GetMapping("/{id}")  
    fun buscarPorId(@PathVariable id: String) = repository.findById(id)  

    // Filtra por ano  
    @GetMapping("/ano/{ano}")  
    fun filtrarPorAno(@PathVariable ano: Int) = repository.findByAnoCerimonia(ano)  
}  
```

🔹 **O que isso faz?*
* Configura as rotas da API.
- **`@RestController`** → Indica que é um controlador REST.
- **`@RequestMapping("/api/indicacoes")`** → Define a URL base.
- **`@GetMapping`** → Define o método HTTP (GET, POST, etc.).
- **`@PathVariable`** → Pega o valor da URL.
- **`@RequestBody`** → Pega o corpo da requisição.