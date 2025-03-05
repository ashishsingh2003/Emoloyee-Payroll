package com.example.EmployeePayroll.controller;

import com.example.EmployeePayroll.model.EmployeeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.EmployeePayroll.repository.EmployeeRepo;

import com.example.EmployeePayroll.repository.EmployeeRepo;

import java.util.List;

import java.util.Optional;


public class EmployeeController {
    @Autowired
    EmployeeRepo employeeRepository;




    public List<EmployeeModel> getAllUsers() {
        return employeeRepository.findAll();
    }


    @GetMapping("/get/{id}")

    public ResponseEntity<EmployeeModel> getUserById(@PathVariable Long id) {
        return employeeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    public EmployeeModel createUser(@RequestBody EmployeeModel employee) {
        return employeeRepository.save(employee);
    }


    @PutMapping("update/{id}")
    public ResponseEntity<EmployeeModel> updateUser(@PathVariable Long id, @RequestBody EmployeeModel userDetails) {
        Optional<EmployeeModel> optionalUser = employeeRepository.findById(id);

        if (optionalUser.isPresent()) {
            EmployeeModel user = optionalUser.get();
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            return ResponseEntity.ok(employeeRepository.save(user));
        }
        return ResponseEntity.notFound().build();
    }

    //http:localhost:8080/employee/delete/1
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}