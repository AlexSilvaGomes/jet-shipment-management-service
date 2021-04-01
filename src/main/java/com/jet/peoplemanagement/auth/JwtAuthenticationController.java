package com.jet.peoplemanagement.auth;

import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.model.Provider;
import com.jet.peoplemanagement.model.UserProfile;
import com.jet.peoplemanagement.service.ClientService;
import com.jet.peoplemanagement.service.ProviderService;
import com.jet.peoplemanagement.user.UserServiceJWT;
import com.jet.peoplemanagement.user.UserType;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Api(value = "Controle para autenticação de usuário")
public class JwtAuthenticationController {

    //@Autowired
    //private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserServiceJWT userDetailsService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProviderService providerService;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest credentials) {

        //authenticate(credencials.getUsername(), credencials.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(credentials.getUsername());

        if (!userDetails.getPassword().equals(credentials.getPassword()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha inválida, por favor tente novamente.");


       /* if ("provider".equals(credencials.getUserType())) {
            userDetails = providerService.loadUserByUsername(credencials.getUsername());
        } else if ("client".equals(credencials.getUserType())) {
            userDetails = clientService.loadUserByUsername(credencials.getUsername());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("userType is invalid");
        }
*/
        final String token = jwtTokenUtil.generateToken(userDetails);
        CredentialUser credentialUser = (CredentialUser)userDetails;
        UserResponse response = null;
        UserProfile profile = null;

        if(credentialUser.getType().equals(UserType.CLIENT.getName()) ){
            profile = clientService.findByEmail(credentialUser.getUsername());
        } else {
            profile =  providerService.findByEmail(credentialUser.getUsername());
        }

        response = getUserResponse(userDetails, token, credentialUser, profile);
        return ResponseEntity.ok(response);
    }

    private UserResponse getUserResponse(UserDetails userDetails, String token, CredentialUser credentialUser,
                                         UserProfile profile) {
        UserResponse response = new UserResponse(token,
                credentialUser.getType(), userDetails.getUsername(),
                profile.getName(), profile.getId(), profile.getCpf());

        return response;
    }

    /*private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }*/
}