# Account Service

**Grupo:** Alex Chequer · Carlos · Lucas
**Disciplina:** Plataformas, Microserviços, DevOps e APIs — Insper 2026.1

---

## Sobre o serviço

Microsserviço de gerenciamento de contas de usuários: criação, consulta
e validação de credenciais. É usado pelo `auth-service` (via OpenFeign)
para autenticar logins e pelo gateway para repassar `id-account` no
header das requisições autenticadas.

## Stack

| Item | Detalhe |
|---|---|
| Linguagem | Java 25 |
| Framework | Spring Boot 4.0.3 |
| Banco | PostgreSQL (schema `accounts`) |
| Migrations | Flyway |
| Observabilidade | Spring Actuator + Micrometer Prometheus |

## Schema (Flyway)

| Migration | Conteúdo |
|---|---|
| `V2026.03.04.001__create_schema.sql` | `CREATE SCHEMA accounts` |
| `V2026.03.04.002__create_table.sql` | `accounts.accounts(id, name, email)` |
| `V2026.03.06.001__create_field_pass_sha256.sql` | adiciona `password_sha256 VARCHAR(64)` |
| `V2026.03.12.001_create_index.sql` | `idx_email_sha256` |
| `V2026.05.06.001__widen_id_column.sql` | `id` `VARCHAR(32)` → `36` (UUID v4 com hífens) |

## Status de entrega

| Atividade | Status |
|---|---|
| Endpoints (`/accounts`, `/accounts/login`, health) | ✅ |
| Schema com Flyway | ✅ |
| k8s manifests | ✅ (`k8s/`) |
| Jenkinsfile + Deploy to EKS | ✅ |

## Docker Hub

`cheqr/account:latest`
