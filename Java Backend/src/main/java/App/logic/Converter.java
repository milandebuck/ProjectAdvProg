package App.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Robbe De Geyndt on 14/12/2016.
 */
public class Converter<T> {
    /**
     * Converts JSON String to a list of entries.
     * @param json user's request
     * @return list of entries
     * @throws Exception when parsing fails
     */
    public List<T> jsonToArrayList(String json, Class<?> valueType) throws Exception {

        List<Object> objects = new ArrayList<Object>();
        List<T> out = new ArrayList<T>();

        //Get input
        ObjectMapper mapper = new ObjectMapper();
        objects = mapper.readValue(json, ArrayList.class);

        //Add entries in list
        for (Object value : objects) {
            T object = mapper.readValue(new JSONObject((HashMap<String,String>)value).toString(), (Class<T>) valueType);
            out.add(object);
        }

        //return result
        return out;
    }


}
