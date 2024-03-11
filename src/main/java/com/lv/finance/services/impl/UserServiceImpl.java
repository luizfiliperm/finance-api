package com.lv.finance.services.impl;

import com.lv.finance.dtos.PageResponse;
import com.lv.finance.dtos.user.UserDto;
import com.lv.finance.dtos.user.UserReceiveDto;
import com.lv.finance.entities.user.User;
import com.lv.finance.exceptions.FinanceException;
import com.lv.finance.repositories.UserRepository;
import com.lv.finance.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new FinanceException("User not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public Boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public PageResponse<UserDto> findAll(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> usersPage = userRepository.findAll(pageable);
        List<User> usersList = usersPage.getContent();

        List<UserDto> content = usersList.stream().map(UserDto::new).toList();

        return new PageResponse<>(content, usersPage.getNumber(), usersPage.getSize(), usersPage.getTotalElements(), usersPage.getTotalPages(), usersPage.isLast());
    }

    @Override
    @Transactional
    public UserDto update(UserReceiveDto userReceiveDto, Long userId) {

        User user = userRepository.findByEmail(userReceiveDto.getEmail())
                .orElseThrow(() -> new FinanceException("User not found", HttpStatus.NOT_FOUND));

        if(!user.getId().equals(userId)){
            throw new FinanceException("User not found", HttpStatus.FORBIDDEN);
        }

        if(!passwordMatches(userReceiveDto.getCurrentPassword(), user.getPassword())){
            throw new FinanceException("Invalid password", HttpStatus.BAD_REQUEST);
        }

        updateUser(userReceiveDto, user);
        return new UserDto(userRepository.save(user));
    }


    @Override
    public UserDetails loadUserByUsername(String username){
        return loadUserByEmail(username);
    }

    private boolean passwordMatches(String password, String encodedPassword){
        return passwordEncoder.matches(password, encodedPassword);
    }

    private void updateUser(UserReceiveDto userReceiveDto, User user){
        user.setName(userReceiveDto.getName());
        user.setEmail(userReceiveDto.getEmail());
        user.setPassword(passwordEncoder.encode(userReceiveDto.getNewPassword()));
        user.setPersonalInformation(userReceiveDto.getPersonalInformation().convertToPersonalInformation());
    }
}
