package pl.pomoku.productservice.dto.respons;

import java.math.BigDecimal;

public record ProductResponse(String id, String name, String decimal, BigDecimal price) {
}
