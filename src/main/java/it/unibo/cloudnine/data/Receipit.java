package it.unibo.cloudnine.data;

import java.sql.Date;
import java.sql.Time;

public record Receipit(Float contoFinale, Integer codComanda, 
            String modatlitaOrdine, Integer nCoperti,
            Date data, Time ora, String nomeMenu, Integer numTavolo, String codFiscale) {

}
