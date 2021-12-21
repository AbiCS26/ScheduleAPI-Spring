package com.scheduler.scheduleAPI;

import com.google.cloud.datastore.DatastoreOptions;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.scheduler.scheduleAPI.model.Calendar;
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
        ObjectifyService.register(Calendar.class);
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        System.out.println("allow origin: " + "http://localhost:8080");
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        //.allowedOrigins("http://localhost")
//                        .allowedOrigins("http://localhost:8080")
//                        .allowedMethods("PUT", "DELETE", "GET", "POST", "PATCH");
//            }
//        };
//    }
}
