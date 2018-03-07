import Entity.ResponseChannel;
import com.alibaba.fastjson.JSON;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.time.LocalDateTime;

public class YouTubeChannels {
    public static final String API_KEY = "AIzaSyCEFEMFYZfkHX4NlozondtA6a2unDc21zE";


    public static final ObjectMapper mapper = new ObjectMapper() {
        public <T> T readValue(String value, Class<T> valueType) {
            return JSON.parseObject(value, valueType);
        }

        public String writeValue(Object value) {
            return JSON.toJSONString(value);
        }
    };
    static {
        Unirest.setObjectMapper(mapper);
    }

    public static ResponseChannel channelSearch(String id) throws UnirestException {

        HttpResponse<ResponseChannel> response = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
                .queryString("key", API_KEY)
                .queryString("id", id)
                .queryString("part", "snippet,statistics")
                .asObject(ResponseChannel.class);

        return response.getBody();


    }

}
