package com.jet.peoplemanagement.params;

import com.jet.peoplemanagement.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class RegionService {

    @Autowired
    RegionRepository regionRepository;

    @Cacheable("params")
    public Page<Region> findAllPageable(Integer pageNumber, Integer pageSize) {
        Page<Region> pageable = regionRepository.findAll(PageRequest.of(isNull(pageNumber) ? 0 : pageNumber, isNull(pageSize) ? 10 : pageSize));

        if (!pageable.hasContent()) throw new EntityNotFoundException(Region.class);

        return pageable;
    }

    //@Cacheable("params")
    public List<Region> findAll() {

        List<Region> regions = regionRepository.findAll();

        if (CollectionUtils.isEmpty(regions)) throw new EntityNotFoundException(Region.class);

        return regions;
    }

    //@Cacheable(value = "findByClientId", key="#clientId")
    public List<Region> findByClientId(String clientId) {

        List<Region> regions = regionRepository.findByClientId(clientId);

        if (CollectionUtils.isEmpty(regions)) throw new EntityNotFoundException(Region.class);

        return regions;
    }

    public Region findById(String id) {
        Optional<Region> providerData = regionRepository.findById(id);
        if (providerData.isPresent()) return providerData.get();
        else throw new EntityNotFoundException(Region.class, "id", id);
    }

    public Region save(Region region) {
        region.setCreatedAt(LocalDateTime.now());
        Region savedAppParams = regionRepository.save(region);
        return savedAppParams;
    }

    public Region update(String id, Region region) {
        Optional<Region> regionData = regionRepository.findById(id);

        if (regionData.isPresent()) {

            Region currentDbRegion = regionData.get();
            mergeAppParams(region, currentDbRegion);
            return regionRepository.save(currentDbRegion);

        } else throw new EntityNotFoundException(Region.class, "id", id);
    }

    private void mergeAppParams(Region updatedAppParams, Region dbAppParams) {
        String ignored[] = {"id", "createdAt", "activated"};
        BeanUtils.copyProperties(updatedAppParams, dbAppParams, ignored);
    }

    public void deleteById(String id) {
        Region document = findById(id);
        log.info("Deleting region with id {}", id);
        regionRepository.deleteById(document.getId());
    }

    public void deleteAll() {
        regionRepository.deleteAll();
    }

    public void addAll(List<Region> regions) {
        if(!CollectionUtils.isEmpty(regions)){
            regions.forEach(region ->  region.setCreatedAt(LocalDateTime.now()) );
            regionRepository.saveAll(regions);
        } else {
            log.info("No regions send to add");
        }
    }

    @Cacheable(value = "mapRegions", key = "#clientId")
    public Map<String, Double> getRegionsPrice(String clientId) {
        Map<String, Double> regionsMap = new HashMap<>();

        findByClientId(clientId).forEach(region -> {
                    regionsMap.put(region.getClientId() + "#" + region.getName(), region.getPrice());
                }
        );
        return regionsMap;
    }

}
