package com.microsoft.projectmanagement.entity.enums;

public enum StatusEnum {
    EM_ABERTO("Em Aberto"),
    CONCLUIDO("Concluído"),
    CANCELADO("Cancelado");

    private final String displayName;

    StatusEnum(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }

    public static StatusEnum fromString(String displayName) {
        for (StatusEnum status : StatusEnum.values()) {
            if (status.displayName.equalsIgnoreCase(displayName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No enum constant for display name: " + displayName);
    }
}
