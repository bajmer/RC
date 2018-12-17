package model.character;

import model.elements.Marker;
import model.enums.ProfessionType;
import model.enums.SexType;
import model.enums.SpecialSkillType;
import model.enums.elements.MarkerType;

import java.util.List;

public class MainCharacter extends Character {
    private ProfessionType profession;
    private SexType sex;
    private List<SpecialSkillType> specialSkills;
    private List<Integer> moraleDown;
    private boolean useOfSpecialSkills;

    public MainCharacter(ProfessionType profession, SexType sex, List<SpecialSkillType> specialSkills, List<Integer> moraleDown, int lives) {
        super(lives);
        for (int i = 0; i < 2; i++) {
            super.getMarkers().add(new Marker(MarkerType.valueOf(profession.toString() + "_MARKER")));
        }
        this.profession = profession;
        this.sex = sex;
        this.specialSkills = specialSkills;
        this.moraleDown = moraleDown;
        this.useOfSpecialSkills = true;
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

    public boolean canUseOfSpecialSkills() {
        return useOfSpecialSkills;
    }

    public void setUseOfSpecialSkills(boolean useOfSpecialSkills) {
        this.useOfSpecialSkills = useOfSpecialSkills;
    }
}
