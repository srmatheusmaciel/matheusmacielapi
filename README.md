# API de Portaria de Condomínio - Spring Boot

Este projeto é uma API RESTful desenvolvida em Java com Spring Boot para gerenciar as operações de uma portaria de condomínio. A aplicação foi criada para meu curso de pós-graduação em Engenharia de Software JAVA na Infnet.

## Funcionalidades Implementadas

Até o momento, o projeto conta com as seguintes features:

* **Arquitetura em Camadas**: O projeto está estruturado com separação de responsabilidades (`Controller`, `Service`, `Repository`, `Domain`, `Loader`).
* **Modelo de Domínio Abrangente**:

  * Herança implementada com a classe abstrata `Pessoa` como base para `Morador` e `Visitante`.
  * Associação (One-to-Many) entre `Morador` e `Veiculo`.
  * Histórico de acessos com a entidade `RegistroAcesso`, associada a `Visitante`.
  * Novo relacionamento One-to-Many entre `Morador` e `Ocorrencia` para registro de eventos.
  * Novos relacionamentos Many-to-One entre `Agendamento` e as entidades `Morador` e `RecursoComum`.
* **Gerenciamento de Agendamentos de Áreas Comuns (NOVO)**:
  Módulo completo para agendar, consultar, atualizar e cancelar o uso de recursos comuns (ex.: churrasqueira e salão de festas).
* **Validação de Dados Avançada**: Uso de Bean Validation com `@Valid` e outras anotações (`@NotBlank`, `@Size`, etc.) para garantir a integridade dos dados de entrada.
* **API RESTful Completa**: Endpoints CRUD para as entidades `Morador`, `Visitante`, `Ocorrencia` e `Agendamento`.
* **Endpoints de Negócio**: Operações específicas como associar/desassociar veículos, registrar a entrada/saída de visitantes e consultas avançadas.
* **Persistência de Dados com JPA**: Utilização do Spring Data JPA e um banco de dados em memória (H2).
* **Carga de Dados Inicial**: Loaders específicos por entidade que populam o banco de dados H2 na inicialização da aplicação.
* **Tratamento de Exceções**: Manipulador de exceções global (`@RestControllerAdvice`) para retornar respostas de erro padronizadas (404 Not Found, 400 Bad Request), incluindo erros de validação.
* **Desenvolvimento Guiado por Testes (TDD) (NOVO)**:
  O módulo de agendamentos foi implementado integralmente seguindo o ciclo **Red-Green-Refactor**, garantindo robustez e qualidade do código.

## Arquitetura Avançada (Feature 3)

O projeto foi refatorado para uma arquitetura multi-módulos, seguindo as melhores práticas de design de software para garantir a separação de responsabilidades, escalabilidade e manutenção. Além disso, o padrão Data Transfer Object (DTO) foi aplicado de forma estratégica.

### Estrutura Multi-Módulos

A aplicação agora está organizada em três módulos Maven distintos, cada um com uma responsabilidade clara:

* **`common-domain`**: Este módulo é a base do nosso negócio. Ele contém as entidades de domínio (classes JPA como `Morador`, `Agendamento`, etc.) que são partilhadas por toda a aplicação. As entidades aqui são "limpas", focadas apenas na representação dos dados e nos relacionamentos, sem anotações de validação de API ou de serialização JSON.

* **`external-api-client`**: Responsável por isolar toda a comunicação com APIs externas. Atualmente, ele contém o Feign Client (`FeriadoApiClient`) e os DTOs necessários para consumir o nosso microsserviço `feriado-api`. Esta separação protege o resto da aplicação de mudanças em serviços de terceiros.

* **`main-application`**: O coração da aplicação. Este módulo contém a lógica de negócio (`Services`), os endpoints REST (`Controllers`), os `Repositories` para acesso a dados e a classe principal que inicia a aplicação Spring Boot. É aqui que os DTOs da nossa própria API são definidos e utilizados.

### Aplicação do Padrão DTO

O padrão DTO foi implementado para criar um "contrato" de API robusto e seguro, desacoplando a representação interna dos dados (entidades) da representação externa (JSON).

* **Request DTOs (Dados de Entrada)**: Para todas as operações de `POST` e `PUT` (ex: `MoradorRequestDTO`, `AgendamentoRequestDTO`), foram criados DTOs específicos para receber os dados. Todas as anotações de validação (`@NotBlank`, `@Email`, `@Size`, etc.) foram movidas das entidades para estes DTOs. Isso protege o nosso domínio de dados inválidos e centraliza as regras de validação da API num único local.

