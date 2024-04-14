package ru.strebkov.t1_MyTasks.repositiy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.strebkov.t1_MyTasks.entity.MyTasksEntity;

@Repository
public interface MyTaskRepository extends JpaRepository<MyTasksEntity, Long> {

}
