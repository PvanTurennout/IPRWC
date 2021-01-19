package nl.pietervanturennout.api.requests;

import com.fasterxml.jackson.annotation.JsonSetter;
import nl.pietervanturennout.model.Bracelet;
import nl.pietervanturennout.utils.constraints.Validator;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class BraceletUpdateRequest{

    @NotNull @Min(1)
    private int braceletId;

    @NotNull @Min(1)
    private int length;

    @NotNull
    @Validator("standard")
    private String material;

    @NotNull
    @Validator("standard")
    private String style;

    @NotNull
    @Validator("standard")
    private String color;


    public int getBraceletId() {
        return this.braceletId;
    }

    public int getLength() {
        return this.length;
    }

    public String getMaterial() {
        return this.material;
    }

    public String getStyle() {
        return this.style;
    }

    public String getColor() {
        return this.color;
    }


    @JsonSetter
    public void setBraceletId(int braceletId) {
        this.braceletId = braceletId;
    }

    @JsonSetter
    public void setBraceletLength(int braceletLength) {
        this.length = braceletLength;
    }

    @JsonSetter
    public void setBraceletMaterial(String braceletMaterial) {
        this.material = braceletMaterial;
    }

    @JsonSetter
    public void setBraceletStyle(String braceletStyle) {
        this.style = braceletStyle;
    }

    @JsonSetter
    public void setBraceletColor(String braceletColor) {
        this.color = braceletColor;
    }


    public Bracelet getBraceletUpdateModel() {
        return new Bracelet(this.braceletId, this.length, this.material, this.style, this.color);
    }
}
