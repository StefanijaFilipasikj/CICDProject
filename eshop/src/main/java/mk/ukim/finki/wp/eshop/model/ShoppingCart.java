package mk.ukim.finki.wp.eshop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateCreated;
    @ManyToMany
    private List<Product> products;

    public ShoppingCart(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
        this.products = new ArrayList<>();
    }
}
