package nl.pietervanturennout.api.responses;

import nl.pietervanturennout.api.responses.management.ListResponseObject;
import nl.pietervanturennout.model.Product;

import java.util.List;

public class ProductListResponse extends BaseResponse{

    public static ListResponseObject generateList(List<Product> products, boolean sellable){
        if (products == null)
            return null;

        return ListResponseObject.listGenerator(products, sellable, ProductResponse::generateMap);
    }

    public ProductListResponse(List<Product> products) { this(products, false); }

    public ProductListResponse(List<Product> products, boolean includeWatch) {
        data = generateList(products, includeWatch);
    }
}
