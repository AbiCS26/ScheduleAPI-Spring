package com.scheduler.scheduleAPI.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.scheduler.scheduleAPI.inputs.IdGenerator;
import com.scheduler.scheduleAPI.security.UserRole;
import com.scheduler.scheduleAPI.validation.Validator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class Contact {


    @Id
    private String id;
    @Index
    private String email;
    private String name;
    private String mobileNumber;
    private String password;
    private String role;
    private String ownerId;

    public Contact() {
    }

    public String getId() {
        return id;
    }

    public Contact(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.mobileNumber = builder.mobileNumber;
        this.role = builder.role.name();
        this.password = builder.password;
        this.ownerId = builder.ownerId;
        this.id = builder.id;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public static class Builder {
        private String id;
        private String email;
        private String name;
        private String mobileNumber;
        private UserRole role;
        private String password;
        private String ownerId;

        public Builder setOwnerId(String ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public Builder setId() {
            this.id = IdGenerator.generateID();
            return this;
        }

        public Builder setPassword(String password) {
            Validator.checkPassword(password);
            String encryptedPassword = new BCryptPasswordEncoder().encode(password);
            this.password = encryptedPassword;
            return this;
        }

        public Builder setName(String name) {
            Validator.checkString(name, "Participant Name");

            this.name = name;
            return this;
        }

        public Builder setEmail(String email) {
            Validator.checkString(email, "Participant Email");

            this.email = email;
            return this;
        }

        public Builder setMobileNumber(String mobileNumber) {
            Validator.checkMobileNumber(mobileNumber);

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

        public Builder setId(String id) {
            this.id = id;
            return this;
        }
    }
}
