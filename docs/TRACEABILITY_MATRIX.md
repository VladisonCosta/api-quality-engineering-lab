# Traceability Matrix

| Requirement | Automated test |
|---|---|
| Mutations require API key | `shouldRejectMutationWithoutApiKey` |
| Product can be created | `shouldCreateProduct` |
| Invalid payload is rejected | `shouldRejectInvalidPayload` |
| SKU must be unique | `shouldRejectDuplicateSku` |
| Product can be queried | `shouldGetProductById` |
| Missing resources return 404 | `shouldReturnNotFound` |
| Collection supports pagination | `shouldListProductsWithPagination` |
| Product can be updated | `shouldUpdateProduct` |
| Product can be deleted | `shouldDeleteProduct` |
| Response respects schema | `shouldMatchProductJsonSchema` |
| API responds in JSON | `shouldReturnJsonContentType` |
