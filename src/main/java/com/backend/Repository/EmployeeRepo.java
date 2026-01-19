package com.backend.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.backend.Model.Employee; 

public interface EmployeeRepo extends MongoRepository<Employee, String> {
    
}
