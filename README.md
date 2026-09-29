# API Quality Engineering Lab

[![API Quality Tests](https://github.com/VladisonCosta/api-quality-engineering-lab/actions/workflows/api-tests.yml/badge.svg)](https://github.com/VladisonCosta/api-quality-engineering-lab/actions/workflows/api-tests.yml)

Projeto de portfólio focado em **Quality Assurance e automação de testes de API**, utilizando uma API Spring Boot autocontida para permitir testes reproduzíveis localmente e em CI.

## O que este projeto demonstra

- Automação de API com **Java + RestAssured + JUnit 5**
- Testes positivos e negativos
- Validação de status code, headers, content type e JSON body
- Autenticação simples por `X-API-Key`
- Validação de payloads e regras de negócio
- CRUD completo e persistência com **Spring Data JPA + H2**
- Paginação
- **JSON Schema Validation**
- Testes manuais/exploratórios com **Postman**
- Evidências com **Allure/Surefire**
- Execução contínua com **GitHub Actions**
- Documentação de plano de testes, casos e rastreabilidade

## Arquitetura

```text
Postman / RestAssured
        |
        v
 Spring Boot REST API
        |
        v
 Service + Validation
        |
        v
 Spring Data JPA
        |
        v
      H2 DB
```

## Endpoints

| Method | Endpoint | Auth | Purpose |
|---|---|---|---|
| GET | `/api/products` | No | Listar/paginar produtos |
| GET | `/api/products/{id}` | No | Consultar produto |
| POST | `/api/products` | `X-API-Key` | Criar produto |
| PUT | `/api/products/{id}` | `X-API-Key` | Atualizar produto |
| DELETE | `/api/products/{id}` | `X-API-Key` | Excluir produto |

## Suíte automatizada

A suíte contém **11 cenários** cobrindo:

1. Autenticação ausente/inválida
2. Criação válida
3. Payload inválido
4. SKU duplicado
5. Consulta de recurso existente
6. Recurso inexistente
7. Paginação
8. Atualização
9. Exclusão
10. JSON Schema
11. Content-Type

## Executar testes

Pré-requisitos: Java 17+ e Maven.

```bash
mvn clean test
```

## Executar a API manualmente

```bash
mvn spring-boot:run
```

Base URL:
```text
http://localhost:8080/api
```

API key de laboratório:
```text
qa-secret-key
```

Exemplo:
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -H "X-API-Key: qa-secret-key" \
  -d '{"name":"Keyboard","sku":"KEY-001","price":299.90,"stock":15}'
```

## Postman

Importe:
```text
postman/API-Quality-Engineering-Lab.postman_collection.json
```

## Documentação QA

- `docs/TEST_PLAN.md`
- `docs/TEST_CASES.md`
- `docs/TRACEABILITY_MATRIX.md`
- `docs/BUG_REPORT_TEMPLATE.md`
- `docs/openapi.yaml`
- `docs/INTERVIEW_STUDY_GUIDE.md`

## CI

O workflow `.github/workflows/api-tests.yml` executa `mvn clean test` a cada push/PR em `main` e publica os relatórios como artifact.

## Estrutura

```text
api-quality-engineering-lab/
├── .github/workflows/api-tests.yml
├── docs/
├── postman/
├── src/main/java/...       # API de laboratório
├── src/main/resources/
├── src/test/java/...       # automação RestAssured
├── src/test/resources/     # JSON Schema + Allure
├── pom.xml
├── README.md
└── START-HERE.md
```

## Próximas evoluções

- PostgreSQL + Testcontainers
- Performance smoke tests com k6
- Contract testing
- Testes de segurança
- Paralelização controlada

## Autor

**Vladison Costa**  
GitHub: https://github.com/VladisonCosta
