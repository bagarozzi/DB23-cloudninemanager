-- *********************************************
-- * SQL MySQL generation                      
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation date: Fri Jul 19 19:41:13 2024 
-- * LUN file: C:\Users\luca\DB23-cloudninemanager\AsianRestaurent.lun 
-- * Schema: RELETIONAL/1 
-- ********************************************* 


-- Database Section
-- ________________ 

create database CLOUDNINE;
use CLOUDNINE;


-- Tables Section
-- _____________ 

create table Account (
     Password varchar(16) not null,
     Nome_Utente varchar(16) not null,
     CodFiscale char(16) not null,
     constraint ID_Account_ID primary key (Nome_Utente),
     constraint FKAccesso_ID unique (CodFiscale));

create table Categoria (
     Nome_Categoria varchar(16) not null,
     constraint ID_Categoria_ID primary key (Nome_Categoria));

create table Comanda (
     Conto_finale float(1),
     Cod_Comanda int not null,
     Modalita_d_odine varchar(16) not null,
     Coperti int not null,
     Data date not null,
     Ora char(4) not null,
     Nome_Menu varchar(16) not null,
     Num_Tavolo char(1) not null,
     CodFiscale char(16),
     constraint ID_Comanda_ID primary key (Cod_Comanda));

create table Ingrediente (
     Soglia_critica float(1) not null,
     Costo_al_kg float(1) not null,
     Nome_Ingrediente varchar(16) not null,
     constraint ID_Ingrediente_ID primary key (Nome_Ingrediente));

create table Materia_Prima (
     Data_scadenza date not null,
     Nome_Ingrediente varchar(16) not null,
     Quantita float(1) not null,
     Data_d_acquisto date not null,
     constraint SID_Materia_Prima_ID unique (Nome_Ingrediente, Data_scadenza));

create table Membro_del_Personale (
     CodFiscale char(16) not null,
     Ruolo_cuoco varchar(16),
     Professione varchar(16) not null,
     Nome varchar(16) not null,
     Cognome varchar(16) not null,
     Numero_Telefono char(10) not null,
     constraint ID_Membro_del_Personale_ID primary key (CodFiscale));

create table Menu (
     Nome_Menu varchar(16) not null,
     Costo_menu_AYCE float(1) not null,
     constraint ID_Menu_ID primary key (Nome_Menu));

create table Necessita (
     Nome_Ingrediente varchar(16) not null,
     Cod_vivanda int not null,
     Quantita_usata float(1) not null,
     constraint ID_Necessita_ID primary key (Nome_Ingrediente, Cod_vivanda));

create table Ordine (
     Cod_Comanda int not null,
     Stato varchar(16) not null,
     N_Ordine int not null,
     constraint ID_Ordine_ID primary key (Cod_Comanda, N_Ordine));

create table Prenoatazione (
     Data date not null,
     Ora char(4) not null,
     Nominativo char(16) not null,
     Coperti int not null,
     Telefono char(10) not null,
     CodFiscale char(16) not null,
     constraint ID_Prenoatazione_ID primary key (Data, Ora, Telefono));

create table Proposta (
     Nome_Menu varchar(16) not null,
     Cod_vivanda int not null,
     constraint ID_Proposta_ID primary key (Nome_Menu, Cod_vivanda));

create table Richiede (
     Cod_Comanda int not null,
     N_Ordine int not null,
     Cod_vivanda int not null,
     N_vivande int not null,
     constraint ID_Richiede_ID primary key (Cod_Comanda, N_Ordine, Cod_vivanda));

create table Servizio_di_diponibilita (
     Servizio varchar(16) not null,
     Giorno varchar(16) not null,
     Nome_Menu varchar(16),
     constraint ID_Servizio_di_diponibilita_ID primary key (Servizio, Giorno));

create table Tavolo (
     Num_Tavolo char(1) not null,
     constraint ID_Tavolo_ID primary key (Num_Tavolo));

create table Vivanda (
     Cod_vivanda int not null,
     prezzo float(1) not null,
     tipologia varchar(16) not null,
     Nome_Vivanda varchar(16) not null,
     Nome_Categoria varchar(16) not null,
     constraint ID_Vivanda_ID primary key (Cod_vivanda));


-- Constraints Section
-- ___________________ 

alter table Account add constraint FKAccesso_FK
     foreign key (CodFiscale)
     references Membro_del_Personale (CodFiscale);

alter table Comanda add constraint FKRiferimento_FK
     foreign key (Nome_Menu)
     references Menu (Nome_Menu);

alter table Comanda add constraint FKRelativa_FK
     foreign key (Num_Tavolo)
     references Tavolo (Num_Tavolo);

alter table Comanda add constraint FKApertura_FK
     foreign key (CodFiscale)
     references Membro_del_Personale (CodFiscale);

