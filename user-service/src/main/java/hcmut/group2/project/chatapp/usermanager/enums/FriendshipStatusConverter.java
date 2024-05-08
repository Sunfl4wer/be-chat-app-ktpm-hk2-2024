package hcmut.group2.project.chatapp.usermanager.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class FriendshipStatusConverter implements AttributeConverter<FriendshipStatus, String> {
 
    @Override
    public String convertToDatabaseColumn(FriendshipStatus status) {
        if (status == null) {
            return null;
        }
        return status.getStatusString();
    }

    @Override
    public FriendshipStatus convertToEntityAttribute(String status) {
        if (status == null) {
            return null;
        }

        return Stream.of(FriendshipStatus.values())
          .filter(c -> c.getStatusString().equals(status))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}
