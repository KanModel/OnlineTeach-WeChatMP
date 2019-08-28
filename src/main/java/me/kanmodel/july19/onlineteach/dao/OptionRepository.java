package me.kanmodel.july19.onlineteach.dao;

import me.kanmodel.july19.onlineteach.entity.Option;
import me.kanmodel.july19.onlineteach.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: KanModel
 * Date: 2019-07-09-9:10
 */
public interface OptionRepository extends JpaRepository<Option, Long> {
    Optional<Option> findByKey(String key);
}
