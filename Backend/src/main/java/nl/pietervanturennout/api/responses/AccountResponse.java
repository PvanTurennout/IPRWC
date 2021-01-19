package nl.pietervanturennout.api.responses;

import nl.pietervanturennout.api.responses.management.ResponseObject;
import nl.pietervanturennout.model.Account;

public class AccountResponse extends BaseResponse{

    public static ResponseObject generateMap(Account account, boolean wishList) {
        if (account == null || account.getAccountId() == 0)
            return null;

        ResponseObject response = new ResponseObject();

        response.put("accountId", account.getAccountId());
        response.put("mailadress", account.getMailAddress());

//        if (wishList)
//            response.put("wishlist", );

        return response;
    }

    public AccountResponse(Account account, boolean includeWishList) { data = generateMap(account, includeWishList); }

    public AccountResponse(Account account) { this(account, false); }
}
