package app.EasyFoodAPI.services;
import app.EasyFoodAPI.dto.*;
import app.EasyFoodAPI.dto.auth.RegisterPersonDTO;
import app.EasyFoodAPI.models.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class MapperService {
    private final ModelMapper modelMapper;

    public MapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CategoryDTO convertCategory(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    public ShortProductInfoDTO convertProduct(Product product) {
        ShortProductInfoDTO productDTO = modelMapper.map(product, ShortProductInfoDTO.class);
        // in product field measurement is object, but we need to set in productDTO only name of measurement
        productDTO.setWeightMeasurement(product.getMeasurement().getName());
        return productDTO;
    }

    public FullProductInfoDTO convertFullProduct(Product product) {
        FullProductInfoDTO productDTO = modelMapper.map(product, FullProductInfoDTO.class);
        // change fields which need to map manually
        productDTO.setWeightMeasurement(product.getMeasurement().getName());
        productDTO.setBrand(product.getBrand().getName());
        productDTO.setCountry(product.getCountry().getName());
        productDTO.setCategoryId(product.getCategory().getId());
        return productDTO;
    }

    public ProvisionerCountryDTO convertCountry(ProvisionerCountry country) {
        return modelMapper.map(country, ProvisionerCountryDTO.class);
    }

    public BrandDTO convertBrand(Brand brand) {
        return modelMapper.map(brand, BrandDTO.class);
    }


    public BasketProductDTO convertBasketProduct(BasketProduct basketProduct) {
        // map manually
        BasketProductDTO productDTO = new BasketProductDTO();
        productDTO.setProductId(basketProduct.getProduct().getId());
        productDTO.setUserId(basketProduct.getPerson().getId());
        productDTO.setName(basketProduct.getProduct().getName());
        productDTO.setPhotoPath(basketProduct.getProduct().getPhotoPath());
        productDTO.setWeight(basketProduct.getProduct().getWeight());
        productDTO.setWeightMeasurement(basketProduct.getProduct().getMeasurement().getName());
        productDTO.setWeightFlexible(basketProduct.getProduct().isWeightFlexible());
        productDTO.setPricePerOneItem(basketProduct.getProduct().getPrice());
        productDTO.setGeneralCount(basketProduct.getCount());
        productDTO.setGeneralPrice(basketProduct.getGeneralPrice());
        productDTO.setCountInStorage(basketProduct.getProduct().getAmountInStorage());
        return productDTO;
    }

    public OrderedProduct convertOrderedProduct(BasketProduct product) {
        OrderedProduct orderedProduct = modelMapper.map(product, OrderedProduct.class);
        orderedProduct.setId(null); // we don't need to map id - orderedProduct will have his own id
        return orderedProduct;
    }

    public OrderDTO convertOrder(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setPrice(order.getGeneralPrice());
        orderDTO.setState(order.getState().getName());
        // date
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateStr = dateFormat.format(order.getDate());
        orderDTO.setDate(dateStr);
        return orderDTO;
    }

    public OrderedProductDTO convertOrderedProduct(OrderedProduct product) {
        OrderedProductDTO productDTO = new OrderedProductDTO();
        productDTO.setOriginalProductId(product.getProduct().getId());
        productDTO.setName(product.getProduct().getName());
        productDTO.setCount(product.getCount());
        productDTO.setPrice(product.getGeneralPrice());
        return productDTO;
    }
}
