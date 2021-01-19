package nl.pietervanturennout.api.responses;

import nl.pietervanturennout.api.responses.management.ResponseObject;
import nl.pietervanturennout.model.ProductAmountModel;

public class ProductAmountResponse extends BaseResponse{

    public static ResponseObject generateMap(ProductAmountModel productAmountModel){
        ResponseObject response = new ResponseObject();

        response.put("product", new ProductResponse(productAmountModel.getProduct()).getData());
        response.put("amount", productAmountModel.getAmount());
        return response;
    }

    public ProductAmountResponse(ProductAmountModel productAmountModel) { data = generateMap(productAmountModel); }
}
