package com.example.springpractice.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.springpractice.entity.dto.OrderDto;
import com.example.springpractice.entity.dto.ProductDto;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    
    private String name;

    @CreatedDate
	@Column(updatable = false)
    private LocalDateTime createAt;

    @OneToMany(mappedBy = "order")
    private List<Product> products = new ArrayList<>();

    public OrderDto toOrderDto() {
        List<ProductDto> dtos = this.products.stream()
                                                .map(Product::toProductDto)
                                                .collect(Collectors.toList());

        return OrderDto.builder()
                        .orderId(this.orderId)
                        .name(this.name)
                        .products(dtos)
                        .build();
    }
    
}