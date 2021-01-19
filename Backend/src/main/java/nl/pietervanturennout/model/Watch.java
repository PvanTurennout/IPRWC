package nl.pietervanturennout.model;

public class Watch {
    private int watchId;
    private Bracelet watchBracelet;

    private String material;
    private int size;
    private String colorPointer;
    private String colorDial;

    public Watch() {
    }

    public Watch(int watchId, Bracelet watchBracelet, String material, int size, String colorPointer, String colorDial) {
        this.watchId = watchId;
        this.watchBracelet = watchBracelet;
        this.material = material;
        this.size = size;
        this.colorPointer = colorPointer;
        this.colorDial = colorDial;
    }

    public Watch(Bracelet watchBracelet, String material, int size, String colorPointer, String colorDial) {
        this.watchBracelet = watchBracelet;
        this.material = material;
        this.size = size;
        this.colorPointer = colorPointer;
        this.colorDial = colorDial;
    }

    public Watch(int watchId){
        this.watchId = watchId;
    }

    public int getWatchId() {
        return watchId;
    }

    public void setWatchId(int watchId) {
        this.watchId = watchId;
    }

    public Bracelet getWatchBracelet() {
        return watchBracelet;
    }

    public void setWatchBracelet(Bracelet watchBracelet) {
        this.watchBracelet = watchBracelet;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColorPointer() {
        return colorPointer;
    }

    public void setColorPointer(String colorPointer) {
        this.colorPointer = colorPointer;
    }

    public String getColorDial() {
        return colorDial;
    }

    public void setColorDial(String colorDial) {
        this.colorDial = colorDial;
    }

    public boolean isIdNull() {
        return this.watchId == 0;
    }
}