package nl.pietervanturennout;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import org.hibernate.validator.constraints.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class IprwcBackendConfiguration extends Configuration {
    @NotNull
    private String filePath;

    @NotNull
    private boolean development;

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @NotNull
    private String secret;

    @NotNull
    private String refreshSecret;

    @NotNull
    private int tokenValidityTime;


    @JsonProperty("filePath")
    public String getFilePath() { return this.filePath; }

    @JsonProperty("database")
    public DataSourceFactory getDatabase() {
        return database;
    }

    @JsonProperty("secret")
    public String getSecret(){ return this.secret;}

    @JsonProperty("development")
    public boolean isDevelopment() {return this.development;}

    @JsonProperty("refreshSecret")
    public String getRefreshSecret() {return this.refreshSecret;}

    @JsonProperty("tokenValidityTime")
    public int getTokenValidityTime(){
        return this.tokenValidityTime;
    }
}
