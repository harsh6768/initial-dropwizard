package com.technohack.managed;

import io.dropwizard.lifecycle.Managed;

import javax.sql.DataSource;

/*
* -------------------------- managed object ( implement Managed interface )
* is used to manage resources that need to be started and stopped when the application starts and stops.
* */
public class MySqlConnectionManager implements Managed {
    private final DataSource dataSource;

    public MySqlConnectionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void start() throws Exception {
        System.out.println("<------Inside of Start Method---->");
    }

    @Override
    public void stop() throws Exception {
        if (dataSource != null) {
            dataSource.getConnection().close();
            System.out.println("MySQL connection closed.");
        }
    }
}
