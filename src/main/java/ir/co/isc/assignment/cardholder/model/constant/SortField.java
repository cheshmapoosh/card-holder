package ir.co.isc.assignment.cardholder.model.constant;

import lombok.Getter;

public enum SortField {
    TYPE("type"),
    EXPIRE_DATE("expireDate"),
    HOLDER_NATIONAL_CODE("holder.nationalCode")
    ;


    @Getter
    private final String type;

    SortField(String type) {
        this.type = type;
    }
}
