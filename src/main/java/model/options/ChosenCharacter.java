package model.options;

import model.enums.ProfessionType;
import model.enums.SexType;

public class ChosenCharacter {

    private ProfessionType profession;
    private SexType sex;

    public ChosenCharacter(ProfessionType profession, SexType sex) {
        this.profession = profession;
        this.sex = sex;
    }

    public ProfessionType getProfession() {
        return profession;
    }

    public SexType getSex() {
        return sex;
    }
}
