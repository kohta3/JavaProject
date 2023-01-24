package com.example.block;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Block;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long>{
	//UserIdからブロック情報のリストを取得
	public List<Block> findByUserId(Long userId);
}
