package hcmut.group2.project.chatapp.usermanager.mapper;

import hcmut.group2.project.chatapp.usermanager.dto.FriendshipDto;
import hcmut.group2.project.chatapp.usermanager.entities.Friendship;

// @Mapper
// public interface FriendshipMapper {
//     FriendshipMapper INSTANCE = Mappers.getMapper(FriendshipMapper.class);

//     FriendshipDto toDto(Friendship friendship);
//     Friendship toEntity(FriendshipDto friendshipDTO);
// }

public class FriendshipMapper {
    public static FriendshipDto toDto(Friendship entity) {
        return new FriendshipDto(
            entity.getId(),
            entity.getUser(),
            entity.getFriend(),
            entity.getStatus(),
            entity.getCreationDatetime()
        );
    }

    public static Friendship toEntity(FriendshipDto dto) {
        return new Friendship(
            dto.getId(),
            dto.getUser(),
            dto.getFriend(),
            dto.getStatus(),
            dto.getCreationDatetime()
        );
    }
}