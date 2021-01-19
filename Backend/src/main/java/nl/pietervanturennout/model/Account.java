package nl.pietervanturennout.model;

import nl.pietervanturennout.utils.types.Group;

import java.util.List;

public class Account {
    private int accountId;
    private String mailAddress;
    private String password;
    private Group group;
    private List<Integer> wishList;
    private List<Order> orders;

    public Account() {}

    public Account(int accountId, String password, String mailAddress) {
        this.accountId = accountId;
        this.password = password;
        this.mailAddress = mailAddress;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public List<Integer> getWishList() {
        return wishList;
    }

    public void setWishList(List<Integer> wishList) {
        this.wishList = wishList;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public Group getGroup() { return group; }

    public void setGroup(Group group) { this.group = group; }
}
