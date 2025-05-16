package com.unrn.vv.crud.utils.enums;

public enum SaleStatus {
    PENDING,
    PAID,
    FINALIZED,
    CANCELLED;

    public static boolean isValidStatus(String status) {
        for (SaleStatus value : values()) {
            if (value.name().equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }
}
