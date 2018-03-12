public class SettingsData {
    private boolean cache;
    private String path;
    private boolean spentTime;

    public SettingsData(boolean cache, String path, boolean spentTime) {
        this.cache = cache;
        this.path = path;
        this.spentTime = spentTime;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSpentTime() {
        return spentTime;
    }

    public void setSpentTime(boolean spentTime) {
        this.spentTime = spentTime;
    }
}
