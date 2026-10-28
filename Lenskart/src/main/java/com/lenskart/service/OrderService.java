package com.lenskart.service;

import com.lenskart.repository.PaymentRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lenskart.entity.OrderEntity;
import com.lenskart.entity.PaymentEntity;
import com.lenskart.exception.OrderNotCreatedException;
import com.lenskart.exception.OrderNotFoundException;
import com.lenskart.repository.OrderRepository;
import com.lenskart.request.OrderRequest;
import com.lenskart.response.OrderResponse;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	PaymentRepository paymentRepository;

	OrderService(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}

	@Transactional
	public OrderResponse saveOrder(OrderRequest orderRequest) {
		System.out.println("OrderService.saveOrder().......START");

		OrderEntity orderEntity = new OrderEntity();

		orderEntity.setCustomerId(orderRequest.getCustomerId());
		orderEntity.setDeliveryAddress(orderRequest.getDeliveryAddress());
		orderEntity.setMobile(orderRequest.getMobile());
		orderEntity.setOrderNumber(getOrderNumber());
		orderEntity.setOrderStatus("CONFIRMED");
		orderEntity.setPaymentStatus("PAID");
		orderEntity.setPrice(orderRequest.getPrice());
		orderEntity.setProductName(orderRequest.getProductName());
		orderEntity.setProductType(orderRequest.getProductType());
		orderEntity.setQuantity(orderRequest.getQuantity());
		orderEntity.setTotalAmount((orderRequest.getPrice() * orderRequest.getQuantity()));
		orderEntity.setOrderDate(LocalDateTime.now());

		OrderEntity entity = null;

		PaymentEntity paymentEntity = new PaymentEntity();
		paymentEntity.setPaymentAmt((orderRequest.getPrice() * orderRequest.getQuantity()));
		paymentEntity.setPaymentRef(getPaymentReference());
		paymentEntity.setPaymentStatus("PAID");

		PaymentEntity responsePaymentEntity = null;
		OrderResponse response = new OrderResponse();

		try {
			entity = orderRepository.save(orderEntity);
			responsePaymentEntity = paymentRepository.save(paymentEntity);

			response.setOrderId(entity.getOrderId());
			response.setCustomerId(entity.getCustomerId());
			response.setDeliveryAddress(entity.getDeliveryAddress());
			response.setMobile(entity.getMobile());
			response.setOrderNumber(entity.getOrderNumber());
			response.setOrderStatus(entity.getOrderStatus());
			response.setPaymentStatus(responsePaymentEntity.getPaymentStatus());
			response.setPrice(entity.getPrice());
			response.setProductName(entity.getProductName());
			response.setProductType(entity.getProductType());
			response.setQuantity(entity.getQuantity());
			response.setTotalAmount(entity.getTotalAmount());
			response.setOrderDate(entity.getOrderDate());
		} catch (Exception e) {
			e.printStackTrace();
			throw new OrderNotCreatedException("Order not created. due to " + e.getMessage());
		}

		System.out.println("OrderService.saveOrder()..........Ended");
		return response;
	}

	private String getPaymentReference() {
		Random random = new Random();
		int num = 10000 + random.nextInt(90000);
		return "REF-" + num;
	}

	private String getOrderNumber() {
		Random random = new Random();
		int num = 1000000 + random.nextInt(9000000);
		return "LK" + num;
	}

	public List<OrderResponse> getAllOrder() {

		List<OrderEntity> entities = (List<OrderEntity>) orderRepository.findAll();

		List<OrderResponse> orderResponses = new ArrayList<OrderResponse>();

		for (OrderEntity entity : entities) {
			OrderResponse response = new OrderResponse();

			response.setOrderId(entity.getOrderId());
			response.setCustomerId(entity.getCustomerId());
			response.setDeliveryAddress(entity.getDeliveryAddress());
			response.setMobile(entity.getMobile());
			response.setOrderNumber(entity.getOrderNumber());
			response.setOrderStatus(entity.getOrderStatus());
			response.setPaymentStatus(entity.getPaymentStatus());
			response.setPrice(entity.getPrice());
			response.setProductName(entity.getProductName());
			response.setProductType(entity.getProductType());
			response.setQuantity(entity.getQuantity());
			response.setTotalAmount(entity.getTotalAmount());
			response.setOrderDate(entity.getOrderDate());

			orderResponses.add(response);
		}

		return orderResponses;
	}
	
	public List<OrderResponse> getOrderByCustomerID(int customerId) {

		List<OrderEntity> entities = (List<OrderEntity>) orderRepository.findOrderByCustomerId(customerId);

		List<OrderResponse> orderResponses = new ArrayList<OrderResponse>();

		try {
			for (OrderEntity entity : entities) {
				OrderResponse response = new OrderResponse();

				response.setOrderId(entity.getOrderId());
				response.setCustomerId(entity.getCustomerId());
				response.setDeliveryAddress(entity.getDeliveryAddress());
				response.setMobile(entity.getMobile());
				response.setOrderNumber(entity.getOrderNumber());
				response.setOrderStatus(entity.getOrderStatus());
				response.setPaymentStatus(entity.getPaymentStatus());
				response.setPrice(entity.getPrice());
				response.setProductName(entity.getProductName());
				response.setProductType(entity.getProductType());
				response.setQuantity(entity.getQuantity());
				response.setTotalAmount(entity.getTotalAmount());
				response.setOrderDate(entity.getOrderDate());

				orderResponses.add(response);
			}
		} catch (Exception e) {
			throw new OrderNotFoundException("Order Not found for Customer "+ customerId);
		}

		return orderResponses;
	}
}
