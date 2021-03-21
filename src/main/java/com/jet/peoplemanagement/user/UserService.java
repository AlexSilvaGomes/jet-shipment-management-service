package com.jet.peoplemanagement.user;

import com.jet.peoplemanagement.auth.CredentialUser;
import com.jet.peoplemanagement.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

   /* public CredentialUser getByEmail(String email) {

        String generatedSecuredPasswordHash = BCrypt.hashpw("123456", BCrypt.gensalt(12));
        //CredentialUser user = new CredentialUser(email, generatedSecuredPasswordHash);
        //user.setType("client");
        return user;
    }*/
}
