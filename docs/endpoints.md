# Endpoints

| Método | Path | Descrição |
|---|---|---|
| `POST` | `/accounts` | Cria conta. Body: `{name, email, password}` (sem hash). Internamente armazena `password_sha256`. Retorna `201 Created`. |
| `POST` | `/accounts/login` | Verifica credenciais. Body: `{email, password}`. Retorna `{idAccount, name, email}` em `200 OK` ou `401 Unauthorized`. |
| `GET` | `/accounts/health-check` | Liveness/readiness — rota aberta no gateway. |
| `GET` | `/actuator/prometheus` | Métricas Prometheus (latência, GC, JVM, Hikari). |

## Auth flow

```mermaid
sequenceDiagram
    actor User
    participant Gateway
    participant Auth as auth-service
    participant Account as account-service

    User->>Gateway: POST /auth/login {email, password}
    Gateway->>Auth: POST /auth/login
    Auth->>Account: POST /accounts/login (Feign)
    Account-->>Auth: {idAccount, name, email}
    Auth-->>Gateway: Set-Cookie: __store_jwt_token
    Gateway-->>User: 200 OK + cookie
```
