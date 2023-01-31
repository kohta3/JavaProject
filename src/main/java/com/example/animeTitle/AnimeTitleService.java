package com.example.animeTitle;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.AnimeTitle;

@Service
public class AnimeTitleService {

	private final AnimeTitleRepository animeTitleRepository;

	@Autowired
	public AnimeTitleService(AnimeTitleRepository animeTitleRepository) {
		super();
		this.animeTitleRepository = animeTitleRepository;
	}

	/**
	 * アニメタイトル全件リスト
	 * @return List<AnimeTitle>
	 */
	public List<AnimeTitle> listAll() {
		return this.animeTitleRepository.findAll();
	}

	/**
	 * アニメタイトルIDからアニメタイトル情報取得
	 */
	public AnimeTitle findById(Long id) {
		return this.animeTitleRepository.findById(id).get();
	}

	/**
	 * アニメタイトルからアニメタイトルIDを取得
	 * @param keyword
	 * @return Long id
	 */
	public Long searchId(String word, Long categoryId) {
		//アニメタイトル検索
		AnimeTitle animeTitle = this.animeTitleRepository.findByName(word);

		//アニメタイトルがない場合
		if(Objects.isNull(animeTitle)) {
			AnimeTitle newAnime = createAnime(word, categoryId);
			return newAnime.getId();
		}
		//アニメタイトルが既存の場合
		return animeTitle.getId();
	}

	/**
	 * アニメタイトル新規作成
	 */
	public AnimeTitle createAnime(String name, Long categoryId) {
		AnimeTitle animeTitle = new AnimeTitle();
		animeTitle.setName(name);
		animeTitle.setCategoryId(categoryId);
		return this.animeTitleRepository.save(animeTitle);
	}

}
