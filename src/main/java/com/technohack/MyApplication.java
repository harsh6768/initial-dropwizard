package com.technohack;
import com.technohack.config.UserConfiguration;
import com.technohack.dao.UserDao;
import com.technohack.entity.User;
import com.technohack.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MyApplication extends Application<UserConfiguration> {
    private final HibernateBundle<UserConfiguration> hibernateBundle =
            new HibernateBundle<UserConfiguration>(User.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(UserConfiguration configuration) {
                    return configuration.getDatabase();
                }
            };


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
        System.out.println("Server is running!");
        final UserDao userDao = new UserDao(hibernateBundle.getSessionFactory());
        final UserResource userResource = new UserResource(userDao);
        environment.jersey().register(userResource);
    }

    @Override
    public void initialize(Bootstrap<UserConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(hibernateBundle);
    }
}
