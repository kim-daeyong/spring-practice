package com.example.springpractice.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.springpractice.entity.dto.OrderProductDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct {

    @Id @GeneratedValue
    private long id;

    @ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "productId")
    private Product product;

    public OrderProductDto toOrderProductDto() {

        return OrderProductDto.builder()
                        .id(this.id)
                        .orderDto(this.order.toOrderDto())
                        .productDto(this.product.toProductDto())
                        .build();
    }
    
}