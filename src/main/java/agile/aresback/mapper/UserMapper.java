package agile.aresback.mapper;

import agile.aresback.dto.UserDTO;
import agile.aresback.model.entity.User;
import agile.aresback.model.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null)
            return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole().name());
        return dto;
    }

    public User toEntity(UserDTO dto) {
        if (dto == null)
            return null;
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setRole(Role.valueOf(dto.getRole()));
        return user;
    }
}
