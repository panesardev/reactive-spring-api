package code.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import code.documents.User;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
	
}
