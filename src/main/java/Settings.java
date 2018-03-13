import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Settings {



    public static void mainUI(Pane root){
        root.getChildren().clear();

        Text text1 = new Text("Настройки");
        text1.setFont(new Font(22));
        text1.setTranslateX(350);
        text1.setTranslateY(50);



        Button button1 = new Button("Вернуться на главный экран");
        button1.setTranslateX(200);
        button1.setTranslateY(220);
        button1.setMinWidth(400);
        button1.setOnAction((event) -> {
            Main.drawUI(root);

        });


        Button button2 = new Button("Изменить Использовать кэш");
        button2.setTranslateX(200);
        button2.setTranslateY(250);
        button2.setMinWidth(400);
        button2.setOnAction((event) -> {
            cacheUI(root);

        });

        Button button3 = new Button("Изменить Путь к кэшу");
        button3.setTranslateX(200);
        button3.setTranslateY(280);
        button3.setMinWidth(400);
        button3.setOnAction((event) -> {
            pathUI(root);

        });

        Button button4 = new Button("Изменить Отображение времени затраченного нa выполнение");
        button4.setTranslateX(200);
        button4.setTranslateY(310);
        button4.setMinWidth(400);
        button4.setOnAction((event) -> {
            spentTimeUI(root);

        });




        root.getChildren().addAll(text1,button1,button2,button3,button4);

    }

    private static void spentTimeUI(Pane root) {
        root.getChildren().clear();

        Text text1 = new Text("Отображать время затраченное на выполнение задачи?");
        text1.setFont(new Font(18));
        text1.setTranslateX(150);
        text1.setTranslateY(100);

        Text text2 = new Text("True / False (Текущее состояние - "+Main.getSettingsData().isSpentTime()+")");
        text2.setTranslateX(350);
        text2.setTranslateY(120);

        TextField textField1 = new TextField();
        textField1.setTranslateX(350);
        textField1.setTranslateY(150);
        textField1.setPromptText(" true / false");

        Button button1 = new Button("Сохранить");
        button1.setTranslateX(350);
        button1.setTranslateY(180);
        button1.setMinWidth(150);
        button1.setOnAction((event) -> {
            String spentTime = textField1.getText().toLowerCase();
            if (spentTime.equals("true")||spentTime.equals("false")){
                Main.getSettingsData().setSpentTime(Boolean.parseBoolean(spentTime));
            } else {
                showAlert("Вы ввели не коректные данные!!! Нужно вводить \"true\" или \"false\" !!!");
            }
            spentTimeUI(root);

        });

        Button button2 = new Button("Вернуться в Настройки");
        button2.setTranslateX(350);
        button2.setTranslateY(210);
        button2.setMinWidth(150);
        button2.setOnAction((event) -> {
            mainUI(root);
        });

        root.getChildren().addAll(text1,text2,button1,button2,textField1);
    }

    private static void pathUI(Pane root) {
        root.getChildren().clear();

        Text text1 = new Text("Путь к кэшу");
        text1.setFont(new Font(18));
        text1.setTranslateX(350);
        text1.setTranslateY(100);

        Text text2 = new Text(Main.getSettingsData().getPath());
        text2.setTranslateX(350);
        text2.setTranslateY(120);

        TextField textField1 = new TextField();
        textField1.setTranslateX(300);
        textField1.setTranslateY(150);
        textField1.setMinWidth(250);
        textField1.setPromptText("новый путь к кэшу");

        Button button1 = new Button("Сохранить");
        button1.setTranslateX(300);
        button1.setTranslateY(180);
        button1.setMinWidth(250);
        button1.setOnAction((event) -> {
            Main.saveCache(Main.getSettingsData().getPath());

            Main.getSettingsData().setPath(textField1.getText());
            try {
                Main.setCache(Main.loadCache(Main.getSettingsData().getPath()));
            } catch (Exception e){
                Settings.showAlert("Ошибка загрузки файла с кешом!!!Кеш пустой!!!");
                Main.setCache(new Cache());
            }
            pathUI(root);

        });

        Button button2 = new Button("Вернуться в Настройки");
        button2.setTranslateX(300);
        button2.setTranslateY(210);
        button2.setMinWidth(250);
        button2.setOnAction((event) -> {
            mainUI(root);
        });

        root.getChildren().addAll(text1,text2,button1,button2,textField1);
    }

    private static void cacheUI(Pane root) {
        root.getChildren().clear();

        Text text1 = new Text("Сохранять кэш?");
        text1.setFont(new Font(18));
        text1.setTranslateX(350);
        text1.setTranslateY(100);

        Text text2 = new Text("True / False (Текущее состояние - "+Main.getSettingsData().isCache()+")");
        text2.setTranslateX(350);
        text2.setTranslateY(120);

        TextField textField1 = new TextField();
        textField1.setTranslateX(350);
        textField1.setTranslateY(150);
        textField1.setPromptText(" true / false");

        Button button1 = new Button("Сохранить");
        button1.setTranslateX(350);
        button1.setTranslateY(180);
        button1.setMinWidth(150);
        button1.setOnAction((event) -> {
            String cache = textField1.getText().toLowerCase();
            if (cache.equals("true")||cache.equals("false")){
                Main.getSettingsData().setCache(Boolean.parseBoolean(cache));
            } else {
                showAlert("Вы ввели не коректные данные!!! Нужно вводить \"true\" или \"false\" !!!");
            }
            cacheUI(root);

        });

        Button button2 = new Button("Вернуться в Настройки");
        button2.setTranslateX(350);
        button2.setTranslateY(210);
        button2.setMinWidth(150);
        button2.setOnAction((event) -> {
            mainUI(root);
        });

        root.getChildren().addAll(text1,text2,button1,button2,textField1);
    }

    static void showAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR!!!");
        alert.setHeaderText(s);
        alert.showAndWait();
    }


}
