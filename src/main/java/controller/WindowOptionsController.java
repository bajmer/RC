package controller;

import engine.GameEngine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import model.enums.ProfessionType;
import model.enums.SexType;
import model.options.ChoosedCharacter;
import model.options.Options;

import java.util.ArrayList;
import java.util.Arrays;

public class WindowOptionsController {

    @FXML
    Pane rootPane;

    @FXML
    Button button;

    @FXML
    void akcja(ActionEvent event) {

        //MOCK
        Options gameOptions = new Options(
                1,
                new ArrayList<>(Arrays.asList(
                        new ChoosedCharacter(ProfessionType.CARPENTER, SexType.MAN),
                        new ChoosedCharacter(ProfessionType.EXPLORER, SexType.WOMAN),
                        new ChoosedCharacter(ProfessionType.SOLDIER, SexType.MAN),
                        new ChoosedCharacter(ProfessionType.COOK, SexType.WOMAN))),
                true,
                true,
                2);

        GameEngine gameEngine = new GameEngine(gameOptions);
    }
}
