package nl.pietervanturennout.api.responses;

import nl.pietervanturennout.api.responses.management.ListResponseObject;
import nl.pietervanturennout.model.ProductAmountModel;

import java.util.List;

public class ProductAmountListResponse extends BaseResponse{
    public static ListResponseObject generateList(List<ProductAmountModel> products){
        if (products == null)
            return null;

        return ListResponseObject.listGenerator(products, ProductAmountResponse::generateMap);
    }
    public ProductAmountListResponse(List<ProductAmountModel> products) {
        data = generateList(products);
    }
}
