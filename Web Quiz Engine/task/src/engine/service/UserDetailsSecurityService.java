package engine.service;

import engine.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsSecurityService implements UserDetailsService {
    @Autowired
    UserService userRepository;
    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param email the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Loading user for authentication " + email); //temp

        Optional<User> optUser = Optional.ofNullable(userRepository.findByEmail(email));

        if(optUser.isEmpty()){
            System.out.println("No user found from the database: " + optUser); //tem
            throw new UsernameNotFoundException("Not found " +email);
         }

        User user = optUser.get();
        UserDetails userDetail=org.springframework.security.core.userdetails.User.withUsername(user.getEmail()).password(user.getPassword()).authorities("USER").build();
        System.out.println("User Details from the database: " + userDetail); //tem
        return userDetail;

    }
    public UserDetailsSecurityService(){

    }
}
