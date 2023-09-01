# Sistema de Gerenciamento de Estoque

## Descrição
Este é um sistema de gerenciamento de estoque desenvolvido utilizando o Spring Framework e um banco de dados PostgreSQL. O sistema permite a criação, atualização, leitura e exclusão de produtos no estoque por meio de requisições HTTP. As interações com o sistema podem ser simuladas usando o Postman.

## Requisitos
Para criar este sistema, foram utilizados os seguintes requisitos:

- Utilização do Spring Framework (Spring Boot, Spring Data JPA) para criar a aplicação.
- Uso de um banco de dados PostgreSQL ou H2 para armazenar os dados dos produtos.
- Criação de endpoints REST para as operações CRUD (criação, leitura, atualização, exclusão) de produtos.
- Possibilidade de criar novos produtos com informações como nome, descrição, preço e quantidade que deseja armazenar.
- Atualização dos detalhes de um produto existente.
- Permissão para obter a lista de todos os produtos ou de um produto específico por ID ou nome.
- Capacidade de excluir produtos.
- Implementação de tratamento de erros e exceções apropriados.
- Realização de testes unitários com cobertura de 100%.
- Documentação da API com o Swagger, tornando-a compreensível para outros desenvolvedores.

## Funcionalidades Adicionais

### Gerenciamento de Categorias

Além do gerenciamento de produtos, nosso sistema também oferece a capacidade de gerenciar categorias. As categorias são atributos associados a cada produto e ajudam na organização do estoque.

#### Funcionalidades de Gerenciamento de Categorias

- Criação de novas categorias com informações como nome e descrição.
- Atualização dos detalhes de uma categoria existente.
- Obtenção da lista de todas as categorias ou de uma categoria específica por ID ou nome.
- Exclusão de categorias.

### Registro de Saídas e Entradas

Para manter um controle preciso do estoque, nosso sistema permite o registro de saídas e entradas de produtos. Isso atualiza automaticamente a quantidade atual de cada produto com base nessas operações.

#### Funcionalidades de Registro de Saídas e Entradas

- Registro de saídas de produtos, indicando o produto, a quantidade e a data da saída.
- Registro de entradas de produtos, indicando o produto, a quantidade e a data da entrada.
- Atualização automática da quantidade atual de produtos com base nas operações de saída e entrada.

## Uso
Para usar o sistema, siga estas etapas:

1. Certifique-se de ter o PostgreSQL instalado e configurado ou utilize o banco de dados H2 em ambiente de desenvolvimento.
2. Clone este repositório em sua máquina local.
3. Configure as informações do banco de dados no arquivo `application.properties`.
4. Execute a aplicação usando o comando `./mvnw spring-boot:run` (ou `mvnw.cmd` no Windows).
5. Use o Postman ou qualquer cliente HTTP para acessar os endpoints da API.

## Documentação da API
A API está documentada usando o Swagger, o que torna mais fácil para outros desenvolvedores entenderem como interagir com o sistema. Você pode acessar a documentação da API em `http://localhost:8080/swagger-ui/index.html#/` após iniciar a aplicação.

## Contribuições
Contribuições são bem-vindas! Sinta-se à vontade para abrir problemas (issues) ou enviar pull requests com melhorias.
