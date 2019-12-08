package com.iqlearning.database.repository;

import com.iqlearning.database.entities.Chat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends CrudRepository<Chat,Long> {

    List<Chat> getAllByUser1AndUser2OrderBySentOnDesc(Long user1, Long user2);

    int countAllByUser1AndUser2(Long id1, Long id2);
}
