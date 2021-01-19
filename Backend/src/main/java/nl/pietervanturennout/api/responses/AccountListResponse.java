package nl.pietervanturennout.api.responses;

import nl.pietervanturennout.api.responses.management.ListResponseObject;
import nl.pietervanturennout.model.Account;

import java.util.List;

public class AccountListResponse extends BaseResponse {

    public static ListResponseObject generateList(List<Account> accounts, boolean wishLists) {
        if (accounts == null)
            return null;

        return ListResponseObject.listGenerator(accounts, wishLists, AccountResponse::generateMap);
    }

    public AccountListResponse(List<Account> accounts) { this(accounts, false); }

    public AccountListResponse(List<Account> accounts, boolean includeWishlists) {
        data = generateList(accounts, includeWishlists);
    }
}
