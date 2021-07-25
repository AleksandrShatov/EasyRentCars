package com.erc.security.service;

import com.erc.domain.hibernate.Role;
import com.erc.domain.hibernate.User;
import com.erc.repository.hibernate.RoleRepository;
import com.erc.repository.hibernate.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProviderService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<User> searchResult = Optional.ofNullable(userRepository.findByLogin(username));
            if (searchResult.isPresent()) {
                User user = searchResult.get();
                return new org.springframework.security.core.userdetails.User( // ЭТО СПРИНГОВЫЙ ЮЗЕР, СО СВОЕЙ СТРУКТУРОЙ!
                        user.getLogin(),
                        user.getPassword(),
//                        ["ROLE_USER", "ROLE_ADMIN"]
                        AuthorityUtils.commaSeparatedStringToAuthorityList(roleRepository.getUserRoles(user).stream().map(Role::getRoleName).collect(Collectors.joining(",")))
                );
            } else {
                throw new UsernameNotFoundException(String.format("No user found with login '%s'.", username));
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("User with this login not found");
        }
    }
}