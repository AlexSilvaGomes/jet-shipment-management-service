package com.jet.peoplemanagement.auth;

import com.jet.peoplemanagement.user.JetUser;
import com.jet.peoplemanagement.user.UserRepository;
import com.jet.peoplemanagement.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceJWT implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return getByUsername(email);
    }

    public CredentialUser getByUsername(String username) {

        Optional<JetUser> jetUser = userRepository.getByUsername(username);

        if (jetUser.isPresent()) {

            //String generatedSecuredPasswordHash = BCrypt.hashpw(jetUser.get().getPassword(), BCrypt.gensalt(12));
            CredentialUser user = new CredentialUser(username, jetUser.get().getPassword(), jetUser.get().getType());
            return user;
        } else throw new UsernameNotFoundException("User not found with username: " + username);
    }

    public JetUser save(JetUser user) {
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }


}