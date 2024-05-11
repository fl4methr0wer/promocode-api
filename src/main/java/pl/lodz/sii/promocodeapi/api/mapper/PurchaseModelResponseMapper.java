package pl.lodz.sii.promocodeapi.api.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.sii.promocodeapi.api.model.PurchaseResponse;
import pl.lodz.sii.promocodeapi.api.model.product.ProductResponse;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;
import pl.lodz.sii.promocodeapi.core.model.Price;
import pl.lodz.sii.promocodeapi.core.model.Product;
import pl.lodz.sii.promocodeapi.core.model.Purchase;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class PurchaseModelResponseMapper implements Mapper<Purchase, PurchaseResponse> {

    private ProductModelResponseMapper productResponseMapper;

    @Override
    public PurchaseResponse map(Purchase purchase) throws ValidationException {
        Long id = purchase.getId();
        LocalDate purchaseDate = purchase.getDate();
        Product product = purchase.getProduct();
        ProductResponse productResponse = productResponseMapper.map(product);

        String promoCode = "";
        if (purchase.getPromoCode().isPresent()) {
            promoCode = purchase.getPromoCode().get().getCode();
        }
        Price regularPrice = purchase.getRegularPrice();
        Price totalPrice = purchase.getTotlaPrice();
        return new PurchaseResponse(
                id,
                purchaseDate,
                productResponse,
                promoCode,
                regularPrice,
                totalPrice);
    }

}
