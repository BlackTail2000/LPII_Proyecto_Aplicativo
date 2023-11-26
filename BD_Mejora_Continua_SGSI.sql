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
est_usua TinyInt,
fec_nac Date,
fec_registro DateTime,
ult_login DateTime,
cod_rol Int Not Null,
Foreign Key (cod_rol) References Rol (cod_rol));

Create Table Tipo(
cod_tipo Int Primary Key Auto_Increment,
nom_tipo Varchar(100));

Create Table Trabajador(
cod_trab Int Primary Key Auto_Increment,
nom_trab Varchar(100),
ape_trab Varchar(100),
email_trab Varchar(70),
tel_trab Varchar(30),
est_trab TinyInt,
fec_nac Date,
fec_contrato Date,
cod_tipo Int,
Foreign Key (cod_tipo) References Tipo (cod_tipo));

Create Table Control_Seguridad(
cod_control Int Primary Key Auto_Increment,
nom_control Varchar(100),
des_control LongText,
categoria Varchar(100),
fecha_impl Date,
est_control Varchar(20),
cod_implementador Int,
Foreign Key (cod_implementador) References Trabajador (cod_trab));

Create Table Solicitud_Accion_Mejoras(
num_soli Int Primary Key Auto_Increment,
fec_soli Date,
cod_solicitante Int,
nom_proceso Varchar(100),
fuente_sam Varchar(100),
descripcion_no_conformidad LongText,
causas_no_conformidad LongText,
acciones_a_tomar LongText,
cod_responsable Int,
plazo_dias Int,
est_soli Varchar(30),
Foreign Key (cod_solicitante) References Trabajador (cod_trab),
Foreign Key (cod_responsable) References Trabajador (cod_trab));

Create Table Informe_General_Mejoras(
num_informe Int Primary Key Auto_Increment,
objetivo LongText,
area_especifica LongText,
mejora_implementada LongText,
descripcion_implementacion LongText,
resultados_prueba LongText,
recomendaciones_adicionales LongText,
conclusiones LongText,
cod_implementador Int,
num_soli Int,
Foreign Key (cod_implementador) References Trabajador (cod_trab),
Foreign Key (num_soli) References Solicitud_Accion_Mejoras (num_soli));

Insert Into Rol Values
(null, 'Administrador'),
(null, 'Gestor de Seguridad'),
(null, 'Gerente de Riesgos'),
(null, 'Director de Realización'),
(null, 'Activador');

Insert Into Tipo Values
(null, 'Implementador'),
(null, 'Oficial de Seguridad de la Información'),
(null, 'Responsable del Ánalisis');

Insert Into Usuario Values
('USUA001', 'Carlos Gabriel', 'Baca Manrique', '$2a$10$ypJLZy7gu5FVCxpdZPPt6eIdec3Vd.6AzDsSR4.q8DeJwm0l0E44O', 'i202212089@cibertec.edu.pe',
1, '2000-09-19', Now(), null, 1);

Select * From Trabajador;
Select * From Control_Seguridad;

Delete From Trabajador;
Update Trabajador
Set est_trab = 1;