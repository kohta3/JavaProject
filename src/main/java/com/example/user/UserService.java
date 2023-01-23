package com.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.entity.User;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * IDに紐づくユーザー情報取得処理
     *
     * @param id ユーザーID
     * @return ユーザー情報
     * @throws NotFoundException
     */
    public User get(Long id) throws NotFoundException {
        // IDに紐づくユーザー情報が存在するかの確認
        if (!this.exists(id)) {
            throw new NotFoundException();
        }
        return userRepository.findById(id).get();
    }

    /**
     * ユーザー情報登録・更新処理
     *
     * @param user 保存したいユーザー情報
     * @return 保存したユーザー情報
     */
    public User save(User user) {
//        //ユーザー情報を更新する場合
//        if (user.getId() != null) {
//            //更新対象のユーザー情報を取得
//            User existingUser = userRepository.findById(user.getId()).get();
//            //保存したいユーザー情報のパスワードが空の場合
//            if (user.getPassword().isEmpty()) {
//                //保存したいユーザー情報に以前のパスワードを格納
//                user.setPassword(existingUser.getPassword());
//            } else {
//                //パスワードのハッシュ化
//                String encodedPassword = encodePassword(user.getPassword());
//                //ハッシュ化したパスワードを格納
//                user.setPassword(encodedPassword);
//            }
//        }
        //ユーザー情報を新規登録する場合
//        else {
            // パスワードのハッシュ化
            String encodedPassword = encodePassword(user.getPassword());
            // ハッシュ化したパスワードを格納
            user.setPassword(encodedPassword);
//        }
        return userRepository.save(user);
    }

    /**
     * ユーザー情報の入力値チェック
     *
     * @param email メールアドレス
     * @param name ユーザー名
     * @return true:正常の入力値 false:異常な入力値
     */
    public boolean isValid(String email, String name) {
        //メールアドレスの判定（10文字から254文字まで）
        if (email.length() < 10 || email.length() > 254) {
            return false;
        }

        //ユーザー名の判定（1文字から100文字まで）
        if (name.length() < 1 || name.length() > 100) {
            return false;
        }

        return true;
    }

    /**
     * ユーザー情報のメールアドレス重複チェック
     *
     * @param name 重複確認したいメールアドレス
     * @return true:重複なし false:重複あり
     */
    public boolean UserEmailcheckUnique(User user) {
        boolean isCreatingNew = (user.getId() == null || user.getId() == 0);
        User userByEmail = userRepository.findByEmail(user.getEmail());

        if (isCreatingNew) {
            if (userByEmail != null) {
                return false;
            }
        } else {
            if (userByEmail != null && userByEmail.getId() != user.getId()) {
                return false;
            }
        }
        return true;
    }

    /**
     * ユーザー情報のユーザー名重複チェック
     *
     * @param name 重複確認したいユーザー名
     * @return true:重複なし false:重複あり
     */
    public boolean UserNamecheckUnique(User user) {
        boolean isCreatingNew = (user.getId() == null || user.getId() == 0);
        User userByName = userRepository.findByName(user.getName());

        if (isCreatingNew) {
            if (userByName != null) {
                return false;
            }
        } else {
            if (userByName != null && userByName.getId() != user.getId()) {
                return false;
            }
        }
        return true;
    }

    /**
     * パスワードのハッシュ化
     *
     * @param rawPassword
     */
    private String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * ユーザー情報の存在チェック
     *
     * @param name 確認したいユーザー情報のID
     * @return true:存在する false:存在しない
     */
    private boolean exists(Long id) {
        Long countById = userRepository.countById(id);
        if (countById == null || countById == 0L) {
            return false;
        }
        return true;
    }

}
