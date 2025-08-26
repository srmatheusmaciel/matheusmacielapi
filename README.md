# API de Portaria de Condomínio - Spring Boot

Este projeto é uma API RESTful desenvolvida em Java com Spring Boot para gerenciar as operações de uma portaria de condomínio. A aplicação foi criada para meu curso de pós-graduação em Engenharia de Software JAVA na Infnet.

## Funcionalidades Implementadas

Até o momento, o projeto conta com as seguintes features:

-   **Arquitetura em Camadas**: O projeto está estruturado com separação de responsabilidades (`Controller`, `Service`, `Repository`, `Domain`, `Loader`).
-   **Modelo de Domínio Abrangente**:
    -   Herança implementada com a classe abstrata `Pessoa` como base para `Morador` e `Visitante`.
    -   Associação (One-to-Many) entre `Morador` e `Veiculo`.
    -   Histórico de acessos com a entidade `RegistroAcesso`, associada a `Visitante`.
    -   Novo relacionamento One-to-Many entre `Morador` e `Ocorrencia` para registro de eventos.
-   **Validação de Dados Avançada**: Uso de Bean Validation com `@Valid` e outras anotações (`@NotBlank`, `@Size`, etc.) para garantir a integridade dos dados de entrada.
-   **API RESTful Completa**: Endpoints para todas as operações CRUD (Criar, Ler, Atualizar, Excluir) para as entidades `Morador`, `Visitante` e `Ocorrencia`.
-   **Endpoints de Negócio**: Operações específicas como associar/desassociar veículos, registrar a entrada/saída de visitantes e consultas avançadas.
-   **Persistência de Dados com JPA**: Utilização do Spring Data JPA e um banco de dados em memória (H2) para persistência de dados real.
-   **Carga de Dados Inicial**: Loaders específicos por entidade que populam o banco de dados H2 na inicialização da aplicação, incluindo dados para a nova entidade `Ocorrencia`.
-   **Tratamento de Exceções**: Manipulador de exceções global (`@RestControllerAdvice`) para retornar respostas de erro padronizadas (404 Not Found, 400 Bad Request), incluindo erros de validação.

## Tecnologias Utilizadas

-   **Java 21** (ou superior)
-   **Spring Boot 3**
-   **Spring Data JPA**
-   **H2 Database**
-   **Bean Validation**
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

### Acessando o Banco de Dados (H2 Console)

Com a aplicação em execução, você pode acessar o console do banco de dados em memória:
1.  Abra seu navegador e acesse: `http://localhost:8080/h2-console`
2.  Use as seguintes credenciais para conectar:
    -   **JDBC URL**: `jdbc:h2:mem:portariadb`
    -   **User Name**: `sa`
    -   **Password**: (deixe em branco)

## Documentação da API

A seguir estão os detalhes dos endpoints disponíveis.

#### Respostas de Erro Comuns

-   **404 Not Found**: Retornado quando um recurso específico não é encontrado.
-   **400 Bad Request**: Retornado para erros de validação de negócio (ex: placa de veículo duplicada).

---

### Endpoints de Moradores

-   `GET /api/moradores`: Lista todos os moradores.
-   `GET /api/moradores/{id}`: Busca um morador por ID.
-   `GET /api/moradores/buscar?nome=ana`: **NOVO:** Busca moradores por parte do nome, ignorando o caso.
-   `POST /api/moradores`: Cria um novo morador.
-   `PUT /api/moradores/{id}`: Atualiza um morador existente.
-   `DELETE /api/moradores/{id}`: Exclui um morador.

#### Gerenciamento de Veículos do Morador

-   `POST /api/moradores/{idMorador}/veiculos`: Adiciona um novo veículo a um morador.
    -   **Corpo da Requisição (JSON):**
        ```json
        {
            "placa": "XYZ1B23",
            "modelo": "Hyundai HB20",
            "cor": "Prata"
        }
        ```
-   `DELETE /api/moradores/{idMorador}/veiculos/{idVeiculo}`: Exclui um veículo específico de um morador.

### Endpoints de Visitantes

-   `GET /api/visitantes`: Lista todos os visitantes.
-   `GET /api/visitantes/{id}`: Busca um visitante por ID.
-   `POST /api/visitantes`: Cria um novo visitante.
-   `PUT /api/visitantes/{id}`: Atualiza um visitante existente.
-   `DELETE /api/visitantes/{id}`: Exclui um visitante.

### Endpoints de Controle de Acesso

-   `POST /api/visitantes/{idVisitante}/registrar-entrada`: Registra uma nova entrada para um visitante. Não requer corpo. Retorna o objeto `RegistroAcesso` criado.
-   `PATCH /api/acessos/{idAcesso}/registrar-saida`: Registra a saída em um registro de acesso existente. Não requer corpo.

### Endpoints de Ocorrências

-   `GET /ocorrencias`: Lista todas as ocorrências.
-   `POST /ocorrencias/morador/{idMorador}`: **NOVO:** Cria uma nova ocorrência associada a um morador.
    -   **Corpo da Requisição (JSON):**
        ```json
        {
            "titulo": "Portão da garagem com defeito",
            "descricao": "O portão da garagem está fazendo um barulho estranho ao fechar.",
            "data": "2025-08-26",
            "status": "EM ANDAMENTO"
        }
        ```
-   `GET /api/ocorrencias/buscar?status=RESOLVIDA&dataInicio=2025-08-01&dataFim=2025-08-30`: **NOVO:** Busca ocorrências por status e intervalo de datas.

## Estrutura de Dados Iniciais

Os dados iniciais são carregados a partir da pasta `/dados`. A estrutura dos arquivos é a seguinte:

-   **`blocos.txt`**: `id_no_arquivo;nome`
-   **`unidades.txt`**: `id_no_arquivo;numero;descricao;id_do_bloco_no_arquivo`
-   **`moradores.txt`**: `nome;documento;telefone;email;id_da_unidade_no_arquivo;proprietario;taxaCondominio`
-   **`veiculos.txt`**: `id_do_morador_no_banco;placa;modelo;cor`
-   **`visitantes.txt`**: `nome;documento;telefone;email;rg;autorizadoPor`
-   **`ocorrencias.txt`**: `id_do_morador_no_banco;titulo;descricao;data(AAAA-MM-DD);status`
