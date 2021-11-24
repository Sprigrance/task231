package ru.kirillov.spring.dao;

import ru.kirillov.spring.models.User;

import java.util.List;

public interface UserDao {
    public List<User> getAllUsers();
    public User getUser(int id);
    public void saveUser(User user);
    public void updateUser(int id, User user);
    public void deleteUser(int id);

    User loadUserByUsername(String name);
}
