package com.kueshi.api_my_finances.user.repositories;

import com.kueshi.api_my_finances.user.documents.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    UserDetails findByUsername(String username);
    User findByCpf(String cpf);
}
