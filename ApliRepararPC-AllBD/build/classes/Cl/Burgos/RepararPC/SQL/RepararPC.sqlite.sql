BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "cliente" (
	"IdCliente"	INTEGER,
	"nombre"	TEXT,
	"apellido"	TEXT,
	"correo"	TEXT,
	"celular"	TEXT,
	"Login_idLogin"	INTEGER,
	PRIMARY KEY("IdCliente" AUTOINCREMENT),
	FOREIGN KEY("Login_idLogin") REFERENCES "login"("IdLogin")
);
CREATE TABLE IF NOT EXISTS "computador" (
	"IdComputador"	INTEGER,
	"marca"	TEXT,
	"modelo"	TEXT,
	"numSerie"	TEXT,
	"sistemaOpe"	TEXT,
	"arquitectura"	TEXT,
	"tipoReparacion"	TEXT,
	"descripcion"	TEXT,
	"valor"	INTEGER,
	"fecha"	TEXT,
	"Cliente_idCliente"	INTEGER,
	"Cliente_Login_idLogin"	INTEGER,
	PRIMARY KEY("IdComputador" AUTOINCREMENT),
	FOREIGN KEY("Cliente_Login_idLogin") REFERENCES "login"("IdLogin"),
	FOREIGN KEY("Cliente_idCliente") REFERENCES "cliente"("IdCliente")
);
CREATE TABLE IF NOT EXISTS "documento" (
	"IdDocumento"	INTEGER,
	"extencion"	TEXT,
	"archivo"	TEXT,
	"Computador_idComputador"	INTEGER,
	PRIMARY KEY("IdDocumento" AUTOINCREMENT),
	FOREIGN KEY("Computador_idComputador") REFERENCES "computador"("IdComputador")
);
CREATE TABLE IF NOT EXISTS "login" (
	"IdLogin"	INTEGER,
	"rut"	TEXT,
	"nombre"	TEXT,
	"apellido"	TEXT,
	"correo"	TEXT,
	"celular"	TEXT,
	"password"	TEXT,
	"tipo"	TEXT,
	PRIMARY KEY("IdLogin" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "registropc" (
	"IdRegistropc"	INTEGER,
	"keypc"	TEXT,
	"keyactivacion"	TEXT,
	"fechaTermino"	TEXT,
	"activo"	TEXT,
	PRIMARY KEY("IdRegistropc" AUTOINCREMENT)
);
INSERT INTO "login" ("IdLogin","rut","nombre","apellido","correo","celular","password","tipo") VALUES 
 (1,'11.111.111-1','Administrador','Administrador','Administrador@prueba.cl','999999999','c893bad68927b457dbed39460e6afd62','Administrador'),
 (2,'22.222.222-2','Usuario','Usuario','admin@admin.com','111111111','c893bad68927b457dbed39460e6afd62','Usuario');
COMMIT;
