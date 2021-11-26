package ru.kirillov.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kirillov.spring.dao.RoleDao;
import ru.kirillov.spring.models.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleByName(String role){
        return roleDao.getRole(role);
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        roleDao.saveRole(role);
    }
}
