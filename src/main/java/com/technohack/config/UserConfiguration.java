package com.technohack.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.server.ServerFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class UserConfiguration extends Configuration {

    @JsonProperty("server")
    public ServerFactory serverFactory;
    @Valid
    @NotNull
    @JsonProperty("databaseResource")
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    @Override
    public ServerFactory getServerFactory() {
        return serverFactory;
    }

    @Override
    public void setServerFactory(ServerFactory serverFactory) {
        this.serverFactory = serverFactory;
    }

    public SwaggerBundleConfiguration getSwaggerBundleConfiguration() {
        return swaggerBundleConfiguration;
    }

    public void setSwaggerBundleConfiguration(SwaggerBundleConfiguration swaggerBundleConfiguration) {
        this.swaggerBundleConfiguration = swaggerBundleConfiguration;
    }

    public DataSourceFactory getDatabase() {
        return database;
    }

    public void setDatabase(DataSourceFactory database) {
        this.database = database;
    }
}
