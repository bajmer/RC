package model.character.main;

import model.character.Character;
import model.data.GlobalData;
import model.enums.ProfessionType;
import model.enums.SexType;
import model.enums.SpecialSkillType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MainCharacter implements Character {
    private Logger logger = LogManager.getLogger(MainCharacter.class);

    private ProfessionType profession;
    private SexType sex;
    //	private IdeaType personalInvention;
    private List<SpecialSkillType> specialSkills;
    private List<Integer> moraleDown;
    private int lives;
    private int beginLives;
    private int determination;
//	private boolean firstPlayer;
//	private boolean starving;
private GlobalData globalData;


    public MainCharacter(ProfessionType profession, SexType sex, List<SpecialSkillType> specialSkills, List<Integer> moraleDown, int lives) {
        this.profession = profession;
        this.sex = sex;
        this.specialSkills = specialSkills;
        this.moraleDown = moraleDown;
        this.lives = lives;
        this.beginLives = lives;
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

    public List<Integer> getMoraleDown() {
        return moraleDown;
    }

    public void setMoraleDown(List<Integer> moraleDown) {
        this.moraleDown = moraleDown;
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

    public GlobalData getGlobalData() {
        return globalData;
    }

    public void setGlobalData(GlobalData globalData) {
        this.globalData = globalData;
    }

    @Override
    public void changeLivesLevel(int value) {
        if (value < 0) {
            for (int i = lives; i >= lives + value; i--) {
                if (moraleDown.contains(i)) {
                    globalData.changeMoraleLevel(-1);
                }
            }
        }
        lives += value;
        if (lives > beginLives) {
            lives = beginLives;
        } else if (lives <= 0) {
            lives = 0;
        }
        logger.debug("The number of " + profession + " lives has been changed to: " + lives);

        if (lives <= 0) {
            // TODO: 2018-11-22 Handle signal GameOver
        }
    }

    @Override
    public void changeDeterminationLevel(int value) {
        determination += value;
        if (determination < 0) {
            changeLivesLevel(determination);
            determination = 0;
        }
        logger.debug("The number of " + profession + " determinations has been changed to: " + determination);
    }
}
