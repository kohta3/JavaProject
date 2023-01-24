package com.example.block;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Block;

@Service
public class BlockService {

	private final BlockRepository blockRepository;

	public BlockService(BlockRepository blockRepository) {
		this.blockRepository = blockRepository;
	}

	/*
	 * ブロックしているユーザー情報取得
	 */
	public List<Block> listAll(Long userId) {
		List<Block> blockList = this.blockRepository.findByUserId(userId);
		return blockList;
	}

	/*
	 * ブロックユーザー登録
	 */
	public void save(Block block) {
		//ユーザーとブロックしたいユーザーが同じ場合登録しない
		if(isBlockUserSame(block)) {
			return;
		}
		//ブロック情報がある場合登録しない
		if(isExist(block)) {
			return;
		}
		//上記以外の場合登録する
		this.blockRepository.save(block);
	}


	/*
	 * ブロック情報があるかチェック
	 */
	private boolean isExist(Block block) {
		//1.ブロックしたいユーザーのID
		Long wantBlockId = block.getBlockId();
		//2.ユーザーのID（主語）
		Long userId = block.getUserId();
		//2からユーザーIDのブロック情報のリスト取得
		List<Block> listBlock = this.blockRepository.findByUserId(userId);
		//ブロック情報があるかの判定
		for(Block element : listBlock) {
			if(element.getBlockId() == wantBlockId) {
				return true;
			}
		}
		return false;
	}

	/*
	 * ブロックユーザー（目的語）とユーザー（主語）が同じかチェック
	 */
	private boolean isBlockUserSame(Block block) {
		if(block.getBlockId() == block.getUserId()) {
			return true;
		}
		return false;
	}
}
