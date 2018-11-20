package model.character.main;

import model.character.Character;
import model.enums.ProfessionType;
import model.enums.SexType;
import model.enums.SpecialSkillType;

import java.util.List;

public class MainCharacter implements Character {
    private ProfessionType profession;
    private SexType sex;
    //	private InventionType personalInvention;
    private List<SpecialSkillType> specialSkills;
    //	private List<Integer> moraleDown;
    private int lives;
    private int determination;
//	private boolean firstPlayer;
//	private boolean starving;


    public MainCharacter(ProfessionType profession, SexType sex, List<SpecialSkillType> specialSkills, int lives) {
        this.profession = profession;
        this.sex = sex;
        this.specialSkills = specialSkills;
        this.lives = lives;
        this.determination = 0;
    }

    public ProfessionType getProfession() {
        return profession;
    }

    public void setProfession(ProfessionType profession) {
        this.profession = profession;
    }

    public SexType getSex() {
        return sex;
    }

    public void setSex(SexType sex) {
        this.sex = sex;
    }

    public List<SpecialSkillType> getSpecialSkills() {
        return specialSkills;
    }

    public void setSpecialSkills(List<SpecialSkillType> specialSkills) {
        this.specialSkills = specialSkills;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getDetermination() {
        return determination;
    }

    public void setDetermination(int determination) {
        this.determination = determination;
    }
}
