package it.unibo.cloudnine.data;

public record Vivanda(Integer codVivanda, String nome, Integer nPiatti) {

        @Override
        public final String toString() {
            return codVivanda + " " + nome;
        }
}