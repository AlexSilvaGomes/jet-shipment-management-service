package com.jet.peoplemanagement.service;

import com.jet.peoplemanagement.user.UserServiceJWT;
import com.jet.peoplemanagement.exception.EntityNotFoundException;
import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.repository.ClientRepository;
import com.jet.peoplemanagement.user.JetUser;
import com.jet.peoplemanagement.user.UserType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        if (clientData.isPresent()) {
            Client dbClient = clientData.get();
            String ignored [] = {"id", "createdAt", "activated"};
            BeanUtils.copyProperties(updatedClient, dbClient, ignored);
            return clientRepository.save(dbClient);
        } else throw new EntityNotFoundException(Client.class, "id", id);
    }

    public void deleteById(String id) {
        Client document = findById(id);
        log.info("Deleting client with id {}", id);
        clientRepository.deleteById(document.getId());
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

    public Client findByCnpj(String cnpj){
        Optional<Client> document = clientRepository.queroMeuCnpj(cnpj);
        if(document.isPresent()) return document.get();
        else throw new EntityNotFoundException(Client.class, "cnpj", cnpj);
    }


    public void deleteAll() {
        clientRepository.deleteAll();
    }

}
