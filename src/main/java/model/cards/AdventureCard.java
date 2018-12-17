package model.cards;

import engine.MethodRepository;
import model.data.GlobalData;
import model.enums.ActionType;
import model.enums.cards.AdventureType;
import model.enums.cards.event.EventType;
import model.enums.elements.TokenType;

public class AdventureCard implements Card {
    private AdventureType adventure;
    private EventType event;

    public AdventureCard(AdventureType adventure, EventType event) {
        this.adventure = adventure;
        this.event = event;
    }

    public AdventureType getAdventure() {
        return adventure;
    }

    public void setAdventure(AdventureType adventure) {
        this.adventure = adventure;
    }

    public EventType getEvent() {
        return event;
    }

    public void setEvent(EventType event) {
        this.event = event;
    }

    public void handleAdventure() {
        switch (adventure) {
            case BUILD_WYCZERPANY:
                break;
            case BUILD_WYCIE_W_BUSZU:
                break;
            case BUILD_ZDEMOTYWOWANY:
                break;
            case BUILD_NIEBEZPIECZNA_PRACA:
                break;
            case BUILD_WICHURA:
                break;
            case BUILD_WYGLODNIALY_DRAPIEZNIK:
                break;
            case BUILD_UZADLENIE:
                break;
            case BUILD_ZALAMANIE:
                break;
            case BUILD_W_POSPIECHU:
                break;
            case BUILD_PEKNIETE_NARZEDZIE:
                break;
            case BUILD_ZACHMURZONE_NIEBO:
                break;
            case BUILD_MARNA_KONSTRUKCJA:
                break;
            case BUILD_KONSTRUKCJA:
                break;
            case BUILD_STRACH_PRZED_BESTIAMI:
                break;
            case BUILD_PASKUDNA_RANA:
                break;
            case BUILD_POSPIECH:
                break;
            case BUILD_MALPY_CIE_OBSERWUJA:
                break;
            case BUILD_OSZCZEDNOSCI:
                break;
            case BUILD_WYPADEK:
                break;
            case BUILD_BRAK_NADZIEI:
                break;
            case BUILD_KONTROLA_NARZEDZI:
                break;
            case BUILD_ZLAMANA_DZWIGNIA:
                break;
            case BUILD_POTRZEBA_ZMIAN:
                break;
            case BUILD_REALNA_OCENA_SYTUACJI:
                break;
            case BUILD_BESTIA_W_OBOZIE:
                break;
            case BUILD_MOZOLNA_PRACA_1:
                break;
            case BUILD_MOZOLNA_PRACA_2:
                break;
            case BUILD_SKALECZENIE:
                break;
            case BUILD_CIEZKA_PRACA:
                break;
            case BUILD_WIZYTA_BESTII:
                break;
            case RES_KONIEC_ZRODLA_1:
                break;
            case RES_KONIEC_ZRODLA_2:
                break;
            case RES_KONIEC_ZRODLA_3:
                break;
            case RES_KONIEC_ZRODLA_4:
                break;
            case RES_KONIEC_ZRODLA_5:
                break;
            case RES_SZKIELET:
                break;
            case RES_SLADY_DRAPIEZNIKA:
                break;
            case RES_SCIEZKA_DRAPIEZNIKA:
                break;
            case RES_NADCHODZI_ZIMA:
                break;
            case RES_SKRECONA_KOSTKA:
                break;
            case RES_PAJAK:
                break;
            case RES_PO_HURAGANIE:
                break;
            case RES_ZIMOWY_CHLOD:
                break;
            case RES_KONCOWKA:
                break;
            case RES_SKRZYNIA_PIRATOW:
                break;
            case RES_OKAZJA:
                break;
            case RES_NIESPODZIEWANE_ZNALEZISKO:
                break;
            case RES_PISKLAKI:
                break;
            case RES_NIESPODZIEWANE_TRUDNOSCI:
                break;
            case RES_NIEBEZPIECZNE_SLADY:
                break;
            case RES_NIEWIARYGODNY_WYSILEK:
                break;
            case RES_ZLOTA_MONETA:
                break;
            case RES_OCZY_W_CIEMNOSCI:
                break;
            case RES_ZALAMANIE_POGODY:
                break;
            case RES_NOWE_STADO:
                break;
            case RES_SKORY:
                break;
            case RES_ZMIJA:
                break;
            case RES_OWOCE:
                break;
            case RES_ZNALEZISKO_W_KRZAKACH:
                break;
            case RES_GRZYBY:
                break;
            case EXP_SLADY_OGNIA_1:
                break;
            case EXP_SLADY_OGNIA_2:
                break;
            case EXP_ZIMNY_WIATR:
                break;
            case EXP_NADCIAGA_SZTORM:
                break;
            case EXP_BAGNISKA:
                break;
            case EXP_BAMBUS:
                break;
            case EXP_COS_SIE_SZYKUJE:
                break;
            case EXP_GRYPA:
                break;
            case EXP_POMYLONE_SCIEZKI:
                break;
            case EXP_DZIKI_PIES:
                break;
            case EXP_ZAGUBIONY_W_GESTWINIE:
                break;
            case EXP_PUSTY_LAS:
                break;
            case EXP_NIESZCZESLIWY_WYPADEK:
                break;
            case EXP_NIEBO_ZACIAGA_SIE:
                break;
            case EXP_POZOSTALOSCI_OSADY:
                break;
            case EXP_DZIKIE_JAGODY:
                break;
            case EXP_ZMIJE:
                break;
            case EXP_NIEBEZPIECZNY_TEREN:
                break;
            case EXP_ZAGUBIONY:
                break;
            case EXP_TYGRYS:
                break;
            case EXP_KAPLICZKA:
                break;
            case EXP_ZAGUBIONY_W_LESIE:
                break;
            case EXP_PUMA:
                break;
            case EXP_PADLINA:
                break;
            case EXP_KOLCZASTY_KRZEW:
                break;
            case EXP_TAJEMNICZA_JASKINIA:
                break;
            case EXP_STARY_GROB:
                break;
            case EXP_STARA_CHATA:
                break;
            case EXP_ZASKAKUJACE_ZNALEZISKO:
                break;
            case EXP_ZNISZCZONA_CHATKA:
                break;
        }
    }

