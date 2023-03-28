package com.technohack;
import com.technohack.config.UserConfiguration;
import com.technohack.dao.UserDao;
import com.technohack.db.entities.User;
import com.technohack.resources.ApplicationHealthCheck;
import com.technohack.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class MyApplication extends Application<UserConfiguration> {

    // HibernateBundle is a Dropwizard bundle that makes it easy to integrate Hibernate ORM into your Dropwizard application.
    // It provides a preconfigured Hibernate SessionFactory, as well as various configuration options and
    // lifecycle management for the SessionFactory

    private HibernateBundle<UserConfiguration> hibernateBundle;
    private  SwaggerBundle<UserConfiguration> swaggerBundle;


    private void loadHibernateBundle(Bootstrap<UserConfiguration> bootstrap){
        hibernateBundle = new HibernateBundle<UserConfiguration>(User.class) {
                    @Override
                    public DataSourceFactory getDataSourceFactory(UserConfiguration configuration) {
                        return configuration.getDatabase();
                    }
                };
        bootstrap.addBundle(hibernateBundle);
    }

    private  void loadSwaggerBundle(Bootstrap<UserConfiguration> bootstrap){
        swaggerBundle=new SwaggerBundle<UserConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(UserConfiguration userConfiguration) {
                return userConfiguration.getSwaggerBundleConfiguration();
            }
        };

        bootstrap.addBundle(swaggerBundle);
    }

    public static void main(String []args) throws Exception {
        try{
            new MyApplication().run(args);
        } catch(Exception error){
          System.out.println("Error---->"+error);
        }
    }

    @Override
    public void run(UserConfiguration userConfiguration, Environment environment) throws Exception {
        // register resources
        //  int PORT=userConfiguration.getServerFactory()
        System.out.println("Server is running!");

        // Health Check Api , it will use admin port
        environment.healthChecks().register(
                "mysql-database",
                new ApplicationHealthCheck(hibernateBundle.getSessionFactory())
        );
        // Register User Resource
        final UserDao userDao = new UserDao(hibernateBundle.getSessionFactory());
        final UserResource userResource = new UserResource(userDao);
        environment.jersey().register(userResource);

    }

    @Override
    public void initialize(Bootstrap<UserConfiguration> bootstrap) {
        //initlization
        loadHibernateBundle(bootstrap);
        loadSwaggerBundle(bootstrap);
    }
}