* **Response DTOs (Dados de Saída)**: Para as respostas da API (`GET`, `POST`, `PUT`), foram criados DTOs que modelam a informação a ser enviada ao cliente (ex: `MoradorResponseDTO`, `AgendamentoResponseDTO`). Esta abordagem permite-nos:
    * **Ocultar dados internos**: Campos como `taxaCondominio` ou `documento` podem ser omitidos.
    * **Formatar os dados**: Um campo booleano `proprietario` foi transformado numa String "Sim" ou "Não" no `MoradorResponseDTO` para uma melhor experiência do cliente.
    * **Orientação ao cliente**: A resposta é desenhada para ser exatamente o que o consumidor da API precisa, nada mais, nada menos.

### Relação entre Entidades, DTOs e Orquestração de Dados

A camada de Serviço (`@Service`) é a responsável por orquestrar a conversão entre as entidades e os DTOs. O fluxo é o seguinte:

1.  Um `Controller` recebe um `RequestDTO` validado.
2.  O `Service` recebe o `RequestDTO`, mapeia os seus dados para uma entidade de domínio (`Morador`, `Agendamento`, etc.) e executa a lógica de negócio (ex: salvar no banco de dados).
3.  Para devolver uma resposta, o `Service` pega a entidade resultante do passo anterior e, se necessário, chama outros serviços (como o `FeriadoApiClient` no `external-api-client`).
4.  Finalmente, o `Service` combina os dados da entidade interna com os dados externos (se houver) e mapeia tudo para um `ResponseDTO`.

O `AgendamentoResponseDTO` é um exemplo claro de **orquestração de dados**: ele contém não só os detalhes do agendamento (dados do `common-domain`), mas também a informação se a data é um feriado e o nome desse feriado (dados do `external-api-client`), fornecendo uma resposta completa e enriquecida para o cliente final.

## Tecnologias Utilizadas

* **Java 21** (ou superior)
* **Spring Boot 3**
* **Spring Data JPA**
* **H2 Database**
* **Bean Validation**
* **JUnit 5 (JUnit Jupiter)**
* **Maven**

## Pré-requisitos

Para executar este projeto localmente, você precisará ter instalado:

* JDK 21 ou superior
* Apache Maven 3.8 ou superior

## Como Executar o Projeto

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/srmatheusmaciel/matheusmacielapi.git
   cd matheusmacielapi
   ```

2. **Compile o projeto com o Maven:**

   ```bash
   ./mvnw clean install
   ```

   *(No Windows, use `mvnw.cmd clean install`)*

3. **Execute a aplicação:**

   ```bash
   java -jar target/matheusmacielapi-*.jar
   ```

A API estará disponível em `http://localhost:8080`.

### Acessando o Banco de Dados (H2 Console)

Com a aplicação em execução, você pode acessar o console do banco de dados em memória:

1. Abra seu navegador e acesse: `http://localhost:8080/h2-console`
2. Use as seguintes credenciais para conectar:

   * **JDBC URL**: `jdbc:h2:mem:portariadb`
   * **User Name**: `sa`
   * **Password**: (deixe em branco)

---

## Documentação da API

A seguir estão os detalhes dos endpoints disponíveis.

#### Respostas de Erro Comuns

* **404 Not Found**: Retornado quando um recurso específico não é encontrado.
* **400 Bad Request**: Retornado para erros de validação de negócio.

---

### Endpoints de Moradores

* `GET /api/moradores`: Lista todos os moradores.
* `GET /api/moradores/{id}`: Busca um morador por ID.
* `GET /api/moradores/buscar?nome=ana`: Busca moradores por parte do nome, ignorando o caso.
* `POST /api/moradores`: Cria um novo morador.
* `PUT /api/moradores/{id}`: Atualiza um morador existente.
* `DELETE /api/moradores/{id}`: Exclui um morador.

#### Gerenciamento de Veículos do Morador

* `POST /api/moradores/{idMorador}/veiculos`: Adiciona um novo veículo a um morador.

  * **Corpo da Requisição (JSON):**

    ```json
    {
        "placa": "XYZ1B23",
        "modelo": "Hyundai HB20",
        "cor": "Prata"
    }
    ```
* `DELETE /api/moradores/{idMorador}/veiculos/{idVeiculo}`: Exclui um veículo específico de um morador.

