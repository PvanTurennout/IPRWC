package nl.pietervanturennout.api.responses;

import nl.pietervanturennout.api.responses.management.ResponseObject;
import nl.pietervanturennout.model.Product;

public class ProductResponse extends BaseResponse {
    public static ResponseObject generateMap(Product product, boolean sellable) {
        if (product == null || product.getProductId() == 0) {
            return null;
        }

        ResponseObject response = new ResponseObject();

        response.put("productId", product.getProductId());
        response.put("name", product.getName());
        response.put("brand", product.getBrand());
        response.put("price", product.getPrice());
        response.put("description", product.getDescription());
        response.put("imagePath", product.getImagePath());
        response.put("stock", product.getStock());

        if (sellable) {
            response.put("watch", new WatchResponse(product.getWatch()).getData());
        }

        return response;
    }

    public ProductResponse(Product product, boolean includeSellable) { data = generateMap(product, includeSellable); }

    public ProductResponse(Product product) { this(product, false); }
}
