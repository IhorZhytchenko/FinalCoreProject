import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AnalyticsUI {

    public static void mainUI(Pane root){
        root.getChildren().clear();

        Text text1 = new Text("YouTube Analytics");
        text1.setFont(new Font(22));
        text1.setTranslateX(310);
        text1.setTranslateY(50);

        Text text2 = new Text("Выберите задачу:");
        text2.setFont(new Font(18));
        text2.setTranslateX(310);
        text2.setTranslateY(150);


        Button button1 = new Button("Вернуться на главный экран");
        button1.setTranslateX(200);
        button1.setTranslateY(220);
        button1.setMinWidth(400);
        button1.setOnAction((event) -> {
            Main.drawUI(root);

        });


        Button button2 = new Button("Отобразить глобальную информацию о канале");
        button2.setTranslateX(200);
        button2.setTranslateY(250);
        button2.setMinWidth(400);
        button2.setOnAction((event) -> {
            globalChannelInfoUI(root,false);
        });

        Button button3 = new Button("Сравнить глобальную информацию о каналах");
        button3.setTranslateX(200);
        button3.setTranslateY(280);
        button3.setMinWidth(400);
        button3.setOnAction((event) -> {
            compareChannelUI(root,false);
        });

        Button button4 = new Button("Сортировать каналы по их данным");
        button4.setTranslateX(200);
        button4.setTranslateY(310);
        button4.setMinWidth(400);
        button4.setOnAction((event) -> {
            sortChannelsUI(root,false);
        });

        Button button5 = new Button("Медиа резонанс");
        button5.setTranslateX(200);
        button5.setTranslateY(340);
        button5.setMinWidth(400);
        button5.setOnAction((event) -> {
            globalChannelInfoUI(root,true);
        });

        Button button6 = new Button("Сравнить Медиа резонанс");
        button6.setTranslateX(200);
        button6.setTranslateY(370);
        button6.setMinWidth(400);
        button6.setOnAction((event) -> {
            compareChannelUI(root,true);


        });

        Button button7 = new Button("Сортировать по Медиа резонансу");
        button7.setTranslateX(200);
        button7.setTranslateY(400);
        button7.setMinWidth(400);
        button7.setOnAction((event) -> {
            sortChannelsUI(root,true);
        });

        root.getChildren().addAll(text1,text2,button1,button2,button3,button4,button5,button6,button7);

    }


     static void sortChannelsUI(Pane root,boolean mediaLable) {
         root.getChildren().clear();

         Text text1 = new Text("Сортировать каналы по их данным");
         text1.setFont(new Font(18));
         text1.setTranslateX(250);
         text1.setTranslateY(100);

         if(mediaLable) text1.setText("Сортировать по Медиа резонансу");

         Text text2 = new Text("Введите Id каналов через пробел!!!");
         text2.setTranslateX(250);
         text2.setTranslateY(140);


         TextField textField1 = new TextField();
         textField1.setTranslateX(50);
         textField1.setTranslateY(150);
         textField1.setMinWidth(700);
         textField1.setPromptText("Введите ChannelID через пробел:");

         ChoiceBox choiceBox = new ChoiceBox();
         choiceBox.setTranslateX(430);
         choiceBox.setTranslateY(180);
         choiceBox.setMinWidth(250);
         choiceBox.setItems(FXCollections.observableArrayList(
                 "Сортировать по имени канала",
                 "Сортировать по дате создания",
                 "Сортировать по кол-ву подписчиков",
                 "Сортировать по кол-ву видео на канале",
                 "Сортировать по кол-ву просмотров видео")
         );
         choiceBox.getSelectionModel().select(0);

         Button button1 = new Button("Выполнить");
         button1.setTranslateX(100);
         button1.setTranslateY(180);
         button1.setMinWidth(250);
         button1.setOnAction((event) -> {
             int sortType=choiceBox.getSelectionModel().getSelectedIndex();
             if(mediaLable) sortType =5;
            YouTubeAnalytics.sortChannels(root,textField1.getText(),sortType,System.currentTimeMillis(),mediaLable);
         });

         if(mediaLable)button1.setTranslateX(280);
         Button button2 = new Button("Вернуться YouTube Analytics");
         button2.setTranslateX(280);
         button2.setTranslateY(210);
         button2.setMinWidth(250);
         button2.setOnAction((event) -> {
             mainUI(root);
         });

         root.getChildren().addAll(text1,button1,button2,textField1,text2);
         if(!mediaLable) root.getChildren().add(choiceBox);
    }

     static void compareChannelUI(Pane root,boolean mediaLable) {
        root.getChildren().clear();

        Text text1 = new Text("Сравнить глобальную информацию о каналах");
        text1.setFont(new Font(18));
        text1.setTranslateX(200);
        text1.setTranslateY(100);
        if(mediaLable){
            text1.setText("Сравнить Медиа резонанс");
            text1.setTranslateX(280);
        }


        TextField textField1 = new TextField();
        textField1.setTranslateX(150);
        textField1.setTranslateY(150);
        textField1.setMinWidth(250);
        textField1.setPromptText("Введите ChannelID №1");

        TextField textField2 = new TextField();
        textField2.setTranslateX(420);
        textField2.setTranslateY(150);
        textField2.setMinWidth(250);
        textField2.setPromptText("Введите ChannelID №2");

        Button button1 = new Button("Выполнить");
        button1.setTranslateX(280);
        button1.setTranslateY(180);
        button1.setMinWidth(250);
        button1.setOnAction((event) -> {
            YouTubeAnalytics.compareChannel(root,textField1.getText(),textField2.getText(),System.currentTimeMillis(),mediaLable);

        });

        Button button2 = new Button("Вернуться YouTube Analytics");
        button2.setTranslateX(280);
        button2.setTranslateY(210);
        button2.setMinWidth(250);
        button2.setOnAction((event) -> {
            mainUI(root);
        });

        root.getChildren().addAll(text1,button1,button2,textField1,textField2);
    }

     static void globalChannelInfoUI(Pane root,boolean mediaLabel) {
        root.getChildren().clear();

        Text text1 = new Text("Отобразить глобальную информацию о канале ");
        text1.setFont(new Font(18));
        text1.setTranslateX(200);
        text1.setTranslateY(100);
         if (mediaLabel){
             text1.setText("Медиа резонанс");
             text1.setTranslateX(320);
             text1.setFont(new Font(22));
         }


        TextField textField1 = new TextField();
        textField1.setTranslateX(280);
        textField1.setTranslateY(150);
        textField1.setMinWidth(250);
        textField1.setPromptText("Введите ChannelID:");

        Button button1 = new Button("Выполнить");
        button1.setTranslateX(280);
        button1.setTranslateY(180);
        button1.setMinWidth(250);
        button1.setOnAction((event) -> {
            YouTubeAnalytics.globalChannelInfo(root,textField1.getText(),System.currentTimeMillis(),mediaLabel);


        });

        Button button2 = new Button("Вернуться YouTube Analytics");
        button2.setTranslateX(280);
        button2.setTranslateY(210);
        button2.setMinWidth(250);
        button2.setOnAction((event) -> {
            mainUI(root);
        });

        root.getChildren().addAll(text1,button1,button2,textField1);
    }

}


