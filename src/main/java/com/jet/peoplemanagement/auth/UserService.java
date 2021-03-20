package com.jet.peoplemanagement.auth;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public UserJet getByEmail(String email) {
        UserJet user = new UserJet(email, "123456");
        String generatedSecuredPasswordHash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(generatedSecuredPasswordHash);
        return user;
    }
}
