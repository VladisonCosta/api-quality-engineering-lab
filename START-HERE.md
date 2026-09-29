# START HERE — 3 passos

## 1. Validar o projeto
Abra a pasta no VS Code e execute:

```powershell
mvn clean test
```

Resultado esperado: **11 testes, 0 falhas, 0 erros, BUILD SUCCESS**.

Para rodar a API manualmente:
```powershell
mvn spring-boot:run
```
A API ficará em `http://localhost:8080/api/products`.

## 2. Testar manualmente / Postman
Importe:
`postman/API-Quality-Engineering-Lab.postman_collection.json`

A chave demo é `qa-secret-key`. Ela é propositalmente pública porque faz parte de uma aplicação de laboratório local, não de um sistema real.

## 3. Publicar no GitHub
Depois do BUILD SUCCESS:
```powershell
git init -b main
git add .
git commit -m "feat: add API quality engineering lab"
```

Crie um repositório público chamado `api-quality-engineering-lab`, conecte o remote e faça o push. O GitHub Actions executará os testes automaticamente.

Depois de validar localmente e no CI, use `docs/CV_BULLETS.md` para atualizar o currículo.
