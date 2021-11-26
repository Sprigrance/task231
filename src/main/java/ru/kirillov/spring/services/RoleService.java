package ru.kirillov.spring.services;

import ru.kirillov.spring.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    void saveRole(Role role);
    Role getRoleByName(String roleName);
}
