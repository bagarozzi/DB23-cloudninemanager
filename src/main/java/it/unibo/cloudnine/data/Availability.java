package it.unibo.cloudnine.data;

public record Availability(String day, String service) {
    
    @Override
    public final String toString() {
        return day + " - " + service;
    }
}
