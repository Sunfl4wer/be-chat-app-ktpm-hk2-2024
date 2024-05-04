package hcmut.group2.project.chatapp.usermanager.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class UserActivityConverter implements AttributeConverter<UserActivity, String> {
 
    @Override
    public String convertToDatabaseColumn(UserActivity activity) {
        if (activity == null) {
            return null;
        }
        return activity.getActivityString();
    }

    @Override
    public UserActivity convertToEntityAttribute(String activity) {
        if (activity == null) {
            return null;
        }

        return Stream.of(UserActivity.values())
          .filter(c -> c.getActivityString().equals(activity))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}
