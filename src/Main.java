import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.util.function.Consumer;

public class Main extends Application {
    private KeyCode upKey = KeyCode.UP;
    private KeyCode downKey = KeyCode.DOWN;
    private KeyCode rightKey = KeyCode.RIGHT;
    private KeyCode leftKey = KeyCode.LEFT;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("snake");
        primaryStage.setScene(buildStartScene(primaryStage));
        primaryStage.show();
    }

    private Scene buildStartScene(Stage stage){
        Label title = buildLabel("Snake", 60);

        VBox titleBox = new VBox(title);
        titleBox.setAlignment(Pos.TOP_CENTER);
        titleBox.setPadding(new Insets(40, 0, 0, 0));

        Button startBtn = buildMenuButton("START", 200);
        Button settingsBtn = buildMenuButton("SETTINGS", 200);
        Button quitBtn = buildMenuButton("QUIT", 200);

        startBtn.setOnAction(e -> stage.setScene(buildGameScene(stage)));
        settingsBtn.setOnAction(e -> stage.setScene(buildSettingsScene(stage)));
        quitBtn.setOnAction(e -> stage.setScene(buildEndScene(stage)));

        VBox menuBox = new VBox(15, startBtn, settingsBtn, quitBtn);
        menuBox.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(titleBox, menuBox);
        root.setStyle("-fx-background-color: black;");

        return new Scene(root, 500, 500);
    }

    private Scene buildSettingsScene(Stage stage){
        Label title = buildLabel("Settings", 60);
        title.setPadding(new Insets(40, 0, 40, 0));

        VBox rowsBox = new VBox(15,
                buildSettingsRow("UP:",    upKey.toString(),    key -> upKey = key),
                buildSettingsRow("DOWN:",  downKey.toString(),  key -> downKey = key),
                buildSettingsRow("RIGHT:", rightKey.toString(), key -> rightKey = key),
                buildSettingsRow("LEFT:",  leftKey.toString(),  key -> leftKey = key)
        );
        rowsBox.setAlignment(Pos.CENTER);

        Button returnBtn = buildMenuButton("RETURN", 145);
        returnBtn.setOnAction(e -> stage.setScene(buildStartScene(stage)));
        HBox returnBox = new HBox(returnBtn);
        returnBox.setPadding(new Insets(0, 0, 20, 20));

        javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
        VBox.setVgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        VBox root = new VBox(title, rowsBox, spacer, returnBox);
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: black;");

        Scene settingsScene = new Scene(root, 500, 500);
        return settingsScene;
    }


    private Scene buildGameScene(Stage stage){
        int CELL_SIZE = 25;
        int GRID_SIZE = 20;
        int WINDOW_SIZE = 500;

        Canvas canvas = new Canvas(500, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Snake snake = new Snake(10, 10);
        Position food = new Position(5, 5);
        GameBoard gameBoard = new GameBoard(snake, food);
        Direction[] currentDirection = {Direction.RIGHT};
        Scene gameScene = new Scene(new Pane(), WINDOW_SIZE, WINDOW_SIZE);

        gameScene.setOnKeyPressed(e -> {
            if (e.getCode() == upKey)    currentDirection[0] = Direction.UP;
            if (e.getCode() == downKey)  currentDirection[0] = Direction.DOWN;
            if (e.getCode() == rightKey) currentDirection[0] = Direction.RIGHT;
            if (e.getCode() == leftKey)  currentDirection[0] = Direction.LEFT;
        });

        AnimationTimer[] timer = {null};
        timer[0] = new AnimationTimer(){
            long lastUpdate = 0;

            @Override
            public void handle(long l) {
                if (gameBoard.isGameOver()) {
                    timer[0].stop();
                    stage.setScene(buildEndScene(stage));
                }
                if (l - lastUpdate < 150_000_000) return;

                gameBoard.update(currentDirection[0]);
                lastUpdate = l;
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);

                gc.setFill(Color.RED);
                Position food = gameBoard.getFood();
                gc.fillRect(food.getX() * CELL_SIZE, food.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                gc.setFill(Color.GREEN);
                for (Position segment : gameBoard.getSnakeBody()) {
                    gc.fillRect(segment.getX() * CELL_SIZE, segment.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        };

        Pane root = new Pane();
        root.getChildren().add(canvas);
        timer[0].start();

        gameScene.setRoot(root);
        return gameScene;
    }

    private Scene buildEndScene(Stage stage){
        Label title = buildLabel("GAME OVER", 60);

        Button restartBtn = buildMenuButton("RESTART", 200);
        Button quitBtn = buildMenuButton("QUIT", 200);

        restartBtn.setOnAction(e -> stage.setScene(buildGameScene(stage)));
        quitBtn.setOnAction(e -> stage.close());

        VBox titleBox = new VBox(title);
        titleBox.setAlignment(Pos.TOP_CENTER);
        titleBox.setPadding(new Insets(40, 0, 0, 0));

        VBox buttonsBox = new VBox (15, restartBtn, quitBtn);
        buttonsBox.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(titleBox, buttonsBox);

        root.setStyle("-fx-background-color: black;");

        return new Scene(root, 500, 500);
    }

    private Button buildMenuButton(String text, int width){
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: #00ff00; -fx-text-fill: black; -fx-font-size: 18px; -fx-font-family: 'Courier New'; -fx-padding: 12 40 12 40; -fx-cursor: hand;");
        btn.setPrefWidth(width);
        return btn;
    }

    private Label buildLabel(String text, int size){
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: #00ff00; -fx-font-size: " + size + "; -fx-font-family: 'Courier New'");
        return label;
    }

    private HBox buildSettingsRow(String labelText, String btnText, Consumer<KeyCode> saveKey){
        Label label = buildLabel(labelText, 18);
        label.setPrefWidth(100);
        Button btn = buildMenuButton(btnText, 135);
        btn.setOnAction(e -> {
            btn.setText("...");
            btn.getScene().setOnKeyPressed(k -> {
                saveKey.accept(k.getCode());
                btn.setText(k.getCode().toString());
                btn.getScene().setOnKeyPressed(null);
            });
        });
        HBox row = new HBox(20, label, btn);
        row.setAlignment(Pos.CENTER);
        return row;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
