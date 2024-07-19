
-- Database Section
-- ________________ 

create database CLOUDNINE;
use CLOUDNINE;


-- Tables Section
-- _____________ 

create table Account (
     Password varchar(16) not null,
     Nome_Utente varchar(16) not null,
     CodFiscale varchar(16) not null,
     constraint ID_Account_ID primary key (Nome_Utente),
     constraint SID_Accou_Membr_ID unique (CodFiscale));

create table Bevanda (
     Cod_vivanda int not null,
     alcolica varchar(4) not null,
     constraint ID_Bevan_Vivan_ID primary key (Cod_vivanda));

create table Cameriere (
     CodFiscale varchar(16) not null,
     constraint ID_Camer_Membr_ID primary key (CodFiscale));

create table Categoria (
     Nome_Categoria varchar(16) not null,
     constraint ID_Categoria_ID primary key (Nome_Categoria));

create table Comanda (
     Conto_finale int,
     Cod_Comanda int not null,
     Modalita_d_odine varchar(10) not null,
     Coperti int not null,
     Data date not null,
     Ora timestamp not null,
     Nome_Menu varchar(16) not null,
     Num_Tavolo int not null,
     CodFiscale varchar(16) not null,
     constraint ID_Comanda_ID primary key (Cod_Comanda));

create table Ingrediente (
     Soglia_critica float(1) not null,
     Costo_al_kg float(1) not null,
     Nome_Ingrediente varchar(16) not null,
     Cod_vivanda int not null,
     constraint ID_Ingrediente_ID primary key (Nome_Ingrediente),
     constraint SID_Ingre_Bevan_ID unique (Cod_vivanda));

create table Materia_Prima (
     Data_scadenza date not null,
     Nome_Ingrediente varchar(16) not null,
     Quantita float(1) not null,
     Data_d_acquisto date not null,
     constraint SID_Materia_Prima_ID unique (Nome_Ingrediente, Data_scadenza));

create table Membro_del_Personale (
     CodFiscale varchar(16) not null,
     Ruolo_cuoco varchar(16),
     Professione varchar(16) not null,
     Nome varchar(16) not null,
     Cognome varchar(16) not null,
     Numero_Telefono varchar(10) not null,
     constraint ID_Membro_del_Personale_ID primary key (CodFiscale));

create table Menu (
     Nome_Menu varchar(16) not null,
     Costo_menu_AYCE int not null,
     constraint ID_Menu_ID primary key (Nome_Menu));

create table Necessita (
     Cod_vivanda int not null,
     Nome_Ingrediente varchar(16) not null,
     Quantita_usata float(1) not null,
     constraint ID_Necessita_ID primary key (Cod_vivanda, Nome_Ingrediente));

create table Ordine (
     Cod_Comanda int not null,
     Stato varchar(16) not null,
     N_Ordine int not null,
     constraint ID_Ordine_ID primary key (Cod_Comanda, N_Ordine));

create table Piatto (
     Cod_vivanda int not null,
     constraint ID_Piatt_Vivan_ID primary key (Cod_vivanda));

create table Prenoatazione (
     Data date not null,
     Ora varchar(4) not null,
     Nominativo varchar(16) not null,
     Coperti int not null,
     Telefono varchar(10) not null,
     CodFiscale varchar(16) not null,
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
     Servizio varchar(10) not null,
     Giorno varchar(10) not null,
     Nome_Menu varchar(16),
     constraint ID_Servizio_di_diponibilita_ID primary key (Servizio, Giorno));

create table Tavolo (
     Num_Tavolo int not null,
     constraint ID_Tavolo_ID primary key (Num_Tavolo));

create table Vivanda (
     Cod_vivanda int not null,
     prezzo float(1) not null,
     Nome_Vivanda varchar(30) not null,
     Nome_Categoria varchar(16) not null,
     constraint ID_Vivanda_ID primary key (Cod_vivanda));


-- Constraints Section
-- ___________________ 

alter table Account add constraint SID_Accou_Membr_FK
     foreign key (CodFiscale)
     references Membro_del_Personale (CodFiscale);

-- Not implemented
-- alter table Bevanda add constraint ID_Bevan_Vivan_CHK
--     check(exists(select * from Ingrediente
--                  where Ingrediente.Cod_vivanda = Cod_vivanda)); 

alter table Bevanda add constraint ID_Bevan_Vivan_FK
     foreign key (Cod_vivanda)
     references Vivanda (Cod_vivanda);

alter table Cameriere add constraint ID_Camer_Membr_FK
     foreign key (CodFiscale)
     references Membro_del_Personale (CodFiscale);

alter table Comanda add constraint REF_Coman_Menu_FK
     foreign key (Nome_Menu)
     references Menu (Nome_Menu);

alter table Comanda add constraint REF_Coman_Tavol_FK
     foreign key (Num_Tavolo)
     references Tavolo (Num_Tavolo);

alter table Comanda add constraint REF_Coman_Camer_FK
     foreign key (CodFiscale)
     references Cameriere (CodFiscale);

