import Entity.ResponseChannel;

import java.util.ArrayList;
import java.util.List;

public class Cache {
    private List<ResponseChannel> responseChannels = new ArrayList<>();

    public List<ResponseChannel> getResponseChannels() {
        return responseChannels;
    }

    public void setResponseChannels(List<ResponseChannel> responseChannels) {
        this.responseChannels = responseChannels;
    }
}
