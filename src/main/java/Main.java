import com.alibaba.fastjson.JSON;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {
    static final int WINDOW_WIDTH = 800;
    static final int WINDOW_HEIGHT = 600;
    static final String SETTINGS_PATH = "files/Settings.txt";

    private static SettingsData settingsData;
    private static Cache cache;

    void windowSetup(Stage primaryStage) {
        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);

        primaryStage.setMaxWidth(WINDOW_WIDTH);
        primaryStage.setMaxHeight(WINDOW_HEIGHT);

        primaryStage.setMinWidth(WINDOW_WIDTH);
        primaryStage.setMinHeight(WINDOW_HEIGHT);
    }

    SettingsData loadSettings(String path) {
        String json = null;
        try (Scanner scanner = new Scanner(new FileInputStream(path))) {
            scanner.useDelimiter("\\z");
            json = scanner.next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return  JSON.parseObject(json, SettingsData.class);
    }

    void saveSettings(String path){
        String json = JSON.toJSONString(settingsData);
        try(FileWriter writer = new FileWriter(path)) {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Cache loadCache(String path) {
        String json = null;
        try (Scanner scanner = new Scanner(new FileInputStream(path))) {
            scanner.useDelimiter("\\z");
            json = scanner.next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return  JSON.parseObject(json, Cache.class);
    }

    void saveCache(String path){
        String json = JSON.toJSONString(cache);
        try(FileWriter writer = new FileWriter(path)) {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static  void drawUI(Pane root) {

        root.getChildren().clear();

        Text text1 = new Text("Hello!");
        text1.setFont(new Font(22));
        text1.setTranslateX(350);
        text1.setTranslateY(50);

        Text text2 = new Text("YouTubeAnalyticsLite ver 1.0");
        text2.setFont(new Font(14));
        text2.setTranslateX(300);
        text2.setTranslateY(80);

        Button analytics = new Button("Перейти в YouTube Analytics");
        analytics.setTranslateX(290);
        analytics.setTranslateY(220);
        analytics.setMinWidth(200);
        analytics.setOnAction((event) -> {
            AnalyticsUI.mainUI(root);

        });


        Button settings = new Button("Перейти в Настройки");
        settings.setTranslateX(290);
        settings.setTranslateY(250);
        settings.setMinWidth(200);
        settings.setOnAction((event) -> {
            Settings.mainUI(root);
        });




        root.getChildren().addAll(text1,text2,analytics,settings);
    }

    public static SettingsData getSettingsData() {
        return settingsData;
    }

    public static Cache getCache() {
        return cache;
    }

    public static void setCache(Cache cache) {
        Main.cache = cache;
    }

    public static void main(String[] args)  { launch(args); }




    @Override
    public void start(Stage primaryStage) throws Exception {
        windowSetup(primaryStage);
        Pane root = new Pane();
        drawUI(root);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        settingsData = loadSettings(SETTINGS_PATH);
         try {
             cache = loadCache(settingsData.getPath());
         } catch (Exception e){
             cache = new Cache();
         }


    }

    @Override
    public void stop() throws Exception {
        super.stop();
        saveSettings(SETTINGS_PATH);
        saveCache(settingsData.getPath());
        System.exit(0);
    }
}
