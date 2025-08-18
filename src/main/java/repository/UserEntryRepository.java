package repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import entity.UserEntry;

@Repository
public interface UserEntryRepository extends MongoRepository<UserEntry , ObjectId>{
	UserEntry findByUserName(String userName);
	
	void deleteByUserName(String userName);
}
