package EntityPlayList;

public class ResponsePlaylist {
    private ItemPlaylist [] items;
    private String nextPageToken;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public ItemPlaylist[] getItems() {
        return items;
    }

    public void setItems(ItemPlaylist[] items) {
        this.items = items;
    }
}
