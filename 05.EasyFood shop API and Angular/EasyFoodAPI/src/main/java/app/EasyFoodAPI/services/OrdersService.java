package app.EasyFoodAPI.services;

import app.EasyFoodAPI.dto.OrderDTO;
import app.EasyFoodAPI.dto.OrderedProductDTO;
import app.EasyFoodAPI.dto.requests.MakeOrderRequestDTO;
import app.EasyFoodAPI.dto.requests.OrdersRequestDTO;
import app.EasyFoodAPI.models.BasketProduct;
import app.EasyFoodAPI.models.Order;
import app.EasyFoodAPI.models.OrderedProduct;
import app.EasyFoodAPI.models.Product;
import app.EasyFoodAPI.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class OrdersService {
    private final MapperService mapper;
    private final OrdersRepository ordersRepository;
    private final BasketsRepository basketsRepository;
    private final PeopleRepository peopleRepository;
    private final OrderStatesRepository orderStatesRepository;
    private final OrderedProductsRepository orderedProductsRepository;

    @Autowired
    public OrdersService(MapperService mapper, OrdersRepository ordersRepository, BasketsRepository basketsRepository, PeopleRepository peopleRepository, OrderStatesRepository orderStatesRepository, OrderedProductsRepository orderedProductsRepository) {
        this.mapper = mapper;
        this.ordersRepository = ordersRepository;
        this.basketsRepository = basketsRepository;
        this.peopleRepository = peopleRepository;
        this.orderStatesRepository = orderStatesRepository;
        this.orderedProductsRepository = orderedProductsRepository;
    }


    @Transactional
    public void makeOrder(MakeOrderRequestDTO params) {
        // create and save order
        Order order = fillOrderEntity(params);
        ordersRepository.save(order);

        // create and save order products
        List<OrderedProduct> orderedProducts = getOrderedProducts(params);
        orderedProducts.forEach(product -> {
                    product.setOrder(order);
                    orderedProductsRepository.save(product);
                });
    }

    private List<OrderedProduct> getOrderedProducts(MakeOrderRequestDTO params) {
        List<BasketProduct> basketProducts = basketsRepository.findByPersonId(params.getUserId());
        return basketProducts.stream()
                .map(mapper::convertOrderedProduct)
                .collect(Collectors.toList());
    }

    private Order fillOrderEntity(MakeOrderRequestDTO params) {
        Order order = new Order();
        order.setPhoneNumber(params.getPhoneNumber());
        order.setGeneralPrice(params.getBasketPrice());
        order.setDate(Calendar.getInstance().getTime()); // current
        // we already validate userId and we sure that it's valid (user exists)
        order.setPerson(peopleRepository.findById(params.getUserId()).get());
        // we sure "processing" state exists
        order.setState(orderStatesRepository.findByName("processing").get());
        return order;
    }


    public List<OrderDTO> getUserOrders(OrdersRequestDTO params) {
        // with pagination and sorting by date
        Pageable paging = PageRequest.of(params.getCurrentPage(), params.getPageSize());
        Page<Order> pageTuts = ordersRepository.findByPersonIdOrderByDateDesc(params.getUserId(), paging);
        return pageTuts.getContent()
                .stream()
                .map(mapper::convertOrder)
                .collect(Collectors.toList());
    }



    public List<OrderedProductDTO> getOrderProducts(int orderId) {
        return orderedProductsRepository.findByOrderId(orderId)
                .stream()
                .map(mapper::convertOrderedProduct)
                .collect(Collectors.toList());
    }

}
