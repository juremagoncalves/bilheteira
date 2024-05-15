# Bilheteira

O projeto Bilheteira é um sistema de reserva de bilhetes para empresas de transporte terrestre, desenvolvido com Spring Boot usando o Java 17, PostgreSQL 15 e pgAdmin 4.

# Clonar o Repositório
git clone https://github.com/juremagoncalves/bilheteira.git

# Configurar o PostgreSQL

Certifique-se de ter o PostgreSQL 15 instalado e o pgAdmin 4 para gerenciar o banco de dados.

Crie um banco de dados chamado bilheteira_db no pgAdmin 4.

# Configurar o Spring Boot
Abra o arquivo src/main/resources/application.properties e configure as seguintes propriedades:
spring.datasource.username=seu-usuario
spring.datasource.password=sua-senha

# Script do Banco de Dados

Na raiz do projeto, você encontrará um arquivo script.sql. Execute este script no seu banco de dados PostgreSQL para criar as tabelas e popular o banco de dados com dados de exemplo.

# Alguns Endpoints Disponíveis

O aplicativo estará disponível em http://localhost:8080/

POST /auth/login

POST /auth/register

UPDATE /rota/update/{pkRota}

CREATE /transporte/
DELETE /transporte/{pkTransporte}
