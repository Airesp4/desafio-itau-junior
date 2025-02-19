# Desafio Itaú Unibanco - Solução

Este repositório contém a solução para o desafio de programação proposto pelo Itaú Unibanco, onde foi desenvolvido uma API REST para o gerenciamento de transações financeiras e cálculo de estatísticas. A API foi construída utilizando **Java** e **Spring Boot**, com as seguintes funcionalidades implementadas:

- Receber transações com valor e dataHora;
- Calcular estatísticas das transações que aconteceram nos últimos 60 segundos;
- Limpar todas as transações armazenadas;
- Validação de regras de negócio para aceitação de transações.

## Estrutura do Projeto

A solução segue a arquitetura de uma aplicação Spring Boot com endpoints REST. Os principais componentes são:

- **Controllers**: Responsáveis pelos endpoints da API.
- **Services**: Contêm a lógica de negócios da aplicação.
- **Modelos**: Representam os objetos de transação e estatísticas.

## Endpoints

A seguir estão os endpoints disponíveis na API:

### `POST /transacao`

Este endpoint recebe uma transação e a armazena em memória. A transação deve conter os campos `valor` e `dataHora`.

#### Exemplo de requisição:

```json
{
    "valor": 123.45,
    "dataHora": "2020-08-07T12:34:56.789-03:00"
}
```

#### Respostas esperadas:

- **201 Created**: Caso a transação seja validada e registrada com sucesso.
- **422 Unprocessable Entity**: Caso a transação não atenda a um ou mais critérios de validação.
- **400 Bad Request**: Caso o corpo da requisição seja inválido.

### `DELETE /transacao`

Este endpoint limpa todas as transações armazenadas em memória.

#### Respostas esperadas:

- **200 OK**: Caso todas as transações tenham sido apagadas com sucesso.

### `GET /estatistica`

Este endpoint calcula e retorna as estatísticas das transações que ocorreram nos últimos 60 segundos. As estatísticas incluem:

- `count`: Número de transações realizadas.
- `sum`: Soma total do valor transacionado.
- `avg`: Média dos valores transacionados.
- `min`: Menor valor transacionado.
- `max`: Maior valor transacionado.

#### Resposta esperada:

```json
{
    "count": 10,
    "sum": 1234.56,
    "avg": 123.456,
    "min": 12.34,
    "max": 123.56
}
```

## Regras de Validação

A API realiza as seguintes validações para as transações:

- **Valor da transação**: Não pode ser negativo, deve ser maior ou igual a zero.
- **Data da transação**: Não pode ser uma data futura.
- **Campos obrigatórios**: `valor` e `dataHora` são obrigatórios.

## Como Rodar o Projeto

1. Clone o repositório para sua máquina local:
   ```bash
   git clone https://github.com/Airesp4/desafio-itau-junior.git
   ```

2. Navegue até o diretório do projeto:
   ```bash
   cd desafio-itau-junior
   ```

3. Abra o projeto no seu IDE (por exemplo, IntelliJ IDEA, Eclipse, ou Visual Studio Code).
   
4. Execute o projeto utilizando o Spring Boot (por exemplo, a partir do arquivo `TransactionChallengeApplication.java`).

5. A API estará disponível localmente em `http://localhost:8080`.

## Testes Automatizados

A solução inclui testes unitários para os componentes principais da aplicação, como validação de transações e cálculo das estatísticas. Os testes podem ser executados diretamente na IDE ou via comando:

```bash
mvn test
```

## Extras

- **Logs**: A aplicação faz uso de logging para rastrear erros e informações relevantes.
- **Healthcheck**: Implementado para monitorar a saúde da aplicação.

## Tecnologias Utilizadas

- **Java 17** ou superior
- **Spring Boot**
- **JUnit 5** (para testes automatizados)
- **Maven** (para gerenciamento de dependências)
