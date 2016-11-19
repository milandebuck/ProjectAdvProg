package main.java.db;

import db.MongoConnection;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.DBCursor;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;
import model.Entry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by milan on 19.11.16.
 */
public class DataAcces{
    //this is our db object
    private DB database;
    private MongoConnection mongoConnection = new MongoConnection();

    private JacksonDBCollection<Entry,String> entries;

    //constructor
    public DataAcces(){
        try{

            // Now connect to your databases
            database = mongoConnection.Connect();
            System.out.println("Connect to database successfully");
            DBCollection _entries=database.getCollection("entries");
            entries= JacksonDBCollection.wrap(_entries,Entry.class,String.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public List<Entry> getEntries(){

        return  entries.find().toArray();
    }




}
