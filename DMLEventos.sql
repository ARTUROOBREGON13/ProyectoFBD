INSERT INTO "EVENTOS"."USUARIO" (K_ID, N_NOMBRE, N_APELLIDO, I_ESTADO, I_TIPO, N_PASSWORD) VALUES ('1011', 'Edwar', 'Diaz', 'A', 'A', 'Botoom');
INSERT INTO "EVENTOS"."USUARIO" (K_ID, N_NOMBRE, N_APELLIDO, I_ESTADO, I_TIPO, N_PASSWORD) VALUES ('3050', 'Carlos', 'Obregon', 'A', 'F', '12345');
INSERT INTO "EVENTOS"."USUARIO" (K_ID, N_NOMBRE, N_APELLIDO, I_ESTADO, I_TIPO, N_PASSWORD) VALUES ('1111', 'Frank', 'Moltres', 'A', 'A', '00001');

INSERT INTO "EVENTOS"."TIPO" (K_CODIGO, N_TIPO) VALUES ('11', 'Recreativo');
INSERT INTO "EVENTOS"."TIPO" (K_CODIGO, N_TIPO) VALUES ('21', 'Cultural');
INSERT INTO "EVENTOS"."TIPO" (K_CODIGO, N_TIPO) VALUES ('31', 'Deportivo');
INSERT INTO "EVENTOS"."TIPO" (K_CODIGO, N_TIPO) VALUES ('41', 'Formacion Academica');

INSERT INTO "EVENTOS"."PROVEEDOR" (K_NITPROVEEDOR, N_NOMBRE, N_DIRECCION, N_TELEFONO) VALUES ('29038471', 'EventAll', 'CalleFalsa123', '3134882890');
INSERT INTO "EVENTOS"."PROVEEDOR" (K_NITPROVEEDOR, N_NOMBRE, N_DIRECCION, N_TELEFONO) VALUES ('13947532', 'EveryHour', 'CalleFalsa456', '3214857496');

INSERT INTO "EVENTOS"."EVENTO" (K_CODIGO, K_TIPO, K_NITPROVEEDOR, N_NOMBRE, N_OBSERVACION, N_LUGAR, F_INICIO, F_FIN, F_LIMITEINSCRIPCION, Q_LIMITEPARTICIPANTES, Q_CUPOSDISPONIBLES, V_COSTOTOTAL, V_PORCSUBSIDIO, V_VALORCOPAGO, I_ESTADO) VALUES ('101', '11', '29038471', 'Dia de Convivencia', 'Toda la familia permitida', 'Parque Simon Bolivar', TO_DATE('2017-06-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2017-06-02 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2017-05-26 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), '150', '150', '500000000', '90', '33500', 'A');
INSERT INTO "EVENTOS"."EVENTO" (K_CODIGO, K_TIPO, K_NITPROVEEDOR, N_NOMBRE, N_OBSERVACION, N_LUGAR, F_INICIO, F_FIN, F_LIMITEINSCRIPCION, Q_LIMITEPARTICIPANTES, Q_CUPOSDISPONIBLES, V_COSTOTOTAL, V_PORCSUBSIDIO, V_VALORCOPAGO, I_ESTADO) VALUES ('102', '21', '29038471', 'Dia del niño', 'Toda la familia permitida', 'Parque Simon Bolivar', TO_DATE('2017-06-28 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2017-06-29 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2017-06-26 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), '150', '150', '10000000', '95', '4000', 'A');

INSERT INTO "EVENTOS"."FAMILIAR" (K_IDFAMILIAR, K_IDUSUARIO, N_NOMBRE, N_APELLIDO, PARENTEZCO, I_TIPOID) VALUES ('1011', '1011', 'Edwar', 'Ruiz', 'Asociado', 'C');
INSERT INTO "EVENTOS"."FAMILIAR" (K_IDFAMILIAR, K_IDUSUARIO, N_NOMBRE, N_APELLIDO, PARENTEZCO, I_TIPOID) VALUES ('555023492', '1011', 'Monica', 'Ruiz', 'Madre', 'C');
INSERT INTO "EVENTOS"."FAMILIAR" (K_IDFAMILIAR, K_IDUSUARIO, N_NOMBRE, N_APELLIDO, PARENTEZCO, I_TIPOID) VALUES ('99123056478', '1011', 'Ricardo', 'Diaz', 'Hermano', 'T');
INSERT INTO "EVENTOS"."FAMILIAR" (K_IDFAMILIAR, K_IDUSUARIO, N_NOMBRE, N_APELLIDO, PARENTEZCO, I_TIPOID) VALUES ('544837998', '1011', 'Diego', 'Diaz', 'Padre', 'C');
INSERT INTO "EVENTOS"."FAMILIAR" (K_IDFAMILIAR, K_IDUSUARIO, N_NOMBRE, N_APELLIDO, PARENTEZCO, I_TIPOID) VALUES ('135495134', '1111', 'rose', 'jimenez', 'hermana', 'C');


--SELECT INSC.K_IDASOCIADO ID_ASO, INSC.K_CODIGO COD, INSC.V_VALORTOTAL    FROM INSCRIPCION INSC   ; prueba