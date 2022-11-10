package project.accountservice;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> user = repository.findUserByUsername(username);
//        if (user.isPresent()) {
//            return new UserDetailsImpl(user.get());
//        }
//
        throw new UsernameNotFoundException("Not found " + username);
    }
}
