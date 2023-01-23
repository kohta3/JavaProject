package com.example.thread;

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
		//コメント数取得
		List<Threads> allThreads = this.threadRepository.findAll();

		// コメント数で並べ替える場合
//		if(order.equals("コメント数")) {
//			//return orderByComment(allThreads);
//			return allThreads;
//		}
//		 //そのほかの場合
//		else {
//			return allThreads;
//		}
		return allThreads;

	}

	/**
	 * カテゴリIdでスレッド検索
	 */
	public List<Threads> findByCategory(Long categoryId) {
		return this.threadRepository.findByCategoryId(categoryId);
	}

	/**
	 * アニメタイトルIdでスレッド検索
	 */
	public List<Threads> findByAnimeTitle(Long animeTitleId) {
		return this.threadRepository.findByAnimeTitleId(animeTitleId);
	}

	/**
	 * スレッドタイトル部分一致検索
	 *
	 */
	public List<Threads> findByTitle(String keyword) {
		return this.threadRepository.findByTitle(keyword);
	}

	/**
	 * スレッド登録処理
	 */
	public Threads save(Threads threads) {
		return this.threadRepository.save(threads);
	}


	/**
	 * コメント数で並べ替え機能
	 * @param List<Threads>
	 * @return 並べ替えたList<Threads>
	 */
	private List<Threads> orderByComment(List<Threads> threadList) {
		return threadList;
	}







}
