package com.example.springpractice.entity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.example.springpractice.entity.dto.ProductDto;

import org.hibernate.annotations.ManyToAny;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// @ToString
// @EqualsAndHashCode
public class Product {
    
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;
    
    private String name;

    private long count;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "orderId")
    // private Order order;
    // @OneToMany(mappedBy = "product", fetch = FetchType.EAGER) //확실히 너무느림
    @OneToMany(mappedBy = "product")
    Set<OrderProduct> orderProducts = new LinkedHashSet<>();

    public ProductDto toProductDto() {
        // List<ProductDto> dtos = this.orderProducts.stream()
        //                                             .map(Product::toProductDto)
        //                                             .collect(Collectors.toList());

        return ProductDto.builder()
                        .productId(this.productId)
                        .name(this.name)
                        .count(this.count)
                        .orderProducts(orderProducts)
                        .build();
    }

}