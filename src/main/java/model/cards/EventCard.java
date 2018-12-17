package model.cards;

import controller.GameWindowController;
import engine.MethodRepository;
import engine.action.Action;
import engine.action.CampOrderingAction;
import engine.phase.ActionPhase;
import engine.phase.NightPhase;
import engine.phase.ProductionPhase;
import model.character.Character;
import model.data.GlobalData;
import model.elements.tiles.IslandTile;
import model.enums.ActionType;
import model.enums.TerrainType;
import model.enums.cards.IdeaType;
import model.enums.cards.event.EventType;
import model.enums.cards.event.IconType;
import model.enums.cards.event.ThreatType;
import model.enums.elements.ResourceType;
import model.enums.elements.TokenType;
import model.requirements.Requirements;

import java.util.*;

public class EventCard implements Card {
    private static boolean decreasedRoof = false;
    private static boolean decreasedPalisade = false;
    private static IslandTile islandTileAfterHurricane = null;
    private static IslandTile islandTileAfterEarthquake = null;
    private static IslandTile islandTileWithAbyss = null;
    private EventType event;
    private IconType icon;
    private ThreatType threat;

    public EventCard(EventType event, IconType icon, ThreatType threat) {
        this.event = event;
        this.icon = icon;
        this.threat = threat;
    }

    public EventType getEvent() {
        return event;
    }

    public void setEvent(EventType event) {
        this.event = event;
    }

    public IconType getIcon() {
        return icon;
    }

    public void setIcon(IconType icon) {
        this.icon = icon;
    }

    public ThreatType getThreat() {
        return threat;
    }

    public void setThreat(ThreatType threat) {
        this.threat = threat;
    }

