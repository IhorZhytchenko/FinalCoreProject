import Entity.ResponseChannel;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class YouTubeAnalytics {
    static private Pane timePane = new Pane();
    static {
        timePane.setTranslateY(275);
        timePane.setMaxHeight(20);
    }
    public static void globalChannelInfo(Pane root,String id,long startTime,boolean mediaLabel){
        new Thread(() -> {
            List<ResponseChannel>  channels = new ArrayList<>() ;
            try {
                if (mediaLabel){
                    channels.add(YouTubeChannels.channelMedia(id));
                } else {
                    channels.add(YouTubeChannels.channelSearch(id));
                }
            } catch (UnirestException e) {
                Platform.runLater(() -> {
                    Settings.showAlert("Ошибка получения данных!!!Проверте соединение с интернетом!!!");
                });


            }
            Platform.runLater(() -> {
                checkList(channels,new String[]{id});
                showInfo(root,channels,startTime,mediaLabel);
            });
        }).start();
    }
    public static void compareChannel(Pane root,String id1,String id2,long startTime,boolean mediaLabel){
        new Thread(() -> {
            List<ResponseChannel>  channels = new ArrayList<>() ;
            try {
                if(mediaLabel){
                    channels.add(YouTubeChannels.channelMedia(id1));
                    channels.add(YouTubeChannels.channelMedia(id2));
                } else {
                    channels.add(YouTubeChannels.channelSearch(id1));
                    channels.add(YouTubeChannels.channelSearch(id2));
                }

            } catch (UnirestException e) {
                Platform.runLater(() -> {
                    Settings.showAlert("Ошибка получения данных!!!Проверте соединение с интернетом!!!");
                });

            }
            Platform.runLater(() -> {
                checkList(channels,new String[]{id1,id2});
                showInfo(root,channels,startTime,mediaLabel);
            });
        }).start();
    }

    public static void sortChannels(Pane root,String channelsId,int sortType,long startTime,boolean mediaLable){
        String[] id = channelsId.split(" +");
        new Thread(() -> {

            List<ResponseChannel>  channels = new ArrayList<>() ;
            try {
                for (int i=0;i<id.length;i++) {
                    if (mediaLable){
                        channels.add(YouTubeChannels.channelMedia(id[i]));
                    } else {
                        channels.add(YouTubeChannels.channelSearch(id[i]));
                    }

                }

            } catch (UnirestException e) {
                Platform.runLater(() -> {
                    Settings.showAlert("Ошибка получения данных!!!Проверте соединение с интернетом!!!");
                });

            }
            Platform.runLater(() -> {
                checkList(channels,id);
                showInfo(root,sort(channels,sortType),startTime,mediaLable);
            });
        }).start();
    }

    private static List<ResponseChannel> sort(List<ResponseChannel> channels, int sortType) {
        switch (sortType) {
            case 0 : return channels.stream()
                    .sorted(Comparator.comparing(s -> s.getItems()[0].getSnippet().getTitle()))
                    .collect(Collectors.toList());
            case 1 : return channels.stream()
                    .sorted(Comparator.comparing(s -> s.getItems()[0].getSnippet().getPublishedAt()))
                    .collect(Collectors.toList());
            case 2:  return channels.stream()
                    .sorted(Comparator.comparing(s -> s.getItems()[0].getStatistics().getSubscriberCount()))
                    .collect(Collectors.toList());
            case 3:  return channels.stream()
                    .sorted(Comparator.comparing(s -> s.getItems()[0].getStatistics().getVideoCount()))
                    .collect(Collectors.toList());
            case 4: return channels.stream()
                    .sorted(Comparator.comparing(s -> s.getItems()[0].getStatistics().getViewCount()))
                    .collect(Collectors.toList());
            case 5: return channels.stream()
                    .sorted(Comparator.comparing(s -> s.getItems()[0].getComment().getCountComment()))
                    .collect(Collectors.toList());
            default:return channels.stream()
                    .sorted(Comparator.comparing(s -> s.getItems()[0].getSnippet().getTitle()))
                    .collect(Collectors.toList());
        }

    }

    private static void showInfo(Pane root, List<ResponseChannel> channels,long startTime,boolean mediaLable) {


        TableView<ChannelInfo> table = new TableView<ChannelInfo>();
        TableColumn titleColumn = new TableColumn("Имя канала");
        TableColumn  publishedAtColumn = new TableColumn("Дата создания канала");
        TableColumn subscriberCountColumn = new TableColumn("Кол-во \nподписчиков");
        TableColumn videoCountColumn = new TableColumn("Кол-во видео\n на канале");
        TableColumn  viewCountColumn = new TableColumn("Кол-во просмотров\n видео");
        TableColumn  commentCountColumn = new TableColumn("Кол-во\n комментариев");
        
        titleColumn.setCellValueFactory(new PropertyValueFactory<ChannelInfo, String>("title"));
        publishedAtColumn.setCellValueFactory(new PropertyValueFactory<ChannelInfo, Date>("publishedAt"));
        subscriberCountColumn.setCellValueFactory(new PropertyValueFactory<ChannelInfo, Long>("subscriberCount"));
        videoCountColumn.setCellValueFactory(new PropertyValueFactory<ChannelInfo, Long>("videoCount"));
        viewCountColumn.setCellValueFactory(new PropertyValueFactory<ChannelInfo, Long>("viewCount"));
        commentCountColumn.setCellValueFactory(new PropertyValueFactory<ChannelInfo, Long>("commentCount"));

        table.getColumns().addAll(titleColumn,publishedAtColumn,subscriberCountColumn,videoCountColumn,viewCountColumn);
        if (mediaLable) table.getColumns().add(commentCountColumn);
        ObservableList<ChannelInfo> data = createObservableList(channels);

        table.setItems(data);
        table.setTranslateY(280);
        table.setMinWidth(780);

        root.getChildren().add(table);
        if(Main.getSettingsData().isSpentTime()){
            timePane.getChildren().clear();
            Text text = new Text("Время выполнения - "+(System.currentTimeMillis()-startTime)+"миллисекунд");
            timePane.getChildren().add(text);
            root.getChildren().remove(timePane);
            root.getChildren().add(timePane);
        }



    }

    private static void checkList(List<ResponseChannel> channels,String[]id){
        List<String> listId =new ArrayList<String>(Arrays.asList(id));
        for (int i =0; i<channels.size();i++){
            if (channels.get(i).getItems().length==0){
                channels.remove(i);
                Settings.showAlert("Канал с id - "+listId.get(i) +" не найден!!!");
                listId.remove(i);
                i--;
            }
        }

    }

    private static ObservableList<ChannelInfo> createObservableList(List<ResponseChannel> channels) {
        ObservableList<ChannelInfo> result = FXCollections.observableArrayList();
        for (ResponseChannel channel : channels) {
            result.add(new ChannelInfo(channel));
        }
        return result;
    }

    public static class ChannelInfo {
        private String title;
        private LocalDate publishedAt;
        private Long subscriberCount;
        private Long videoCount;
        private Long viewCount;
        private Long commentCount;


        public ChannelInfo(ResponseChannel responseChannel){
            this.title = responseChannel.getItems()[0].getSnippet().getTitle();
            this.publishedAt = responseChannel.getItems()[0].getSnippet().getPublishedAt().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            this.subscriberCount = responseChannel.getItems()[0].getStatistics().getSubscriberCount();
            this.videoCount = responseChannel.getItems()[0].getStatistics().getVideoCount();
            this.viewCount = responseChannel.getItems()[0].getStatistics().getViewCount();
            this.commentCount =responseChannel.getItems()[0].getComment().getCountComment();
        }


        public String getTitle(){return title;}
        public LocalDate getPublishedAt(){return publishedAt;}
        public Long getSubscriberCount(){return subscriberCount;}
        public Long getVideoCount(){return videoCount;}
        public Long getViewCount(){return viewCount;}
        public Long getCommentCount(){return commentCount;}


        public void setTitle(String title) {
            this.title = title;
        }
        public void setPublishedAt(LocalDate publishedAt) {
            this.publishedAt = publishedAt;
        }
        public void setSubscriberCount(Long subscriberCount) {
            this.subscriberCount = subscriberCount;
        }
        public void setVideoCount(Long videoCount) {
            this.videoCount = videoCount;
        }
        public void setViewCount(Long viewCount) {
            this.viewCount = viewCount;
        }
        public void setCommentCount(Long commentCount) {
            this.commentCount = commentCount;
        }

    }

}
