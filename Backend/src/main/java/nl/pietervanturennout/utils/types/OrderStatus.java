package nl.pietervanturennout.utils.types;

public enum OrderStatus {
    RECEIVED(1),
    PROCESSED(2),
    SEND(3),
    DELIVERED(4),
    ARCHIVED(5),
    EMPTY(6);

    private final int statusId;

    OrderStatus(int id){
        this.statusId = id;
    }

    public int getStatusId() { return statusId; }

    public static OrderStatus formStatusId(int id){
        switch (id) {
            case 1:
                return RECEIVED;
            case 2:
                return PROCESSED;
            case 3:
                return SEND;
            case 4:
                return DELIVERED;
            case 5:
                return ARCHIVED;
            default:
                return EMPTY;
        }
    }
}
