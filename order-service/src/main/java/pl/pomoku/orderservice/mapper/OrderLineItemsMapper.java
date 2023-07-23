package pl.pomoku.orderservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.pomoku.orderservice.dto.OrderLineItemsDto;
import pl.pomoku.orderservice.entity.OrderLineItems;

@Mapper
public interface OrderLineItemsMapper {
    OrderLineItemsMapper INSTANCE = Mappers.getMapper(OrderLineItemsMapper.class);

    @Mapping(target = "id", ignore = true)
    OrderLineItems dtoToEntity(OrderLineItemsDto dto);

    OrderLineItemsDto entityToDto(OrderLineItems entity);
}
