package com.lv.finance.services;

import com.lv.finance.entities.user.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    UserDetails loadUserByEmail(String email);

    Boolean userExistsByEmail(String email);

    User save(User user);
}
