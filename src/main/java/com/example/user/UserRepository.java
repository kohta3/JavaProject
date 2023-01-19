package com.example.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 管理者情報の件数取得
     *
     * @param id ブランドID
     * @return 取得件数
     */
    public Long countById(Long id);

    /**
     * メールアドレスに紐づく管理者情報取得クエリ
     *
     * @param email メールアドレス
     * @return 管理者情報
     */
    public User findByEmail(String email);

    /**
     * 管理者情報検索クエリ
     *
     * @param keyword 検索キーワード
     * @return 管理者情報のリスト
     */
    @Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.name, ' ') LIKE %?1%")
    public List<User> search(String keyword);

}
