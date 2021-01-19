package nl.pietervanturennout.utils.types;

public enum Group {
    ADMIN(1),
    SELLER(2),
    CUSTOMER(3);

    private final int groupId;

    Group(int id){
        this.groupId = id;
    }

    public int getGroupId() { return groupId; }
}
