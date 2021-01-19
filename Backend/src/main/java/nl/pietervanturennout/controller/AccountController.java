package nl.pietervanturennout.controller;

import nl.pietervanturennout.api.requests.AuthenticationRequest;
import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.NotFoundException;
import nl.pietervanturennout.utils.BCrypt;
import nl.pietervanturennout.dao.AccountDAO;
import nl.pietervanturennout.exceptions.DuplicateEntryException;
import nl.pietervanturennout.exceptions.OperationFailedException;
import nl.pietervanturennout.model.Account;
import nl.pietervanturennout.utils.types.Group;

import javax.inject.Singleton;
import javax.ws.rs.InternalServerErrorException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class AccountController {

    private static final AccountController instance;
    static { instance = new AccountController(); }
    public static AccountController getInstance() { return instance; }


    private final AccountDAO accountDAO;

    public AccountController() {
        this.accountDAO = new AccountDAO();
    }

    public Account getAccountFromEmail(String email) throws OperationFailedException, NotFoundException {
        Account account =  accountDAO.searchAccountByEmail(email);
        account.setWishList(WishListController.getInstance().getAccountWishlist(account.getAccountId()));
        return account;
    }

    public Account getAccountFromId(int accountId) throws OperationFailedException, NotFoundException {
        Account account =  accountDAO.searchAccountById(accountId);
        account.setWishList(WishListController.getInstance().getAccountWishlist(accountId));
        return account;
    }

    public int registerAccount(AuthenticationRequest account) throws OperationFailedException, DuplicateEntryException, InvalidDataException {
        Account accountToRegister = new Account();
        accountToRegister.setMailAddress(account.getMailAddress());

        accountToRegister.setPassword(new BCrypt().hash(account.getPassword()));

        int accountId = accountDAO.registerAccount(accountToRegister);

        if (accountId == 0)
            throw new InternalServerErrorException("Not Able To Register Account");

        return accountId;
    }

    public int registerSeller(AuthenticationRequest account) throws OperationFailedException, DuplicateEntryException, InvalidDataException, NotFoundException {
        int accountId = registerAccount(account);

        updateGroup(accountId, Group.SELLER);

        return accountId;
    }

    public int registerAdmin(AuthenticationRequest account) throws OperationFailedException, DuplicateEntryException, InvalidDataException, NotFoundException {
        int accountId = registerAccount(account);

        updateGroup(accountId, Group.ADMIN);

        return accountId;
    }

    private void updateGroup(int account, Group group) throws OperationFailedException, InvalidDataException, NotFoundException {
        accountDAO.changeGroup(account, group);
    }

    public void addOrder(int accountId, int orderId) throws NotFoundException, OperationFailedException {
        accountDAO.addOrderToAccount(accountId, orderId);
    }

    public void removeOrder(int orderId) throws NotFoundException, OperationFailedException {
        accountDAO.removeOrderFromAccount(orderId);
    }

    public List<Integer> getOrders(int accountId) throws NotFoundException, OperationFailedException{
        return accountDAO.getAccountOrders(accountId);
    }
}
