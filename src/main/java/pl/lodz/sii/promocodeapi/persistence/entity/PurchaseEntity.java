package pl.lodz.sii.promocodeapi.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.sii.promocodeapi.core.model.Currency;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="purchases")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDate purchaseDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    ProductEntity product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promo_code_id", nullable = true)
    PromoCodeEntity promoCode;

    BigDecimal regularPrice;

    BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    Currency currency;
}
