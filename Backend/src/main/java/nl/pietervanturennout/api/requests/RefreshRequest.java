package nl.pietervanturennout.api.requests;

import com.fasterxml.jackson.annotation.JsonSetter;

import javax.validation.constraints.NotNull;

public class RefreshRequest {

    @NotNull
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    @JsonSetter
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
