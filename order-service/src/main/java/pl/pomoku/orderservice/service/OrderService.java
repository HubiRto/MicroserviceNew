package pl.pomoku.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import pl.pomoku.orderservice.dto.OrderLineItemsDto;
import pl.pomoku.orderservice.dto.OrderRequest;
import pl.pomoku.orderservice.dto.response.InventoryResponse;
import pl.pomoku.orderservice.entity.Order;
import pl.pomoku.orderservice.entity.OrderLineItems;
import pl.pomoku.orderservice.mapper.OrderLineItemsMapper;
import pl.pomoku.orderservice.repository.OrderRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final OrderRepository repository;
    private final WebClient webClient;
    private final OrderLineItemsMapper orderLineItemsMapper = OrderLineItemsMapper.INSTANCE;
    public void placeOrder(OrderRequest request) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());


        List<OrderLineItems> orderLineItemsList = request.orderLineItemsDtoList()
                .stream().map(orderLineItemsMapper::dtoToEntity).collect(Collectors.toList());
        order.setOrderLineItemsList(orderLineItemsList);

        List<String> skuCodes = orderLineItemsList.stream().map(OrderLineItems::getSkuCode).toList();

        //Synchronous Communication
        //Call inventory service, and place order if product is in stock

        InventoryResponse[] inventoryResponsesArray = webClient.get().uri(
                        "http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build()
                )
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        if (inventoryResponsesArray == null) throw new NullPointerException("Response is null");
        boolean allProductsInStock = Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::isInStock);

        if (!allProductsInStock) throw new IllegalArgumentException("Product is not in stock, please try again later.");
        repository.save(order);
    }
}
