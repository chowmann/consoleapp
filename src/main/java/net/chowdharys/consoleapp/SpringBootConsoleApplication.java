package net.chowdharys.consoleapp;

import net.chowdharys.consoleapp.mongo.MongoDbPersonTest;
import net.chowdharys.consoleapp.mongo.MongoDbTest;
import net.chowdharys.consoleapp.service.HelloMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.lang.System.exit;

@SpringBootApplication
public class SpringBootConsoleApplication implements CommandLineRunner {

    @Autowired
    private HelloMessageService helloService;

    public static void main(String[] args) throws Exception {

        //disabled banner, don't want to see the spring logo
        SpringApplication app = new SpringApplication(SpringBootConsoleApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

        //SpringApplication.run(SpringBootConsoleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (args.length > 0 ) {
            System.out.println(helloService.getMessage(args[0].toString()));
        }else{
            System.out.println(helloService.getMessage());
        }

        MongoDbTest dbTest = new MongoDbTest();
        dbTest.test();
        MongoDbPersonTest dbpersonTest = new MongoDbPersonTest();
        dbpersonTest.personTest();

        exit(0);
    }
}
