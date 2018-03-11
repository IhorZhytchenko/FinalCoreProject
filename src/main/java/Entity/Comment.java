package Entity;

public class Comment {
    private long countComment;
    private boolean cacheLabel=false;

    public long getCountComment() {
        return countComment;
    }

    public void setCountComment(long countComment) {
        this.countComment = countComment;
    }

    public boolean isCacheLabel() {
        return cacheLabel;
    }

    public void setCacheLabel(boolean cacheLabel) {
        this.cacheLabel = cacheLabel;
    }
}
