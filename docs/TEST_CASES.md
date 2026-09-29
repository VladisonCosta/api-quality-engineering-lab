# Test Cases

| ID | Scenario | Expected |
|---|---|---|
| API-001 | POST without API key | 401 Unauthorized |
| API-002 | Create valid product | 201, Location header and persisted body |
| API-003 | Invalid required fields | 400 with validationErrors |
| API-004 | Duplicate SKU | 409 Conflict |
| API-005 | Get existing product | 200 with correct product |
| API-006 | Get nonexistent product | 404 Not Found |
| API-007 | Paginated listing | 200 with page metadata and expected size |
| API-008 | Update existing product | 200 with modified data |
| API-009 | Delete existing product | 204 and subsequent GET = 404 |
| API-010 | JSON Schema validation | Response follows product contract |
| API-011 | Content-Type contract | `application/json` |
