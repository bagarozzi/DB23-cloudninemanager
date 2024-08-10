package it.unibo.cloudnine.data;

import java.util.Optional;

public record StaffMember(String code, String name, String surname, String phone, String job, Optional<String> kitchenRole) {
    
    @Override
    public final String toString() {
        return code;
    }
}
