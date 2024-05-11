package pl.lodz.sii.promocodeapi.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import pl.lodz.sii.promocodeapi.core.model.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "promo_codes")
@Data
@NoArgsConstructor
public class PromoCodeEntity {
    @Id
    private String code;
    LocalDate expires;
    BigDecimal value;
    @Enumerated(EnumType.STRING)
    Currency currency;
    Integer maximumUsages;
    Integer hasBeenUsedTimes;
}
