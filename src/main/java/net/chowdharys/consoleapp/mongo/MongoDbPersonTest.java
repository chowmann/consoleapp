package net.chowdharys.consoleapp.mongo;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.chowdharys.consoleapp.mongo.testmodels.Address;
import net.chowdharys.consoleapp.mongo.testmodels.Person;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Arrays;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDbPersonTest {
    public void personTest() {
        MongoCollection<Person> collection = getPersonMongoCollection();

        collection.drop();

        collection.insertMany(people);
        System.out.println("Inserted many people successfully");

        collection.find().forEach(printPersonBlock);

    }

    private MongoCollection<Person> getPersonMongoCollection() {
        // create codec registry for POJOs
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        // get handle to "mydb" database
        MongoDatabase database = (new MongoDbTest().getMongoClient()).getDatabase("local").withCodecRegistry(pojoCodecRegistry);

        // get a handle to the "people" collection
        return database.getCollection("persons", Person.class);
    }

    Block<Person> printPersonBlock = new Block<Person>() {
        @Override
        public void apply(final Person person) {
            System.out.println(person);
        }
    };

    // now, lets add some more people so we can explore queries and cursors
    private static List<Person> people = Arrays.asList(
            new Person("Charles Babbage", "Babbage", new Address("5 Devonshire Street", "London", "W11")),
            new Person("Alan Turing", "Turing", new Address("Bletchley Hall", "Bletchley Park", "MK12")),
            new Person("Timothy Berners-Lee", "Berners-Lee", new Address("Colehill", "Wimborne", null))
    );

}
