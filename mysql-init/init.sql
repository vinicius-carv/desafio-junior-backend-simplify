create schema if not exists simplifytec;
CREATE USER 'simplifytecpage'@'localhost' identified by 'Uma_Senha_Forte144!';
GRANT ALL PRIVILEGES ON simplifytec.* to 'simplifytecpage'@'localhost';
FLUSH PRIVILEGES;
use simplifytec;
create table if not exists usuario(
    id int NOT NULL AUTO_INCREMENT unique,
    nome varchar(100) not null,
    email varchar(100) not null unique,
    senha varchar(100) not null,
    primary key(id)
    );
insert into usuario(nome, email, senha) values('Joao do teste', 'joao@teste.com','123@senhaDeTeste!');
create table if not exists tarefa(
    id int not null AUTO_INCREMENT unique,
    nome varchar(255) not null,
    descricao varchar(255),
    realizado tinyint not null,
    prioridade int not null,
    data_criado date not null,
    id_criador int not null unique,
    primary key (id),
    foreign key(id_criador) references usuario(id)
    );
insert into tarefa(nome, descricao, realizado, prioridade, data_criado, id_criador) values (' desafio SimplifyTec'
,'Fazer aplicacao To Do list com Spring', 0,1, CURRENT_DATE, 1);