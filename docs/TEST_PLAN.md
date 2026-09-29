# Test Plan — Product API

## Objective
Validate functional behavior, HTTP contracts, validation rules, authentication, persistence and error handling of the Product API.

## Scope
- POST `/api/products`
- GET `/api/products/{id}`
- GET `/api/products`
- PUT `/api/products/{id}`
- DELETE `/api/products/{id}`

## Test types
- Functional API tests
- Positive and negative scenarios
- Authentication checks
- Validation and business-rule checks
- HTTP status/header/body validation
- JSON Schema contract validation
- CRUD integration with H2 database
- Pagination validation

## Entry criteria
- Java and Maven available
- Application compiles
- Test database available through Spring Boot/H2

## Exit criteria
- Automated suite executes with zero failures/errors
- CI workflow is green
- Evidence is uploaded by GitHub Actions

## Risks
- Dependency/network download issues on first Maven execution
- Contract changes require schema/test updates
- Local port conflicts only affect manual execution, not RANDOM_PORT automated tests
