package com.microservices.orderservice.service;

import com.microservices.orderservice.client.ProductClient;
import com.microservices.orderservice.dto.ProductResponse;
import com.microservices.orderservice.entity.Order;
import com.microservices.orderservice.entity.OrderItem;
import com.microservices.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public OrderService(
            OrderRepository orderRepository,
            ProductClient productClient) {

        this.orderRepository = orderRepository;
        this.productClient = productClient;
    }

    // Create order
    @Transactional
    public Order createOrder(Order order) {

        if (order.getItems() != null) {

            for (OrderItem item : order.getItems()) {

                // Check product using Product Service
                ProductResponse product =
                        productClient.getProductById(
                                item.getProductId()
                        );

                if (product == null) {
                    throw new RuntimeException(
                            "Product not found: "
                                    + item.getProductId()
                    );
                }

                // Use current product price
                item.setPrice(product.getPrice());

                // Connect item to order
                item.setOrder(order);
            }
        }

        return orderRepository.save(order);
    }

    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get order by ID
    public Order getOrderById(Long id) {

        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found with id: " + id
                        ));
    }

    // Get orders by user email
    public List<Order> getOrdersByUserEmail(
            String userEmail) {

        return orderRepository.findByUserEmail(userEmail);
    }

    // Update order status
    @Transactional
    public Order updateOrderStatus(
            Long id,
            String status) {

        Order order = getOrderById(id);

        order.setStatus(status);

        return orderRepository.save(order);
    }

    // Delete order
    @Transactional
    public void deleteOrder(Long id) {

        Order order = getOrderById(id);

        orderRepository.delete(order);
    }
}