alter table Materia_Prima add constraint FKLotto
     foreign key (Nome_Ingrediente)
     references Ingrediente (Nome_Ingrediente);

-- Not implemented
-- alter table Membro_del_Personale add constraint ID_Membro_del_Personale_CHK
--     check(exists(select * from Account
--                  where Account.CodFiscale = CodFiscale)); 

-- Not implemented
-- alter table Menu add constraint ID_Menu_CHK
--     check(exists(select * from Servizio_di_diponibilita
--                  where Servizio_di_diponibilita.Nome_Menu = Nome_Menu)); 

alter table Necessita add constraint FKNec_Viv_FK
     foreign key (Cod_vivanda)
     references Vivanda (Cod_vivanda);

alter table Necessita add constraint FKNec_Ing
     foreign key (Nome_Ingrediente)
     references Ingrediente (Nome_Ingrediente);

-- Not implemented
-- alter table Ordine add constraint ID_Ordine_CHK
--     check(exists(select * from Richiede
--                  where Richiede.Cod_Comanda = Cod_Comanda and Richiede.N_Ordine = N_Ordine)); 

alter table Ordine add constraint FKComposto
     foreign key (Cod_Comanda)
     references Comanda (Cod_Comanda);

alter table Prenoatazione add constraint FKAcquisizione_FK
     foreign key (CodFiscale)
     references Membro_del_Personale (CodFiscale);

alter table Proposta add constraint FKPro_Viv_FK
     foreign key (Cod_vivanda)
     references Vivanda (Cod_vivanda);

alter table Proposta add constraint FKPro_Men
     foreign key (Nome_Menu)
     references Menu (Nome_Menu);

alter table Richiede add constraint FKRic_Viv_FK
     foreign key (Cod_vivanda)
     references Vivanda (Cod_vivanda);

alter table Richiede add constraint FKRic_Ord
     foreign key (Cod_Comanda, N_Ordine)
     references Ordine (Cod_Comanda, N_Ordine);

alter table Servizio_di_diponibilita add constraint FKDurata_FK
     foreign key (Nome_Menu)
     references Menu (Nome_Menu);

-- Not implemented
-- alter table Vivanda add constraint ID_Vivanda_CHK
--     check(exists(select * from Necessita
--                  where Necessita.Cod_vivanda = Cod_vivanda)); 

alter table Vivanda add constraint FKAppartenenza_FK
     foreign key (Nome_Categoria)
     references Categoria (Nome_Categoria);


-- Index Section
-- _____________ 

create unique index ID_Account_IND
     on Account (Nome_Utente);

create unique index FKAccesso_IND
     on Account (CodFiscale);

create unique index ID_Categoria_IND
     on Categoria (Nome_Categoria);

create unique index ID_Comanda_IND
     on Comanda (Cod_Comanda);

create index FKRiferimento_IND
     on Comanda (Nome_Menu);

create index FKRelativa_IND
     on Comanda (Num_Tavolo);

create index FKApertura_IND
     on Comanda (CodFiscale);

create unique index ID_Ingrediente_IND
     on Ingrediente (Nome_Ingrediente);

create unique index SID_Materia_Prima_IND
     on Materia_Prima (Nome_Ingrediente, Data_scadenza);

create unique index ID_Membro_del_Personale_IND
     on Membro_del_Personale (CodFiscale);

create unique index ID_Menu_IND
     on Menu (Nome_Menu);

create unique index ID_Necessita_IND
     on Necessita (Nome_Ingrediente, Cod_vivanda);

create index FKNec_Viv_IND
     on Necessita (Cod_vivanda);

create unique index ID_Ordine_IND
     on Ordine (Cod_Comanda, N_Ordine);

create unique index ID_Prenoatazione_IND
     on Prenoatazione (Data, Ora, Telefono);

create index FKAcquisizione_IND
     on Prenoatazione (CodFiscale);

create unique index ID_Proposta_IND
     on Proposta (Nome_Menu, Cod_vivanda);

create index FKPro_Viv_IND
     on Proposta (Cod_vivanda);

create unique index ID_Richiede_IND
     on Richiede (Cod_Comanda, N_Ordine, Cod_vivanda);

create index FKRic_Viv_IND
     on Richiede (Cod_vivanda);

create unique index ID_Servizio_di_diponibilita_IND
     on Servizio_di_diponibilita (Servizio, Giorno);

create index FKDurata_IND
     on Servizio_di_diponibilita (Nome_Menu);

create unique index ID_Tavolo_IND
     on Tavolo (Num_Tavolo);

create unique index ID_Vivanda_IND
     on Vivanda (Cod_vivanda);

create index FKAppartenenza_IND
     on Vivanda (Nome_Categoria);

