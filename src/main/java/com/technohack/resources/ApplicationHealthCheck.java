package com.technohack.resources;

import com.codahale.metrics.health.HealthCheck;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.management.Query;

public class ApplicationHealthCheck extends HealthCheck {
    private final SessionFactory sessionFactory;

    public ApplicationHealthCheck(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }

   // Cannot connect to database: No session currently bound to execution context , gives error for the above healthcheck

    // The error "No session currently bound to execution context" indicates that there is no
   // Hibernate session available in the current execution context
    @Override
    protected Result check() throws Exception {
        // open session using session factory to open new session
        try (Session session = sessionFactory.openSession()) {
            session.createNativeQuery("SELECT 1").uniqueResult();
            return Result.healthy();
        } catch (Exception e) {
            return Result.unhealthy("Cannot connect to database: " + e.getMessage());
        }
    }
}
