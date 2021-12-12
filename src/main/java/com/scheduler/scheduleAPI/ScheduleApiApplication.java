package com.scheduler.scheduleAPI;

import com.google.cloud.datastore.DatastoreOptions;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.scheduler.scheduleAPI.model.Contact;
import com.scheduler.scheduleAPI.model.Event;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScheduleApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleApiApplication.class, args);
        ObjectifyService.init(new ObjectifyFactory(
                DatastoreOptions.newBuilder()
                        .setProjectId("bookmyschedule-329807")
                        .build()
                        .getService()
        ));
        ObjectifyService.register(Event.class);
        ObjectifyService.register(Contact.class);
    }


}
