package com.example.thread;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Threads;

@Repository
public interface ThreadRepository extends JpaRepository<Threads, Long>{
//	@Query(value =
//			"SELECT "
//			+ "ThreadsCommentCount(th.id, th.title, th.comment, th.dateTime, th.user.name, th.category.name, th.animeTitle.name, COUNT(th.id))"
//			+ "FROM Threads th "
//			+ "GROUP BY th.id", nativeQuery = true)
//	List<ThreadsCommentCount> listThreads();
}
