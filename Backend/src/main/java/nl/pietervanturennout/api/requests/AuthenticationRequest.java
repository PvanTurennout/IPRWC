package nl.pietervanturennout.api.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.pietervanturennout.utils.constraints.Validator;

import javax.validation.constraints.NotNull;

public class AuthenticationRequest {
    @JsonProperty
    @NotNull
    @Validator("mailAddress")
    private String mailAddress;

    @JsonProperty
    @NotNull
    @Validator("password")
    private String password;

    public String getMailAddress() {return mailAddress;}

    public String getPassword() {return password;}
}
