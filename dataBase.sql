-- SCRIPT DE CRIAÇÃO DO BANCO SAGE
-- Autor: Luana Patricia
-- Data: 2025

CREATE DATABASE IF NOT EXISTS sage_db;
USE sage_db;

-- 1. Tabela ENTE (Com a coluna discriminadora 'tipo_ente')
CREATE TABLE ente (
    codigo VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    prioridade VARCHAR(20) NOT NULL,
    tipo_ente VARCHAR(50) NOT NULL
);

-- 2. Tabela ACOMPANHAMENTO
CREATE TABLE acompanhamento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    competencia VARCHAR(10) NOT NULL,
    codigo_ente VARCHAR(20) NOT NULL,
    status_envio VARCHAR(50) NOT NULL,
    observacoes TEXT,

    FOREIGN KEY (codigo_ente) REFERENCES ente(codigo)
);

-- DADOS DE EXEMPLO
INSERT INTO ente (codigo, nome, prioridade, tipo_ente) VALUES
('201001', 'AGUA BRANCA', 'normal', 'Prefeitura'),
('201015', 'AREIA DE BARAUNA', 'intermediaria', 'Prefeitura'),
('201082', 'GUARABIRA', 'urgente', 'Prefeitura'),
('201180', 'SAO DOMINGOS DO CARIRI', 'urgente', 'Prefeitura'),
('201208', 'SOLANEA', 'normal', 'Prefeitura'),
('301012', 'ARARA', 'intermediaria', 'Instituto'),
('601015', 'AREIA DE BARAUNA', 'normal', 'FundoMunicipal');