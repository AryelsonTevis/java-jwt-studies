# Estudos sobre jwt
API REST desenvolvida com Spring Boot para estudo de conceitos de jwt, validações em MySQL.
## 🚀 Tecnologias

- Java 27
- DataBase H2 in memory
- Lombok
- JWT
- Spring Validation
## 📌 Funcionalidades

### User (`/User`)
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/users/auth/register` | Cria um novo Usuario |
| POST | `/users/auth/login` | Loga o Usuario |
| GET | `/users/me` |Busca informações do usuario usando um token jwt|

O projeto também conta com um `RestExceptionHandler` global para retornar respostas de erro padronizadas.

## ⚙️ Como rodar o projeto

### Pré-requisitos
- Java 27+
- Maven (ou use o `./mvnw` incluso no projeto)

1. Clone o repositório:
   ```bash
   git clone https://github.com/AryelsonTevis/java-jwt-studies.git
   cd jwtJava
   ```

2. Crie um arquivo `.env` na raiz do projeto com suas credenciais de banco:
   ```
   DB_URL=jjdbc:h2:mem:jwtusers
   DB_CLASSNAME=org.h2.Driver
   DB_USERNAME=seu_usuario
   DB_PASSWORD=sua_senha
   DB_PLATFORM= org.hibernate.dialect.H2Dialect
   API_SECURITY_TOKEN_SECRET="sua_secret_para_o_JWT"
   
   ```
3. Rode a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```
4. A API estará disponível em `http://localhost:8080`.

## 📄 Licença

Este projeto está sob a licença MIT.