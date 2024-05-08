package hcmut.group2.project.chatapp.usermanager.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {
 
    @Override
    public String convertToDatabaseColumn(UserRole role) {
        if (role == null) {
            return null;
        }
        return role.getRoleString();
    }

    @Override
    public UserRole convertToEntityAttribute(String role) {
        if (role == null) {
            return null;
        }

        return Stream.of(UserRole.values())
          .filter(c -> c.getRoleString().equals(role))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}
