package controller;

import engine.GameEngine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import model.enums.ProfessionType;
import model.enums.SexType;
import model.options.ChosenCharacter;
import model.options.Options;

import java.util.Arrays;

public class OptionsWindowController {

    @FXML
    Pane rootPane;

    @FXML
    Button button;

    @FXML
    void akcja(ActionEvent event) {

        //MOCK
        Options gameOptions = new Options(
                1,
                Arrays.asList(
                        new ChosenCharacter(ProfessionType.CARPENTER, SexType.MAN),
                        new ChosenCharacter(ProfessionType.EXPLORER, SexType.WOMAN),
                        new ChosenCharacter(ProfessionType.SOLDIER, SexType.MAN),
                        new ChosenCharacter(ProfessionType.COOK, SexType.WOMAN)),
                true,
                true,
                2);

        GameEngine gameEngine = new GameEngine(gameOptions);
        gameEngine.play();
    }
}