---

### Endpoints de Visitantes

* `GET /api/visitantes`: Lista todos os visitantes.
* `GET /api/visitantes/{id}`: Busca um visitante por ID.
* `POST /api/visitantes`: Cria um novo visitante.
* `PUT /api/visitantes/{id}`: Atualiza um visitante existente.
* `DELETE /api/visitantes/{id}`: Exclui um visitante.

---

### Endpoints de Controle de Acesso

* `POST /api/visitantes/{idVisitante}/registrar-entrada`: Registra uma nova entrada para um visitante. Não requer corpo. Retorna o objeto `RegistroAcesso` criado.
* `PATCH /api/acessos/{idAcesso}/registrar-saida`: Registra a saída em um registro de acesso existente. Não requer corpo.

---

### Endpoints de Ocorrências

* `GET /api/ocorrencias`: Lista todas as ocorrências.
* `POST /api/ocorrencias/morador/{idMorador}`: Cria uma nova ocorrência associada a um morador.

  * **Corpo da Requisição (JSON):**

    ```json
    {
        "titulo": "Portão da garagem com defeito",
        "descricao": "O portão da garagem está fazendo um barulho estranho ao fechar.",
        "data": "2025-08-26",
        "status": "EM ANDAMENTO"
    }
    ```
* `GET /api/ocorrencias/buscar?status=RESOLVIDA&dataInicio=2025-08-01&dataFim=2025-08-30`: Busca ocorrências por status e intervalo de datas.

---

Cenários de Teste (Ciclo Red-Green-Refactor) 

O AgendamentoService foi desenvolvido seguindo rigorosamente o ciclo TDD. Abaixo estão os principais cenários de teste implementados:

    @DisplayName("Deve agendar com sucesso quando o recurso está disponível e a data é futura")

        Objetivo: Testar o "caminho feliz". Garante que um agendamento válido, para uma data futura e disponível, pode ser criado com sucesso. 

@DisplayName("Deve lançar exceção ao agendar recurso em data já reservada")

    Objetivo: Testar a regra de negócio de conflito. Verifica se o sistema impede que o mesmo recurso seja agendado duas vezes na mesma data, lançando uma exceção. 

@DisplayName("Deve lançar exceção ao tentar agendar para uma data no passado")

    Objetivo: Testar a validação de datas. Assegura que o sistema não permite agendamentos para datas que já passaram, garantindo a integridade dos dados. 

@DisplayName("Deve buscar um agendamento por ID com sucesso")

    Objetivo: Validar a funcionalidade de consulta. Confirma que um agendamento existente pode ser recuperado corretamente pelo seu ID. 

@DisplayName("Deve lançar exceção ao tentar cancelar agendamento inexistente")

    Objetivo: Testar o tratamento de erros em operações de exclusão. Garante que o sistema lida de forma controlada com tentativas de cancelar um agendamento que não existe. 



### Endpoints de Agendamentos (NOVO)

* `GET /api/agendamentos`: Lista todos os agendamentos.
* `GET /api/agendamentos/{id}`: Busca um agendamento por ID.
* `POST /api/agendamentos`: Cria um novo agendamento.

  * **Corpo da Requisição (JSON):**

    ```json
    {
        "idMorador": 1,
        "idRecurso": 1,
        "dataAgendamento": "2025-10-25"
    }
    ```
* `PUT /api/agendamentos/{id}`: Atualiza a data de um agendamento existente.

  * **Corpo da Requisição (JSON):**

    ```json
    "2025-11-05"
    ```
* `DELETE /api/agendamentos/{id}`: Cancela (exclui) um agendamento.

---

## Estrutura de Dados Iniciais

Os dados iniciais são carregados a partir da pasta `/dados`. A estrutura dos arquivos é a seguinte:

* **`blocos.txt`**: `id_no_arquivo;nome`
* **`unidades.txt`**: `id_no_arquivo;numero;descricao;id_do_bloco_no_arquivo`
* **`moradores.txt`**: `nome;documento;telefone;email;id_da_unidade_no_arquivo;proprietario;taxaCondominio`
* **`veiculos.txt`**: `id_do_morador_no_banco;placa;modelo;cor`
* **`visitantes.txt`**: `nome;documento;telefone;email;rg;autorizadoPor`
* **`ocorrencias.txt`**: `id_do_morador_no_banco;titulo;descricao;data(AAAA-MM-DD);status`

---
