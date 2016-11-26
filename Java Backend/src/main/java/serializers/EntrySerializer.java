package serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import model.Entry;

import java.io.IOException;

/**
 * Created by Robbe De Geyndt on 26/11/2016.
 */
public class EntrySerializer extends JsonSerializer<Entry> {

    @Override
    public void serialize(Entry value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeStringField("word", value.getWord());
        gen.writeStringField("translation", value.getTranslation());
        gen.writeObjectField("languages", value.getLanguages());
        gen.writeEndObject();
    }
}
