package com.jet.peoplemanagement.user;

import com.jet.peoplemanagement.auth.CredentialUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceJWT implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return getCredentialByUsername(email);
    }

    public CredentialUser getCredentialByUsername(String username) {

        Optional<JetUser> jetUser = userRepository.getByUsername(username);

        if (jetUser.isPresent()) {
            //String generatedSecuredPasswordHash = BCrypt.hashpw(jetUser.get().getPassword(), BCrypt.gensalt(12));
            CredentialUser user = new CredentialUser(username, jetUser.get().getPassword(), jetUser.get().getType());
            return user;
        } else throw new UsernameNotFoundException("User not found with username: " + username);
    }

    public JetUser getJetUserByUsername(String username) {

        Optional<JetUser> jetUser = userRepository.getByUsername(username);

        if (jetUser.isPresent()) {
            return jetUser.get();
        } else throw new UsernameNotFoundException("User not found with username: " + username);
    }

    public JetUser save(JetUser user) {
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public void deleteByUsername(String username) {

        JetUser currentUser = getJetUserByUsername(username);
        userRepository.delete(currentUser);
    }


}