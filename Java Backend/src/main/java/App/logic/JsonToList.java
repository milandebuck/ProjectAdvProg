package App.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Entry;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Takes oncomin json containing list of entries and converts them to Entry-objects.
 * Created by Robbe De Geyndt on 3/12/2016.
 */
public class JsonToList {

    public static List<Entry> convert(String json) throws Exception {

        List<Object> entries = new ArrayList<Object>();
        List<Entry> out = new ArrayList<Entry>();

        //Get input
        ObjectMapper mapper = new ObjectMapper();
        entries = mapper.readValue(json, ArrayList.class);

        //Add entries in list
        for (Object value : entries) {
            Entry entry = mapper.readValue(new JSONObject((HashMap<String,String>)value).toString(), Entry.class);
            out.add(entry);
        }

        //return result
        return out;
    }
}