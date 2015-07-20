package here.cityexplorer.Controllers;

import here.cityexplorer.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Repo extends MongoRepository<User, String> {

}
