
# !!! ATENÇÃO !!!
# Este Script não precisa ser utilizado, uma vez que a aplicação
# é capaz de criar o  Banco de Dados  automaticamente graças  ao
# JPA + Hibernate no  contêiner  logo após o  deploy do sistema.

CREATE DATABASE IF NOT EXISTS DB_PROVA_INFUSE;
USE DB_PROVA_INFUSE;

CREATE TABLE IF NOT EXISTS Cliente(
	codigo INTEGER NOT NULL AUTO_INCREMENT,
    nome VARCHAR(30) NOT NULL,
    
    PRIMARY KEY (codigo)
);

CREATE TABLE IF NOT EXISTS Pedido(
	numeroControle INTEGER NOT NULL,
    dataCadastro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    nome VARCHAR(30) NOT NULL,
    valor FLOAT NOT NULL,
    quantidade INTEGER NOT NULL DEFAULT 1,
    codigoCliente INTEGER NOT NULL,
    
    PRIMARY KEY (numeroControle),
    
    FOREIGN KEY (codigoCliente)
    REFERENCES Cliente(codigo)
);

INSERT INTO Cliente (nome) VALUES
("Cliente 1"),
("Cliente 2"),
("Cliente 3"),
("Cliente 4"),
("Cliente 5"),
("Cliente 6"),
("Cliente 7"),
("Cliente 8"),
("Cliente 9"),
("Cliente 10");
