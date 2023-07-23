package pl.pomoku.inventoryservice.reposioty;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pomoku.inventoryservice.entity.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
