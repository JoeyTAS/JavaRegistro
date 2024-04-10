/********************* ROLES **********************/

CREATE ROLE RDB$ADMIN;
/********************* UDFS ***********************/

/****************** GENERATORS ********************/

CREATE GENERATOR GID_PERSONAS;
/******************** DOMAINS *********************/

CREATE DOMAIN SEC$KEY
 AS Varchar(63) CHARACTER SET UTF8
 NOT NULL
 COLLATE UTF8;
CREATE DOMAIN SEC$NAME_PART
 AS Varchar(32) CHARACTER SET UTF8
 NOT NULL
 COLLATE UTF8;
CREATE DOMAIN SEC$USER_NAME
 AS Varchar(63) CHARACTER SET UTF8
 NOT NULL
 COLLATE UTF8;
CREATE DOMAIN SEC$VALUE
 AS Varchar(255) CHARACTER SET UTF8
 NOT NULL
 COLLATE UTF8;
/******************* PROCEDURES ******************/

/******************** TABLES **********************/

CREATE TABLE PERSONAS
(
  ID_PERSONAS Integer NOT NULL,
  NOMBRES Varchar(40),
  APELLIDOS Varchar(40),
  TELEFONO Varchar(9),
  PRIMARY KEY (ID_PERSONAS)
);
/********************* VIEWS **********************/

/******************* EXCEPTIONS *******************/

/******************** TRIGGERS ********************/

SET TERM ^ ;
CREATE TRIGGER TID_PERSONAS FOR PERSONAS ACTIVE
BEFORE INSERT POSITION 0
AS 
BEGIN 
	NEW.ID_PERSONAS=gen_id(GID_PERSONAS,1);
END^
SET TERM ; ^

GRANT RDB$ADMIN TO SYSDBA WITH ADMIN OPTION;
GRANT DELETE, INSERT, REFERENCES, SELECT, UPDATE
 ON PERSONAS TO  SYSDBA WITH GRANT OPTION;
 
