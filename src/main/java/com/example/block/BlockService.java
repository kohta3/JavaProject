package com.example.block;

import java.util.ArrayList;
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

	/**
	 * ブロックしているユーザーのIDのリストを取得
	 * @param block
	 */
	public List<Long> listUserId(Long userId) {
		List<Block> blockList = listAll(userId);
		List<Long> listNum = new ArrayList<Long>();
		for(Block block : blockList) {
			listNum.add(block.getBlockId());
		}
		return listNum;
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

	/**
	 * ブロックユーザーIDとユーザーIDからブロック情報取得
	 * @param block
	 * @return
	 */
	public Block getByUserIdAndBlockId(Long userId, Long blockId) {
		return this.blockRepository.findByUserIdAndBlockId(userId, blockId);
	}

	/**
	 * ブロック情報削除
	 * @param block
	 * @return
	 */
	public void deleteBlock(Block block) {
		this.blockRepository.delete(block);
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
