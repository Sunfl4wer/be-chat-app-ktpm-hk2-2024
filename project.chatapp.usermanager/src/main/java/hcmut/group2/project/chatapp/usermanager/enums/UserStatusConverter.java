package hcmut.group2.project.chatapp.usermanager.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {
 
    @Override
    public String convertToDatabaseColumn(UserStatus status) {
        if (status == null) {
            return null;
        }
        return status.getStatusString();
    }

    @Override
    public UserStatus convertToEntityAttribute(String status) {
        if (status == null) {
            return null;
        }

        return Stream.of(UserStatus.values())
          .filter(c -> c.getStatusString().equals(status))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}
