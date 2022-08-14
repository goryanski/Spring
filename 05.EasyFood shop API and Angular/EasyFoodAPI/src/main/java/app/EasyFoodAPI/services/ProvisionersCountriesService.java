package app.EasyFoodAPI.services;
import app.EasyFoodAPI.dto.ProvisionerCountryDTO;
import app.EasyFoodAPI.repositories.ProvisionersCountriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProvisionersCountriesService {
    private final ProvisionersCountriesRepository countriesRepository;
    private final MapperService mapper;

    @Autowired
    public ProvisionersCountriesService(ProvisionersCountriesRepository countriesRepository, MapperService mapper) {
        this.countriesRepository = countriesRepository;
        this.mapper = mapper;
    }

    public List<ProvisionerCountryDTO> getAll() {
        return countriesRepository.findAllByOrderByNameAsc()
                .stream()
                .map(mapper::convertCountry)
                .collect(Collectors.toList());
    }
}
