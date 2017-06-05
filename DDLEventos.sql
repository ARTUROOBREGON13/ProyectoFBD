DROP TABLE ComprobantePago CASCADE CONSTRAINTS
;
DROP TABLE Detalle_Inscripcion CASCADE CONSTRAINTS
;
DROP TABLE Evento CASCADE CONSTRAINTS
;
DROP TABLE Familiar CASCADE CONSTRAINTS
;
DROP TABLE Inscripcion CASCADE CONSTRAINTS
;
DROP TABLE PagoLogistica CASCADE CONSTRAINTS
;
DROP TABLE Proveedor CASCADE CONSTRAINTS
;
DROP TABLE Tipo CASCADE CONSTRAINTS
;
DROP TABLE Usuario CASCADE CONSTRAINTS
;

CREATE TABLE ComprobantePago
(
	k_codigo       NUMBER(8) NOT NULL,
	k_inscripcion  NUMBER(8) NOT NULL,
	f_fecha        DATE,
	n_FormaPago    VARCHAR2(10)
)
;


CREATE TABLE Detalle_Inscripcion
(
	k_Inscripcion  NUMBER(8) NOT NULL,
	k_consecutivo  NUMBER(2) NOT NULL,
	k_familiar     NUMBER(15) NOT NULL,
	k_asociado     NUMBER(15) NOT NULL
)
;


CREATE TABLE Evento
(
	k_codigo               NUMBER(5) NOT NULL,
	k_tipo                 NUMBER(3) NOT NULL,
	k_nitProveedor         NUMBER(12) NOT NULL,
	n_nombre               VARCHAR2(30) NOT NULL,
	n_observacion          VARCHAR2(50),
	n_lugar                VARCHAR2(30) NOT NULL,
	f_inicio               DATE NOT NULL,
	f_fin                  DATE NOT NULL,
	f_limiteInscripcion    DATE NOT NULL,
	q_limiteParticipantes  NUMBER(5),
	q_cuposDisponibles     NUMBER(5),
	v_costoTotal           NUMBER(9) NOT NULL,
	v_porcSubsidio         NUMBER(3) NOT NULL,
	v_valorCopago          NUMBER(8),
	i_estado               CHAR(1) NOT NULL
)
;


CREATE TABLE Familiar
(
	k_IdFamiliar  NUMBER(15) NOT NULL,
	k_IdUsuario   NUMBER(15) NOT NULL,
	n_nombre      VARCHAR2(25) NOT NULL,
	n_apellido    VARCHAR2(25) NOT NULL,
	Parentezco    VARCHAR2(15) NOT NULL,
	i_TipoId      CHAR(1) NOT NULL
)
;


CREATE TABLE Inscripcion
(
	k_codigo          NUMBER(8) NOT NULL,
	k_codigoEvento    NUMBER(5) NOT NULL,
	k_IdAsociado      NUMBER(15) NOT NULL,
	q_cantAsistentes  NUMBER(2),
	i_estado          CHAR(1) NOT NULL,
	v_valorTotal      NUMBER(6),
	i_tipoPago        CHAR(1) NOT NULL,
	q_cantCuotas      NUMBER(2) NOT NULL
)
;


CREATE TABLE PagoLogistica
(
	k_evento  NUMBER(5) NOT NULL,
	f_Pago    DATE,
	v_valor   NUMBER(9)
)
;


CREATE TABLE Proveedor
(
	k_nitProveedor  NUMBER(12) NOT NULL,
	n_nombre        VARCHAR2(30) NOT NULL,
	n_direccion     VARCHAR2(30) NOT NULL,
	n_telefono      VARCHAR2(10) NOT NULL
)
;


CREATE TABLE Tipo
(
	k_codigo  NUMBER(3) NOT NULL,
	n_tipo    VARCHAR2(20) NOT NULL
)
;


CREATE TABLE Usuario
(
	k_Id        NUMBER(15) NOT NULL,
	n_nombre    VARCHAR2(25) NOT NULL,
	n_apellido  VARCHAR2(25) NOT NULL,
	i_estado    CHAR(1) NOT NULL,
	i_tipo      CHAR(1) NOT NULL,
	n_password  VARCHAR2(30) NOT NULL
)
;



ALTER TABLE ComprobantePago ADD CONSTRAINT PK_ComprobantePago 
	PRIMARY KEY (k_codigo)
;

ALTER TABLE Detalle_Inscripcion ADD CONSTRAINT PK_Detalle_Inscripcion 
	PRIMARY KEY (k_Inscripcion, k_consecutivo)
;

ALTER TABLE Evento ADD CONSTRAINT PK_Evento 
	PRIMARY KEY (k_codigo)
;

ALTER TABLE Familiar ADD CONSTRAINT PK_Familiar 
	PRIMARY KEY (k_IdFamiliar, k_IdUsuario)
