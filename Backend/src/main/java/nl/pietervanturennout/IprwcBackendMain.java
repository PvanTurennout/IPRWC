package nl.pietervanturennout;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import nl.pietervanturennout.api.responses.management.BaseResponseFilter;
import nl.pietervanturennout.api.authentication.AuthenticateObject;
import nl.pietervanturennout.api.authorization.MyOnlyAuthorizer;
import nl.pietervanturennout.api.authentication.MyOnlyAuthenticator;
import nl.pietervanturennout.exceptions.mappers.ConstraintExceptionMapper;
import nl.pietervanturennout.exceptions.mappers.GlobalExceptionMapper;
import nl.pietervanturennout.resource.*;
import nl.pietervanturennout.services.DatabaseService;
import nl.pietervanturennout.sql.DatabaseHealthCheck;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class IprwcBackendMain extends Application<IprwcBackendConfiguration> {

    private static IprwcBackendConfiguration serverConfiguration;

    public static void main(final String[] args) throws Exception {
        new IprwcBackendMain().run(args);
    }

    public static IprwcBackendConfiguration getServerConfiguration() {
        return serverConfiguration;
    }

    @Override
    public String getName() {
        return "IPRWC-Backend";
    }

    @Override
    public void initialize(Bootstrap<IprwcBackendConfiguration> bootstrap) {
    }

    @Override
    public void run(final IprwcBackendConfiguration configuration, final Environment environment) {
        serverConfiguration = configuration;
        register(environment);
        configure(environment);
    }


    private void register(Environment environment){
        registerResources(environment);
        registerMappers(environment);
    }

    private void registerResources(Environment e){
        e.jersey().register(new AccountResource());
        e.jersey().register(new AuthenticationResource());
        e.jersey().register(new ProductResource());
        e.jersey().register(new WishListResource());
        e.jersey().register(new OrderResource());
        e.jersey().register(new CoffeeResource());
        e.jersey().register(new ImageResource());
    }

    private void registerMappers(Environment e) {
        e.jersey().register(GlobalExceptionMapper.class);
        GlobalExceptionMapper.setDetailedMessage(serverConfiguration.isDevelopment());
        e.jersey().register(ConstraintExceptionMapper.class);
    }


    private void configure(Environment e) {
        DatabaseService.getInstance().setConfiguration(serverConfiguration.getDatabase());
        authenticationSetup(e);
        healthCheckSetup(e);
        e.jersey().register(BaseResponseFilter.class);
        e.jersey().register(MultiPartFeature.class);

        corsSettings(e);
    }

    private void authenticationSetup(Environment e) {
        e.jersey().register(new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<AuthenticateObject>()
                        .setAuthenticator(new MyOnlyAuthenticator())
                        .setAuthorizer(new MyOnlyAuthorizer())
                        .setPrefix("Bearer")
                        .buildAuthFilter()
        ));
        e.jersey().register(RolesAllowedDynamicFeature.class);
        e.jersey().register(new AuthValueFactoryProvider.Binder<>(AuthenticateObject.class));
    }

    private void healthCheckSetup(Environment e){
        e.healthChecks().register("Postgresql-database-Webshop", new DatabaseHealthCheck());
    }

    private void corsSettings(Environment e){
        // source: https://stackoverflow.com/questions/25775364/enabling-cors-in-dropwizard-not-working
        // Enable CORS headers
        final FilterRegistration.Dynamic cors =
                e.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "*");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        // DO NOT pass a preflight request to down-stream auth filters
        // unauthenticated preflight requests should be permitted by spec
        cors.setInitParameter(CrossOriginFilter.CHAIN_PREFLIGHT_PARAM, Boolean.FALSE.toString());
    }

}
