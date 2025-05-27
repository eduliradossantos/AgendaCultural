package security;

import java.util.ArrayList;

import model.User;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // Aqui usamos o email como username
                user.getPassword(),
                new ArrayList<>() // Aqui entrariam as roles, se existissem
        );
    }
}
