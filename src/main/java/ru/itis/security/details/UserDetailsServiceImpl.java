package ru.itis.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.models.User;
import ru.itis.repositories.UserRepository;

import java.util.Optional;

@Service(value = "customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = usersRepository.find(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new UserDetailsImpl(user);
        }
        throw new UsernameNotFoundException("User not found");
    }
}
