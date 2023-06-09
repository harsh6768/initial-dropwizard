package com.technohack;

import com.technohack.config.UserConfiguration;
import com.technohack.dao.UserDao;
import com.technohack.db.entities.User;
import com.technohack.managed.MySqlConnectionManager;
import com.technohack.middlewares.ResponseFilter;
import com.technohack.resources.ApplicationHealthCheck;
import com.technohack.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import javax.sql.DataSource;

public class MyApplication extends Application<UserConfiguration> {

    // HibernateBundle is a Dropwizard bundle that makes it easy to integrate Hibernate ORM into your Dropwizard application.
    // It provides a preconfigured Hibernate SessionFactory, as well as various configuration options and
    // lifecycle management for the SessionFactory

    private HibernateBundle<UserConfiguration> hibernateBundle;
    private SwaggerBundle<UserConfiguration> swaggerBundle;

    public MyApplication() {
    }


    private void loadHibernateBundle(Bootstrap<UserConfiguration> bootstrap) {
        hibernateBundle = new HibernateBundle<UserConfiguration>(User.class) {
            @Override
            public DataSourceFactory getDataSourceFactory(UserConfiguration configuration) {
                return configuration.getDatabase();
            }
        };
        bootstrap.addBundle(hibernateBundle);
    }

    private void loadSwaggerBundle(Bootstrap<UserConfiguration> bootstrap) {
        swaggerBundle = new SwaggerBundle<UserConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(UserConfiguration userConfiguration) {
                return userConfiguration.getSwaggerBundleConfiguration();
            }
        };

        bootstrap.addBundle(swaggerBundle);
    }

    public static void main(String[] args) throws Exception {
        try {
            new MyApplication().run(args);
        } catch (Exception error) {
            System.out.println("Error---->" + error);
        }
    }

    @Override
    public void run(UserConfiguration userConfiguration, Environment environment) throws Exception {

        // managed objects to manage lifecycle
        final DataSourceFactory dataSourceFactory = userConfiguration.getDatabase();
        final DataSource dataSource = dataSourceFactory.build(environment.metrics(), "mysql");
        MySqlConnectionManager connectionManager = new MySqlConnectionManager(dataSource);
        environment.lifecycle().manage(connectionManager);

        // Health Check Api , it will use admin port
        environment.healthChecks().register(
                "mysql-database",
                new ApplicationHealthCheck(hibernateBundle.getSessionFactory())
        );
        // Register User Resource
        final UserDao userDao = new UserDao(hibernateBundle.getSessionFactory());
        final UserResource userResource = new UserResource(userDao);
        environment.jersey().register(userResource);

        // Response Middleware
        environment.jersey().register(new ResponseFilter("Hello Harsh"));

    }

    // The bootstrap.addBundle() method is used to add a Bundle to a Dropwizard application's bootstrap process.
    // A bundle is a reusable component that encapsulates various types of functionality that can be added to a Dropwizard application.
    // When the run() method of the Application class is called, Dropwizard will call the initialize() method of each bundle that has been added to the bootstrap.
    // This provides an opportunity for the bundle to set up any required configuration, register resources, and perform any other necessary initialization.
    @Override
    public void initialize(Bootstrap<UserConfiguration> bootstrap) {
        //initlization
        loadHibernateBundle(bootstrap);
        loadSwaggerBundle(bootstrap);
    }
}
