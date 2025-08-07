# API de Portaria de Condomínio - Spring Boot

Este projeto é uma API RESTful desenvolvida em Java com Spring Boot para gerenciar as operações de uma portaria de condomínio. A aplicação foi criada como parte do curso de pós-graduação em Engenharia de Software JAVA da INFNET.

## Feature 1: Base da API e CRUD de Moradores

Nesta primeira entrega, foram implementados os alicerces da aplicação, incluindo:

-   **Arquitetura em Camadas**: O projeto está estruturado com separação de responsabilidades (`Controller`, `Service`, `Domain`).
-   **Entidade Principal**: Modelagem da entidade `Morador` e suas associações (`Pessoa`, `Unidade`, `Bloco`).
-   **API RESTful Completa**: Endpoints para todas as operações CRUD (Criar, Ler, Atualizar, Excluir) para a entidade `Morador`.
-   **Persistência em Memória**: Utilização de `ConcurrentHashMap` na camada de serviço para simular um banco de dados e armazenar os dados em tempo de execução.
-   **Carregamento de Dados Iniciais**: Um `DataLoader` que popula o sistema com dados iniciais a partir de arquivos de texto (`.txt`) na inicialização da aplicação.
-   **Tratamento de Exceções**: Implementação de um manipulador de exceções global (`@RestControllerAdvice`) para retornar respostas de erro padronizadas (ex: 404 Not Found).

## Tecnologias Utilizadas

-   **Java 21**
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
    *(No Windows, use `mvn clean install`)*

3.  **Execute a aplicação:**
    ```bash
    java -jar target/matheusmacielapi-0.0.1-SNAPSHOT.jar
    ```

A API estará disponível em `http://localhost:8080`.

## Documentação da API

A seguir estão os detalhes dos endpoints disponíveis para a entidade `Morador`.

#### Respostas de Erro

Caso um recurso não seja encontrado (ex: um morador com um ID inexistente), a API retornará o status `404 Not Found` com o seguinte corpo:
```json
{
    "timestamp": "2025-08-07T23:59:59.12345Z",
    "status": 404,
    "error": "Recurso não encontrado",
    "message": "Morador não encontrado para o ID: 99",
    "path": "/moradores/99"
}
```

---

### Endpoints de Moradores

#### 1. Listar Todos os Moradores

-   **Método:** `GET`
-   **URL:** `/api/moradores`
-   **Resposta de Sucesso (200 OK):**
    ```json
    [
        {
            "id": 1,
            "nome": "Ana Souza",
            "documento": "111.222.333-44",
            "telefone": "(21)99999-8888",
            "email": "ana.souza@email.com",
            "unidade": {
                "id": 203,
                "numero": "Apto 203",
                "descricao": "3 quartos, fundos",
                "bloco": { "id": 1, "nome": "Bloco A - Girassol" }
            },
            "proprietario": false,
            "taxaCondominio": 550.0
        }
    ]
    ```

#### 2. Buscar Morador por ID

-   **Método:** `GET`
-   **URL:** `/api/moradores/{id}`
-   **Exemplo:** `/moradores/1`
-   **Resposta de Sucesso (200 OK):**
    ```json
    {
        "id": 1,
        "nome": "Ana Souza",
        "documento": "111.222.333-44",
        "telefone": "(21)99999-8888",
        "email": "ana.souza@email.com",
        "unidade": {
            "id": 203,
            "numero": "Apto 203",
            "descricao": "3 quartos, fundos",
            "bloco": { "id": 1, "nome": "Bloco A - Girassol" }
        },
        "proprietario": false,
        "taxaCondominio": 550.0
    }
    ```

#### 3. Criar Novo Morador

-   **Método:** `POST`
-   **URL:** `/api/moradores`
-   **Corpo da Requisição (JSON):**
    ```json
    {
      "nome": "Pedro Costa",
      "documento": "456.789.123-00",
      "telefone": "(21)95555-4444",
      "email": "pedro.costa@email.com",
      "unidade": {
        "id": 101,
        "numero": "Apto 101",
        "descricao": "2 quartos, frente",
        "bloco": { "id": 1, "nome": "Bloco A - Girassol" }
      },
      "proprietario": false,
      "taxaCondominio": 620.00
    }
    ```
-   **Resposta de Sucesso (201 Created):** Retorna o objeto criado com o novo `id` e o header `Location` com a URL do novo recurso.

#### 4. Atualizar Morador Existente

-   **Método:** `PUT`
-   **URL:** `/api/moradores/{id}`
-   **Exemplo:** `/moradores/1`
-   **Corpo da Requisição (JSON):**
    ```json
    {
      "id": 1,
      "nome": "Ana Souza Silva",
      "documento": "111.222.333-44",
      "telefone": "(21)99999-8888",
      "email": "ana.silva@email.com",
      "unidade": {
        "id": 203,
        "numero": "Apto 203",
        "descricao": "3 quartos, fundos",
        "bloco": { "id": 1, "nome": "Bloco A - Girassol" }
      },
      "proprietario": false,
      "taxaCondominio": 550.0
    }
    ```
-   **Resposta de Sucesso (200 OK):** Retorna o objeto completo com os dados atualizados.

#### 5. Excluir Morador

-   **Método:** `DELETE`
-   **URL:** `/api/moradores/{id}`
-   **Exemplo:** `/moradores/2`
-   **Resposta de Sucesso (204 No Content):** Retorna uma resposta vazia.

## Estrutura de Dados Iniciais

Os dados iniciais são carregados a partir da pasta `/dados`. A estrutura dos arquivos é a seguinte:

-   **`blocos.txt`**: `id;nome`
-   **`unidades.txt`**: `id;numero;descricao;id_do_bloco`
-   **`moradores.txt`**: `nome;documento;telefone;email;id_da_unidade;proprietario;taxaCondominio`
