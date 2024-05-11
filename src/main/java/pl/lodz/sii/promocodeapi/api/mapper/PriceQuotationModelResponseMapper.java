package pl.lodz.sii.promocodeapi.api.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.sii.promocodeapi.api.model.PriceQuotationResponse;
import pl.lodz.sii.promocodeapi.api.model.product.ProductResponse;
import pl.lodz.sii.promocodeapi.core.exception.ValidationException;
import pl.lodz.sii.promocodeapi.core.model.Currency;
import pl.lodz.sii.promocodeapi.core.model.PriceQuotation;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class PriceQuotationModelResponseMapper implements Mapper<PriceQuotation, PriceQuotationResponse> {

    private ProductModelResponseMapper responseMapper;

    @Override
    public PriceQuotationResponse map(PriceQuotation quotation) throws ValidationException {
        ProductResponse response = responseMapper.map(quotation.getProduct());
        BigDecimal price = quotation.getPrice().getValue();
        Currency currency = quotation.getPrice().getCurrency();
        String warning = quotation.getWarning();
        return new PriceQuotationResponse(response, price, currency, warning);
    }

}
