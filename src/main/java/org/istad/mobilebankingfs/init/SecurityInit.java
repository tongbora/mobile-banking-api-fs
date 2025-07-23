package org.istad.mobilebankingfs.init;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.istad.mobilebankingfs.domain.Role;
import org.istad.mobilebankingfs.domain.User;
import org.istad.mobilebankingfs.repository.RoleRepository;
import org.istad.mobilebankingfs.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SecurityInit {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostConstruct
    public void init(){
        Role defaultRole = new Role();
        defaultRole.setRole("USER");

        Role admin = new Role();
        admin.setRole("ADMIN");

        Role staff = new Role();
        staff.setRole("STAFF");

        Role customer = new Role();
        customer.setRole("CUSTOMER");

        if(roleRepository.count() == 0){
            roleRepository.saveAll(List.of(defaultRole, admin, staff, customer));
        }

        if(userRepository.count() == 0){
            User userAdmin = new User();
            userAdmin.setUsername("admin");
            userAdmin.setIsEnabled(true);
            userAdmin.setPassword(passwordEncoder.encode("qwer@123"));
            userAdmin.setRoles(List.of(admin, defaultRole));

            User userStaff = new User();
            userStaff.setUsername("staff");
            userStaff.setIsEnabled(true);
            userStaff.setPassword(passwordEncoder.encode("qwer@123"));
            userStaff.setRoles(List.of(staff, defaultRole));

            User userCustomer = new User();
            userCustomer.setUsername("customer");
            userCustomer.setIsEnabled(true);
            userCustomer.setPassword(passwordEncoder.encode("qwer@123"));
            userCustomer.setRoles(List.of(customer, defaultRole));

            userRepository.saveAll(List.of(userAdmin, userStaff, userCustomer));
        }
    }
}
