package nl.pietervanturennout.controller;

import nl.pietervanturennout.dao.WishListDAO;
import nl.pietervanturennout.exceptions.DuplicateEntryException;
import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.NotFoundException;
import nl.pietervanturennout.exceptions.OperationFailedException;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class WishListController {

    //Singleton
    private static final WishListController instance;
    static { instance = new WishListController(); }
    public static WishListController getInstance() { return instance; }


    private final WishListDAO wishListDAO;

    public WishListController(){
        this.wishListDAO = new WishListDAO();
    }

    public void addProductToAccount(int accountId, int productId) throws DuplicateEntryException, OperationFailedException, InvalidDataException, NotFoundException {
        wishListDAO.add(accountId, productId);
    }

    public void deleteProductFromAccount(int accountId, int productId) throws NotFoundException, OperationFailedException {
        wishListDAO.remove(accountId, productId);
    }

    public void removeProductFromWishlistTable(int productId) throws OperationFailedException {
        wishListDAO.removeProduct(productId);
    }

    public List<Integer> getAccountWishlist(int accountId) throws OperationFailedException {
        return wishListDAO.get(accountId);
    }

}
