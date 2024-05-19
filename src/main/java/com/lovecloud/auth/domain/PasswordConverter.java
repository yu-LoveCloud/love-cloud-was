package com.lovecloud.auth.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PasswordConverter implements AttributeConverter<Password, String> {

    @Override
    public String convertToDatabaseColumn(Password attribute) {
        return attribute == null ? null : attribute.getEncryptedPassword();
    }

    @Override
    public Password convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new Password(dbData);
    }
}
