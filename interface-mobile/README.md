# Aplicativo que consome uma API RESTful usando Kotlin SpringBoot

Este projeto é um APP que consome uma API RESTful desenvolvida com Java SpringBoot. A API fornece operações básicas de CRUD (Criar, Ler, Atualizar, Excluir) para uma entidade específica. O aplicativo Kotlin interage com a API para realizar essas operações.

## Pré-requisitos
- Java Development Kit (JDK) 11 ou superior
- Kotlin 1.4 ou superior
- Gradle
- Postman (opcional, para testar a API)
- Banco de dados MongoDB ou MySQL (dependendo da configuração da API)
- IDE (como IntelliJ IDEA ou Eclipse) para desenvolvimento

## Estrutura do Projeto
```

📂 app/
├── 📂 src/
│   ├── 📂 main/
│   │   ├── 📂 java/com/seupacote/oscarapp/  (substitua pelo seu pacote)
│   │   │   ├── 📂 data/                     (Tudo relacionado a dados)
│   │   │   │   ├── 📂 api/                  (Chamadas HTTP)
│   │   │   │   │   ├── 📄 ApiService.kt     (Chamada da API)
│   │   │   │   ├── 📂 repository/           (Opcional: Repositórios se tiver lógica complexa)
│   │   │   │   │   └── 📄 RetrofitInstance.kt (Interface Retrofit)
│   │   │   │   └── 📂 models/               (Modelos de dados)
│   │   │   │       └── 📄 IndicacaoOscar.kt
│   │   │   │
│   │   │   ├── 📂 ui/                       (Todas as telas e componentes visuais)
│   │   │   │   ├── 📂 components/           (Componentes reutilizáveis)
│   │   │   │   │   └── 📄 IndicacaoItem.kt
│   │   │   │   │
│   │   │   │   ├── 📂 screens/              (Telas principais)
│   │   │   │   │   ├── 📄 ListaIndicacoesScreen.kt
│   │   │   │   │   └── 📄 DetalhesIndicacaoScreen.kt (Futura implementação)
│   │   │   │   │
│   │   │   │   └── 📂 viewmodels/           (ViewModels)
│   │   │   │       └── 📄 OscarViewModel.kt
│   │   │   │
│   │   │   └── 📄 MainActivity.kt
│   │   │
│   │   ├── 📂 res/
│   │   │   ├── 📂 drawable/                 (Ícones/imagens)
│   │   │   ├── 📂 values/
│   │   │   │   ├── 📄 colors.xml
│   │   │   │   ├── 📄 strings.xml
│   │   │   │   └── 📄 themes.xml
│   │   │   └── 📂 xml/                      (Outros recursos)
│   │   │   │   ├── 📄 network_security_config.xml (Configurações de rede)
│   │   │
│   │   └── 📄 AndroidManifest.xml
│   │
│   └── 📂 test/                             (Testes unitários)
│
└── 📄 build.gradle.kts
```

## Como funciona a chamada da API
A chamada da API é feita através do Retrofit, uma biblioteca que facilita a comunicação com serviços RESTful. O `RetrofitInstance.kt` configura o Retrofit e define a URL base da API. O `ApiService.kt` contém as definições das chamadas HTTP, como GET, POST, PUT e DELETE.

###  Fluxo de Dados

```plaintext
[APP] -> "Quero ver as indicações do Oscar!" 
    👇 
[VIEWMODEL] -> Invoca o método no ApiService 
    👇 
[API SERVICE] -> Chamada Retrofit: GET /api/indicacoes 
    👇 
[RETROFIT] -> Envia a requisição HTTP para a API 
    👇 
[API SPRING] -> Processa a requisição e acessa o banco de dados 
    👇 
[MONGODB] -> Retorna os dados solicitados 
   
🔄 (O caminho volta com os dados!)
```
Ou de uma forma mais leve:

```plaintext
[📱 APP] -> "Quero ver as indicações do Oscar!" 
   👇 
[🎬 VIEWMODEL] -> "Vamos buscar isso!" 
   👇 
[📞 API SERVICE] -> Dial: GET /api/indicacoes 
   👇 
[🚀 RETROFIT] -> 🌐 Internet Magic! 
   👇 
[🌩️ API SPRING] -> "Banco, me dê os dados!" 
   👇 
[🗄️ MONGODB] -> "Aqui estão!" 
   🔄 (O caminho volta com os dados!)
```

## Como Executar o Projeto
1. Com uma cópia do projeto, abra o projeto no Android Studio. 
2. Certifique-se de que o JDK e o Kotlin estão configurados corretamente.
3. Execute o gradle para baixar as dependências se necessário:
   ```bash
   ./gradlew build
   ```
4. Inicie o servidor da API Java SpringBoot (se não estiver rodando) e certifique-se de que está acessível na URL configurada no `RetrofitInstance.kt`.
5. Execute o aplicativo no emulador ou dispositivo Android.

## Próximos Passos
- Implementar a tela de detalhes da indicação do Oscar.
- Adicionar funcionalidades de criação, atualização e exclusão de indicações.
- Melhorar a interface do usuário com mais componentes visuais.

## Recursos
- [Documentação Java SpringBoot](https://spring.io/projects/spring-boot)
- [Documentação do Postman](https://learning.postman.com/docs/)
- [Documentação do Retrofit](https://square.github.io/retrofit/)
- [Documentação do Kotlin](https://kotlinlang.org/docs/home.html)
- [Documentação do Android](https://developer.android.com/docs)

## Licença
Este projeto está licenciado sob a [Licença MIT](LICENSE). Sinta-se à vontade para usar e modificar o código para fins educacionais.