    public void handleEvent() {
        switch (event) {
            case BUILD_SPOR:
                MethodRepository.changeMoraleLevel(-1);
                break;
            case BUILD_BESTIA_JEST_TUTAJ:
                break;
            case BUILD_NARZEDZIA_SIE_PSUJA:
                MethodRepository.decreaseDeterminationOfAllMainCharacters(-1);
                break;
            case BUILD_NATURALNA_PALISADA:
                if (GlobalData.isShelter() || GlobalData.getCamp().isNaturalShelter()) {
                    MethodRepository.changePalisadeLevel(1);
                }
                break;
            case BUILD_REWIZYTA:
                break;
            case BUILD_DRESZCZE:
                break;
            case BUILD_DOBRZE_IDZIE:
                MethodRepository.changeDeterminationLevel(2, GlobalData.getFirstPlayer());
                break;
            case BUILD_TRZASK:
                MethodRepository.changePalisadeLevel(-1);
                break;
            case BUILD_KONIEC_ULEWY:
                if (GlobalData.isShelter() || GlobalData.getCamp().isNaturalShelter()) {
                    MethodRepository.changePalisadeLevel(1);
                }
                break;
            case BUILD_TRACH:
                MethodRepository.changeRoofLevel(-(int) Math.floor(GlobalData.getLevels().getRoofLevel() / 2));
                break;
            case BUILD_MOCNIEJSZA_KONSTRUKCJA:
                if (GlobalData.isShelter() || GlobalData.getCamp().isNaturalShelter()) {
                    MethodRepository.changePalisadeLevel(1);
                }
                break;
            case BUILD_KOSZTOWNA_OCHRONA:
                break;
            case BUILD_ZAKAZENIE:
                break;
            case BUILD_CO_NAGLE_TO_PO_DIABLE:
                MethodRepository.changeRoofOrPalisadeLevelByValue(-1);
                break;
            case BUILD_MALPY_W_OBOZIE:
                MethodRepository.decreaseRoofOrPalisadeLevelByHalf();
                break;
            case BUILD_LICHA_KONSTRUKCJA:
                MethodRepository.changeMoraleLevel(-1);
                break;
            case BUILD_GANGRENA:
                break;
            case BUILD_ZEPSUTE_NARZEDZIA:
                break;
            case BUILD_ROZBUDOWA_OBOZOWISKA:
                break;
            case BUILD_BRAK_POMYSLOW:
                MethodRepository.changeMoraleLevel(-1);
                break;
            case BUILD_NOSIL_WILK_RAZY_KILKA:
                MethodRepository.changeFoodLevel(2);
                MethodRepository.changeFurLevel(1);
                break;
            case BUILD_BOL_GLOWY:
                break;
            case BUILD_NOCNA_WIZYTA:
                MethodRepository.putAnimalsDiceOnWeatherField();
                break;
            case RES_WSPOMNIENIA_MARTWEGO_ODKRYWCY:
                MethodRepository.changeMoraleLevel(-1);
                break;
            case RES_ATAK_BESTII:
                MethodRepository.putAnimalsDiceOnWeatherField();
                break;
            case RES_PRZYMROZEK:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.SNOW_CLOUD_TOKEN);
                break;
            case RES_SPUCHNIETA_KOSTKA:
                break;
            case RES_UGRYZIENIE_W_KARK:
                break;
            case RES_KOLEJNY_HURAGAN:
                break;
            case RES_KLATWA:
                break;
            case RES_ZAPADNIETY_DACH:
                MethodRepository.changeRoofLevel(-(int) Math.floor(GlobalData.getLevels().getRoofLevel() / 2));
                break;
            case RES_WSCIEKLE_PTASZYSKO:
                MethodRepository.changeRoofLevel(-1);
                break;
            case RES_ATAK_WYGLODNIALEGO_DRAPIEZNIKA:
                break;
            case RES_OBOLALE_RAMIONA:
                break;
            case RES_PRZEKLETA_MONETA:
                MethodRepository.putTokenOnActionField(ActionType.BUILDING_ACTION, TokenType.ROLL_AGAIN_IF_SUCCESS_TOKEN);
                MethodRepository.putTokenOnActionField(ActionType.RESOURCES_ACTION, TokenType.ROLL_AGAIN_IF_SUCCESS_TOKEN);
                MethodRepository.putTokenOnActionField(ActionType.EXPLORATION_ACTION, TokenType.ROLL_AGAIN_IF_SUCCESS_TOKEN);
                break;
            case RES_NIESPODZIEWANA_WIZYTA:
                MethodRepository.putAnimalsDiceOnWeatherField();
                break;
            case RES_SZTORM:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.STORM_TOKEN);
                break;
            case RES_WSZYSTKO_PRZEPADLO:
                break;
            case RES_INSEKTY:
                break;
            case RES_UKASZENIE:
                break;
            case RES_BOL_BRZUCHA:
                break;
            case RES_WSPOMNIENIA:
                MethodRepository.changeMoraleLevel(-1);
                break;
            case RES_ROZWOLNIENIE:
                break;
            case EXP_SNIEG:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.SNOW_CLOUD_TOKEN);
                break;
            case EXP_SZTORM:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.STORM_TOKEN);
                break;
            case EXP_TRZASK_LAMANEGO_DREWNA:
                MethodRepository.changeRoofOrPalisadeLevelByValue(-1);
                break;
            case EXP_PRZEKLETA_WYSPA:
                MethodRepository.putTokenOnActionField(ActionType.EXPLORATION_ACTION, TokenType.ADVENTURE_TOKEN);
                break;
            case EXP_BOL_GARDLA:
                break;
            case EXP_STARY_ZNAJOMY:
                break;
            case EXP_GLODNE_DRAPIEZNIKI:
                MethodRepository.putTokenOnActionField(ActionType.HUNTING_ACTION, TokenType.BEAST_STRENGTH_PLUS_ONE_TOKEN);
                break;
            case EXP_SPUCHNIETA_KOSTKA:
                break;
            case EXP_OBERWANIE_CHMURY:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.RAIN_CLOUD_TOKEN);
                break;
            case EXP_EPIDEMIA:
                MethodRepository.decreaseLivesOfAllMainCharacters(-1);
                break;
            case EXP_NIESTRAWNOSC:
                break;
            case EXP_GORACZKA:
                break;
            case EXP_TYGRYS_WAS_ODNAJDUJE:
                break;
            case EXP_KOSZMARY:
                MethodRepository.putTokenOnActionField(ActionType.BUILDING_ACTION, TokenType.ROLL_AGAIN_IF_SUCCESS_TOKEN);
                MethodRepository.putTokenOnActionField(ActionType.RESOURCES_ACTION, TokenType.ROLL_AGAIN_IF_SUCCESS_TOKEN);
                MethodRepository.putTokenOnActionField(ActionType.EXPLORATION_ACTION, TokenType.ROLL_AGAIN_IF_SUCCESS_TOKEN);
                break;
            case EXP_ATAK_PUMY:
                break;
            case EXP_BIEGUNKA:
                MethodRepository.decreaseLivesOfAllMainCharacters(-1);
                break;
            case EXP_SPUCHNIETE_RAMIE:
                break;
            case EXP_PRZEBUDZENIE_BESTII:
                break;
            case EXP_WSPOMNIENIA_O_MARTWYM_ROZBITKU:
                MethodRepository.changeDeterminationLevel(-2, GlobalData.getFirstPlayer());
                break;
            case EXP_DUCH_ROZBITKA:
                MethodRepository.decreaseDeterminationOfAllMainCharacters(-1);
                break;
            case EXP_NIESPOKOJNE_SNY:
                MethodRepository.changeMoraleLevel(-1);
                break;
            case NONE:
                break;

        }
    }
}
