package com.order.ecommerce.mapper;

import com.order.ecommerce.dto.ProductDto;
import com.order.ecommerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "version", ignore = true)
    Product toProductEntity(ProductDto productDto);

    ProductDto toProductDto(Product product);
}
