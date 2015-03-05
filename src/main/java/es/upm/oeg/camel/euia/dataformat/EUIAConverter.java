package es.upm.oeg.camel.euia.dataformat;


import com.google.gson.Gson;
import es.upm.oeg.camel.euia.model.Context;
import org.apache.camel.Converter;

@Converter
public class EUIAConverter {

    private static final Gson gson = new Gson();

    @Converter
    public static String contextToJson(Context context){
        return gson.toJson(context);
    }

    @Converter
    public static Context jsonToContext(String json){
        return gson.fromJson(json, Context.class);
    }
}