alter table Ingrediente add constraint SID_Ingre_Bevan_FK
     foreign key (Cod_vivanda)
     references Bevanda (Cod_vivanda);

alter table Materia_Prima add constraint REF_Mater_Ingre
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

alter table Necessita add constraint REF_Neces_Ingre_FK
     foreign key (Nome_Ingrediente)
     references Ingrediente (Nome_Ingrediente);

alter table Necessita add constraint EQU_Neces_Piatt
     foreign key (Cod_vivanda)
     references Piatto (Cod_vivanda);

-- Not implemented
-- alter table Ordine add constraint ID_Ordine_CHK
--     check(exists(select * from Richiede
--                  where Richiede.Cod_Comanda = Cod_Comanda and Richiede.N_Ordine = N_Ordine)); 

alter table Ordine add constraint REF_Ordin_Coman
     foreign key (Cod_Comanda)
     references Comanda (Cod_Comanda);

-- Not implemented
-- alter table Piatto add constraint ID_Piatt_Vivan_CHK
--     check(exists(select * from Necessita
--                  where Necessita.Cod_vivanda = Cod_vivanda)); 

alter table Piatto add constraint ID_Piatt_Vivan_FK
     foreign key (Cod_vivanda)
     references Vivanda (Cod_vivanda);

alter table Prenoatazione add constraint REF_Preno_Membr_FK
     foreign key (CodFiscale)
     references Membro_del_Personale (CodFiscale);

alter table Proposta add constraint REF_Propo_Vivan_FK
     foreign key (Cod_vivanda)
     references Vivanda (Cod_vivanda);

alter table Proposta add constraint REF_Propo_Menu
     foreign key (Nome_Menu)
     references Menu (Nome_Menu);

alter table Richiede add constraint REF_Richi_Vivan_FK
     foreign key (Cod_vivanda)
     references Vivanda (Cod_vivanda);

alter table Richiede add constraint EQU_Richi_Ordin
     foreign key (Cod_Comanda, N_Ordine)
     references Ordine (Cod_Comanda, N_Ordine);

alter table Servizio_di_diponibilita add constraint EQU_Servi_Menu_FK
     foreign key (Nome_Menu)
     references Menu (Nome_Menu);

alter table Vivanda add constraint REF_Vivan_Categ_FK
     foreign key (Nome_Categoria)
     references Categoria (Nome_Categoria);


-- Index Section
-- _____________ 

create unique index ID_Account_IND
     on Account (Nome_Utente);

create unique index SID_Accou_Membr_IND
     on Account (CodFiscale);

create unique index ID_Bevan_Vivan_IND
     on Bevanda (Cod_vivanda);

create unique index ID_Camer_Membr_IND
     on Cameriere (CodFiscale);

create unique index ID_Categoria_IND
     on Categoria (Nome_Categoria);

create unique index ID_Comanda_IND
     on Comanda (Cod_Comanda);

create index REF_Coman_Menu_IND
     on Comanda (Nome_Menu);

create index REF_Coman_Tavol_IND
     on Comanda (Num_Tavolo);

create index REF_Coman_Camer_IND
     on Comanda (CodFiscale);

create unique index ID_Ingrediente_IND
     on Ingrediente (Nome_Ingrediente);

create unique index SID_Ingre_Bevan_IND
     on Ingrediente (Cod_vivanda);

create unique index SID_Materia_Prima_IND
     on Materia_Prima (Nome_Ingrediente, Data_scadenza);

create unique index ID_Membro_del_Personale_IND
     on Membro_del_Personale (CodFiscale);

create unique index ID_Menu_IND
     on Menu (Nome_Menu);

create unique index ID_Necessita_IND
     on Necessita (Cod_vivanda, Nome_Ingrediente);

create index REF_Neces_Ingre_IND
     on Necessita (Nome_Ingrediente);

create unique index ID_Ordine_IND
     on Ordine (Cod_Comanda, N_Ordine);

create unique index ID_Piatt_Vivan_IND
     on Piatto (Cod_vivanda);

create unique index ID_Prenoatazione_IND
     on Prenoatazione (Data, Ora, Telefono);

create index REF_Preno_Membr_IND
     on Prenoatazione (CodFiscale);

create unique index ID_Proposta_IND
     on Proposta (Nome_Menu, Cod_vivanda);

create index REF_Propo_Vivan_IND
     on Proposta (Cod_vivanda);

create unique index ID_Richiede_IND
     on Richiede (Cod_Comanda, N_Ordine, Cod_vivanda);

create index REF_Richi_Vivan_IND
     on Richiede (Cod_vivanda);

create unique index ID_Servizio_di_diponibilita_IND
     on Servizio_di_diponibilita (Servizio, Giorno);

create index EQU_Servi_Menu_IND
     on Servizio_di_diponibilita (Nome_Menu);

create unique index ID_Tavolo_IND
     on Tavolo (Num_Tavolo);

create unique index ID_Vivanda_IND
     on Vivanda (Cod_vivanda);

create index REF_Vivan_Categ_IND
     on Vivanda (Nome_Categoria);

