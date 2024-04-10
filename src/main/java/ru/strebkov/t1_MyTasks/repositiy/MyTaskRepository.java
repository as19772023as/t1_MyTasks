package ru.strebkov.t1_MyTasks.repositiy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.strebkov.t1_MyTasks.entity.MyTasksEntity;

import java.util.List;

@Repository
public interface MyTaskRepository extends JpaRepository<MyTasksEntity, Long> {
//    List<MyTasksEntity> findByPublished(boolean published);
//    List<MyTasksEntity> findByTitleContaining(String title);
}
