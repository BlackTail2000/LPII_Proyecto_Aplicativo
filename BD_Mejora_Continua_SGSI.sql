Drop Database If Exists BD_Mejora_Continua_SGSI;
Create Database If Not Exists BD_Mejora_Continua_SGSI;
Use BD_Mejora_Continua_SGSI;

Create Table Rol(
cod_rol Int Primary Key Auto_Increment,
nom_rol Varchar(50) Not Null);

Create Table Usuario(
cod_usua Char(7) Primary Key,
nom_usua Varchar(100),
ape_usua Varchar(100),
clv_usua Char(60),
email_usua Varchar(70),
est_usua TinyInt(1),
fec_nac Date,
fec_registro DateTime,
ult_login DateTime,
cod_rol Int Not Null,
Foreign Key (cod_rol) References Rol (cod_rol));

Create Table Empleado(
cod_emp Int Primary Key Auto_Increment,
nom_emp Varchar(100),
ape_emp Varchar(100),
email_emp Varchar(70),
tel_emp Varchar(30),
est_emp TinyInt(1),
fec_nac Date,
fec_contrato Date);

Insert Into Rol Values
(null, 'Administrador'),
(null, 'Gestor de Seguridad'),
(null, 'Gerente de Riesgos'),
(null, 'Director de Realización'),
(null, 'Activador');

Insert Into Usuario Values
('USUA001', 'Carlos Gabriel', 'Baca Manrique', 'Callie&Marie123', 'cgbm2000@gmail.com',
1, '2000-09-19', Now(), null, 1);

Select * From Usuario;