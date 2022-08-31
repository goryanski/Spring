package app.EasyFoodAPI.services;
import app.EasyFoodAPI.dto.BasketProductDTO;
import app.EasyFoodAPI.dto.requests.BasketProductRequestDTO;
import app.EasyFoodAPI.dto.requests.RemoveBasketProductRequestDTO;
import app.EasyFoodAPI.dto.requests.UpdateBasketProductRequestDTO;
import app.EasyFoodAPI.dto.responses.BasketResponse;
import app.EasyFoodAPI.models.BasketProduct;
import app.EasyFoodAPI.models.Person;
import app.EasyFoodAPI.models.Product;
import app.EasyFoodAPI.repositories.BasketProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BasketsService {
    private final MapperService mapper;
    private final PeopleService peopleService;
    private final GetProductsService productsService;
    private final BasketProductRepository basketProductRepository;

    @Autowired
    public BasketsService(MapperService mapper, PeopleService peopleService, GetProductsService productsService, BasketProductRepository basketProductRepository) {
        this.mapper = mapper;
        this.peopleService = peopleService;
        this.productsService = productsService;
        this.basketProductRepository = basketProductRepository;
    }

    @Transactional
    public String addProductToBasket(BasketProductRequestDTO basketProductDTO) {
        // we already validate productId and userId and we sure that they are valid
        Product product = productsService.getProductById(basketProductDTO.getProductId()).get();

        // check if this product already is in the basket
        Optional<BasketProduct> existingProductInBasket = basketProductRepository.findByPersonIdAndProductId(
                basketProductDTO.getUserId(),
                basketProductDTO.getProductId());


        // add or update and return response what we've done
        if(existingProductInBasket.isEmpty()) {
            // this product isn't in basket - create a new entity and add to DB
            BasketProduct basketProduct = fillBasketProduct(basketProductDTO, product);
            basketProductRepository.save(basketProduct);
            return "added";
        } else {
            // this product already in basket - just update count column and update general price as well
            return updateProduct(existingProductInBasket.get(),
                    basketProductDTO.getCount(),
                    product.getPrice() * basketProductDTO.getCount());
        }
    }

    @Transactional
    public String removeProductFromBasket(RemoveBasketProductRequestDTO basketProductDTO) {
        // check if this product already is in the basket
        Optional<BasketProduct> existingProductInBasket = basketProductRepository.findByPersonIdAndProductId(
                basketProductDTO.getUserId(),
                basketProductDTO.getProductId());

        if(existingProductInBasket.isEmpty()) {
            return "nothing to remove";
        } else {
            basketProductRepository.delete(existingProductInBasket.get());
            return "removed";
        }
    }

    public int getUserProductsCountInBasket(int userId) {
        return basketProductRepository.countByPersonId(userId);
    }

    public BasketResponse getUserProducts(int id) {
        List<BasketProductDTO> productsDTO = basketProductRepository.findByPersonId(id)
                .stream()
                .map(mapper::convertBasketProduct)
                .collect(Collectors.toList());

        Double basketPrice = 0.0;
        for (var product: productsDTO) {
            basketPrice += product.getGeneralPrice();
        }
        basketPrice = Math.round(basketPrice * 100.0) / 100.0; // 2 digits after point

        return new BasketResponse(productsDTO, basketPrice);
    }

    @Transactional
    public String updateBasketProduct(UpdateBasketProductRequestDTO basketProductDTO) {
        Optional<BasketProduct> existingProductInBasket = basketProductRepository.findByPersonIdAndProductId(
                basketProductDTO.getUserId(),
                basketProductDTO.getProductId());

        if(existingProductInBasket.isEmpty()) {
            return "Product not fount";
        } else {
            return updateProduct(existingProductInBasket.get(),
                    basketProductDTO.getGeneralCount(),
                    basketProductDTO.getGeneralPrice());
        }
    }

    @Transactional
    public int deleteAllProducts(int id) {
        // return count of removed products
        return basketProductRepository.removeByPersonId(id);
    }

    private BasketProduct fillBasketProduct(BasketProductRequestDTO basketProductDTO,
                                            Product product) {
        BasketProduct basketProduct = new BasketProduct();
        Person person = peopleService.getPersonById(basketProductDTO.getUserId()).get();

        basketProduct.setProduct(product);
        basketProduct.setPerson(person);
        basketProduct.setCount(basketProductDTO.getCount());
        basketProduct.setGeneralPrice(product.getPrice() * basketProduct.getCount());
        return basketProduct;
    }

    @Transactional
    public String updateProduct(BasketProduct product, Float newCount, Float newPrice) {
        product.setCount(newCount);
        product.setGeneralPrice(newPrice);
        return "updated";
    }
}
