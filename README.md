# Gerenciador de Projetos API

## Descrição

A Project Management API é uma aplicação RESTful desenvolvida com Spring Boot com arquitetura em camadas seguindo o padrão MVC (Model-View-Controller), facilitando a separação de responsabilidades e a manutenção do código. 

O sistema é projetado para gerenciar os projetos da empresa, facilitando o planejamento e acompanhamento dos times, projetos, clientes e tarefas.


## Inicializar API, Postgresql e PgAdmin
Navegue até o diretório docker:
```bash
cd docker
```
Remova os containers, networks e volumes preexistentes no arquivo docker-compose.yaml:
```bash
docker-compose down
```
Inicie os serviços definidos no arquivo docker-compose.yaml:
```bash
docker-compose up
```

## Acesso aos Serviços 
API disponível no Swagger UI:
```bash
http://localhost:8080/swagger-ui/index.html
```
PgAdmin com login através de **admin@gmail.com** e senha **admin** acessível em:
```bash
http://localhost:16543/login?next=%2F
```
##### Adicione um Novo Servidor no PgAdmin:
![pgAdmin](https://github.com/JuhLima85/Gerenciador-de-Projetos/assets/89745459/ea99b7be-f93b-4bf4-9543-1eaf82b59a68)

## Tecnologias Utilizadas
- Java 17
- Java Spring
- Maven
- Apache Tomcat
- Banco de Dados PostgreSQL
- Docker
- Swagger
- JUnit
- Mockito

## A API fornece os seguintes endpoints:
```bash
## Cliente
GET /api/clientes
GET /api/clientes/{id}
POST /api/clientes
PUT /api/clientes/{id}
DELETE /api/clientes/{id}

## Projeto
GET /api/projetos
GET /api/projetos/{id}
GET /api/projetos/em-aberto
POST /api/projetos
PUT /api/projetos/{id}
DELETE /api/projetos/{id}

## Atividade
POST /api/atividades
```

## Funcionalidades

- **Clientes**: Gerenciamento de clientes, incluindo criação, leitura, atualização e exclusão de registros.
- **Projetos**: Gerenciamento de projetos, incluindo criação, leitura, atualização e exclusão de registros. Os projetos são associados aos clientes.
- **Atividades**: Inclusão de atividades que são associadas aos projetos e, por consequência, aos clientes.
- **Listagem de Projetos em Aberto**: Endpoint que lista os projetos em aberto, mostrando a qual cliente pertence e permitindo visualizar a lista de atividades cadastradas para o projeto.
 
## Diagrama de Classe
![Diagrama de Classe](https://github.com/JuhLima85/Gerenciador-de-Projetos/assets/89745459/4908b0fc-5fa1-4525-90a5-97fcfa1b588d)

## Diagrama Entidade-Relacionamento
![DER](https://github.com/JuhLima85/Gerenciador-de-Projetos/assets/89745459/7da6eee5-9a42-4b8d-b73c-fbb8ab505dc0)

## Tratamento de Erro de Validação
![Erro](https://github.com/JuhLima85/Gerenciador-de-Projetos/assets/89745459/e8f15fa8-9221-4ad7-a7eb-42744c50af87)

## Testes Unitários
A API possui testes unitários utilizando JUnit e Mockito para garantir a qualidade e a confiabilidade do código. Esses testes cobrem as funcionalidades principais dos serviços, verificando o comportamento esperado das operações de CRUD e outros serviços oferecidos pela aplicação.
![testes](https://github.com/JuhLima85/Gerenciador-de-Projetos/assets/89745459/629e68a1-2c0d-46e7-9dd0-327abf6274e6)

## Desenvolvedora:
Juliana Lima de Souza

[![Linkedin](https://img.shields.io/badge/-LinkedIn-%230077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/feed/?trk=guest_homepage-basic_nav-header-signin)
[![WhatsApp](https://img.shields.io/badge/WhatsApp-25D366?style=for-the-badge&logo=whatsapp&logoColor=white)](https://contate.me/Juliana-Lima)
[![Portfólio](https://img.shields.io/badge/Portf%C3%B3lio-%E2%9C%88%EF%B8%8F-lightgrey?style=for-the-badge)](https://codedeving.netlify.app/)
