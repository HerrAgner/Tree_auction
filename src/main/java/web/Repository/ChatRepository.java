package web.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import web.Entity.Chat;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Long> {

}
