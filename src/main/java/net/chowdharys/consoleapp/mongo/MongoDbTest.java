package net.chowdharys.consoleapp.mongo;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import net.chowdharys.consoleapp.mongo.testmodels.Person;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 *
 *
 * https://github.com/mongodb/mongo-java-driver/blob/master/driver-sync/src/examples/tour/PojoQuickTour.java
 */
public class MongoDbTest {
    public static final String HOST_NAME = "localhost";
    public static final String PORT = "27017";

    public MongoClient getMongoClient() {
        return new MongoClient(HOST_NAME, Integer.parseInt(PORT));
    }

    public MongoDatabase getMongoDatabase(String dbName) {
        return getMongoClient().getDatabase(dbName);
    }

    public MongoCollection<Document> getMongoCollection(String dbName, String collectionName) {
        return getMongoDatabase(dbName).getCollection(collectionName);
    }

    public void test() {
        MongoCollection<Document> collection = getMongoCollection("local", "persons");
        Document mydoc = collection.find().first();
        System.out.println(mydoc.toJson());

        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }

        collection.find().forEach(printBlock);

    }

    Block<Document> printBlock = new Block<Document>() {
        @Override
        public void apply(final Document document) {
            System.out.println(document.toJson());
        }
    };

}
