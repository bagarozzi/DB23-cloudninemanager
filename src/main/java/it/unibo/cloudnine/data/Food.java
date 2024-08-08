package it.unibo.cloudnine.data;

public record Food(int codice, String name, String category, String type, float price) {

    @Override
    public final String toString() {
        return codice + " - " + name;
    }
}
