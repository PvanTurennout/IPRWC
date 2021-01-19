package nl.pietervanturennout.controller;

import nl.pietervanturennout.api.requests.ProductRequest;
import nl.pietervanturennout.api.requests.ProductUpdateRequest;
import nl.pietervanturennout.dao.ProductDAO;
import nl.pietervanturennout.exceptions.DuplicateEntryException;
import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.NotFoundException;
import nl.pietervanturennout.exceptions.OperationFailedException;
import nl.pietervanturennout.model.Product;
import nl.pietervanturennout.model.Watch;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class ProductController {

    //Singleton
    private static final ProductController instance;
    static { instance = new ProductController(); }
    public static ProductController getInstance() { return instance; }


    private final ProductDAO productDAO;

    public ProductController() {
        productDAO = new ProductDAO();
    }

    public List<Product> getAllProducts() throws OperationFailedException, NotFoundException {
        return productDAO.getAllProducts();
    }

    public Product getProductFromId(int productId) throws OperationFailedException, NotFoundException {
        Product foundProduct = productDAO.getProductById(productId);
        Watch actualWatch = WatchController.getInstance().getWatchById(foundProduct.getWatch().getWatchId());
        foundProduct.setWatch(actualWatch);
        return foundProduct;
    }

    public List<Product> getProductsFromIdList(List<Integer> ids) throws NotFoundException, OperationFailedException {
        return productDAO.getProductsFromIdList(ids);
    }

    public List<Product> getTopSellingProducts() throws OperationFailedException, NotFoundException {
        return productDAO.getTopSellingProducts();
    }

    public List<Product> getHighlightedProducts() throws OperationFailedException, NotFoundException {
        return productDAO.getHighlightedProducts();
    }

    public void deleteProductById(int id) throws OperationFailedException, NotFoundException {
        Product productToDelete = getProductFromId(id);
        OrderProductsController.getInstance().productDeleteUpdate(productToDelete.getProductId());
        WishListController.getInstance().removeProductFromWishlistTable(productToDelete.getProductId());
        productDAO.removeProduct(id);
        WatchController.getInstance().removeWatch(productToDelete.getWatch());
    }

    public int createProductFromRequest(ProductRequest product) throws OperationFailedException, DuplicateEntryException, InvalidDataException {
        int itemId = createWatch(product.getWatch().getWatchModel());
        Product productModel = product.getProductModel();
        return productDAO.insertProduct(productModel, itemId);
    }

    public void updateProductFromRequest(ProductUpdateRequest update) throws OperationFailedException, DuplicateEntryException, InvalidDataException, NotFoundException {
        Product productFromRequest = update.getProductModel();

        updateWatch(productFromRequest.getWatch());

        productDAO.updateProduct(productFromRequest);
    }

    private int createWatch(Watch newWatch) throws OperationFailedException, DuplicateEntryException, InvalidDataException {
        return WatchController.getInstance().createWatchFromModel(newWatch);
    }

    private void updateWatch(Watch watch) throws OperationFailedException, DuplicateEntryException, InvalidDataException, NotFoundException {
        WatchController.getInstance().updateWatchFromModel(watch);
    }
}

