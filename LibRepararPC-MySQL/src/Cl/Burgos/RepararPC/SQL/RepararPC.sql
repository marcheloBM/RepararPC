-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema RepararPC
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `RepararPC` ;

-- -----------------------------------------------------
-- Schema RepararPC
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `RepararPC` DEFAULT CHARACTER SET latin1 ;
USE `RepararPC` ;

-- -----------------------------------------------------
-- Table `RepararPC`.`Login`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RepararPC`.`Login` ;

CREATE TABLE IF NOT EXISTS `RepararPC`.`Login` (
  `idLogin` INT NOT NULL AUTO_INCREMENT,
  `rut` VARCHAR(12) NOT NULL,
  `nombre` VARCHAR(25) NOT NULL,
  `apellido` VARCHAR(45) NOT NULL,
  `correo` VARCHAR(45) NOT NULL,
  `celular` VARCHAR(13) NOT NULL,
  `passworld` VARCHAR(45) NOT NULL,
  `tipo` ENUM('Administrador', 'Usuario') NOT NULL,
  PRIMARY KEY (`idLogin`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RepararPC`.`Cliente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RepararPC`.`Cliente` ;

CREATE TABLE IF NOT EXISTS `RepararPC`.`Cliente` (
  `idCliente` INT(11) NOT NULL AUTO_INCREMENT,
  `rut` VARCHAR(12) NOT NULL,
  `nombre` VARCHAR(25) NOT NULL,
  `apellido` VARCHAR(25) NULL,
  `correo` VARCHAR(45) NULL,
  `celular` VARCHAR(13) NULL,
  `Login_idLogin` INT NOT NULL,
  PRIMARY KEY (`idCliente`, `Login_idLogin`),
  INDEX `fk_Cliente_Usuario1_idx` (`Login_idLogin` ASC),
  CONSTRAINT `fk_Cliente_Usuario1`
    FOREIGN KEY (`Login_idLogin`)
    REFERENCES `RepararPC`.`Login` (`idLogin`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `RepararPC`.`Computador`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RepararPC`.`Computador` ;

CREATE TABLE IF NOT EXISTS `RepararPC`.`Computador` (
  `idComputador` INT NOT NULL AUTO_INCREMENT,
  `marca` VARCHAR(45) NOT NULL,
  `modelo` VARCHAR(45) NOT NULL,
  `numSerie` VARCHAR(45) NULL DEFAULT NULL,
  `ram` VARCHAR(25) NOT NULL,
  `hdd` VARCHAR(25) NOT NULL,
  `sistemaOpe` VARCHAR(25) NOT NULL,
  `arquitectura` VARCHAR(25) NOT NULL,
  `version` VARCHAR(25) NOT NULL,
  `tipoReparacion` ENUM('Formateo', 'Limpieza', 'Instalacion', 'Reparacion') NOT NULL,
  `descripcion` VARCHAR(200) NOT NULL,
  `valor` INT NULL DEFAULT NULL,
  `fecha` DATETIME NOT NULL,
  `Cliente_idCliente` INT NOT NULL,
  `Cliente_Login_idLogin` INT NOT NULL,
  PRIMARY KEY (`idComputador`, `Cliente_idCliente`, `Cliente_Login_idLogin`),
  INDEX `fk_PC_Cliente1_idx` (`Cliente_idCliente` ASC, `Cliente_Login_idLogin` ASC),
  CONSTRAINT `fk_PC_Cliente1`
    FOREIGN KEY (`Cliente_idCliente` , `Cliente_Login_idLogin`)
    REFERENCES `RepararPC`.`Cliente` (`idCliente` , `Login_idLogin`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `RepararPC`.`AudiLogin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RepararPC`.`AudiLogin` ;

CREATE TABLE IF NOT EXISTS `RepararPC`.`AudiLogin` (
  `idAudiLogin` INT NOT NULL AUTO_INCREMENT,
  `rut` VARCHAR(12) NULL,
  `nombre` VARCHAR(25) NULL,
  `apellido` VARCHAR(25) NULL,
  `correo` VARCHAR(45) NULL,
  `celular` VARCHAR(13) NULL,
  `passworld` VARCHAR(45) NULL,
  `tipo` ENUM('Administrador', 'Usuario') NULL,
  `fecha` DATETIME NULL,
  `proceso` VARCHAR(25) NULL,
  `usuario_bd` VARCHAR(25) NULL,
  `Login_idLogin` INT NULL,
  PRIMARY KEY (`idAudiLogin`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RepararPC`.`AudiCliente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RepararPC`.`AudiCliente` ;

CREATE TABLE IF NOT EXISTS `RepararPC`.`AudiCliente` (
  `idAudiCliente` INT NOT NULL AUTO_INCREMENT,
  `rut` VARCHAR(12) NULL,
  `nombre` VARCHAR(25) NULL,
  `apellido` VARCHAR(25) NULL,
  `correo` VARCHAR(45) NULL,
  `celular` VARCHAR(13) NULL,
  `idLogin` INT NULL,
  `fecha` DATETIME NULL,
  `proceso` VARCHAR(25) NULL,
  `usuario_bd` VARCHAR(25) NULL,
  `Cliente_idCliente` INT NULL,
  PRIMARY KEY (`idAudiCliente`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RepararPC`.`AudiComputador`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RepararPC`.`AudiComputador` ;

CREATE TABLE IF NOT EXISTS `RepararPC`.`AudiComputador` (
  `idAudiComputador` INT NOT NULL AUTO_INCREMENT,
  `marca` VARCHAR(45) NULL,
  `modelo` VARCHAR(45) NULL,
  `numSerie` VARCHAR(45) NULL,
  `ram` VARCHAR(25) NULL,
  `hdd` VARCHAR(25) NULL,
  `sistemaOpe` VARCHAR(25) NULL,
  `arquitectura` VARCHAR(25) NULL,
  `version` VARCHAR(25) NULL,
  `tipoReparacion` ENUM('Formateo', 'Limpieza', 'Instalacion', 'Reparacion') NULL,
  `descripcion` VARCHAR(200) NULL,
  `valor` INT NULL,
  `fechaComp` DATETIME NULL,
  `idCliente` INT NULL,
  `idLogin` INT NULL,
  `fecha` DATETIME NULL,
  `proceso` VARCHAR(25) NULL,
  `usuario_bd` VARCHAR(25) NULL,
  `Computador_idComputador` INT NULL,
  PRIMARY KEY (`idAudiComputador`))
ENGINE = InnoDB;

USE `RepararPC` ;

-- -----------------------------------------------------
-- procedure ProClienteCuantos
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProClienteCuantos`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProClienteCuantos` (in Busqueda varchar(200))
reads sql data
deterministic
BEGIN
select 
count(*) as cuantos
from
`Cliente`
where 
rut like concat('%',Busqueda,'%') or nombre like concat('%',Busqueda,'%') or apellido like concat('%',Busqueda,'%') or 
correo like concat('%',Busqueda,'%') or celular like concat('%',Busqueda,'%');
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProClienteListarAll
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProClienteListarAll`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProClienteListarAll` (in Desde bigint,in Cuantos bigint,in Busqueda varchar(200))
reads sql data
BEGIN
select 
right(idCliente, 5) as idCliente,
rut, nombre, apellido, correo, celular, Login_idLogin
from `Cliente`
where 
rut like concat('%',Busqueda,'%') or nombre like concat('%',Busqueda,'%') or apellido like concat('%',Busqueda,'%') or 
correo like concat('%',Busqueda,'%') or celular like concat('%',Busqueda,'%') or Login_idLogin like concat('%',Busqueda,'%')
LIMIT desde, cuantos;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProLoginValidar
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProLoginValidar`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProLoginValidar` (in digito varchar(12),in Pass varchar(45))
reads sql data
BEGIN
select 
idLogin,rut,nombre,apellido,correo,celular,passworld,tipo
from `Login`
where rut=digito and passworld=Pass;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProLoginInsertar
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProLoginInsertar`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProLoginInsertar` (in Rut varchar(12),in Nombre varchar(25), in Apellido varchar(25),in Correo varchar(45),in Celular varchar(13),in Passworld varchar(45),in Tipo varchar(25))
BEGIN
insert into Login(rut,nombre,apellido,correo,celular,passworld,tipo)
value(Rut,Nombre,Apellido,Correo,Celular,Passworld,Tipo);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProLoginModificar
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProLoginModificar`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProLoginModificar` (in Rut varchar(12),in Nombre varchar(25), in Apellido varchar(25),in Correo varchar(45),in Celular varchar(13),in Passworld varchar(45),in Tipo varchar(25),in id int)
BEGIN
update
`Login`
set
rut=Rut,
nombre=Nombre,
apellido=Apellido,
correo=Correo,
celular=Celular,
passworld=Passworld,
tipo=Tipo 
WHERE 
idLogin=id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProLoginEliminar
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProLoginEliminar`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProLoginEliminar` (in id int)
BEGIN
delete
from
`Login`
where idLogin=id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProLoginListarAll
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProLoginListarAll`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProLoginListarAll` (in Desde bigint,in Cuantos bigint,in Busqueda varchar(200))
reads sql data
BEGIN
select 
right(idLogin, 5) as idLogin,
rut, nombre, apellido, correo, celular, passworld, tipo
from `Login`
where 
rut like concat('%',Busqueda,'%') or nombre like concat('%',Busqueda,'%') or 
apellido like concat('%',Busqueda,'%') or correo like concat('%',Busqueda,'%') or 
celular like concat('%',Busqueda,'%') or passworld like concat('%',Busqueda,'%') or 
tipo like concat('%',Busqueda,'%')
LIMIT desde, cuantos;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProLoginListarAll
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProLoginListarAll`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProLoginListarAll` (in Desde bigint,in Cuantos bigint,in Busqueda varchar(200))
reads sql data
BEGIN
select 
right(idLogin, 5) as idLogin,
rut, nombre, apellido, correo, celular, passworld, tipo
from `Login`
where 
rut like concat('%',Busqueda,'%') or nombre like concat('%',Busqueda,'%') or 
apellido like concat('%',Busqueda,'%') or correo like concat('%',Busqueda,'%') or 
celular like concat('%',Busqueda,'%') or passworld like concat('%',Busqueda,'%') or 
tipo like concat('%',Busqueda,'%')
LIMIT desde, cuantos;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProLoginCuantos
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProLoginCuantos`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProLoginCuantos` (in Busqueda varchar(200))
reads sql data
deterministic
BEGIN
select 
count(*) as cuantos
from
`Login`
where
rut like concat('%',Busqueda,'%') or nombre like concat('%',Busqueda,'%') or apellido like concat('%',Busqueda,'%') or 
correo like concat('%',Busqueda,'%') or celular like concat('%',Busqueda,'%') or 
passworld like concat('%',Busqueda,'%') or tipo like concat('%',Busqueda,'%');
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProLoginBuscarRut
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProLoginBuscarRut`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProLoginBuscarRut` (in Rutl varchar(12))
reads sql data
BEGIN
select 
idLogin,rut,nombre,apellido,correo,celular,passworld,tipo
from `Login`
where rut=Rutl;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProClienteInsertar
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProClienteInsertar`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProClienteInsertar` (in Rut varchar(12),in Nombre varchar(25), in Apellido varchar(25),in Correo varchar(45),in Celular varchar(13),in IdLogin int)
BEGIN
insert into Cliente(rut,nombre,apellido,correo,celular,Login_idLogin)
value(Rut,Nombre,Apellido,Correo,Celular,IdLogin);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProLoginBuscarID
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProLoginBuscarID`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProLoginBuscarID` (in id bigint)
reads sql data
BEGIN
select right(idLogin, 5) as idLogin,
rut,
nombre,
apellido,
correo,
celular,
passworld,
tipo
from
`Login`
where idLogin=id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProClienteBuscarID
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProClienteBuscarID`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProClienteBuscarID` (in id bigint)
reads sql data
BEGIN
select right(idCliente, 5) as idCliente,
rut,
nombre,
apellido,
correo,
celular,
Login_idLogin
from
`Cliente`
where idCliente=id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProComputadorInsertar
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProComputadorInsertar`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProComputadorInsertar` (in Marca varchar(45),in Modelo varchar(45), in NumSerie varchar(45),in Ram varchar(25),in HDD varchar(25),in SisteOpera varchar(25),in Arquitectura varchar(25),in Version varchar(25),in TipoReparacion varchar(25),in Descripcion varchar(126),in Valor int,in FechaIngreso DATETIME,in IdCliente int,in IdLogin int)
BEGIN
insert into Computador(marca, modelo, numSerie, ram, hdd, sistemaOpe, arquitectura, version, tipoReparacion, descripcion, valor, fecha, Cliente_idCliente,Cliente_Login_IdLogin)
value(Marca, Modelo, NumSerie, Ram, HDD, SisteOpera, Arquitectura, Version, TipoReparacion, Descripcion, Valor, FechaIngreso, IdCliente,IdLogin);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProComputadorListarId
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProComputadorListarId`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProComputadorListarId` (in Desde bigint,in Cuantos bigint,in Busqueda varchar(200),in ID INT(11))
reads sql data
BEGIN
select 
right(idComputador, 5) as idComputador, marca, modelo, numSerie, ram, hdd, sistemaOpe, arquitectura, version, tipoReparacion, descripcion, valor, fecha, Cliente_idCliente, Cliente_Login_idLogin
from `Computador`
where Cliente_idCliente=ID 
LIMIT desde, cuantos;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProComputadorCuantos
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProComputadorCuantos`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProComputadorCuantos` (in Busqueda varchar(200))
reads sql data
deterministic
BEGIN
select 
count(*) as cuantos
from
`Computador`
where
marca like concat('%',Busqueda,'%') or modelo like concat('%',Busqueda,'%') or numSerie like concat('%',Busqueda,'%') or 
ram like concat('%',Busqueda,'%') or hdd like concat('%',Busqueda,'%') or hdd like concat('%',Busqueda,'%') or tipoReparacion like concat('%',Busqueda,'%') or descripcion like concat('%',Busqueda,'%') 
or fecha like concat('%',Busqueda,'%') or Cliente_idCliente like concat('%',Busqueda,'%');
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProComputadorListarIdPC
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProComputadorListarIdPC`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProComputadorListarIdPC` (in ID INT(11))
reads sql data
BEGIN
select 
right(idComputador, 5) as idComputador,
marca, modelo, numSerie, ram, hdd, sistemaOpe, arquitectura, version, tipoReparacion, descripcion, valor, fecha, Cliente_idCliente, Cliente_Login_idLogin
from `Computador`
where idComputador=ID ;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProComputadorModificar
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProComputadorModificar`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProComputadorModificar` (in Marca varchar(45),in Modelo varchar(45), in NumSerie varchar(45),in Ram varchar(25),in HDD varchar(25),in SisteOpera varchar(25),in Arquitectura varchar(25),in Version varchar(25),in TipoReparacion varchar(25),in Descripcion varchar(126),in Valor int,in Fecha DATETIME,in IdCliente int,in IdLogin int,in id int)
BEGIN
update
`Computador`
set
marca=Marca,
modelo=Modelo,
numSerie=NumSerie,
ram=Ram,
hdd=HDD,
sistemaOpe=SisteOpera,
arquitectura=Arquitectura,
version=Version,
tipoReparacion=tipoReparacion,
descripcion=Descripcion,
valor=Valor,
fecha=Fecha,
Cliente_idCliente=IdCliente,
Cliente_Login_idLogin=IdLogin
WHERE
idComputador=id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProComputadorEliminar
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProComputadorEliminar`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProComputadorEliminar` (in id int)
BEGIN
delete
from
`Computador`
where idComputador=id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProComputadorListarAll
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProComputadorListarAll`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProComputadorListarAll` (in Desde bigint,in Cuantos bigint,in Busqueda varchar(200))
reads sql data
BEGIN
select 
right(idComputador, 5) as idComputador,
marca, modelo, numSerie, ram, hdd, sistemaOpe, arquitectura, version, tipoReparacion, descripcion, valor, fecha, Cliente_idCliente, Cliente_Login_idLogin
from `Computador`
where 
marca like concat('%',Busqueda,'%') or modelo like concat('%',Busqueda,'%') or numSerie like concat('%',Busqueda,'%') or 
ram like concat('%',Busqueda,'%') or hdd like concat('%',Busqueda,'%') or
sistemaOpe like concat('%',Busqueda,'%') or arquitectura like concat('%',Busqueda,'%') or version like concat('%',Busqueda,'%') or
 tipoReparacion like concat('%',Busqueda,'%') or descripcion like concat('%',Busqueda,'%') or valor like concat('%',Busqueda,'%') 
or fecha like concat('%',Busqueda,'%') or Cliente_idCliente like concat('%',Busqueda,'%') or Cliente_Login_idLogin like concat('%',Busqueda,'%')
LIMIT desde, cuantos;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProClienteModificar
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProClienteModificar`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProClienteModificar` (in Rut varchar(12),in Nombre varchar(25), in Apellido varchar(25),in Correo varchar(45),in Celular varchar(13),in IdLogin int,in id int)
BEGIN
update
`Cliente`
set
rut=Rut,
nombre=Nombre,
apellido=Apellido,
correo=Correo,
celular=Celular,
Login_idLogin=IdLogin
WHERE 
idCliente=id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProClienteEliminar
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProClienteEliminar`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProClienteEliminar` (in id int)
BEGIN
delete
from
`Cliente`
WHERE 
idCliente=id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProClienteBuscarRUT
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProClienteBuscarRUT`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProClienteBuscarRUT` (in RUT varchar(12))
reads sql data
BEGIN
select idCliente,
rut,
nombre,
apellido,
correo,
celular,
Login_idLogin
from
`Cliente`
where rut=RUT;
END$$

DELIMITER ;
USE `RepararPC`;

DELIMITER $$

USE `RepararPC`$$
DROP TRIGGER IF EXISTS `RepararPC`.`insertAudiL` $$
USE `RepararPC`$$
CREATE DEFINER = CURRENT_USER TRIGGER `RepararPC`.`insertAudiL` AFTER INSERT ON `Login` FOR EACH ROW
BEGIN
INSERT
INTO
AudiLogin(rut, nombre, apellido, correo, celular, passworld, tipo, fecha, proceso, usuario_bd, Login_idlogin)
value(new.rut, new.nombre, new.apellido, new.correo, new.celular, new.passworld, new.tipo, NOW(), "Insertado", CURRENT_USER(), new.idLogin);
END$$


USE `RepararPC`$$
DROP TRIGGER IF EXISTS `RepararPC`.`updateAudiL` $$
USE `RepararPC`$$
CREATE DEFINER = CURRENT_USER TRIGGER `RepararPC`.`updateAudiL` AFTER UPDATE ON `Login` FOR EACH ROW
BEGIN
INSERT
INTO
AudiLogin(rut, nombre, apellido, correo, celular, passworld, tipo, fecha, proceso, usuario_bd, Login_idlogin)
value(new.rut, new.nombre, new.apellido, new.correo, new.celular, new.passworld, new.tipo, NOW(), "Modificado", CURRENT_USER(), new.idLogin);
END$$


USE `RepararPC`$$
DROP TRIGGER IF EXISTS `RepararPC`.`deleteAudiL` $$
USE `RepararPC`$$
CREATE DEFINER = CURRENT_USER TRIGGER `RepararPC`.`deleteAudiL` AFTER DELETE ON `Login` FOR EACH ROW
BEGIN
INSERT
INTO
AudiLogin(rut, nombre, apellido, correo, celular, passworld, tipo, fecha, proceso, usuario_bd, Login_idlogin)
value(old.rut, old.nombre, old.apellido, old.correo, old.celular, old.passworld, old.tipo, NOW(), "Eliminado", CURRENT_USER(), old.idLogin);
END$$


USE `RepararPC`$$
DROP TRIGGER IF EXISTS `RepararPC`.`insertAudiC` $$
USE `RepararPC`$$
CREATE DEFINER = CURRENT_USER TRIGGER `RepararPC`.`insertAudiC` AFTER INSERT ON `Cliente` FOR EACH ROW
BEGIN
INSERT
INTO
AudiCliente(rut, nombre, apellido, correo, celular, idLogin, fecha, proceso, usuario_bd, Cliente_idCliente)
value(new.rut, new.nombre, new.apellido, new.correo, new.celular, new.Login_idLogin, NOW(), "Insertado", CURRENT_USER(), new.idCliente);
END$$


USE `RepararPC`$$
DROP TRIGGER IF EXISTS `RepararPC`.`updateAudiC` $$
USE `RepararPC`$$
CREATE DEFINER = CURRENT_USER TRIGGER `RepararPC`.`updateAudiC` AFTER UPDATE ON `Cliente` FOR EACH ROW
BEGIN
INSERT
INTO
AudiCliente(rut, nombre, apellido, correo, celular, idLogin, fecha, proceso, usuario_bd, Cliente_idCliente)
value(new.rut, new.nombre, new.apellido, new.correo, new.celular, new.Login_idLogin, NOW(), "Modificado", CURRENT_USER(), new.idCliente);
END$$


USE `RepararPC`$$
DROP TRIGGER IF EXISTS `RepararPC`.`deleteAudiC` $$
USE `RepararPC`$$
CREATE DEFINER = CURRENT_USER TRIGGER `RepararPC`.`deleteAudiC` AFTER DELETE ON `Cliente` FOR EACH ROW
BEGIN
INSERT
INTO
AudiCliente(rut, nombre, apellido, correo, celular, idLogin, fecha, proceso, usuario_bd, Cliente_idCliente)
value(old.rut, old.nombre, old.apellido, old.correo, old.celular, old.Login_idLogin, NOW(), "Eliminado", CURRENT_USER(), old.idCliente);
END$$


USE `RepararPC`$$
DROP TRIGGER IF EXISTS `RepararPC`.`insertAudiP` $$
USE `RepararPC`$$
CREATE DEFINER = CURRENT_USER TRIGGER `RepararPC`.`insertAudiP` AFTER INSERT ON `Computador` FOR EACH ROW
BEGIN
INSERT
INTO
AudiComputador(marca, modelo, numSerie, ram, hdd, sistemaOpe,arquitectura,version, tipoReparacion, descripcion, valor, fechaComp, idCliente, idLogin, fecha, proceso, usuario_bd, Computador_idComputador)
value(new.marca, new.modelo, new.numSerie, new.ram, new.hdd, new.sistemaOpe, new.arquitectura, new.version, new.tipoReparacion, new.descripcion, new.valor, new.fecha, new.Cliente_idCliente, new.Cliente_Login_idLogin, NOW(), "Insertado", CURRENT_USER(), new.idComputador);
END$$


USE `RepararPC`$$
DROP TRIGGER IF EXISTS `RepararPC`.`updateAudiP` $$
USE `RepararPC`$$
CREATE DEFINER = CURRENT_USER TRIGGER `RepararPC`.`updateAudiP` AFTER UPDATE ON `Computador` FOR EACH ROW
BEGIN
INSERT
INTO
AudiComputador(marca, modelo, numSerie, ram, hdd, sistemaOpe,arquitectura,version, tipoReparacion, descripcion, valor, fechaComp, idCliente, idLogin, fecha, proceso, usuario_bd, Computador_idComputador)
value(new.marca, new.modelo, new.numSerie, new.ram, new.hdd, new.sistemaOpe, new.arquitectura, new.version, new.tipoReparacion, new.descripcion, new.valor, new.fecha, new.Cliente_idCliente, new.Cliente_Login_idLogin, NOW(), "Modificado", CURRENT_USER(), new.idComputador);
END$$


USE `RepararPC`$$
DROP TRIGGER IF EXISTS `RepararPC`.`deleteAudiP` $$
USE `RepararPC`$$
CREATE DEFINER = CURRENT_USER TRIGGER `RepararPC`.`deleteAudiP` AFTER DELETE ON `Computador` FOR EACH ROW
BEGIN
INSERT
INTO
AudiComputador(marca, modelo, numSerie, ram, hdd, sistemaOpe,arquitectura,version, tipoReparacion, descripcion, valor, fechaComp, idCliente, idLogin, fecha, proceso, usuario_bd, Computador_idComputador)
value(old.marca, old.modelo, old.numSerie, old.ram, old.hdd, old.sistemaOpe, old.arquitectura, old.version, old.tipoReparacion, old.descripcion, old.valor, old.fecha, old.Cliente_idCliente, old.Cliente_Login_idLogin, NOW(), "Eliminado", CURRENT_USER(), old.idComputador);
END$$


DELIMITER ;
SET SQL_MODE = '';
GRANT USAGE ON *.* TO marchelo@localhost;
 DROP USER marchelo@localhost;
SET SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
CREATE USER 'marchelo'@'localhost' IDENTIFIED BY 'HP2117la';

GRANT SELECT, INSERT, TRIGGER ON TABLE `RepararPC`.* TO 'marchelo'@'localhost';
GRANT SELECT ON TABLE `RepararPC`.* TO 'marchelo'@'localhost';
GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE `RepararPC`.* TO 'marchelo'@'localhost';
GRANT ALL ON `RepararPC`.* TO 'marchelo'@'localhost';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
