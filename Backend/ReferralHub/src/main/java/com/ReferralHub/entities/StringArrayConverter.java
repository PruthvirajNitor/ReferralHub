package com.ReferralHub.entities;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringArrayConverter implements AttributeConverter<String[],String> {
    @Override
    public String convertToDatabaseColumn(String[] attribute) {
        if (attribute == null || attribute.length == 0) {
            return "";
        }
        return Arrays.stream(attribute)
                .collect(Collectors.joining(","));
    }

    @Override
    public String[] convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new String[0];
        }
        return dbData.split(",");
    }
}
