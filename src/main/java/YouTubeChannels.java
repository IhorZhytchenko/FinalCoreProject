import Entity.ResponseChannel;
import EntityPlayList.ResponsePlaylist;
import EntityVideo.ResponseVideo;
import com.alibaba.fastjson.JSON;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        if (Main.getCache() !=null){
        for(ResponseChannel responseChannel: Main.getCache().getResponseChannels()){
            if (id.equals(responseChannel.getItems()[0].getId())){
                return responseChannel;
            }
        }}

        HttpResponse<ResponseChannel> response = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
                .queryString("key", API_KEY)
                .queryString("id", id)
                .queryString("part", "snippet,statistics,contentDetails")
                .asObject(ResponseChannel.class);

        if (Main.getSettingsData().isCache()&&response.getBody().getItems().length!=0) {
            Main.getCache().getResponseChannels().add(response.getBody());
        }

        return response.getBody();

    }


    public static ResponseChannel channelMedia(String id) throws UnirestException {
        ResponseChannel responseChannel =channelSearch(id);
        if(responseChannel.getItems().length==0)return responseChannel;
        if (responseChannel.getItems()[0].getComment().isCacheLabel()) return responseChannel;
        List<String> cannelsId = getCannelsId(responseChannel.getItems()[0].getContentDetails().getRelatedPlaylists().getUploads());
        long commentsCount = getComments(cannelsId);

        responseChannel.getItems()[0].getComment().setCountComment(commentsCount);
        responseChannel.getItems()[0].getComment().setCacheLabel(true);




        return responseChannel;

    }

    private static long getComments(List<String> cannelsId) throws UnirestException {
        long result = 0;
        for (String id:cannelsId) {
            HttpResponse<ResponseVideo> response = Unirest.get("https://www.googleapis.com/youtube/v3/videos")
                    .queryString("key", API_KEY)
                    .queryString("id", id)
                    .queryString("part", "statistics")
                    .asObject(ResponseVideo.class);
            result+=response.getBody().getItems()[0].getStatistics().getCommentCount();
        }
        return result;
    }

    private static List<String> getCannelsId(String uploads) throws UnirestException {
        List<String> result = new ArrayList<>();
        HttpResponse<ResponsePlaylist> response = Unirest.get("https://www.googleapis.com/youtube/v3/playlistItems")
                .queryString("key", API_KEY)
                .queryString("playlistId", uploads)
                .queryString("part", "snippet")
                .queryString("maxResults", 50)
                .asObject(ResponsePlaylist.class);

        copyId(result,response.getBody());

        String page =response.getBody().getNextPageToken();
        while (page!=null){
            HttpResponse<ResponsePlaylist> resp = Unirest.get("https://www.googleapis.com/youtube/v3/playlistItems")
                    .queryString("key", API_KEY)
                    .queryString("playlistId", uploads)
                    .queryString("part", "snippet")
                    .queryString("maxResults", 50)
                    .queryString("pageToken", page)
                    .asObject(ResponsePlaylist.class);
            page = resp.getBody().getNextPageToken();
            copyId(result,resp.getBody());
        }



        return result;
    }

    private static void copyId(List<String> result, ResponsePlaylist body) {
        for (int i=0;i<body.getItems().length;i++){
            result.add(body.getItems()[i].getSnippet().getResourceId().getVideoId());
        }
    }


}