    public void handleEvent() {
        switch (event) {
            case EVENT_SKRZYNIE_Z_JEDZENIEM:
                break;
            case EVENT_KUFER_KAPITANA:
                break;
            case EVENT_RESZTKI_SZALUPY:
                break;
            case EVENT_TRUCIZNA:
                MethodRepository.decreaseLivesOfAllMainCharacters(-1);
                break;
            case EVENT_CIALO_NA_PLAZY:
                MethodRepository.changeMoraleLevel(-1);
                break;
            case EVENT_SUSZA:
                MethodRepository.coverUncoverTerrainTypeIfPossible(TerrainType.RIVER, true);
                break;
            case EVENT_UTRATA_NADZIEI:
                Action.setAcceptedMarkersChangedForFirstPlayer(true);
                break;
            case EVENT_SPUSTOSZENIA_PO_HURAGANIE:
                List<Integer> neighbourPositions = GlobalData.getBoard().getTilesPositionsIdInNeighbourhood().get(GlobalData.getCamp().getTileID());
                List<IslandTile> possibleIslandTiles = new ArrayList<>();
                GlobalData.getDiscoveredIslandTiles().stream()
                        .filter(islandTile -> neighbourPositions.contains(islandTile.getTileID()))
                        .forEach(possibleIslandTiles::add);
                if (!possibleIslandTiles.isEmpty()) {
                    IslandTile chosenIslandTile = GameWindowController.chooseIslandTile(possibleIslandTiles);
                    chosenIslandTile.setEndOfLeftSource(true);
                    chosenIslandTile.setEndOfRightSource(true);
                    islandTileAfterHurricane = chosenIslandTile;
                }
                break;
            case EVENT_WYCZERPUJACA_NOC:
                MethodRepository.putTokenOnActionField(ActionType.BUILDING_ACTION, TokenType.ROLL_AGAIN_IF_SUCCESS_TOKEN);
                break;
            case EVENT_WYCIE_OD_STRONY_LASU:
                Optional<BeastCard> optional = GlobalData.getDecks().getBeastCardsDeck().stream().limit(3).max(Comparator.comparing(BeastCard::getStrength));
                if (optional.isPresent()) {
                    BeastCard beastCard = optional.get();
                    GlobalData.getDecks().getBeastCardsDeck().remove(beastCard); // TODO: Podgląd 3 kart?
                    GlobalData.getAvailableBeastCards().addFirst(beastCard);
                }
                break;
            case EVENT_ZNOJNA_PRACA:
                MethodRepository.putTokenOnActionField(ActionType.BUILDING_ACTION, TokenType.ONE_MORE_WOOD_TOKEN);
                break;
            case EVENT_ZEW_NATURY:
                MethodRepository.putTokenOnActionField(ActionType.HUNTING_ACTION, TokenType.BEAST_STRENGTH_PLUS_ONE_TOKEN);
                break;
            case EVENT_OSLABIENIE:
                GlobalData.getFirstPlayer().setUseOfSpecialSkills(false);
                break;
            case EVENT_WYSPA_BUNTUJE_SIE:
                MethodRepository.handleBookIcon();
                break;
            case EVENT_SILNY_WIATR:
                MethodRepository.putTokenOnActionField(ActionType.BUILDING_ACTION, TokenType.ONE_MORE_CHARACTER_MARKER_TOKEN);
                break;
            case EVENT_NISZCZYCIELSKIE_PODMUCHY:
                MethodRepository.coverSourcesOnNeighboringIslandTiles(2);
                break;
            case EVENT_BRAK_WIARY:
                GlobalData.setFirstPlayerModifier(GlobalData.getFirstPlayerModifier() + 1);
                MethodRepository.nextFirstPlayer();
                break;
            case EVENT_BEZSENNA_NOC:
                MethodRepository.putTokenOnActionField(ActionType.BUILDING_ACTION, TokenType.ROLL_AGAIN_IF_SUCCESS_TOKEN);
                MethodRepository.putTokenOnActionField(ActionType.RESOURCES_ACTION, TokenType.ROLL_AGAIN_IF_SUCCESS_TOKEN);
                MethodRepository.putTokenOnActionField(ActionType.EXPLORATION_ACTION, TokenType.ROLL_AGAIN_IF_SUCCESS_TOKEN);
            case EVENT_WYDRY:
                MethodRepository.coverOrUncoverSourceClosestToTheCamp(Collections.singletonList(ResourceType.FOOD_FISH), true);
                break;
            case EVENT_WALKA_W_CIEMNOSCIACH:
                if (!GlobalData.getAvailableBeastCards().isEmpty()) {
                    GlobalData.getAvailableBeastCards().removeFirst();
                }
                break;
            case EVENT_KLOTNIA:
                ActionPhase.setNoActionCanBePerformedByTwoDifferentMarkers(true);
                break;
            case EVENT_PEKA_NATURALNA_TAMA:
                MethodRepository.addOneResourceWhileProductionPhase("WOOD");
                MethodRepository.blockSourceWhileProductionPhase("FOOD");
                MethodRepository.putTokenOnActionField(ActionType.EXPLORATION_ACTION, TokenType.ADVENTURE_TOKEN);
                break;
            case EVENT_BALAGAN_W_OBOZIE:
                MethodRepository.putTokenOnActionField(ActionType.CAMP_ACTION, TokenType.ONE_MORE_CHARACTER_MARKER_TOKEN);
                break;
            case EVENT_ZIMOWA_DEPRESJA:
                MethodRepository.changeMoraleLevel(-1);
                break;
            case EVENT_WYGLODNIALE_DRAPIEZNIKI:
                MethodRepository.putTokenOnActionField(ActionType.HUNTING_ACTION, TokenType.BEAST_STRENGTH_PLUS_ONE_TOKEN);
                break;
            case EVENT_NOCNE_WYCIE_Z_DZUNGLI:
                MethodRepository.putTokenOnActionField(ActionType.HUNTING_ACTION, TokenType.BEAST_STRENGTH_PLUS_ONE_TOKEN);
                break;
            case EVENT_POWALONE_DRZEWA:
                MethodRepository.putTokenOnActionField(ActionType.EXPLORATION_ACTION, TokenType.ROLL_AGAIN_IF_SUCCESS_TOKEN);
                break;
            case EVENT_NIEBEZPIECZNA_NOC:
                // TODO: 2018-12-14 sprawdzic
                break;
            case EVENT_NARADA:
                MethodRepository.discardIdeas(3);
                break;
            case EVENT_ROZTRZASKANY_BALON:
                MethodRepository.changeMoraleLevel(-1);
                break;
            case EVENT_DRAPIEZNIK_W_OKOLICY:
                MethodRepository.coverOrUncoverSourceClosestToTheCamp(Collections.singletonList(ResourceType.FOOD_BIRD), true);
                break;
            case EVENT_INSEKTY:
                if (GlobalData.getAvailableResources().getWoodAmount() > 0) {
                    MethodRepository.changeWoodLevel(-1);
                }
                break;
            case EVENT_MGLA:
                MethodRepository.putTokenOnActionField(ActionType.EXPLORATION_ACTION, TokenType.ONE_MORE_CHARACTER_MARKER_TOKEN);
                break;
            case EVENT_NIEDZWIEDZ:
                CampOrderingAction.setBlackFlag(true);
                break;
            case EVENT_DEPRESJA:
                MethodRepository.discardIdeas(2);
                MethodRepository.decreaseDeterminationOfAllMainCharacters(-1);
                break;
            case EVENT_ZALAMANIE_POGODY:
                MethodRepository.putTokenOnActionField(ActionType.RESOURCES_ACTION, TokenType.ONE_MORE_CHARACTER_MARKER_TOKEN);
                break;
            case EVENT_NIEZWYKLE_ZIMNA_NOC:
                MethodRepository.changeWoodLevel(-2);
                break;
            case EVENT_TRZESIENIE_ZIEMI:
                islandTileAfterEarthquake = MethodRepository.upsideDownNeighboringIslandTileIfPossible();
                break;
            case EVENT_DYM_NA_HORYZONCIE:
                MethodRepository.coverUncoverTerrainTypeIfPossible(TerrainType.PLAINS, true);
                break;
            case EVENT_ZLE_PRZECZUCIA:
                MethodRepository.putTokenOnActionField(ActionType.RESOURCES_ACTION, TokenType.ROLL_AGAIN_IF_SUCCESS_TOKEN);
                break;
            case EVENT_ZAWROTY_GLOWY:
                ActionPhase.setFirstPlayerCanUseOnlyOneMarker(true);
                break;
            case EVENT_WYBOISTE_PRZEJSCIE:
                MethodRepository.coverUncoverTerrainTypeIfPossible(TerrainType.HILLS, true);
                break;
            case EVENT_WSPOMNIENIA_Z_REJSU:
                MethodRepository.changeMoraleLevel(-1);
                break;
            case EVENT_OSUWISKO:
                MethodRepository.moveCampToNeighboringIslandTile();
                break;
            case EVENT_DESZCZOWE_CHMURY:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.RAIN_CLOUD_TOKEN);
                break;
            case EVENT_LATAJACA_NIESPODZIANKA:
                MethodRepository.changeRoofOrPalisadeLevelByValue(-1);
                break;
            case EVENT_JAGUAR:
                MethodRepository.decreaseLivesOfAllMainCharacters(-1);
                break;
            case EVENT_POWALONE_DRZEWO:
                MethodRepository.decreaseLivesOfAllMainCharacters(-1);
                break;
            case EVENT_BOJKA:
                MethodRepository.decreaseLivesOfAllMainCharacters(-1);
                break;
            case EVENT_ULEWA:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.RAIN_CLOUD_TOKEN);
                break;
            case EVENT_KATAKLIZM:
                GlobalData.setUnavailableInventions(true);
                break;
            case EVENT_ZLY_LOS:
                GlobalData.getThreatActionCards().forEach(EventCard::handleThreatEvent);
                GlobalData.getThreatActionCards().clear();
                break;
            case EVENT_DESZCZ:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.RAIN_CLOUD_TOKEN);
                break;
            case EVENT_BURZA_Z_PIORUNAMI:
                MethodRepository.coverOrUncoverSourceClosestToTheCamp(Collections.singletonList(ResourceType.WOOD), true);
                break;
            case EVENT_ROZSZALALA_RZEKA:
                ProductionPhase.setHalfProduction(true);
                break;
            case EVENT_SZTORM_1:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.STORM_TOKEN);
                break;
            case EVENT_SZTORM_2:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.STORM_TOKEN);
                break;
            case EVENT_RWACA_RZEKA:
                MethodRepository.putTokenOnActionField(ActionType.EXPLORATION_ACTION, TokenType.ADVENTURE_TOKEN);
                break;
            case EVENT_NISZCZYCIELSKI_HURAGAN:
                MethodRepository.changeRoofOrPalisadeLevelByValue(-1);
                break;
            case EVENT_PRZETRZEBIONA_OKOLICA:
                ProductionPhase.setNoFood(true);
                break;
            case EVENT_MROZ:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.SNOW_CLOUD_TOKEN);
                break;
            case EVENT_NADCHODZI_ULEWA:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.RAIN_CLOUD_TOKEN);
                break;
            case EVENT_OBERWANIE_CHMURY:
                int roof = GlobalData.getLevels().getRoofLevel();
                int palisade = GlobalData.getLevels().getPalisadeLevel();
                MethodRepository.moveCampToNeighboringIslandTile();
                decreasedRoof = GlobalData.getLevels().getRoofLevel() < roof;
                decreasedPalisade = GlobalData.getLevels().getPalisadeLevel() < palisade;
                break;
            case EVENT_KATASTROFA:
                MethodRepository.changeRoofOrPalisadeLevelByValue(-1);
                break;
            case EVENT_DRAPIEZNIKI_W_POBLIZU:
                MethodRepository.putTokenOnActionField(ActionType.EXPLORATION_ACTION, TokenType.BEAST_STRENGTH_PLUS_ONE_TOKEN);
                break;
            case EVENT_POZAR:
                ProductionPhase.setNoWood(true);
                break;
            case EVENT_OKROPNA_POGODA:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.RAIN_CLOUD_TOKEN);
                break;
            case EVENT_PRZENIKLIWIE_ZIMNY_DESZCZ_1:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.RAIN_CLOUD_TOKEN);
                break;
            case EVENT_PRZENIKLIWIE_ZIMNY_DESZCZ_2:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.RAIN_CLOUD_TOKEN);
                break;
            case EVENT_PRZEPASC:
                islandTileWithAbyss = MethodRepository.upsideDownNeighboringIslandTileIfPossible();
                break;
            case EVENT_DRAPIEZNIKI_W_OKOLICY:
                MethodRepository.putAnimalsDiceOnWeatherField();
                break;
            case EVENT_POWODZ:
                MethodRepository.chooseInventionOrWeaponOrPalisadeOrWound();
                break;
            case EVENT_CHRONICZNE_ZMECZENIE:
                NightPhase.setEveryoneNeedAdditionalFood(true);
                break;
            case EVENT_NOCNY_ATAK:
                MethodRepository.decreaseLivesOfAllMainCharacters(-1);
                if (!GlobalData.getAvailableBeastCards().isEmpty()) {
                    BeastCard beastCard = GlobalData.getAvailableBeastCards().getFirst();
                    // TODO: 2018-12-11 Podejrzenie karty
                }
                break;
            case EVENT_DRAPIEZNIKI:
                MethodRepository.coverOrUncoverSourceClosestToTheCamp(Arrays.asList(ResourceType.FOOD_BIRD, ResourceType.FOOD_FISH), true);
                break;
            case EVENT_ROZSZALALA_WICHURA:
                if (GlobalData.getLevels().getWeaponLevel() == 1) {
                    MethodRepository.changeWeaponLevel(-1, null);
                } else if (GlobalData.getLevels().getWeaponLevel() >= 2) {
                    MethodRepository.changeWeaponLevel(-2, null);
                }
                break;
        }
    }

    public void handleThreatEvent() {
        switch (event) {
            case EVENT_SKRZYNIE_Z_JEDZENIEM:
                break;
            case EVENT_KUFER_KAPITANA:
                break;
            case EVENT_RESZTKI_SZALUPY:
                break;
            case EVENT_TRUCIZNA:
                MethodRepository.decreaseLivesOfAllMainCharacters(-1);
                break;
            case EVENT_CIALO_NA_PLAZY:
                MethodRepository.changeMoraleLevel(-2);
                break;
            case EVENT_SUSZA:
                MethodRepository.coverUncoverTerrainTypeIfPossible(TerrainType.RIVER, true);
                break;
            case EVENT_UTRATA_NADZIEI:
                MethodRepository.changeMoraleLevel(-2);
                break;
            case EVENT_SPUSTOSZENIA_PO_HURAGANIE:
                islandTileAfterHurricane = null;
                break;
            case EVENT_WYCZERPUJACA_NOC:
                MethodRepository.changeMoraleLevel(-2);
                break;
            case EVENT_WYCIE_OD_STRONY_LASU:
                if (!GlobalData.getAvailableBeastCards().isEmpty()) {
                    BeastCard beastCard = GlobalData.getAvailableBeastCards().removeFirst();
                    int strength = beastCard.getStrength();
                    int weaponDamage = beastCard.getWeaponLevelDecrease();
                    int food = beastCard.getFoodAmount();
                    int furs = beastCard.getFurAmount();

                    int weaponLevel = GlobalData.getLevels().getWeaponLevel();
                    if (weaponLevel < strength) {
                        MethodRepository.decreaseLivesOfAllMainCharacters(weaponLevel - strength);
                    }
                    MethodRepository.changeWeaponLevel(weaponDamage, null);
                    GlobalData.getAvailableResources().setFoodAmount(GlobalData.getAvailableResources().getFoodAmount() + food);
                    GlobalData.getAvailableResources().setFurAmount(GlobalData.getAvailableResources().getFurAmount() + furs);
                }
                break;
            case EVENT_ZNOJNA_PRACA:
                MethodRepository.putTokenOnActionField(ActionType.BUILDING_ACTION, TokenType.ONE_MORE_WOOD_TOKEN);
                break;
            case EVENT_ZEW_NATURY:
                MethodRepository.putTokenOnActionField(ActionType.HUNTING_ACTION, TokenType.BEAST_STRENGTH_PLUS_ONE_TOKEN);
                break;
            case EVENT_OSLABIENIE:
                GlobalData.getFirstPlayer().setUseOfSpecialSkills(false);
                break;
            case EVENT_WYSPA_BUNTUJE_SIE:
                MethodRepository.handleBookIcon();
                break;
            case EVENT_SILNY_WIATR:
                MethodRepository.removeInventionIfPossible(1, true);
                break;
            case EVENT_NISZCZYCIELSKIE_PODMUCHY:
                MethodRepository.coverSourcesOnNeighboringIslandTiles(1);
                break;
            case EVENT_BRAK_WIARY:
                MethodRepository.changeMoraleLevel(-2);
                break;
            case EVENT_BEZSENNA_NOC:
                MethodRepository.putTokenOnActionField(ActionType.RESOURCES_ACTION, TokenType.ADVENTURE_TOKEN);
                MethodRepository.putTokenOnActionField(ActionType.EXPLORATION_ACTION, TokenType.ADVENTURE_TOKEN);
                break;
            case EVENT_WYDRY:
                MethodRepository.coverOrUncoverSourceClosestToTheCamp(Collections.singletonList(ResourceType.FOOD_FISH), true);
                break;
            case EVENT_WALKA_W_CIEMNOSCIACH:
                MethodRepository.putAnimalsDiceOnWeatherField();
                break;
            case EVENT_KLOTNIA:
                MethodRepository.decreaseDeterminationOfAllMainCharacters(-1);
                MethodRepository.changeMoraleLevel(-1);
                break;
            case EVENT_PEKA_NATURALNA_TAMA:
                MethodRepository.removeInventionIfPossible(2, true);
                break;
            case EVENT_BALAGAN_W_OBOZIE:
                MethodRepository.decreaseDeterminationOfAllMainCharacters(-1);
                break;
            case EVENT_ZIMOWA_DEPRESJA:
                MethodRepository.decreaseDeterminationOfAllMainCharacters(-1);
                break;
            case EVENT_WYGLODNIALE_DRAPIEZNIKI:
                MethodRepository.changePalisadeLevel(-1);
                break;
            case EVENT_NOCNE_WYCIE_Z_DZUNGLI:
                MethodRepository.changePalisadeLevel(-1);
                break;
            case EVENT_POWALONE_DRZEWA:
                MethodRepository.putTokenOnActionField(ActionType.RESOURCES_ACTION, TokenType.ADVENTURE_TOKEN);
                MethodRepository.putTokenOnActionField(ActionType.EXPLORATION_ACTION, TokenType.ADVENTURE_TOKEN);
                break;
            case EVENT_NIEBEZPIECZNA_NOC:
                break;
            case EVENT_NARADA:
                MethodRepository.discardIdeas(2);
                break;
            case EVENT_ROZTRZASKANY_BALON:
                break;
            case EVENT_DRAPIEZNIK_W_OKOLICY:
                MethodRepository.putTokenOnActionField(ActionType.RESOURCES_ACTION, TokenType.ADVENTURE_TOKEN);
                MethodRepository.putTokenOnActionField(ActionType.EXPLORATION_ACTION, TokenType.ADVENTURE_TOKEN);
                break;
            case EVENT_INSEKTY:
                if (GlobalData.getAvailableResources().getWoodAmount() > 0) {
                    MethodRepository.changeWoodLevel(-1);
                }
                break;
            case EVENT_MGLA:
                MethodRepository.putTokenOnActionField(ActionType.EXPLORATION_ACTION, TokenType.ONE_MORE_CHARACTER_MARKER_TOKEN);
                MethodRepository.putTokenOnActionField(ActionType.EXPLORATION_ACTION, TokenType.ADVENTURE_TOKEN);
                break;
            case EVENT_NIEDZWIEDZ:
                MethodRepository.decreaseDeterminationOfAllMainCharacters(-1);
                MethodRepository.changeMoraleLevel(-1);
                break;
            case EVENT_DEPRESJA:
                MethodRepository.changeMoraleLevel(-1);
                MethodRepository.discardIdeas(2);
                break;
            case EVENT_ZALAMANIE_POGODY:
                MethodRepository.coverSourcesOnNeighboringIslandTiles(2);
                break;
            case EVENT_NIEZWYKLE_ZIMNA_NOC:
                MethodRepository.decreaseLivesOfAllMainCharacters(-1);
                break;
            case EVENT_TRZESIENIE_ZIEMI:
                islandTileAfterEarthquake = null;
                break;
            case EVENT_DYM_NA_HORYZONCIE:
                MethodRepository.coverUncoverTerrainTypeIfPossible(TerrainType.PLAINS, true);
                break;
            case EVENT_ZLE_PRZECZUCIA:
                MethodRepository.putTokenOnActionField(ActionType.RESOURCES_ACTION, TokenType.ADVENTURE_TOKEN);
                MethodRepository.putTokenOnActionField(ActionType.EXPLORATION_ACTION, TokenType.ROLL_AGAIN_IF_SUCCESS_TOKEN);
                break;
            case EVENT_ZAWROTY_GLOWY:
                MethodRepository.decreaseDeterminationOfAllMainCharacters(-1);
                break;
            case EVENT_WYBOISTE_PRZEJSCIE:
                MethodRepository.coverUncoverTerrainTypeIfPossible(TerrainType.HILLS, true);
                break;
            case EVENT_WSPOMNIENIA_Z_REJSU:
                MethodRepository.decreaseDeterminationOfAllMainCharacters(-1);
                break;
            case EVENT_OSUWISKO:
                ProductionPhase.setNoWood(true);
                ProductionPhase.setNoFood(true);
                break;
            case EVENT_DESZCZOWE_CHMURY:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.RAIN_CLOUD_TOKEN);
                break;
            case EVENT_LATAJACA_NIESPODZIANKA:
                MethodRepository.changeRoofOrPalisadeLevelByValue(-1);
                break;
            case EVENT_JAGUAR:
                MethodRepository.decreaseLivesOfAllMainCharacters(-1);
                break;
            case EVENT_POWALONE_DRZEWO:
                MethodRepository.changeMoraleLevel(-1);
                break;
            case EVENT_BOJKA:
                MethodRepository.decreaseDeterminationOfAllMainCharacters(-2);
                break;
            case EVENT_ULEWA:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.RAIN_CLOUD_TOKEN);
                break;
            case EVENT_KATAKLIZM:
                MethodRepository.removeInventionIfPossible(1, false);
                break;
            case EVENT_ZLY_LOS:
                MethodRepository.decreaseDeterminationOfAllMainCharacters(-1);
                break;
            case EVENT_DESZCZ:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.RAIN_CLOUD_TOKEN);
                break;
            case EVENT_BURZA_Z_PIORUNAMI:
                MethodRepository.coverOrUncoverSourceClosestToTheCamp(Collections.singletonList(ResourceType.WOOD), true);
                break;
            case EVENT_ROZSZALALA_RZEKA:
                ProductionPhase.setNoWood(true);
                ProductionPhase.setNoFood(true);
                break;
            case EVENT_SZTORM_1:
                MethodRepository.changePalisadeLevel(-1);
                break;
            case EVENT_SZTORM_2:
                MethodRepository.changePalisadeLevel(-1);
                break;
            case EVENT_RWACA_RZEKA:
                MethodRepository.changeWoodLevel(-GlobalData.getAvailableResources().getWoodAmount());
                MethodRepository.changeFurLevel(-GlobalData.getAvailableResources().getFurAmount());
                MethodRepository.changeFoodLevel(-GlobalData.getAvailableResources().getFoodAmount());
                MethodRepository.changeLongTermFoodLevel(-GlobalData.getAvailableResources().getLongTermFoodAmount());

                MethodRepository.blockSourceWhileProductionPhase("WOOD");
                MethodRepository.blockSourceWhileProductionPhase("FOOD");
                break;
            case EVENT_NISZCZYCIELSKI_HURAGAN:
                MethodRepository.changeRoofOrPalisadeLevelByValue(-1);
                break;
            case EVENT_PRZETRZEBIONA_OKOLICA:
                break;
            case EVENT_MROZ:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.SNOW_CLOUD_TOKEN);
                break;
            case EVENT_NADCHODZI_ULEWA:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.RAIN_CLOUD_TOKEN);
                break;
            case EVENT_OBERWANIE_CHMURY:
                break;
            case EVENT_KATASTROFA:
                MethodRepository.decreaseRoofOrPalisadeLevelByHalf();
                break;
            case EVENT_DRAPIEZNIKI_W_POBLIZU:
                MethodRepository.decreaseLivesOfAllMainCharacters(-1);
                if (ActionPhase.getActionsTokens().get(ActionType.EXPLORATION_ACTION).contains(TokenType.BEAST_STRENGTH_PLUS_ONE_TOKEN)) {
                    ActionPhase.getActionsTokens().get(ActionType.EXPLORATION_ACTION).remove(TokenType.BEAST_STRENGTH_PLUS_ONE_TOKEN);
                }
                break;
            case EVENT_POZAR:
                MethodRepository.blockSourceWhileProductionPhase("WOOD");
                break;
            case EVENT_OKROPNA_POGODA:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.SNOW_CLOUD_TOKEN);
                break;
            case EVENT_PRZENIKLIWIE_ZIMNY_DESZCZ_1:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.RAIN_CLOUD_TOKEN);
                break;
            case EVENT_PRZENIKLIWIE_ZIMNY_DESZCZ_2:
                MethodRepository.putWeatherTokenOnWeatherField(TokenType.RAIN_CLOUD_TOKEN);
                break;
            case EVENT_PRZEPASC:
                islandTileWithAbyss = null;
                break;
            case EVENT_DRAPIEZNIKI_W_OKOLICY:
                MethodRepository.putAnimalsDiceOnWeatherField();
                break;
            case EVENT_POWODZ:
                MethodRepository.changePalisadeLevel(-1);
                break;
            case EVENT_CHRONICZNE_ZMECZENIE:
                MethodRepository.decreaseLivesOfAllMainCharacters(-1);
                break;
            case EVENT_NOCNY_ATAK:
                if (!GlobalData.getAvailableBeastCards().isEmpty()) {
                    GlobalData.getAvailableBeastCards().removeFirst();
                }
                break;
            case EVENT_DRAPIEZNIKI:
                MethodRepository.coverOrUncoverSourceClosestToTheCamp(Arrays.asList(ResourceType.FOOD_BIRD, ResourceType.FOOD_FISH), true);
                break;
            case EVENT_ROZSZALALA_WICHURA:
                break;
            case NONE:
                break;
        }
    }

    public void handleThreatAction(Character leader, int markersNumber) {
        switch (threat) {
            case WYPRAWA_PO_JEDZENIE:
                GlobalData.getFutureResources().setFoodAmount(GlobalData.getFutureResources().getFoodAmount() + 1);
                if (markersNumber == 2) {
                    GlobalData.getFutureResources().setLongTermFoodAmount(GlobalData.getFutureResources().getLongTermFoodAmount() + 1);
                }
                break;
            case WYPRAWA_PO_SKARBY:
                GlobalData.getFutureResources().setWoodAmount(GlobalData.getFutureResources().getWoodAmount() + 1);
                if (markersNumber == 2) {
                    GlobalData.getStartingItems().add(GlobalData.getDecks().getStartingItemsDeck().removeFirst());
                }
                break;
            case WYPRAWA_PO_DREWNO:
                GlobalData.getFutureResources().setWoodAmount(GlobalData.getFutureResources().getWoodAmount() + markersNumber);
                break;
            case ODSACZANIE_TRUCIZNY:
                MethodRepository.changeDeterminationLevel(1, leader);
                MethodRepository.changeWeaponLevel(1, null);
                break;
            case POCHOWEK:
                MethodRepository.changeDeterminationLevel(2, leader);
                break;
            case NOWE_KORYTO:
                MethodRepository.coverUncoverTerrainTypeIfPossible(TerrainType.RIVER, false);
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case ODPOCZYNEK_1:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case ODZYSKANIE_ZRODEL:
                if (islandTileAfterHurricane != null) {
                    islandTileAfterHurricane.setEndOfLeftSource(false);
                    islandTileAfterHurricane.setEndOfRightSource(false);
                    islandTileAfterHurricane = null;
                }
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case ODPOCZYNEK_2:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case WYPRAWA:
                MethodRepository.changeDeterminationLevel(1, leader);
                BeastCard beast = GlobalData.getAvailableBeastCards().removeFirst();
                GlobalData.getAvailableBeastCards().addLast(beast);
                break;
            case ODPOCZYNEK_3:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case ZACIEKLE_POLOWANIE:
                GlobalData.getFutureResources().setFoodAmount(GlobalData.getFutureResources().getFoodAmount() + 1);
                break;
            case ODPOCZYNEK_4:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case RATUNEK:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case KONSERWACJA_NARZEDZI:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case NOWE_SCIEZKI:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case MOBILIZACJA:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case ODPOCZYNEK_5:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case UDANE_POLOWANIE:
                MethodRepository.coverOrUncoverSourceClosestToTheCamp(Collections.singletonList(ResourceType.FOOD_FISH), false);
                break;
            case TROPIENIE_DRAPIEZNIKA:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case POGODZENIE_SIE:
                MethodRepository.changeMoraleLevel(1);
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case ZABEZPIECZENIE:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case PORZADKOWANIE_DOBYTKU:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case ROZGRZANIE:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case PRZEGANIANIE_ZWIERZAT:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case OCHRONA_PRZED_BESTIAMI:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case POSZUKIWANIE_NOWEJ_SCIEZKI:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case BUDOWA_ZABEZPIECZEN:
                // TODO: 2018-12-14 sprawdzic
                break;
            case BURZA_MOZGOW:
                MethodRepository.changeDeterminationLevel(1, leader);
                MethodRepository.takeOneCardFromIdeasDeck();
                break;
            case POSZUKIWANIA:
                GlobalData.getDecks().getMysteryCardsDeck().removeFirst().handleMystery();
                break;
            case WALKA_O_POZYWIENIE:
                MethodRepository.coverOrUncoverSourceClosestToTheCamp(Collections.singletonList(ResourceType.FOOD_BIRD), false);
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case WALKA_ZE_SZKODNIKAMI:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case ODSZUKANIE_DAWNEGO_TROPU:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case NAPRAWA:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case POCIESZENIE:
                MethodRepository.changeDeterminationLevel(2, leader);
                break;
            case ZABEZPIECZENIE_ZRODEL:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case OCIEPLENIE_OBOZOWISKA:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case BUDOWA_PRZEJSCIA:
                if (islandTileAfterEarthquake != null) {
                    islandTileAfterEarthquake.setUpsideDown(false);
                    islandTileAfterEarthquake = null;
                }
                break;
            case GASZENIE_OGNIA:
                MethodRepository.coverUncoverTerrainTypeIfPossible(TerrainType.PLAINS, false);
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case OSTROZNE_ZBIERANIE:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case ODPOCZYNEK_6:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case WYTYCZANIE_NOWEJ_SCIEZKI:
                MethodRepository.coverUncoverTerrainTypeIfPossible(TerrainType.HILLS, false);
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case SZANTY:
                MethodRepository.changeDeterminationLevel(2, leader);
                MethodRepository.changeMoraleLevel(1);
                break;
            case ZABEZPIECZENIE_OBOZOWISKA:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case WZMOCNIENIE_DACHU:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case SPRZATANIE_PO_WYPADKU:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case WARTA:
                break;
            case USUNIECIE_DRZEWA:
                GlobalData.getFutureResources().setWoodAmount(GlobalData.getFutureResources().getWoodAmount() + 1);
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case PRZEMOWA:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case PRZYGOTOWANIE_DO_ULEWY:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case NAPRAWA_NARZEDZI:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case POKRZEPIAJACY_ODPOCZYNEK:
                MethodRepository.changeDeterminationLevel(3, leader);
                break;
            case WZMOCNIONY_DACH:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case ODGRODZENIE_OGNIA:
                MethodRepository.coverOrUncoverSourceClosestToTheCamp(Collections.singletonList(ResourceType.WOOD), false);
                break;
            case BUDOWA_FOSY:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case WZMOCNIENIE_OBOZOWISKA_1:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case WZMOCNIENIE_OBOZOWISKA_2:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case ODPLYW:
                MethodRepository.changeDeterminationLevel(2, leader);
                break;
            case REPERACJA_OBOZOWISKA:
                if (GlobalData.isShelter() || GlobalData.getCamp().isNaturalShelter()) {
                    MethodRepository.changeRoofOrPalisadeLevelByValue(1);
                }
                break;
            case OBFITE_POLOWANIE:
                GlobalData.getFutureResources().setFoodAmount(GlobalData.getFutureResources().getFoodAmount() + 2);
                break;
            case OGRZANIE_OBOZU:
                MethodRepository.changeDeterminationLevel(2, leader);
                break;
            case IRYGACJA_3:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case PRZEPROWADZKA:
                if (decreasedRoof && decreasedPalisade) {
                    MethodRepository.changeRoofOrPalisadeLevelByValue(1);
                } else if (decreasedRoof) {
                    MethodRepository.changeRoofLevel(1);
                } else if (decreasedPalisade) {
                    MethodRepository.changePalisadeLevel(1);
                }
                break;
            case LATA:
                if (GlobalData.isShelter() || GlobalData.getCamp().isNaturalShelter()) {
                    MethodRepository.changeRoofOrPalisadeLevelByValue(1);
                }
                break;
            case POSZUKIWANIA_DRAPIEZNIKA:
                if (ActionPhase.getActionsTokens().get(ActionType.EXPLORATION_ACTION).contains(TokenType.BEAST_STRENGTH_PLUS_ONE_TOKEN)) {
                    ActionPhase.getActionsTokens().get(ActionType.EXPLORATION_ACTION).remove(TokenType.BEAST_STRENGTH_PLUS_ONE_TOKEN);
                }
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case WALKA_Z_OGNIEM:
                MethodRepository.changeDeterminationLevel(2, leader);
                break;
            case WIATROCHRON:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case IRYGACJA_1:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case IRYGACJA_2:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case BUDOWA_MOSTU:
                if (islandTileWithAbyss != null) {
                    islandTileWithAbyss.setUpsideDown(false);
                    islandTileWithAbyss = null;
                }
                break;
            case PRZEPEDZENIE_DRAPIEZNIKA:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case BUDOWA_ODPLYWU:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case REGENERACJA:
                MethodRepository.changeDeterminationLevel(1, leader);
                break;
            case W_POSZUKIWANIU_BESTII:
                BeastCard beastCard = GlobalData.getAvailableBeastCards().removeFirst();
                int weaponDamage = beastCard.getWeaponLevelDecrease();
                int food = beastCard.getFoodAmount();
                int furs = beastCard.getFurAmount();
                MethodRepository.changeWeaponLevel(weaponDamage, leader);
                GlobalData.getFutureResources().setFoodAmount(GlobalData.getFutureResources().getFoodAmount() + food);
                GlobalData.getFutureResources().setFurAmount(GlobalData.getFutureResources().getFurAmount() + furs);
                break;
            case WALKA_Z_DRAPIEZNIKAMI:
                MethodRepository.coverOrUncoverSourceClosestToTheCamp(Arrays.asList(ResourceType.FOOD_BIRD, ResourceType.FOOD_FISH), false);
                break;
            case NOWA_BRON:
                MethodRepository.changeDeterminationLevel(1, leader);
                MethodRepository.changeWeaponLevel(1, null);
                break;
            case NONE:
                break;
        }
        GlobalData.getThreatActionCards().remove(this);
    }

    public Requirements specifyRequirements() {
        Requirements requirements = new Requirements();

        switch (threat) {
            case WYPRAWA_PO_JEDZENIE:
                break;
            case WYPRAWA_PO_SKARBY:
                break;
            case WYPRAWA_PO_DREWNO:
                break;
            case ODSACZANIE_TRUCIZNY:
                requirements.getRequiredInventions().add(IdeaType.KNIFE);
                break;
            case POCHOWEK:
                requirements.getRequiredInventions().add(IdeaType.SHOVEL);
                break;
            case NOWE_KORYTO:
                requirements.getRequiredInventions().add(IdeaType.SHOVEL);
                break;
            case ODPOCZYNEK_1:
                break;
            case ODZYSKANIE_ZRODEL:
                requirements.getRequiredInventions().add(IdeaType.SHOVEL);
                break;
            case ODPOCZYNEK_2:
                break;
            case WYPRAWA:
                requirements.getRequiredInventions().add(IdeaType.FIRE);
                break;
            case ODPOCZYNEK_3:
                break;
            case ZACIEKLE_POLOWANIE:
                requirements.getRequiredLevels().setWeaponLevel(2);
                break;
            case ODPOCZYNEK_4:
                break;
            case RATUNEK:
                break;
            case KONSERWACJA_NARZEDZI:
                break;
            case NOWE_SCIEZKI:
                requirements.getRequiredInventions().add(IdeaType.SHOVEL);
                break;
            case MOBILIZACJA:
                requirements.getRequiredResources().setFoodAmount(1);
                break;
            case ODPOCZYNEK_5:
                break;
            case UDANE_POLOWANIE:
                requirements.getRequiredLevels().setWeaponLevel(1);
                break;
            case TROPIENIE_DRAPIEZNIKA:
                requirements.getRequiredLevels().setWeaponLevel(2);
                break;
            case POGODZENIE_SIE:
                // TODO: 2018-12-17 2 pionki różnych postaci
                break;
            case ZABEZPIECZENIE:
                requirements.getRequiredInventions().add(IdeaType.SHOVEL);
                break;
            case PORZADKOWANIE_DOBYTKU:
                break;
            case ROZGRZANIE:
                requirements.getRequiredResources().setFoodAmount(1);
                break;
            case PRZEGANIANIE_ZWIERZAT:
                requirements.getRequiredInventions().add(IdeaType.FIRE);
                break;
            case OCHRONA_PRZED_BESTIAMI:
                requirements.getRequiredLevels().setWeaponLevel(2);
                break;
            case POSZUKIWANIE_NOWEJ_SCIEZKI:
                break;
            case BUDOWA_ZABEZPIECZEN:
                requirements.getRequiredLevels().setWeaponLevel(2);
                break;
            case BURZA_MOZGOW:
                break;
            case POSZUKIWANIA:
                break;
            case WALKA_O_POZYWIENIE:
                requirements.getRequiredLevels().setWeaponLevel(2);
                break;
            case WALKA_ZE_SZKODNIKAMI:
                requirements.getRequiredInventions().add(IdeaType.FIRE);
                break;
            case ODSZUKANIE_DAWNEGO_TROPU:
                break;
            case NAPRAWA:
                requirements.getRequiredInventions().add(IdeaType.SHOVEL);
                break;
            case POCIESZENIE:
                requirements.getRequiredResources().setFoodAmount(1);
                break;
            case ZABEZPIECZENIE_ZRODEL:
                requirements.getRequiredInventions().add(IdeaType.SHOVEL);
                break;
            case OCIEPLENIE_OBOZOWISKA:
                // TODO: 2018-12-17 1 drewno lub 1 skóra
                break;
            case BUDOWA_PRZEJSCIA:
                requirements.getRequiredInventions().add(IdeaType.SHOVEL);
                break;
            case GASZENIE_OGNIA:
                break;
            case OSTROZNE_ZBIERANIE:
                break;
            case ODPOCZYNEK_6:
                break;
            case WYTYCZANIE_NOWEJ_SCIEZKI:
                break;
            case SZANTY:
                // TODO: 2018-12-17 2 ludzi
                break;
            case ZABEZPIECZENIE_OBOZOWISKA:
                requirements.getRequiredInventions().add(IdeaType.SHOVEL);
                break;
            case WZMOCNIENIE_DACHU:
                requirements.getRequiredResources().setFurAmount(1);
                break;
            case SPRZATANIE_PO_WYPADKU:
                requirements.getRequiredResources().setWoodAmount(1);
                break;
            case WARTA:
                requirements.getRequiredLevels().setWeaponLevel(2);
                break;
            case USUNIECIE_DRZEWA:
                requirements.getRequiredInventions().add(IdeaType.ROPE);
                break;
            case PRZEMOWA:
                break;
            case PRZYGOTOWANIE_DO_ULEWY:
                requirements.getRequiredResources().setWoodAmount(1);
                break;
            case NAPRAWA_NARZEDZI:
                break;
            case POKRZEPIAJACY_ODPOCZYNEK:
                break;
            case WZMOCNIONY_DACH:
                requirements.getRequiredResources().setFurAmount(1);
                break;
            case ODGRODZENIE_OGNIA:
                requirements.getRequiredInventions().add(IdeaType.SHOVEL);
                break;
            case BUDOWA_FOSY:
                requirements.getRequiredInventions().add(IdeaType.SHOVEL);
                break;
            case WZMOCNIENIE_OBOZOWISKA_1:
                // TODO: 2018-12-17 1 drewno lub łopata
                break;
            case WZMOCNIENIE_OBOZOWISKA_2:
                // TODO: 2018-12-17 1 drewno lub łopata
                break;
            case ODPLYW:
                // TODO: 2018-12-17 1 drewno lub łopata
                break;
            case REPERACJA_OBOZOWISKA:
                requirements.getRequiredResources().setWoodAmount(1);
                break;
            case OBFITE_POLOWANIE:
                requirements.getRequiredLevels().setWeaponLevel(2);
                break;
            case OGRZANIE_OBOZU:
                // TODO: 2018-12-17 1 drewno albo skóra
                break;
            case IRYGACJA_3:
                requirements.getRequiredInventions().add(IdeaType.SHOVEL);
                break;
            case PRZEPROWADZKA:
                break;
            case LATA:
                requirements.getRequiredResources().setWoodAmount(1);
                break;
            case POSZUKIWANIA_DRAPIEZNIKA:
                requirements.getRequiredLevels().setWeaponLevel(1);
                break;
            case WALKA_Z_OGNIEM:
                requirements.getRequiredInventions().add(IdeaType.SHOVEL);
                break;
            case WIATROCHRON:
                requirements.getRequiredResources().setWoodAmount(1);
                break;
            case IRYGACJA_1:
                requirements.getRequiredInventions().add(IdeaType.SHOVEL);
                break;
            case IRYGACJA_2:
                requirements.getRequiredInventions().add(IdeaType.SHOVEL);
                break;
            case BUDOWA_MOSTU:
                requirements.getRequiredResources().setWoodAmount(2);
                break;
            case PRZEPEDZENIE_DRAPIEZNIKA:
                requirements.getRequiredLevels().setWeaponLevel(2);
                break;
            case BUDOWA_ODPLYWU:
                requirements.getRequiredInventions().add(IdeaType.SHOVEL);
                break;
            case REGENERACJA:
                // TODO: 2018-12-17 2 pionki
                break;
            case W_POSZUKIWANIU_BESTII:
                requirements.getRequiredLevels().setWeaponLevel(3);
                break;
            case WALKA_Z_DRAPIEZNIKAMI:
                // TODO: 2018-12-17 2 pionki
                requirements.getRequiredLevels().setWeaponLevel(2);
                break;
            case NOWA_BRON:
                break;
            case NONE:
                break;
        }


        return requirements;
    }
}
