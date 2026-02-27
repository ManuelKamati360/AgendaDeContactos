CREATE DATABASE IF NOT EXISTS Agenda_Contatos
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_general_ci;
USE Agenda_Contatos;

CREATE TABLE IF NOT EXISTS Contato (
    
    id INT AUTO_INCREMENT,
    nome VARCHAR(45) NOT NULL,
    dataNascimento DATE,
    telefone CHAR(13) NOT NULL,
    email CHAR(1),
    endereco VARCHAR(45),
    cidade VARCHAR(45),
    estado VARCHAR(45),
    
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Teste de inserção de dados
INSERT INTO Contato (nome, telefone, dataNascimento, email, endereco, cidade, estado) VALUES
    ('Manuel', '(+244) 111222333', '1990-05-15', '2000-01-01', 'Rua Principal, 123', 'Luanda', 'Luanda');


-- -------------------- Listar Contatos -------------------------
DELIMITER $$
CREATE PROCEDURE sp_ListarContatos()
BEGIN
    SELECT 
        id, 
        nome, 
        dataNascimento, 
        telefone, 
        email, 
        endereco, 
        estado, 
        cidade 
    FROM Contato
    ORDER BY nome ASC;
END $$
DELIMITER ;
CALL sp_ListarContatos();

-- -------------------- Buscar Contatos por termo -------------------------
DELIMITER $$
CREATE PROCEDURE sp_BuscarContatos (
    IN inNome VARCHAR(45),
    IN inEmail VARCHAR(50),
    IN inTelefone VARCHAR(20),
    IN inEndereco VARCHAR(45),
    IN inCidade VARCHAR(45),
    IN inEstado VARCHAR(45)
)
BEGIN
    SELECT * 
    FROM Contato 
    WHERE 
        nome LIKE CONCAT('%', inNome, '%') OR  
        email LIKE CONCAT('%', inEmail, '%') OR
        telefone LIKE CONCAT('%', inTelefone, '%') OR
        endereco LIKE CONCAT('%', inEndereco, '%') OR
        cidade LIKE CONCAT('%', inCidade, '%') OR
        estado LIKE CONCAT('%', inEstado, '%');
END $$
DELIMITER ;
CALL sp_BuscarContatos("Man","Man", "Man", "Man", "Man", "Man");

-- -------------------- Buscar Contato por ID -------------------------
DELIMITER $$
CREATE PROCEDURE sp_BuscarContatoPorID (
    IN inID VARCHAR(45)
)
BEGIN
    SELECT * FROM Contato WHERE id = inID;
END $$
DELIMITER ;
CALL sp_BuscarContatoPorID("1");


-- -------------------- Inserir Contato -------------------------
DELIMITER $$
CREATE PROCEDURE sp_InserirContato (
    IN inNome VARCHAR(45),
    IN inEmail VARCHAR(50),
    IN inTelefone VARCHAR(20),
    IN inEndereco VARCHAR(45),
    IN inCidade VARCHAR(45),
    IN inEstado VARCHAR(45),
    IN inDataNascimento DATE
)
BEGIN
    INSERT INTO Contato (
        nome, 
        email, 
        telefone, 
        endereco, 
        cidade, 
        estado, 
        dataNascimento
    ) 
    VALUES (
        inNome,
        inEmail,
        inTelefone,
        inEndereco,
        inCidade,
        inEstado,
        inDataNascimento
    );
END $$
DELIMITER ;
CALL sp_InserirContato("Robert Moore","robert.moore@email.com", "123-456-7890", "Rua das Flores, 123", "São Paulo", "SP", "1985-10-25");

-- -------------------- Atualizar Contato -------------------------
DELIMITER $$
CREATE PROCEDURE sp_AtualizarContato (
    IN inNome VARCHAR(45),
    IN inEmail VARCHAR(50),
    IN inTelefone VARCHAR(20),
    IN inEndereco VARCHAR(45),
    IN inCidade VARCHAR(45),
    IN inEstado VARCHAR(45),
    IN inDataNascimento DATE,
    IN inId INT
)
BEGIN
    UPDATE Contato 
    SET 
        nome = inNome, 
        email = inEmail,
        telefone = inTelefone, 
        endereco = inEndereco, 
        cidade = inCidade,
        estado = inEstado,         
        dataNascimento = inDataNascimento
    WHERE id = inId;
END $$
DELIMITER ;
CALL sp_AtualizarContato("Robert Moore ShakeSpear","robert.moore@email.com", "123-456-7890", "Rua das Flores, 123", "São Paulo", "SP", "1985-10-25", 36);

-- -------------------- Deletar Contato -------------------------
DELIMITER $$
CREATE PROCEDURE sp_DeletarContato (
    IN inId INT
)
BEGIN
    DELETE FROM Contato WHERE id = inId;
END $$
DELIMITER ;

CALL sp_DeletarContato(1); 