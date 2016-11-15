import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;

import java.util.List;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DbFiller{
    public static void main(string[] args){
        //Make database
        MongoCredential credential = MongoCredential.createCredential(user1, translator_db, Azerty123);
        MongoClient mongoClient = new MongoClient("localhost", Arrays.asList(credential));
        Db db = mongoClient.getDB("translator_db");
        DbCollection collection = db.getCollection("en_nl_list");

        //Read file
        BufferedReader br = null;
        try {
            String currentline;
            int count = 0;
            String wordNL = "";
            String wordEN = "";
            br = new BufferedReader(new FileReader("/data/output.txt"));
            while ((currentline = br.readLine()) != null) {
                count += 1;
                if((count % 2) == 1){
                    wordNL = currentline;
                }
                else{
                    wordEN = currentline;
                    BasicDBObject doc = new BasicDBObject("en", wordEN)
                            .append("nl", wordNL);
                    collection.insert(doc);
                }
            }
        }
    }
}
