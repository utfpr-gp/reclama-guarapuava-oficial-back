[![Build Status](https://travis-ci.org/utfpr-gp/reclama-guarapuava-oficial-back.svg?branch=master)](https://travis-ci.org/utfpr-gp/reclama-guarapuava-oficial-back)

# Reclama Guarapuava - API

É uma API Restful para publicação e monitoramento de problemas de infraestrutura que ocorrem diariamente na cidade de Guarapuava - PR.

A API está sendo desenvolvida pela turma do segundo semestre de 2018 da disciplina de Web IV do curso de TSI da UTFPR do Campus Guarapuava.

## Principais Funcionalidades

- Cadastro de categorias e problemas de infraestrutura
- Cadastro de ocorrências de problemas
- Cadastro de bairros
- Filtragem das ocorrências por bairro e categorias
- Listagem e controle de estado das ocorrências cadastradas por um usuário
- Apresentação de relatórios
- Controle de curtidas e comentários por ocorrências
- Autenticação e autorização para os papeis de usuário, gerente e administrador

## Online
- [Link para a API Online - Reclama Guarapuava](https://reclama-guarapuava-api.herokuapp.com/)
- [Link para a documentação Swagger](https://reclama-guarapuava-api.herokuapp.com/swagger-ui.html)

## Tecnologias

- Spring Boot
- Spring Data
- Spring MVC
- Spring Security
- JWT
- Lombok
- Swagger

## Ferramentas

- STS
- Tomcat (Embutido)
- MySQL
- H2
- Maven

## Manual de Execução

- Clonar o repositório com `git clone`
- Entrar na raiz do projeto via prompt de comando e instalar as dependência definidas no arquivo `pom.xml` do Maven
  - Digite o comando `mvn clean install`
- Criar um novo banco de dados no MySQL com o nome `reclama_guarapuava_db`
- Opcionalmente, usar o banco de dados em memória alterando o arquivo `application.properties` para uso do perfil `test`
  - Já está configurado com o login e senha padrão
- Executar o projeto como Spring Boot.

## Versão Corrente

0.0.1 - Release de ??/12/2018
