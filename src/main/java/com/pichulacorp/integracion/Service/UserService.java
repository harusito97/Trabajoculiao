package com.pichulacorp.integracion.Service;

import com.pichulacorp.integracion.Entity.User;
import com.pichulacorp.integracion.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User saveUser(User user){
        return repository.save(user);
    }

    public User getUserById(int id){
        return repository.findById(id).orElse(null);
    }

    public User getUserByRut(String rut){
        return repository.findByRut(rut).orElse(null);
    }

    public List<User> getUsers(){
        return repository.findAll();
    }

    public String deleteUser(int id){
        repository.deleteById(id);
        return "user removed !!"+id;
    }

    public User updateUser(User user){
        User actualUser=repository.findById(user.getId()).orElse(user);
        actualUser.setName(user.getName());
        actualUser.setLastname(user.getLastname());
        actualUser.setPwd(user.getPwd());
        actualUser.setRut(user.getRut());
        actualUser.setEmail(user.getEmail());
        actualUser.setTelefono(user.getTelefono());
        return repository.save(actualUser);
    }





}
