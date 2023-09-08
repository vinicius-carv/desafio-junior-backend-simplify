# Info sobre o projeto
### Tecnologias utilizadas

- Java ;)
- Spring MVC
- Java POJO
- RESTful API
- Redis Server
- MySQL
- HTML
- CSS
- JavaScript

### Como Executar
0. Requisitos
   - MySQL;
   - Java;
   - Maven;
   - redis-server;
1. Preparar o ambiente
   - Utilizar o script init.sql no diretorio /mysql-init para criar o banco de dados, tabela e usuario;
   - Instalar o 'redis-server' para intermediar operacoes entre o app Spring e o banco de dados:
   - Ubuntu:
   - > sudo snap install redis
   - Fedora:
   - > sudo dnf install redis
2. Buildar o app:
   - Apos clonar o repositorio, navegue ate a pagina raiz:
     > cd desafio-junior-backend-simplify
   - E crie uma build do projeto:
     > mvn clean install
3. Com o projeto buildado, agora e so executar:
   > cd target
   > java -jar DesafioSimplifyTec-0.0.1-SNAPSHOT.jar
4. O app esta rodando, visite:
   - [http://localhost:8080](http://localhost:8080)
5. Aproveite!

---------------
# Desafio sistema de gerenciamento de tarefas (To-Do List)
Repositório para ser usado pelos candidatos à vaga de Desenvolvedor Júnior Backend Liferay da Simplify

## Descrição
- Desenvolva uma aplicação web utilizando uma linguagem de programação e um framework de sua escolha. A aplicação deve consistir em um sistema de gerenciamento de tarefas, onde os usuários podem criar, visualizar, editar e excluir tarefas.

## Requisitos
- Usar banco de dados
- Campos mínimos da entidade de tarefa
    - Nome
    - Descrição
    - Realizado
    - Prioridade
- Criar CRUD de tarefas

## Instruções
- Fazer um fork do repositório para sua conta pessoal do git
- Trabalhar utilizando commits
- Documentar como executar sua aplicação
- Descrever as funcionalidades do software
