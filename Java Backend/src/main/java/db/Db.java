package db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Db{
    public static void FillDb(String[] args){
        //Make database
        /*MongoCredential credential = MongoCredential.createCredential(user1, ProjectDB, Azerty123);
        MongoClient mongoClient = new MongoClient("localhost", Arrays.asList(credential));
        Db db = mongoClient.getDB("ProjectDB");
        DbCollection collection = db.getCollection("en_nl_list");*/

        //Read file
        BufferedReader br;
        try {
            String currentline;
            int count = 0;
            String wordNL;
            String wordEN;
            br = new BufferedReader(new FileReader("/data/output.txt"));
            while ((currentline = br.readLine()) != null) {
                count += 1;
                if((count % 2) == 1){
                    wordNL = currentline;
                }
                else{
                    wordEN = currentline;
                    /*BasicDBObject doc = new BasicDBObject("en", wordEN)
                            .append("nl", wordNL);
                    collection.insert(doc);*/
                }
            }
        }
        catch(IOException ex){
            System.out.print(ex.getMessage());
        }
    }
}
