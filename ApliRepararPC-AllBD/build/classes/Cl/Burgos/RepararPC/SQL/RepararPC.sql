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
  `nombre` VARCHAR(25) NOT NULL,
  `apellido` VARCHAR(25) NULL,
  `correo` VARCHAR(45) NULL,
  `celular` VARCHAR(13) NULL,
  `Login_idLogin` INT NOT NULL,
  PRIMARY KEY (`idCliente`, `Login_idLogin`),
  CONSTRAINT `fk_Cliente_Usuario1`
    FOREIGN KEY (`Login_idLogin`)
    REFERENCES `RepararPC`.`Login` (`idLogin`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

CREATE INDEX `fk_Cliente_Usuario1_idx` ON `RepararPC`.`Cliente` (`Login_idLogin` ASC);


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
  CONSTRAINT `fk_PC_Cliente1`
    FOREIGN KEY (`Cliente_idCliente` , `Cliente_Login_idLogin`)
    REFERENCES `RepararPC`.`Cliente` (`idCliente` , `Login_idLogin`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

CREATE INDEX `fk_PC_Cliente1_idx` ON `RepararPC`.`Computador` (`Cliente_idCliente` ASC, `Cliente_Login_idLogin` ASC);


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
nombre like concat('%',Busqueda,'%') or apellido like concat('%',Busqueda,'%') or 
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
nombre, apellido, correo, celular, Login_idLogin
from `Cliente`
where 
nombre like concat('%',Busqueda,'%') or apellido like concat('%',Busqueda,'%') or 
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
CREATE PROCEDURE `ProClienteInsertar` (in Nombre varchar(25), in Apellido varchar(25),in Correo varchar(45),in Celular varchar(13),in IdLogin int)
BEGIN
insert into Cliente(nombre,apellido,correo,celular,Login_idLogin)
value(Nombre,Apellido,Correo,Celular,IdLogin);
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
CREATE PROCEDURE `ProClienteModificar` (in Nombre varchar(25), in Apellido varchar(25),in Correo varchar(45),in Celular varchar(13),in IdLogin int,in id int)
BEGIN
update
`Cliente`
set
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
-- procedure ProClienteBuscarNombre
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProClienteBuscarNombre`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProClienteBuscarNombre` (in NOMBRE varchar(25))
reads sql data
BEGIN
select idCliente,
apellido,
correo,
celular,
Login_idLogin
from
`Cliente`
where nombre=NOMBRE;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure ProClienteBuscarIdLogin
-- -----------------------------------------------------

USE `RepararPC`;
DROP procedure IF EXISTS `RepararPC`.`ProClienteBuscarIdLogin`;

DELIMITER $$
USE `RepararPC`$$
CREATE PROCEDURE `ProClienteBuscarIdLogin` (in ID int)
reads sql data
BEGIN
select idCliente,
nombre,
apellido,
correo,
celular,
Login_idLogin
from
`Cliente`
where Login_idLogin=ID;
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
AudiCliente(nombre, apellido, correo, celular, idLogin, fecha, proceso, usuario_bd, Cliente_idCliente)
value(new.nombre, new.apellido, new.correo, new.celular, new.Login_idLogin, NOW(), "Insertado", CURRENT_USER(), new.idCliente);
END$$


USE `RepararPC`$$
DROP TRIGGER IF EXISTS `RepararPC`.`updateAudiC` $$
USE `RepararPC`$$
CREATE DEFINER = CURRENT_USER TRIGGER `RepararPC`.`updateAudiC` AFTER UPDATE ON `Cliente` FOR EACH ROW
BEGIN
INSERT
INTO
AudiCliente(nombre, apellido, correo, celular, idLogin, fecha, proceso, usuario_bd, Cliente_idCliente)
value(new.nombre, new.apellido, new.correo, new.celular, new.Login_idLogin, NOW(), "Modificado", CURRENT_USER(), new.idCliente);
END$$


USE `RepararPC`$$
DROP TRIGGER IF EXISTS `RepararPC`.`deleteAudiC` $$
USE `RepararPC`$$
CREATE DEFINER = CURRENT_USER TRIGGER `RepararPC`.`deleteAudiC` AFTER DELETE ON `Cliente` FOR EACH ROW
BEGIN
INSERT
INTO
AudiCliente(nombre, apellido, correo, celular, idLogin, fecha, proceso, usuario_bd, Cliente_idCliente)
value(old.nombre, old.apellido, old.correo, old.celular, old.Login_idLogin, NOW(), "Eliminado", CURRENT_USER(), old.idCliente);
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

-- -----------------------------------------------------
-- Data for table `RepararPC`.`Login`
-- -----------------------------------------------------
START TRANSACTION;
USE `RepararPC`;
INSERT INTO `RepararPC`.`Login` (`idLogin`, `rut`, `nombre`, `apellido`, `correo`, `celular`, `passworld`, `tipo`) VALUES (1, '17.008.864-6', 'Marcelo', 'Burgos', 'marchelo.1989@live.cl', '990715586', '729c35493e6b8e990aaaddc2a52d02c1', 'Usuario');
INSERT INTO `RepararPC`.`Login` (`idLogin`, `rut`, `nombre`, `apellido`, `correo`, `celular`, `passworld`, `tipo`) VALUES (2, '11.111.111-1', 'Administrador', 'Administrador', 'admin@admin.cl', '111111111', '21232f297a57a5a743894a0e4a801fc3', 'Administrador');
INSERT INTO `RepararPC`.`Login` (`idLogin`, `rut`, `nombre`, `apellido`, `correo`, `celular`, `passworld`, `tipo`) VALUES (3, '8.787.902-k', 'jose', 'Prueba', 'prueba@prueba.cl', '111111111', '80e380bb24647ada593f44e60a806b63', 'Usuario');
INSERT INTO `RepararPC`.`Login` (`idLogin`, `rut`, `nombre`, `apellido`, `correo`, `celular`, `passworld`, `tipo`) VALUES (4, '23.645.403-7', 'Prueba', 'Prueba', 'Prueba@Prueba.cl', '111111111', 'e99a18c428cb38d5f260853678922e03', 'Usuario');
INSERT INTO `RepararPC`.`Login` (`idLogin`, `rut`, `nombre`, `apellido`, `correo`, `celular`, `passworld`, `tipo`) VALUES (5, '13.712.309-6', 'Prueba', 'Prueba', 'Prueba@Prueba.cl', '111111111', 'e99a18c428cb38d5f260853678922e03', 'Usuario');
INSERT INTO `RepararPC`.`Login` (`idLogin`, `rut`, `nombre`, `apellido`, `correo`, `celular`, `passworld`, `tipo`) VALUES (6, '24.368.533-8', 'Prueba', 'Prueba', 'Prueba@Prueba.cl', '111111111', 'e99a18c428cb38d5f260853678922e03', 'Usuario');
INSERT INTO `RepararPC`.`Login` (`idLogin`, `rut`, `nombre`, `apellido`, `correo`, `celular`, `passworld`, `tipo`) VALUES (7, '7.178.134-8', 'Prueba', 'Prueba', 'Prueba@Prueba.cl', '111111111', 'e99a18c428cb38d5f260853678922e03', 'Usuario');
INSERT INTO `RepararPC`.`Login` (`idLogin`, `rut`, `nombre`, `apellido`, `correo`, `celular`, `passworld`, `tipo`) VALUES (8, '16.178.166-5', 'Prueba', 'Prueba', 'Prueba@Prueba.cl', '111111111', 'e99a18c428cb38d5f260853678922e03', 'Usuario');
INSERT INTO `RepararPC`.`Login` (`idLogin`, `rut`, `nombre`, `apellido`, `correo`, `celular`, `passworld`, `tipo`) VALUES (9, '16.510.840-k', 'Prueba', 'Prueba', 'Prueba@Prueba.cl', '111111111', 'e99a18c428cb38d5f260853678922e03', 'Usuario');
INSERT INTO `RepararPC`.`Login` (`idLogin`, `rut`, `nombre`, `apellido`, `correo`, `celular`, `passworld`, `tipo`) VALUES (10, '19.025.185-3', 'Marcelo', 'Burgos', 'marchelo.1989@live.cl', '990715586', '729c35493e6b8e990aaaddc2a52d02c1', 'Usuario');
INSERT INTO `RepararPC`.`Login` (`idLogin`, `rut`, `nombre`, `apellido`, `correo`, `celular`, `passworld`, `tipo`) VALUES (11, '23.528.237-2', 'Marcelo', 'Burgos', 'marchelo.1989@live.cl', '990715586', '729c35493e6b8e990aaaddc2a52d02c1', 'Usuario');
INSERT INTO `RepararPC`.`Login` (`idLogin`, `rut`, `nombre`, `apellido`, `correo`, `celular`, `passworld`, `tipo`) VALUES (12, '12.855.106-9', 'Marcelo', 'Burgos', 'marchelo.1989@live.cl', '990715586', '729c35493e6b8e990aaaddc2a52d02c1', 'Usuario');
INSERT INTO `RepararPC`.`Login` (`idLogin`, `rut`, `nombre`, `apellido`, `correo`, `celular`, `passworld`, `tipo`) VALUES (13, '12.798.703-3', 'Marcelo', 'Burgos', 'marchelo.1989@live.cl', '990715586', '729c35493e6b8e990aaaddc2a52d02c1', 'Usuario');
INSERT INTO `RepararPC`.`Login` (`idLogin`, `rut`, `nombre`, `apellido`, `correo`, `celular`, `passworld`, `tipo`) VALUES (14, '18.905.757-1', 'Marcelo jose maria', 'Burgos', 'marchelo.1989@live.cl', '990715586', '7e71ee4bca198c08bfd165ce52a3f07b', 'Administrador');
INSERT INTO `RepararPC`.`Login` (`idLogin`, `rut`, `nombre`, `apellido`, `correo`, `celular`, `passworld`, `tipo`) VALUES (15, '18.804.903-6', 'Marcelo', 'Burgos', 'marchelo.1989@live.cl', '990715586', '729c35493e6b8e990aaaddc2a52d02c1', 'Administrador');
INSERT INTO `RepararPC`.`Login` (`idLogin`, `rut`, `nombre`, `apellido`, `correo`, `celular`, `passworld`, `tipo`) VALUES (16, '13.624.058-7', 'Marcelo', 'Burgos', 'marchelo.1989@live.cl', '990715586', '729c35493e6b8e990aaaddc2a52d02c1', 'Usuario');
INSERT INTO `RepararPC`.`Login` (`idLogin`, `rut`, `nombre`, `apellido`, `correo`, `celular`, `passworld`, `tipo`) VALUES (17, '5.664.551-9', 'Marcelo', 'Burgos', 'marchelo.1989@live.cl', '990715586', '729c35493e6b8e990aaaddc2a52d02c1', 'Administrador');
INSERT INTO `RepararPC`.`Login` (`idLogin`, `rut`, `nombre`, `apellido`, `correo`, `celular`, `passworld`, `tipo`) VALUES (18, '10.150.062-4', 'Juanito Marco', 'Gusto Nose', 'marko.g@live.cl', '990555555', 'e99a18c428cb38d5f260853678922e03', 'Administrador');

COMMIT;


-- -----------------------------------------------------
-- Data for table `RepararPC`.`Cliente`
-- -----------------------------------------------------
START TRANSACTION;
USE `RepararPC`;
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (1, 'Marcelo jose maria', 'Burgos', 'marchelo.1989@live.cl', '990715586', 1);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (2, 'Prueba', 'Prueba', 'Prueba@Prueba.com', '123456789', 1);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (3, 'Marcelo', 'Burgos', 'marchelo.1989@live.cl', '990715586', 2);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (4, 'Prueba1', 'Prueba1', 'Prueba1@Prueba1.com', '123456789', 8);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (5, 'Prueba2', 'Prueba2', 'Prueba2@Prueba2.com', '123456789', 7);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (6, 'Prueba3', 'Prueba2', 'Prueba2@Prueba2.cl', '987654321', 8);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (7, 'Prueba4', 'Prueba4', 'Prueba4@Prueba4.cl', '987654321', 13);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (8, 'Prueba5', 'Prueba5', 'Prueba5@Prueba5.com', '987654321', 18);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (9, 'Prueba6', 'Prueba6', 'Prueba6@Prueba6.cl', '123456789', 16);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (10, 'Prueba7', 'Prueba7', 'Prueba7@Prueba7.cl', '123456789', 13);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (11, 'Prueba8', 'Prueba8', 'Prueba8@Prueba8.cl', '123456789', 16);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (12, 'Prueba9', 'Prueba9', 'Prueba9@Prueba9.cl', '123456789', 1);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (13, 'Juan', '', 'juan@gmail.com', '', 1);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (20, 'Jose', 'Bustos', 'j.Bustos@live.cl', '123456789', 12);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (21, 'Jose', 'Bustos', 'j.Bustos@live.cl', '123456789', 1);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (22, 'Jose', 'Bustos', 'j.Bustos@live.cl', '123456789', 8);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (23, 'Romina', 'Penafiel', 'garfield366@gmail.com', '999112305', 1);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (24, 'Marcelo', 'Burgos', 'marchelo.1989@live.cl', '990715586', 13);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (25, 'Marcelo', 'Burgos', 'marchelo.1989@live.cl', '990715586', 18);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (26, 'Marcelo', 'Burgos', 'marchelo.1989@live.cl', '990715586', 13);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (27, 'Marcelo', 'Burgos', 'marchelo.1989@live.cl', '990715586', 13);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (28, 'Marcelo', 'Burgos', 'marchelo.1989@live.cl', '990715586', 1);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (29, 'Marcelo', 'Burgos', 'marchelo.1989@live.cl', '990715586', 14);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (30, 'Marcelo', 'Burgos', 'marchelo.1989@live.cl', '990715586', 8);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (31, 'Marcelo', 'Burgos', 'marchelo.1989@live.cl', '990715586', 2);
INSERT INTO `RepararPC`.`Cliente` (`idCliente`, `nombre`, `apellido`, `correo`, `celular`, `Login_idLogin`) VALUES (32, 'Marcelo', '', 'marchelo.1989@live.cl', '', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `RepararPC`.`Computador`
-- -----------------------------------------------------
START TRANSACTION;
USE `RepararPC`;
INSERT INTO `RepararPC`.`Computador` (`idComputador`, `marca`, `modelo`, `numSerie`, `ram`, `hdd`, `sistemaOpe`, `arquitectura`, `version`, `tipoReparacion`, `descripcion`, `valor`, `fecha`, `Cliente_idCliente`, `Cliente_Login_idLogin`) VALUES (1, 'TOSHIBA', 'Satellite L45-A', '5D019380K', '1  MB', '12  MB', 'Windows 10', 'amd64', '10.0', 'Formateo', 'sadasdasdasdasda', 15000, '2017-12-19 02:40:21', 1, 1);
INSERT INTO `RepararPC`.`Computador` (`idComputador`, `marca`, `modelo`, `numSerie`, `ram`, `hdd`, `sistemaOpe`, `arquitectura`, `version`, `tipoReparacion`, `descripcion`, `valor`, `fecha`, `Cliente_idCliente`, `Cliente_Login_idLogin`) VALUES (2, 'sadasd', 'asdasd', 'asdasdasd', '2 MB', '55 MB', 'Windows 10', 'amd64', '10.1', 'Formateo', 'fsadfasdfa adfsdfsdf', 15000, '2017-08-02 02:47:57', 1, 1);
INSERT INTO `RepararPC`.`Computador` (`idComputador`, `marca`, `modelo`, `numSerie`, `ram`, `hdd`, `sistemaOpe`, `arquitectura`, `version`, `tipoReparacion`, `descripcion`, `valor`, `fecha`, `Cliente_idCliente`, `Cliente_Login_idLogin`) VALUES (3, 'sdfghj', 'kytfyt', 'uygusyusyg', '1 MB', '1000 MB', 'Windows 10', 'amd64', '10.1', 'Formateo', 'fsadfasdfa adfsdfsdf', 15000, '2017-08-02 02:47:57', 1, 1);
INSERT INTO `RepararPC`.`Computador` (`idComputador`, `marca`, `modelo`, `numSerie`, `ram`, `hdd`, `sistemaOpe`, `arquitectura`, `version`, `tipoReparacion`, `descripcion`, `valor`, `fecha`, `Cliente_idCliente`, `Cliente_Login_idLogin`) VALUES (4, 'TOSHIBA', 'Satellite L45-A', '5D019380K', '6 GB', '240 GB', 'Windows 10', 'amd64', '10.0', 'Formateo', 'hgfdsa', 0, '2017-12-19 02:40:21', 1, 1);
INSERT INTO `RepararPC`.`Computador` (`idComputador`, `marca`, `modelo`, `numSerie`, `ram`, `hdd`, `sistemaOpe`, `arquitectura`, `version`, `tipoReparacion`, `descripcion`, `valor`, `fecha`, `Cliente_idCliente`, `Cliente_Login_idLogin`) VALUES (5, 'TOSHIBA', 'Satellite L45-A', '5D019380K', '8 GB', '240 GB', 'Windows 10', 'amd64', '10.0', 'Formateo', 'instalacion del sistema de windows 10', 0, '2017-12-19 02:40:21', 21, 1);
INSERT INTO `RepararPC`.`Computador` (`idComputador`, `marca`, `modelo`, `numSerie`, `ram`, `hdd`, `sistemaOpe`, `arquitectura`, `version`, `tipoReparacion`, `descripcion`, `valor`, `fecha`, `Cliente_idCliente`, `Cliente_Login_idLogin`) VALUES (6, 'TOSHIBA', 'Satellite L305', '29216579Q', '4 GB', '500 GB', 'Windows 7', 'amd64', '6.1', 'Formateo', 'instlacion del sistema operativo', 0, '2016-12-08 02:15:45', 23, 1);
INSERT INTO `RepararPC`.`Computador` (`idComputador`, `marca`, `modelo`, `numSerie`, `ram`, `hdd`, `sistemaOpe`, `arquitectura`, `version`, `tipoReparacion`, `descripcion`, `valor`, `fecha`, `Cliente_idCliente`, `Cliente_Login_idLogin`) VALUES (7, 'TOSHIBA', 'Satellite L45-A', '5D019380K', '8 GB', '240 GB', 'Windows 10', 'amd64', '10.0', 'Limpieza', 'kjhgfds', 0, '2017-12-19 02:40:21', 1, 1);
INSERT INTO `RepararPC`.`Computador` (`idComputador`, `marca`, `modelo`, `numSerie`, `ram`, `hdd`, `sistemaOpe`, `arquitectura`, `version`, `tipoReparacion`, `descripcion`, `valor`, `fecha`, `Cliente_idCliente`, `Cliente_Login_idLogin`) VALUES (8, 'DESKTOP-MCH5I2R', 'Satellite L45-A', '5D019380K', '8 GB', '240 GB', 'Windows 10', 'amd64', '10.0', 'Formateo', 'Instalacion del sistema actualizado y programas', 0, '2018-10-19 03:35:49', 1, 1);

COMMIT;

