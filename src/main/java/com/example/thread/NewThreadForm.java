package com.example.thread;

import com.example.entity.AnimeTitle;
import com.example.entity.Threads;

public class NewThreadForm {

	private Threads threads = new Threads();

	private AnimeTitle animeTitle = new AnimeTitle();

	public Threads getThreads() {
		return threads;
	}

	public void setThreads(Threads threads) {
		this.threads = threads;
	}

	public AnimeTitle getAnimeTitle() {
		return animeTitle;
	}

	public void setAnimeTitle(AnimeTitle animeTitle) {
		this.animeTitle = animeTitle;
	}

}
