# Sistema de Votação
Este projeto implementa um sistema de votação para ser usado em assembleias de cooperativas. Os associados podem votar em pautas, e os resultados são contabilizados e enviados via RabbitMQ para processamento posterior.

## Tecnologias
- Spring Boot
- PostgreSQL
- RabbitMQ
- Docker
- JUnit 5 e Mockito (para testes)

## Para executar a aplicação, siga os passos abaixo: 
- Postgres 17 com uma database chamada “voting_system 
- Configure o arquivo  application.properties com o seu usuario e senha do banco de dados, no projeto java. 
- Java 17 para iniciar o projeto 
- Clone o repositório: 
- git clone [URL do repositório] 
- Instale as dependências: Execute o comando: mvn install 
- Inicie a aplicação: Execute o comando: mvn spring-boot:run
- Com o Docker instalado, execute o seguinte comando na raiz do projeto: docker-compose up 

## Endpoints da API
 - Documentação de como usar os Endpoints : https://documenter.getpostman.com/view/34825244/2sAYJ3G2jv
