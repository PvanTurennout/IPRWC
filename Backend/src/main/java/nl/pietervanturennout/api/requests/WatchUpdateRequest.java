package nl.pietervanturennout.api.requests;

import com.fasterxml.jackson.annotation.JsonSetter;
import nl.pietervanturennout.model.Watch;
import nl.pietervanturennout.utils.constraints.Validator;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class WatchUpdateRequest{

    @NotNull @Min(1)
    private int watchId;

    @NotNull @Valid
    private BraceletUpdateRequest watchBracelet;

    @NotNull
    @Validator("standard")
    private String material;

    @NotNull @Min(1)
    private int size;

    @NotNull
    @Validator("standard")
    private String colorPointer;

    @NotNull
    @Validator("standard")
    private String colorDial;


    public int getWatchId() {
        return this.watchId;
    }

    public BraceletUpdateRequest getWatchBracelet() {
        return this.watchBracelet;
    }

    public String getMaterial() {
        return this.material;
    }

    public int getSize() {
        return this.size;
    }

    public String getColorPointer() {
        return this.colorPointer;
    }

    public String getColorDial() {
        return this.colorDial;
    }


    @JsonSetter
    public void setWatchId(int watchId) {
        this.watchId = watchId;
    }

    @JsonSetter
    public void setWatchBracelet(BraceletUpdateRequest watchBracelet) {
        this.watchBracelet = watchBracelet;
    }

    @JsonSetter
    public void setWatchMaterial(String watchMaterial) {
        this.material = watchMaterial;
    }

    @JsonSetter
    public void setWatchSize(int watchSize) {
        this.size = watchSize;
    }

    @JsonSetter
    public void setWatchColorPointer(String watchColorPointer) {
        this.colorPointer = watchColorPointer;
    }

    @JsonSetter
    public void setWatchColorDial(String watchColorDial) {
        this.colorDial = watchColorDial;
    }


    public Watch getWatchUpdateModel() {
        return new Watch(this.watchId, this.watchBracelet.getBraceletUpdateModel(),
                this.material, this.size, this.colorPointer, this.colorDial);
    }
}
