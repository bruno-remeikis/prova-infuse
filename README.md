# Prova da Infuse para desenvolvedor Java

Esta é uma API desenvolvida para responder à prova da [Infuse](https://www.infuse.srv.br/).

## Tecnologias
Para o desenvolvimento desta API, foram utilizadas as seguintes tecnologias:
- <strong>Java 21:</strong> Linguagem de programação
  - <strong>Spring Boot:</strong> framework para criação de APIs REST
  - <strong>JPA:</strong> Mapear as entidades do banco de dados
  - <strong>Hibernate:</strong> Acessar e manipular banco de dados (ORM)
  - <strong>Lombok:</strong> Tornar criação de classes model menos verbosa
  - <strong>JUnit:</strong> Biblioteca para testes unitários
  - <strong>Mockito:</strong> Biblioteca para mock de dados
  - <strong>FasterXML Jackson Dataformat:</strong> Permite entrada de objetos no formato XML
- <strong>MySQL:</strong> Banco de dados
## Rotas (endpoints) da API

### $\color{cyan}{GET}$ `/pedido` &emsp; Exibir todos os pedidos

### $\color{cyan}{GET}$ `/pedido/filtro` &emsp; Filtrar os pedidos
<strong>Query params:</strong>
- `numeroControle` (Integer): &emsp; Busca o pedido específico
- `dataCadastro` (Date `"dd-MM-yyyy"`): &emsp; Busca os pedidos daquele dia

### $\color{green}{POST}$ `/pedido` &emsp; Insere de 1 a 10 pedidos
```json
Body example
[
    {
        "numeroControle": 1,
        "dataCadastro": null, // Caso `null`, insere a data atual
        "nome": "Pedido 1",
        "valor": 2.50,
        "quantidade": 2, // Caso `null`, insere 1
        "codigoCliente": 1
    },
    ...
]
```
