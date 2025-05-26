# Criando uma API com Spring Boot, MongoDB e Kotlin

Este projeto é uma API básica construída usando Kotlin & SpringBoot. Ele serve como exemplo para uso em sala de aula, fornecendo uma introdução à construção de APIs com SpringBoot e apresentando vários recursos e potencialidades das tecnologias envolvidas.

**Visão Geral do Projeto**

Vamos construir uma API que:

✅ **Cadastra** indicações ao Oscar

✅ **Lista** todas as indicações

✅ **Filtra** por ano, categoria, vencedores etc.

✅ **Atualiza** e **deleta** registros

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

**Diagrama de Funcionamento**

```markdown
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
mongodb+srv://USUARIO:SENHA@CLUSTER.mongodb.net/NOMEDOBANCO
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