package kde.backend.service;

import kde.backend.domain.Reuser;
import kde.backend.repository.ReuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ReuserRepository reuserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Reuser> optionalReuser = reuserRepository.findByUsername(username);

        User.UserBuilder userBuilder = null;
        if(optionalReuser.isPresent()) {
            Reuser cuser = optionalReuser.get(); //현재 user
            userBuilder =  User.withUsername(username);
            userBuilder.password(cuser.getPassword());
            userBuilder.roles(cuser.getRole());
        }else{
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        return userBuilder.build();
    }
}