package com.scheduler.scheduleAPI.security;

import com.google.api.client.util.Lists;
import com.google.cloud.datastore.*;
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
        Entity user = getContactEntityByEmail(username);

        if (user == null)
            throw new UsernameNotFoundException("User not found for username: " + username);

        if (user.getString("role").equals("GUEST")) {
            userDetails = User.withUsername(username)
                    .password(user.getString("password"))
                    .authorities(GUEST.getAuthorities())
                    .build();

        } else if (user.getString("role").equals("OWNER")) {
            userDetails = User.withUsername(username)
                    .password(user.getString("password"))
                    .authorities(OWNER.getAuthorities())
                    .build();
        }
        return userDetails;
    }

    private Entity getContactEntityByEmail(String email) {
        Datastore datastore = DatastoreOptions.newBuilder().setProjectId("bookmyschedule-329807").build().getService();
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Contact")
                .setFilter(StructuredQuery.PropertyFilter.eq("email", email))
                .build();
        return Lists.newArrayList(datastore.run(query)).get(0);
    }


}

