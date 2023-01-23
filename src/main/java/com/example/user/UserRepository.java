package com.example.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * ユーザー情報の件数取得
     *
     * @param id ユーザーID
     * @return 取得件数
     */
    public Long countById(Long id);

    /**
     * メールアドレスに紐づくユーザー情報取得クエリ
     *
     * @param email メールアドレス
     * @return ユーザー情報
     */
    public User findByEmail(String email);

    /**
     * ユーザー名に紐づくユーザー情報取得クエリ
     *
     * @param name ユーザー名
     * @return ユーザー情報
     */
    public User findByName(String name);

    /**
     * ユーザー情報検索クエリ
     *
     * @param keyword 検索キーワード
     * @return ユーザー情報のリスト
     */
    @Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.name, ' ') LIKE %?1%")
    public List<User> search(String keyword);

}
