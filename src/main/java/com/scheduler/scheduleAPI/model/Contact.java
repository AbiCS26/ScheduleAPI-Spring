package com.scheduler.scheduleAPI.model;

import com.google.cloud.datastore.Entity;
import com.googlecode.objectify.annotation.Id;
import inputs.IdGenerator;
import inputs.Validation;

@com.googlecode.objectify.annotation.Entity
public class Contact {
    @Id
    private String id;
    private String name;
    private String email;
    private String mobileNumber;
    private Role role;

    enum Role {
        GUEST,
        OWNER
    }

    public Contact() {
    }

    public Contact(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.mobileNumber = builder.mobileNumber;
        this.role = builder.role;
        this.id = builder.id;
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

    public Contact convertEntityToContact(Entity entity) {
        this.name = entity.getString("name");
        this.email = entity.getString("email");
        this.mobileNumber = entity.getString("mobileNumber");
        this.role = Role.valueOf(entity.getString("role"));
        this.id = entity.getKey().getName();
        return this;
    }

    public static class Builder {
        private String id;
        private String name;
        private String email;
        private String mobileNumber;
        private Role role;

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
            this.role = Role.GUEST;
            return this;
        }

        public Builder setOwnerRole() {
            this.role = Role.OWNER;
            return this;
        }
    }
}
