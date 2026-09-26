# DevShowcase API — Etapa Final

Projeto preparado para a etapa final: regras de negócio, feedback com nota média, upvote, filtro/paginação, tratamento global de erros e Swagger/OpenAPI.

## Tecnologias
- Java 21
- Spring Boot 3.5.5
- Spring Data JPA
- PostgreSQL
- Bean Validation
- Swagger/OpenAPI (Springdoc)
- Maven
- Lombok

## Endpoints da etapa final

### 1. Feedback
`POST /api/projects/{id}/feedbacks`

Exemplo:
```json
{
  "rating": 5,
  "comment": "Projeto muito bom!",
  "author": "Lucas José de Sousa"
}
```

A nota deve estar entre 1 e 5. Após salvar, a média do projeto é recalculada e atualizada.

### 2. Upvote
`PUT /api/projects/{id}/upvote`

Cada chamada incrementa `upvotes` em 1.

### 3. Projetos com filtro e paginação
`GET /api/projects?technology=Java&page=0&size=10`

Parâmetros:
- `technology`: opcional, filtra pelo nome da tecnologia.
- `page`: começa em 0.
- `size`: entre 1 e 100.

## Tratamento global de erros
A API possui `ApiExceptionHandler` para:
- 400 — validação e parâmetros inválidos;
- 404 — recurso inexistente;
- 409 — conflito de regra/banco;
- 500 — erro interno não tratado.

## Swagger
Com a API executando:
- Swagger UI: `/swagger-ui.html`
- OpenAPI JSON: `/v3/api-docs`

Exemplo local:
`http://localhost:8080/swagger-ui.html`

## Banco local
Crie o banco PostgreSQL `devshowcase` e configure `application-local.properties`:

```properties
DB_USERNAME=postgres
DB_PASSWORD=sua_senha
```

Esse arquivo não deve ir para o GitHub.

## Variáveis para produção
No Render, configure pelo menos:
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`

Para facilitar produção, a configuração pode ser adaptada para a URL JDBC fornecida pelo provedor.

## Postman
Use:
`postman/DevShowcase-API-Etapa-Final.postman_collection.json`

A coleção contém:
- criação de dados de apoio;
- GET com paginação;
- GET com filtro por tecnologia;
- POST feedback válido;
- POST feedback provocando 400;
- PUT upvote;
- requisição para recurso inexistente provocando 404;
- acesso ao Swagger.

## Antes do GitHub
Não envie senhas ou tokens. O arquivo `application-local.properties` deve permanecer fora do Git.

## Execução
No IntelliJ, execute `DevshowcaseApplication`.

A API local ficará em:
`http://localhost:8080`

