package com.project38.appuser.auth;

import com.google.common.collect.Lists;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project38.appuser.security.ApplicationUserRole.*;

@Service
@NoArgsConstructor(force = true)
public class ApplicationUserService implements UserDetailsService {

//    private final ApplicationUserRepo applicationUserRepo;
    private final ApplicationUserDao applicationUserDao;
    private final PasswordEncoder passwordEncoder;
    public ApplicationUserService(@Qualifier("fake") ApplicationUserDao applicationUserDao, PasswordEncoder passwordEncoder) {
        this.applicationUserDao = applicationUserDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserDao
                .selectApplicationUserByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(
                        String.format("Username %s not found", username)
                ));
    }

    public List<ApplicationUser> getApplicationUsers() {
        return Lists.newArrayList(
                new ApplicationUser(
                        "annasmith",
                        passwordEncoder.encode("password"),
                        PUBLISHER.grantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "linda",
                        passwordEncoder.encode("password"),
                        ADMIN.grantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "tom",
                        passwordEncoder.encode("password"),
                        ARTIST_MANAGER.grantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                )
        );
    }
}
