package com.jet.peoplemanagement.service;

import com.jet.peoplemanagement.user.UserServiceJWT;
import com.jet.peoplemanagement.exception.EntityNotFoundException;
import com.jet.peoplemanagement.model.Provider;
import com.jet.peoplemanagement.repository.ProviderRepository;
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

//@Primary
@Service
@Slf4j
public class ProviderService {

    @Autowired
    ProviderRepository providerRepository;

    @Autowired
    UserServiceJWT userService;

    public Page<Provider> findAll(Integer pageNumber, Integer pageSize) {
        Page<Provider> pageable = providerRepository.findAll(PageRequest.of(isNull(pageNumber) ? 0 : pageNumber, isNull(pageSize) ? 10 : pageSize));

        if (!pageable.hasContent()) throw new EntityNotFoundException(Provider.class);

        return pageable;
    }

    public Provider findById(String id) {
        Optional<Provider> providerData = providerRepository.findById(id);
        if (providerData.isPresent()) return providerData.get();
        else throw new EntityNotFoundException(Provider.class, "id", id);
    }

    public Provider save(Provider provider) {
        provider.setCreatedAt(LocalDateTime.now());
        provider.setActivated(true);
        Provider savedProvider = providerRepository.save(provider);
        JetUser jetUser = new JetUser(provider.getEmail(), MUDAR_123, UserType.PROVIDER.getName());
        userService.save(jetUser);
        return savedProvider;
    }

    public Provider update(String id, Provider updatedProvider) {
        Optional<Provider> providerData = providerRepository.findById(id);

        if (providerData.isPresent()) {
            Provider dbProvider = providerData.get();
            String ignored[] = {"id", "createdAt", "activated"};
            BeanUtils.copyProperties(updatedProvider, dbProvider, ignored);
            return providerRepository.save(dbProvider);
        } else throw new EntityNotFoundException(Provider.class, "id", id);
    }

    public void deleteById(String id) {
        Provider document = findById(id);
        log.info("Deleting provider with id {}", id);
        providerRepository.deleteById(document.getId());
    }

    public void inactivate(String id) {
        Provider document = findById(id);
        document.setActivated(false);
        log.info("Inactivating provider with cpf {}", document.getCpf());
        providerRepository.save(document);
    }

    public void activate(String id) {
        Provider document = findById(id);
        document.setActivated(true);
        log.info("Activating provider with cpf {}", document.getCpf());
        providerRepository.save(document);
    }


    public void deleteAll() {
        providerRepository.deleteAll();
    }

}
