package mk.ukim.finki.wp.eshop.repository;

import mk.ukim.finki.wp.eshop.model.ShoppingCart;
import mk.ukim.finki.wp.eshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
