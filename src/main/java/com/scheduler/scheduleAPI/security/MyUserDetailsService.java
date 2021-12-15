package com.scheduler.scheduleAPI.security;

import com.google.cloud.datastore.Entity;
import com.scheduler.scheduleAPI.service.DatastoreOperations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.scheduler.scheduleAPI.security.UserRole.GUEST;
import static com.scheduler.scheduleAPI.security.UserRole.OWNER;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = null;

        Entity user = DatastoreOperations.getContactEntityByIdQuery(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found for id: " + username);

        if (user.getString("role").equals("GUEST")) {
            System.out.println("In guest if statement");
            System.out.println(GUEST.getAuthorities());
            userDetails = User.withUsername(username)
                    .password(user.getString("password"))
                    .authorities(GUEST.getAuthorities())
                    .build();
        } else if (user.getString("role").equals("OWNER")) {
            System.out.println("In owner if statement");
            userDetails = User.withUsername(username)
                    .password(user.getString("password"))
                    .authorities(OWNER.getAuthorities())
                    .build();
        }
        return userDetails;
    }
}
