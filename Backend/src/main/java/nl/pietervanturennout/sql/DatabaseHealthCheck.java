package nl.pietervanturennout.sql;

import com.codahale.metrics.health.HealthCheck;
import nl.pietervanturennout.services.DatabaseService;

public class DatabaseHealthCheck extends HealthCheck {
    @Override
    protected Result check() throws Exception{
        if (DatabaseService.getInstance().ping()){
            return Result.healthy();
        }

        return Result.unhealthy("Unable To Establish Database Connection!");
    }
}
