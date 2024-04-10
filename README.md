# Prova da Infuse para desenvolvedor Java

Esta é uma API desenvolvida para responder à prova da [Infuse](https://www.infuse.srv.br/).

## Demonstração

Para demonstração, esta API foi publicada na [Heroku](https://dashboard.heroku.com/)
- Link para acesso da demo: [https://prova-infuse-2535c756b63c.herokuapp.com/](https://prova-infuse-2535c756b63c.herokuapp.com/)

## Execução local
Caso deseje executar a API localmente, certifique-se de ter o docker rodando em sua máquina.
   
### Banco de Dados
O Banco de Dados é gerado automaticamente ao executar a aplicação
graças ao JPA + Hibernate.<br>
Mesmo assim, caso queira consultar o script SQL para DDL (criação) e DML (inserção) no MySQL, consulte-o [aqui (`database-script.sql`)](database-script.sql)

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
- <strong>Docker Compose:</strong> Conteinerizar o banco de dados
## Rotas (endpoints) da API

### $\color{cyan}{GET}$ `/pedido` &emsp; Exibir todos os pedidos &emsp; [exemplo](https://prova-infuse-2535c756b63c.herokuapp.com/pedido)
<strong>Query params:</strong>
- `cliente` (Integer): &emsp; Filtra os pedidos por código do cliente (`não obrigatório`) &emsp; [exemplo](https://prova-infuse-2535c756b63c.herokuapp.com/pedido?cliente=1)

### $\color{cyan}{GET}$ `/pedido/filtro` &emsp; Filtrar os pedidos &emsp; [exemplo](https://prova-infuse-2535c756b63c.herokuapp.com/pedido/filtro)
<strong>Query params:</strong>
- `numeroControle` (Integer): &emsp; Busca o pedido específico (`não obrigatório`) &emsp; [exemplo](https://prova-infuse-2535c756b63c.herokuapp.com/pedido/filtro?numeroControle=1)
- `dataCadastro` (Date `"dd-MM-yyyy"`): &emsp; Busca os pedidos daquele dia (`não obrigatório`) &emsp; [exemplo](https://prova-infuse-2535c756b63c.herokuapp.com/pedido/filtro?dataCadastro=10-04-2024)

<strong>Obs.:</strong><br>
Caso nenhum deles seja passado, todos os pedidos seão exibidos;<br>
Caso `numeroControle` seja passado, `dataCadastro` será ignorado.

### $\color{green}{POST}$ `/pedido` &emsp; Insere de 1 a 10 pedidos
Recebe uma lista de pedidos (máximo 10) no corpo da requisição (formato JSON ou XML).

```json
JSON body example
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

```xml
XML body example
<List>
  <item>
    <numeroControle>1</numeroControle>
    <nome>Pedido 1 (xml)</nome>
    <valor>2.50</valor>
    <quantidade>2</quantidade>
    <codigoCliente>1</codigoCliente>
  </item>
  ...
</List>
```
