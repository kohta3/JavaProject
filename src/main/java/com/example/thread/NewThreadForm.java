package com.example.thread;

import com.example.entity.Threads;

public class NewThreadForm {

	private Threads threads = new Threads();

	private String animeTitle;

	public Threads getThreads() {
		return threads;
	}

	public void setThreads(Threads threads) {
		this.threads = threads;
	}

	public String getAnimeTitle() {
		return animeTitle;
	}

	public void setAnimeTitle(String animeTitle) {
		this.animeTitle = animeTitle;
	}



}
