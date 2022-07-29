package app.EasyFoodAPI.services;
import app.EasyFoodAPI.models.ProvisionerCountry;
import app.EasyFoodAPI.repositories.ProvisionersCountriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProvisionersCountriesService {
    private final ProvisionersCountriesRepository countriesRepository;

    @Autowired
    public ProvisionersCountriesService(ProvisionersCountriesRepository countriesRepository) {
        this.countriesRepository = countriesRepository;
    }

    public List<ProvisionerCountry> getAll() {
        return countriesRepository.findAll();
    }
}
