package sisalfa.android.com.appsisalfa.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import sisalfa.android.com.appsisalfa.model.Contexto;

/**
 * Created by Pichau on 06/01/2018.
 */

public class ContextoDec implements JsonDeserializer {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement element = json.getAsJsonObject();
        if(json.getAsJsonObject() != null){
            element = json.getAsJsonObject();
        }
        return (new Gson().fromJson(element, Contexto.class));
    }
}
