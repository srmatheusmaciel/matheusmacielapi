# API de Portaria de Condomínio - Spring Boot

Este projeto é uma API RESTful desenvolvida em Java com Spring Boot para gerenciar as operações de uma portaria de condomínio. A aplicação foi criada como parte do curso de pós-graduação em Engenharia de Software JAVA.

## Funcionalidades Implementadas

Até o momento, o projeto conta com as seguintes features:

-   **Arquitetura em Camadas**: O projeto está estruturado com separação de responsabilidades (`Controller`, `Service`, `Domain`, `Loader`).
-   **Modelo de Domínio Abrangente**:
    -   Herança implementada com a classe abstrata `Pessoa` como base para `Morador` e `Visitante`.
    -   Associação (One-to-Many) entre `Morador` e `Veiculo`.
    -   Histórico de acessos com a entidade `RegistroAcesso`, associada a `Visitante`.
-   **API RESTful Completa**: Endpoints para todas as operações CRUD (Criar, Ler, Atualizar, Excluir) para as entidades `Morador` e `Visitante`.
-   **Endpoints de Negócio**: Operações específicas como associar veículos e registrar a entrada/saída de visitantes.
-   **Persistência em Memória**: Utilização de `ConcurrentHashMap` na camada de serviço para simular um banco de dados.
-   **Carga de Dados Inicial**: Loaders específicos por entidade (`MoradorLoader`, `VeiculoLoader`, `VisitanteLoader`) que populam o sistema a partir de arquivos de texto (`.txt`).
-   **Tratamento de Exceções**: Manipulador de exceções global (`@RestControllerAdvice`) para retornar respostas de erro padronizadas (404 Not Found, 400 Bad Request).

## Tecnologias Utilizadas

-   **Java 21** (ou superior)
-   **Spring Boot 3**
-   **Maven**

## Pré-requisitos

Para executar este projeto localmente, você precisará ter instalado:

-   JDK 21 ou superior
-   Apache Maven 3.8 ou superior

## Como Executar o Projeto

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/srmatheusmaciel/matheusmacielapi.git](https://github.com/srmatheusmaciel/matheusmacielapi.git)
    cd matheusmacielapi
    ```

2.  **Compile o projeto com o Maven:**
    ```bash
    ./mvnw clean install
    ```
    *(No Windows, use `mvnw.cmd clean install`)*

3.  **Execute a aplicação:**
    ```bash
    java -jar target/matheusmacielapi-*.jar
    ```

A API estará disponível em `http://localhost:8080`.

## Documentação da API

A seguir estão os detalhes dos endpoints disponíveis.

#### Respostas de Erro Comuns

-   **404 Not Found**: Retornado quando um recurso específico (morador, visitante, etc.) não é encontrado.
    ```json
    {
        "timestamp": "2025-08-09T16:50:10.123Z",
        "status": 404,
        "error": "Recurso não encontrado",
        "message": "Morador não encontrado para o ID: 99",
        "path": "/moradores/99"
    }
    ```
-   **400 Bad Request**: Retornado para erros de validação de negócio (ex: placa de veículo duplicada).
    ```json
    {
        "timestamp": "2025-08-09T16:51:20.456Z",
        "status": 400,
        "error": "Erro de validação",
        "message": "A placa 'RIO2A18' já está cadastrada no sistema.",
        "path": "/moradores/1/veiculos"
    }
    ```

---

### Endpoints de Moradores

-   `GET /moradores`: Lista todos os moradores.
-   `GET /moradores/{id}`: Busca um morador por ID.
-   `POST /moradores`: Cria um novo morador.
-   `PUT /moradores/{id}`: Atualiza um morador existente.
-   `DELETE /moradores/{id}`: Exclui um morador.
-   `POST /moradores/{idMorador}/veiculos`: Adiciona um novo veículo a um morador.
    -   **Corpo da Requisição (JSON):**
        ```json
        {
            "placa": "XYZ1B23",
            "modelo": "Hyundai HB20",
            "cor": "Prata"
        }
        ```

### Endpoints de Visitantes

-   `GET /visitantes`: Lista todos os visitantes.
-   `GET /visitantes/{id}`: Busca um visitante por ID.
-   `POST /visitantes`: Cria um novo visitante.
    -   **Corpo da Requisição (JSON):**
        ```json
        {
            "nome": "Carla Dias",
            "documento": "101.202.303-04",
            "telefone": "(21)94444-5555",
            "email": "carla.dias@email.com",
            "rg": "55.666.777-8",
            "autorizadoPor": "Ana Souza"
        }
        ```
-   `PUT /visitantes/{id}`: Atualiza um visitante existente.
-   `DELETE /visitantes/{id}`: Exclui um visitante.

### Endpoints de Controle de Acesso

-   `POST /visitantes/{idVisitante}/registrar-entrada`: Registra uma nova entrada para um visitante. Não requer corpo. Retorna o objeto `RegistroAcesso` criado.
-   `PATCH /acessos/{idAcesso}/registrar-saida`: Registra a saída em um registro de acesso existente. Não requer corpo.

## Estrutura de Dados Iniciais

Os dados iniciais são carregados a partir da pasta `/dados`. A estrutura dos arquivos é a seguinte:

-   **`blocos.txt`**: `id;nome`
-   **`unidades.txt`**: `id;numero;descricao;id_do_bloco`
-   **`moradores.txt`**: `nome;documento;telefone;email;id_da_unidade;proprietario;taxaCondominio`
-   **`veiculos.txt`**: `id_do_morador;placa;modelo;cor`
-   **`visitantes.txt`**: `nome;documento;telefone;email;rg;autorizadoPor`
