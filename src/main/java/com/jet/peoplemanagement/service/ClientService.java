package com.jet.peoplemanagement.service;

import com.jet.peoplemanagement.model.UserProfile;
import com.jet.peoplemanagement.shipment.Shipment;
import com.jet.peoplemanagement.user.UserServiceJWT;
import com.jet.peoplemanagement.exception.EntityNotFoundException;
import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.repository.ClientRepository;
import com.jet.peoplemanagement.user.JetUser;
import com.jet.peoplemanagement.user.UserType;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.jet.peoplemanagement.util.Constants.MUDAR_123;
import static java.util.Objects.isNull;

@Service
@Slf4j
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    UserServiceJWT userService;

    public Page<Client> findAll(Integer pageNumber, Integer pageSize) {
        Page<Client> pageable = clientRepository.findAll(PageRequest.of(isNull(pageNumber) ? 0 : pageNumber, isNull(pageSize) ? 10 : pageSize));
        if (!pageable.hasContent()) throw new EntityNotFoundException(Client.class);
        return pageable;
    }

    public Client findById(String id) {
        Optional<Client> clientData = clientRepository.findById(id);
        if (clientData.isPresent()) return clientData.get();
        else throw new EntityNotFoundException(Client.class, "id", id);
    }

    public Client save(Client client) {
        client.setCreatedAt(LocalDateTime.now());
        client.setActivated(true);
        Client clientSaved = clientRepository.save(client);
        JetUser jetUser = new JetUser(client.getEmail(), MUDAR_123, UserType.CLIENT.getName());
        userService.save(jetUser);
        return clientSaved;
    }

    public Client update(String id, Client updatedClient) {
        Optional<Client> clientData = clientRepository.findById(id);
        Client clientSaved = null;

        if (clientData.isPresent()) {
            Client currentDbClient = clientData.get();

            if(hasEmailUpdates(updatedClient, currentDbClient)){
                JetUser jetUser = userService.getJetUserByUsername(currentDbClient.getEmail());
                jetUser.setUsername(updatedClient.getEmail());
                mergeClient(updatedClient, currentDbClient);
                clientSaved = clientRepository.save(currentDbClient);
                userService.save(jetUser);

            } else{
                mergeClient(updatedClient, currentDbClient);
                clientSaved = clientRepository.save(currentDbClient);
            }
            return clientSaved;

        } else throw new EntityNotFoundException(Client.class, "id", id);
    }

    private boolean hasEmailUpdates(Client updatedClient, Client currentDbClient) {
        return !currentDbClient.getEmail().equals(updatedClient.getEmail());
    }

    private void mergeClient(Client updatedClient, Client currentDbClient) {
        String ignored[] = {"id", "createdAt", "activated"};
        BeanUtils.copyProperties(updatedClient, currentDbClient, ignored);
    }

    public void deleteById(String id) {
        Client document = findById(id);
        log.info("Deleting client with id {}", id);
        Client clientFound = findById(id);
        clientRepository.deleteById(document.getId());
        userService.deleteByUsername(clientFound.getEmail());
    }

    public void inactivate(String id) {
        Client document = findById(id);
        document.setActivated(false);
        log.info("Inactivating client with cnpj {}", document.getCnpj());
        clientRepository.save(document);
    }

    public void activate(String id) {
        Client document = findById(id);
        document.setActivated(true);
        log.info("Activating client with cnpj {}", document.getCnpj());
        clientRepository.save(document);
    }

    public Page findByCnpjLike(String cnpj) {
        List<Client> documents = clientRepository.findByCnpjLike(cnpj);

        if (!Collections.isEmpty(documents)) {
            Page page = new PageImpl(documents, PageRequest.of(0, documents.size()), documents.size());
            return page;
        } else throw new EntityNotFoundException(Shipment.class, "cnpj", cnpj);

    }

    public Client findByCnpj(String cnpj) {
        Optional<Client> document = clientRepository.queroMeuCnpj(cnpj);
        if (document.isPresent()) {
            return document.get();
        } else throw new EntityNotFoundException(Client.class, "cnpj", cnpj);
    }

    public UserProfile findByEmail(String email) {
        Optional<Client> profile = clientRepository.queroByEmail(email);
        if (profile.isPresent()) return (UserProfile) profile.get();
        else throw new EntityNotFoundException(Client.class, "email", email);
    }

    public void deleteAll() {
        clientRepository.deleteAll();
    }

    public Client findByCompanyName(String companyName) {
        List<Client> document = clientRepository.findByCompanyRegexIgnoreCase(companyName, PageRequest.of(0, 1));
        if (!Collections.isEmpty(document)) {
            return document.stream().findFirst().get();
        } else throw new EntityNotFoundException(Client.class, "companyName", companyName);
    }
}
