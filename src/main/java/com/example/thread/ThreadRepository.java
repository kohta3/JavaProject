package com.example.thread;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
	//カテゴリIDでスレッド検索
	public List<Threads> findByCategoryId(Long categoryId);
	//アニメタイトルIDでスレッド検索
	public List<Threads> findByAnimeTitleId(Long animeTitleId);
	//スレッドタイトル検索（部分一致）
	@Query("SELECT t FROM Threads t WHERE t.title LIKE %?1%")
	public List<Threads> findByTitle(String keyword);
}