;

ALTER TABLE Inscripcion ADD CONSTRAINT PK_Inscripcion 
	PRIMARY KEY (k_codigo)
;

ALTER TABLE PagoLogistica ADD CONSTRAINT PK_PagoLogistica 
	PRIMARY KEY (k_evento)
;

ALTER TABLE Proveedor ADD CONSTRAINT PK_Proveedor 
	PRIMARY KEY (k_nitProveedor)
;

ALTER TABLE Tipo ADD CONSTRAINT PK_Tipo 
	PRIMARY KEY (k_codigo)
;

ALTER TABLE Usuario ADD CONSTRAINT PK_Usuario 
	PRIMARY KEY (k_Id)
;

ALTER TABLE Evento
ADD CONSTRAINT CK_fechaIni_fechaLim CHECK (f_limiteInscripcion < f_inicio)
;

ALTER TABLE Evento
ADD CONSTRAINT CK_LimiteParticipantes CHECK (q_limiteParticipantes >= 0)
;

ALTER TABLE Evento
ADD CONSTRAINT CK_CuposDisponibles CHECK (q_cuposDisponibles >= 0)
;

ALTER TABLE Evento
ADD CONSTRAINT CK_CostoTotal CHECK (v_costoTotal >= 0)
;

ALTER TABLE Evento
ADD CONSTRAINT CK_Subsidio CHECK (v_porcSubsidio BETWEEN 0 AND 100)
;

ALTER TABLE Evento
ADD CONSTRAINT CK_Copago CHECK (v_valorCopago >= 0)
;

ALTER TABLE Evento
ADD CONSTRAINT CK_Estado CHECK (i_estado IN ('A', 'T'))
;

ALTER TABLE Evento
ADD CONSTRAINT Ck_fechaIni_fechaFin CHECK (f_inicio < f_fin)
;

ALTER TABLE Familiar
	ADD CONSTRAINT UQ_Familiar_k_IdFamiliar UNIQUE (k_IdFamiliar)
;

ALTER TABLE Familiar
ADD CONSTRAINT Ck_TipoId CHECK (i_TipoId IN ('C', 'T'))
;

ALTER TABLE Inscripcion
ADD CONSTRAINT CK_Estado CHECK (i_estado IN ('C', 'I', 'P'))
;

ALTER TABLE Inscripcion
ADD CONSTRAINT CK_ValorTotal CHECK (v_valorTotal >= 0)
;

ALTER TABLE Inscripcion
ADD CONSTRAINT CK_TipoPago CHECK (i_tipoPago IN ('N', 'V'))
;

ALTER TABLE PagoLogistica
ADD CONSTRAINT CK_valor CHECK (v_valor >= 0)
;

ALTER TABLE Usuario
ADD CONSTRAINT CK_Estado CHECK (i_estado IN ('A', 'I') )
;

ALTER TABLE Usuario
ADD CONSTRAINT CK_tipo CHECK (i_tipo IN ('A', 'F'))
;


ALTER TABLE ComprobantePago ADD CONSTRAINT FK_ComprobantePago_Inscripcion 
	FOREIGN KEY (k_inscripcion) REFERENCES Inscripcion (k_codigo)
;

ALTER TABLE Detalle_Inscripcion ADD CONSTRAINT FK_Det_Insc_Familiar 
	FOREIGN KEY (k_familiar, k_asociado) REFERENCES Familiar (k_IdFamiliar, k_IdUsuario)
;

ALTER TABLE Detalle_Inscripcion ADD CONSTRAINT FK_Det_Insc_Insc 
	FOREIGN KEY (k_Inscripcion) REFERENCES Inscripcion (k_codigo)
;

ALTER TABLE Evento ADD CONSTRAINT FK_Evento_Proveedor 
	FOREIGN KEY (k_nitProveedor) REFERENCES Proveedor (k_nitProveedor)
;

ALTER TABLE Evento ADD CONSTRAINT FK_Evento_Tipo 
	FOREIGN KEY (k_tipo) REFERENCES Tipo (k_codigo)
;

ALTER TABLE Familiar ADD CONSTRAINT FK_Familiar_Usuario 
	FOREIGN KEY (k_IdUsuario) REFERENCES Usuario (k_Id)
;

ALTER TABLE Inscripcion ADD CONSTRAINT FK_Inscripcion_Evento 
	FOREIGN KEY (k_codigoEvento) REFERENCES Evento (k_codigo)
;

ALTER TABLE Inscripcion ADD CONSTRAINT FK_Inscripcion_Usuario 
	FOREIGN KEY (k_IdAsociado) REFERENCES Usuario (k_Id)
;

ALTER TABLE PagoLogistica ADD CONSTRAINT FK_PagoLogistica_Evento 
	FOREIGN KEY (k_evento) REFERENCES Evento (k_codigo)
;
