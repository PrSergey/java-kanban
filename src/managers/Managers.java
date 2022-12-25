package managers;

import adapter.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

public class Managers {

    public static TaskManager getDefaultMemory() {
        return new InMemoryTaskManager();
    }
    public static TaskManager getDefaultFile(String path) throws IOException, InterruptedException {

        return new HttpTaskManager(path);
    }


    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public  static Gson getGson(){
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        return gsonBuilder.create();
    }

}
