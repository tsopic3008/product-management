package com.tscore.dao;

import com.tscore.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserDAO implements PanacheRepository<User> {

}
