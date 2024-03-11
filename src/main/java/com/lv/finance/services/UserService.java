package com.lv.finance.services;

import com.lv.finance.dtos.PageResponse;
import com.lv.finance.dtos.user.UserDto;
import com.lv.finance.dtos.user.UserReceiveDto;
import com.lv.finance.entities.user.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    UserDetails loadUserByEmail(String email);

    Boolean userExistsByEmail(String email);

    User save(User user);

    PageResponse<UserDto> findAll(int page, int size, String sortBy, String sortDir);

    UserDto update(UserReceiveDto userReceiveDto, Long userId);
}
