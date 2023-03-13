package com.example.loanlending.Repositories;

import com.example.loanlending.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UsersRepo extends JpaRepository<Users,Integer> {
    Users findUsersByUserID(int id);
    List<Users> findAll();



}
