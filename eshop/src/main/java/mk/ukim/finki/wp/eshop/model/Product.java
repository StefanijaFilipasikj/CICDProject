package mk.ukim.finki.wp.eshop.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    private Double price;
    private Integer quantity;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Manufacturer manufacturer;

    public Product(String name, String image, Double price, Integer quantity, Category category, Manufacturer manufacturer) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.manufacturer = manufacturer;
    }
}
