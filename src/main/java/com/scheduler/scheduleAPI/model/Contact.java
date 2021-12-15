package com.scheduler.scheduleAPI.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.scheduler.scheduleAPI.security.UserRole;
import inputs.IdGenerator;
import inputs.Validation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class Contact {
    @Id
    private String id;
    private String name;
    @Index
    private String email;
    private String mobileNumber;
    private String password;
    private UserRole role;

    public Contact() {
    }

    public Contact(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.mobileNumber = builder.mobileNumber;
        this.role = builder.role;
        this.id = builder.id;
        this.password = builder.password;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getRole() {
        return this.role.toString();
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public static class Builder {
        private String id;
        private String name;
        private String email;
        private String mobileNumber;
        private UserRole role;
        private String password;

        public Builder setPassword(String password) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(password);
            this.password = encryptedPassword;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setId() {
            this.id = IdGenerator.generateID();
            return this;
        }

        public Builder setName(String name) {
            Validation.checkString(name, "Participant Name");

            this.name = name;
            return this;
        }

        public Builder setEmail(String email) {
            Validation.checkString(email, "Participant Email");

            this.email = email;
            return this;
        }

        public Builder setMobileNumber(String mobileNumber) {
            Validation.checkMobileNumber(mobileNumber);

            this.mobileNumber = mobileNumber;
            return this;
        }

        public Contact build() {
            return new Contact(this);
        }

        public Builder setGuestRole() {
            this.role = UserRole.GUEST;
            return this;
        }

        public Builder setOwnerRole() {
            this.role = UserRole.OWNER;
            return this;
        }
    }
}
