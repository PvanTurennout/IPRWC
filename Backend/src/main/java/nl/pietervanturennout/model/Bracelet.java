package nl.pietervanturennout.model;

public class Bracelet {
    private int braceletId;
    private int length;
    private String material;
    private String style;
    private String color;

    public Bracelet() {}

    public Bracelet(int id, int length, String material, String style, String color){
        this.braceletId = id;
        this.length = length;
        this.material = material;
        this.style = style;
        this.color = color;
    }

    public Bracelet(int length, String material, String style, String color){
        this.length = length;
        this.material = material;
        this.style = style;
        this.color = color;
    }

    public int getBraceletId() {
        return braceletId;
    }

    public void setBraceletId(int braceletId) {
        this.braceletId = braceletId;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isIdNull() {
        return this.braceletId == 0;
    }
}