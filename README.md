# API <#Educaweb>

## Sobre o projeto
Essa API foi feita para ser consumida pelo cliente do meu projeto pessoal, o Educaweb!

## Construção:
- Java: Linguagem de programação, necessária estar instalada na versão 17;
- Spring Boot: Projeto do ecossistema Spring que facilita a configuração do projeto, como dependências, configurações e servidores embutidos.
- Jpa: Mapeamento objeto-relacional.
- PostgreSQL Driver: Componente para integração com banco postgres.
- Spring Security: Projeto do ecossistema Spring que disponibiliza configurações de segurança, como criptografia e autenticação.
- Lombok: Biblioteca de anotações para gerar códigos repetitivos automaticamente.

## Motivação
O projeto surgiu a partir da necessidade de criar um servidor próprio para a plataforma EducaWeb, um projeto pessoal.

## Próximos passos
- Realizar Autenticação;
- Fazer deploy da aplicação em nuvem.
- Fazer deploy do banco de dados em nuvem.

## Pré-requisitos para funcionamento:
- Ter o jdk-17 ou superior instalado em sua máquina.

## Instruções para execução
1. Faça o clone do projeto;
2. Abra a pasta raiz no terminal e digite o comando:
```
mvn clean install
```
3. Após isso, digite: 
```
mvn package
```
4. Será gerado um arquivo .jar na pasta target do projeto, basta agora digitar:
```
java -jar target/nome-do-arquivo.jar
```
## API Endpoints
Para consultar todos os endpoints da API, exigências e possíveis retornos. Basta acessar http://localhost:8081/swagger-ui/index.html#/


