package com.example.thread;

import java.util.Collections;
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
			//ランダムに並べ替える場合
			if(order.equals("ランダム")) {
				return orderByRandom(allThreads);
			}
		}
		 //そのほかの場合
		return allThreads;
	}

	/**
	 * 並べ替え機能
	 */
	public List<Threads> order(String order, List<Threads> threads) {

		if(order != null) {
			if(order.equals("コメント数")) {
				return this.orderByComment(threads);
			}
			if(order.equals("ランダム")) {
				return this.orderByRandom(threads);
			}
		}

		return this.orderByDateTime(threads);
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
			//ランダムに並べ替える場合
			if(order.equals("ランダム")) {
				return orderByRandom(categoryThreads);
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
			//ランダムに並べ替える場合
			if(order.equals("ランダム")) {
				return orderByRandom(animeTitleThreads);
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
			//ランダムに並べ替える場合
			if(order.equals("ランダム")) {
				return orderByRandom(searchThreads);
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
	 * @param List<threads>
	 * @return
	 */
	private List<Threads> orderByDateTime(List<Threads> threadList) {
		threadList.sort(Comparator.comparing(Threads::getDateTime).reversed());
		return threadList;
	}

	/**
	 * ランダムに並べ替え
	 * @param List<threads>
	 * @return ランダムにしたスレッド情報のリスト
	 */
	private List<Threads> orderByRandom(List<Threads> threadList) {
		Collections.shuffle(threadList);
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

    /**
     * スレッドタイトルの入力値チェック
     * @param title スレッドタイトル
     * @return true:正常の入力値 false:異常な入力値
     */
    public boolean isValidTitle(String title) {
    	System.out.println(title);
        //スレッドタイトルの判定（1文字から50文字まで）
        if (title.length() < 1 || title.length() > 50) {
            return false;
        }
        return true;
    }

    /**
     * １コメの入力値チェック
     * @param comments １コメ内容
     * @return true:正常の入力値 false:異常な入力値
     */
    public boolean isValidComments(String comments) {
    	System.out.println(comments);
        //１コメの文字数の判定（1文字から600文字まで）
        if (comments.length() < 1 || comments.length() > 600) {
            return false;
        }
        return true;
    }


}
