package com.example.thread;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Threads;

@Service
public class ThreadService {

	private final ThreadRepository threadRepository;

	@Autowired
	public ThreadService(ThreadRepository threadRepository) {
		this.threadRepository = threadRepository;
	}

	//スレッド情報全件取得
	public List<Threads> listAll(String order) {

		List<Threads> allThreads = this.threadRepository.findAll();
		//日時の降順で並べ替え
		allThreads = orderByDateTime(allThreads);
		//orderがNullじゃない時
		if(order != null) {
			// コメント数で並べ替える場合
			if(order.equals("コメント数")) {
				return orderByComment(allThreads);
			}
		}
		 //そのほかの場合
		return allThreads;
	}

	/**
	 * カテゴリIdでスレッド検索
	 */
	public List<Threads> findByCategory(Long categoryId, String order) {
		//スレッド情報取得
		List<Threads> categoryThreads = this.threadRepository.findByCategoryId(categoryId);
		//日時の降順で並べ替え
		categoryThreads = orderByDateTime(categoryThreads);
		//orderがNullじゃない時
		if(order != null) {
			// コメント数で並べ替える場合
			if(order.equals("コメント数")) {
				return orderByComment(categoryThreads);
			}
		}
		//そのほかの場合
		return categoryThreads;
	}

	/**
	 * アニメタイトルIdでスレッド検索
	 */
	public List<Threads> findByAnimeTitle(Long animeTitleId, String order) {
		List<Threads> animeTitleThreads = this.threadRepository.findByAnimeTitleId(animeTitleId);
		//日時の降順で並べ替え
		animeTitleThreads = orderByDateTime(animeTitleThreads);
		//orderがNullじゃない時
		if(order != null) {
			// コメント数で並べ替える場合
			if(order.equals("コメント数")) {
				return orderByComment(animeTitleThreads);
			}
		}
		//そのほかの場合
		return animeTitleThreads;
	}

	/**
	 * スレッド削除
	 */
	public void deleteThread(Threads thread) {
		this.threadRepository.delete(thread);
	}

	/**
	 * スレッドタイトル部分一致検索
	 *
	 */
	public List<Threads> findByTitle(String keyword, String order) {
		List<Threads> searchThreads = this.threadRepository.findByTitle(keyword);
		//日時の降順で並べ替え
		searchThreads = orderByDateTime(searchThreads);
		//orderがNullじゃない時
		if(order != null) {
			// コメント数で並べ替える場合
			if(order.equals("コメント数")) {
				return orderByComment(searchThreads);
			}
		}
		//そのほかの場合
		return searchThreads;
	}

	/**
	 * スレッド登録処理
	 */
	public Threads save(Threads threads) {
		return this.threadRepository.save(threads);
	}


	/**
	 * コメント数で並べ替え降順機能
	 * @param List<Threads>
	 * @return 並べ替えたList<Threads>
	 */
	private List<Threads> orderByComment(List<Threads> threadList) {
		threadList.sort(Comparator.comparing(Threads::getCommentSum).reversed());
		return threadList;
	}

	/**
	 * 日時で並べ替え降順
	 * @param id
	 * @return
	 */
	private List<Threads> orderByDateTime(List<Threads> threadList) {
		threadList.sort(Comparator.comparing(Threads::getDateTime).reversed());
		return threadList;
	}

	/**
	 * IDからスレッド情報を取得
	 * @param id
	 * @return
	 */
	public Threads get(Long id) {
		return threadRepository.findById(id).get();
	}






}
