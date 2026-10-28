package com.lenskart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lenskart.request.OrderRequest;
import com.lenskart.response.OrderResponse;
import com.lenskart.service.OrderService;

@RestController
@RequestMapping("order/")
public class OrderController {

	@Autowired
	OrderService orderService;

	@PostMapping("place")
	public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
		System.out.println("OrderController.createOrder()............START");
		return orderService.saveOrder(orderRequest);
	}
	
	@GetMapping("findAllOrder")
	public List<OrderResponse> findOrder() {
		return orderService.getAllOrder();
	}
	
	@GetMapping("findOrderByCustomerId")
	public List<OrderResponse> findOrderByCustomerID(@RequestParam("customerId") int customerId) {
		return orderService.getOrderByCustomerID(customerId);
	}
}
