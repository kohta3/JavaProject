package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.user.UserRepository;

@Service
public class A2ChannelUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

	@Autowired
	public A2ChannelUserDetailsService(UserRepository userRepository) {
	    this.userRepository = userRepository;
    }

	@Override
	public A2ChannelUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("ユーザが見つかりません");
        }

        return new A2ChannelUserDetails(user);
	}
}
