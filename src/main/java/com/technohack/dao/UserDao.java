package com.technohack.dao;

import com.technohack.db.entities.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserDao extends AbstractDAO<User> {

    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    public Optional<User> findById(Long id) {
        System.out.println("user Id ---->"+id);
        return Optional.ofNullable(get(id));
    }

    public  User create(User user) {
        try{
            return persist(user);
        }catch(Exception error){
            System.out.println("Insert Error :"+error);
        }
        return user;
    }

    public List<User> findAll() {
        Query<User> query = currentSession().createQuery("from User", User.class);
        return list(query);
    }

    public void update(User user) {
        currentSession().merge(user);
    }

    public void delete(User user) {
        // current session is defined in the parent class ,
        // and child class can access protected methods of the parent class
        currentSession().delete(user);
    }

    //TODO: we can use the below method as well
//    public void delete(User user) {
//        currentSession().createQuery("DELETE FROM User WHERE id = :id")
//                .setParameter("id", user.getId())
//                .executeUpdate();
//    }
}