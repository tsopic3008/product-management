package com.tscore.service;

import com.tscore.UserDTO;
import com.tscore.dao.UserDAO;
import com.tscore.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class UserService {

    @Inject
    UserDAO userDAO;

    @Transactional
    public void registerUser(UserDTO userDto) {
        User user = toEntity(userDto);
        String hashedPassword = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        userDAO.persist(user);
    }

    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setFirst_name(dto.getFirstName());
        user.setLast_name(dto.getLastName());
        return user;
    }

    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setFirstName(user.getFirst_name());
        dto.setLastName(user.getLast_name());
        return dto;
    }


}
