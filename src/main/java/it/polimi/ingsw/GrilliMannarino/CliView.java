package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Faction;
import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import it.polimi.ingsw.GrilliMannarino.Message.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CliView extends ClientView {

    private Scanner scanner;
    private HashMap<Integer, JSONObject> jsonCardsProduction = new HashMap<>();
    private HashMap<Integer, JSONObject> jsonLeaderCards = new HashMap<>();

    public CliView(){
        scanner = new Scanner(System.in);
    }


    @Override
    public void viewError(String errorMessage) {
        System.out.println(errorMessage.toUpperCase(Locale.ROOT));
        selectAction();
    }

    @Override
    public void showMarbleMarket(ArrayList<ArrayList<Marble>> marbleList, Marble marbleOut) {
        String rowColumn;
        int number;

        System.out.println("Marble Market:");
        System.out.println();
        String s = "";
        int i=0;
        System.out.format("%15s", s);
        for(int x=0; x<marbleList.size(); x++){
            String column = "Column:" + (x+1);
            System.out.format("%15s", column);
        }
        System.out.println();
        for (int x=0; x<marbleList.get(0).size(); x++){
            String row = "Row: " + (i+1);
            System.out.format("%15s", row);
            i++;
            for(int y=0; y<marbleList.size(); y++){
                System.out.format("%15s", marbleList.get(y).get(x).toString());
            }
            System.out.println();
        }
        String out = "Marble Out " + marbleOut.toString();
        System.out.format("%15s", out);
        System.out.println();

        System.out.println("Insert R for Row and C for Column");
        rowColumn = scanner.nextLine();
        while(!rowColumn.equals("C") && !rowColumn.equals("R")){
            System.out.println("Error, Invalid Input");
            rowColumn = scanner.nextLine();
        }
        System.out.println("Insert number of Row/Column");
        if(rowColumn.equals("R")){
            try{
                number = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                number=0;
            }
            while(number > 3 || number < 1){
                System.out.println("Error, Invalid Input");
                try{
                    number = Integer.parseInt(scanner.nextLine());
                }catch (NumberFormatException e){
                    number=0;
                }
            }
        }
        else{
            try{
                number = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                number=0;
            }
            while(number > 4 || number < 1){
                System.out.println("Error, Invalid Input");
                try{
                    number = Integer.parseInt(scanner.nextLine());
                }catch (NumberFormatException e){
                    number=0;
                }
            }
        }

        MarbleMarketMessage message = new MarbleMarketMessage(this.gameId, this.playerId);
        message.setSelectColumnRow(true);
        message.setColumnRow(rowColumn);
        message.setColumnRowValue(number);
        controller.sendMessageToServer(message);
    }

    @Override
    public void selectMarble(ArrayList<ArrayList<Marble>> returnedMarble) {
        System.out.println("For each Row you have to select the Marble you prefer");
        ArrayList<Marble> toReturn = new ArrayList<>();
        for(ArrayList<Marble> arr : returnedMarble){
            if(arr.size()==1){
                toReturn.add(arr.get(0));
            }
            else {
                ArrayList<Marble> marble = new ArrayList<>();
                for (int x = 0; x < arr.size(); x++) {
                    System.out.format("%15s", arr.get(x).toString());
                    marble.add(arr.get(x));
                }
                System.out.println();
                Marble m = null;
                try {
                    m = Marble.valueOf(scanner.nextLine());
                } catch (IllegalArgumentException e) {
                }
                while (!marble.contains(m)) {
                    System.out.println("Invalid Input");
                    try {
                        m = Marble.valueOf(scanner.nextLine());
                    } catch (IllegalArgumentException e) {
                    }
                }
                toReturn.add(m);
            }
        }

        ArrayList<Resource> resources = new ArrayList<>();
        toReturn.forEach((m) -> resources.add(Marble.getResource(m)));

        MarbleMarketMessage message = new MarbleMarketMessage(this.gameId, this.playerId);
        message.setCheckReturnedResource(true);
        message.setReturnedResource(resources);
        controller.sendMessageToServer(message);
    }

    private void getResourceToPlace(ArrayList<Resource> resources) {

        System.out.println("Resources needed to be placed: ");
        resources.forEach(res -> System.out.format("%15s", res.toString()));
        System.out.println();
        System.out.println("Do you want to place a resource into warehouse P, Move resources M or Discharge the ramaining resource D?");
        String s = scanner.nextLine();
        while(!s.equals("P") && !s.equals("M") && !s.equals("D")){
            System.out.println("Invalid Input");
            s = scanner.nextLine();
        }
        if(s.equals("D")){
            MarbleMarketMessage message = new MarbleMarketMessage(this.gameId, this.playerId);
            message.setReturnedResource(resources);
            message.setDestroyRemaining(true);
            controller.sendMessageToServer(message);
            return;
        }
        else if(s.equals("M")){
            MarbleMarketMessage message = new MarbleMarketMessage(this.gameId, this.playerId);
            message.setSwapRow(true);
            System.out.println("Select two row to reverse");
            System.out.println("Select first row: ");
            Row one = null, two = null;
            try{
                one = Row.valueOf(scanner.nextLine());
            }
            catch(IllegalArgumentException e){
            }
            while(!this.warehouse.containsKey(one)){
                System.out.println("Invalid Input");
                try{
                    one = Row.valueOf(scanner.nextLine());
                }
                catch(IllegalArgumentException e){
                }
            }
            System.out.println("Select second row: ");
            try{
                two = Row.valueOf(scanner.nextLine());
            }
            catch(IllegalArgumentException e){
            }
            while(!this.warehouse.containsKey(two)){
                System.out.println("Invalid Input");
                try{
                    two = Row.valueOf(scanner.nextLine());
                }
                catch(IllegalArgumentException e){
                }
            }
            message.setRowOne(one);
            message.setRowTwo(two);
            message.setReturnedResource(resources);
            System.out.println("You have selected rows: " + one.toString() + " and " + two.toString());
            System.out.println("Do you want to force the reverse? Doing so some resources may be lost. Y/N");
            String ss = scanner.nextLine();
            while(!ss.equals("N") && !ss.equals("Y")){
                System.out.println("Invalid Input");
                ss = scanner.nextLine();
            }
            if(ss.equals("Y")){
                message.setForceSwap(true);
            }
            controller.sendMessageToServer(message);
            return;
        }

        System.out.println("Select the Resource to place into warehouse");
        Resource resource = null;
        Row row = null;
        try {
            resource = Resource.valueOf(scanner.nextLine());
        }
        catch(IllegalArgumentException e){
        }
        while(!resources.contains(resource)){
            System.out.println("Invalid Input");
            try {
                resource = Resource.valueOf(scanner.nextLine());
            }
            catch(IllegalArgumentException e){
            }
        }
        System.out.println("Now select the row you want to put in");
        try {
            row = Row.valueOf(scanner.nextLine());
        }
        catch(IllegalArgumentException e){
        }
        while(!this.warehouse.containsKey(row)){
            System.out.println("Invalid Input");
            try {
                row = Row.valueOf(scanner.nextLine());
            }
            catch(IllegalArgumentException e){
            }
        }

        System.out.println("You selected " + resource.toString() + " resource to put into " + row.toString() + " row");
        MarbleMarketMessage message = new MarbleMarketMessage(this.gameId, this.playerId);
        message.setAddedResource(true);
        message.setResourceType(resource);
        message.setInsertRow(row);
        message.setReturnedResource(resources);
        controller.sendMessageToServer(message);

    }

    private void printWareHouse(){
        System.out.println("Warehouse: ");
        ArrayList<Row> iterate = new ArrayList<>(Row.orderedRow());
        for(Row r : iterate) {
            if (this.warehouse.containsKey(r)) {
                System.out.format("%15s", r.toString());
                if (this.warehouse.get(r).isEmpty()) {
                    for (int x = 0; x < r.getMaxValue(); x++)
                        System.out.format("%15s", "Empty");
                } else {
                    for (Resource res : this.warehouse.get(r).keySet()) {
                        for (int x = 0; x < this.warehouse.get(r).get(res); x++) {
                            System.out.format("%15s", res.toString());
                        }
                        if (r.getMaxValue() > this.warehouse.get(r).get(res)) {
                            for (int x = 0; x < (r.getMaxValue() - this.warehouse.get(r).get(res)); x++) {
                                System.out.format("%15s", "Empty");
                            }
                        }
                    }
                }
                System.out.println();
            }
        }
    }

    private void printChest(){
        System.out.println("Chest: ");
        for(Resource res : this.chest.keySet()){
            System.out.format("%s %d\n", res.toString(), this.chest.get(res));
        }
    }

    @Override
    public void addedResource(Resource resourceType, Row insertRow, ArrayList<Resource> remainingResource, boolean addResourceCorrect) {
        if(addResourceCorrect){
            System.out.println("The resource selected: " + resourceType.toString() + " has been added correctly");
        }
        else{
            System.out.println("The resource selected: " + resourceType.toString() + " was not placed!!");
        }
        if(remainingResource.isEmpty()){
            System.out.println("You have finished the resource to place");
            MarbleMarketMessage message = new MarbleMarketMessage(this.gameId, this.playerId);
            message.setDestroyRemaining(true);
            message.setReturnedResource(new ArrayList<>());
            controller.sendMessageToServer(message);
            return;
        }
        getResourceToPlace(remainingResource);
    }

    @Override
    public void showCardMarket(HashMap<Faction, HashMap<Integer, Map.Entry<Integer, Boolean>>> buyableCard) {
        if(buyableCard!=null) {
            HashMap<Integer, Boolean> bc = new HashMap<>();
            for(Faction fac : buyableCard.keySet()){
                for(Integer value : buyableCard.get(fac).keySet()){
                    bc.put(buyableCard.get(fac).get(value).getKey(), buyableCard.get(fac).get(value).getValue());
                }
            }
            showBuyableCard(bc);

            System.out.println("Enter the card code of the production card you want to buy");
            System.out.println("Enter -1 to exit");
            Integer cardCode = null;
            try {
                cardCode = Integer.parseInt(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                cardCode = 0;
            }
            while ((!bc.containsKey(cardCode) || !bc.get(cardCode)) && cardCode!=-1) {
                if(!bc.containsKey(cardCode))
                    System.out.println("Invalid Input");
                else
                    System.out.println("You can't buy that card");
                try {
                    cardCode = Integer.parseInt(scanner.nextLine());
                } catch (IllegalArgumentException e) {
                    cardCode = 0;
                }
            }
            if(cardCode!=-1) {
                BuyProductionCardMessage message = new BuyProductionCardMessage(this.gameId, this.playerId);
                message.setSelectedCard(cardCode);
                showCardInProductionLine(this.productionLine);
                Integer position = null;
                System.out.println("Enter the position you want to place your card");
                try {
                    position = Integer.parseInt(scanner.nextLine());
                } catch (IllegalArgumentException e) {
                    position = -1;
                }
                while (position <= 0) {
                    System.out.println("Invalid position");
                    try {
                        position = Integer.parseInt(scanner.nextLine());
                    } catch (IllegalArgumentException e) {
                        position = -1;
                    }
                }

                message.setPositionCard(position-1);
                message.setSelectCard(true);
                controller.sendMessageToServer(message);
            }
            else
                viewError("Exiting from buying production card");

        }
        else
            viewError("No card in the market to show");
    }


    @Override
    public void setCardIntoProductionLine(Integer selectedCard, Integer positionCard, HashMap<Integer, Integer> cardInProductionline) {
        this.productionLine = new HashMap<>(cardInProductionline);
        showCardInProductionLine(cardInProductionline);
    }

    public void selectAction(){
        System.out.println("What do you want to do?");
        if(this.normalAction) {
            System.out.println("B for buying a card to the market");
            System.out.println("M for going to the Marble market");
            System.out.println("P for producing with your card");
        }
        if(this.leaderAction)
            System.out.println("L for Leader card action");
        System.out.println("S for swapping resources from your warehouse");
        System.out.println("N for next turn");
        String s = scanner.nextLine();
        boolean check = true;
        while(check) {
            if ("M".equals(s) && this.normalAction) {
                MarbleMarketMessage marbleMessage = new MarbleMarketMessage(this.gameId, this.playerId);
                marbleMessage.setDisplayMarbleMarket(true);
                check = false;
                controller.sendMessageToServer(marbleMessage);
            } else if ("B".equals(s) && this.normalAction) {
                BuyProductionCardMessage buyProductionMessage = new BuyProductionCardMessage(this.gameId, this.playerId);
                buyProductionMessage.setDisplayCard(true);
                check = false;
                controller.sendMessageToServer(buyProductionMessage);
            } else if ("L".equals(s) && this.leaderAction) {
                LeaderCardMessage leaderMessage = new LeaderCardMessage(this.gameId, this.playerId);
                leaderMessage.setShowLeaderCard(true);
                check = false;
                controller.sendMessageToServer(leaderMessage);
            } else if ("S".equals(s)) {
                check = false;
                moveResource();
            }else if("N".equals(s)){
                TurnMessage turnMessage = new TurnMessage(this.gameId, this.playerId);
                check = false;
                controller.sendMessageToServer(turnMessage);
            }else if("P".equals(s)) {
                ProductionMessage productionMessage = new ProductionMessage(this.gameId, this.playerId);
                productionMessage.setDisplayCard(true);
                check = false;
                controller.sendMessageToServer(productionMessage);
            }
            else
            {
                System.out.println("Invalid Input");
                s = scanner.nextLine();
            }

        }
    }

    private void moveResource() {
        MoveResourceMessage moveMessage = new MoveResourceMessage(this.gameId, this.playerId);
        printWareHouse();
        System.out.println("Select two row to reverse");
        System.out.println("Select first row: ");
        Row one = null, two = null;
        try{
            one = Row.valueOf(scanner.nextLine());
        }
        catch(IllegalArgumentException e){
        }
        while(!this.warehouse.containsKey(one)){
            System.out.println("Invalid Input");
            try{
                one = Row.valueOf(scanner.nextLine());
            }
            catch(IllegalArgumentException e){
            }
        }
        System.out.println("Select second row: ");
        try{
            two = Row.valueOf(scanner.nextLine());
        }
        catch(IllegalArgumentException e){
        }
        while(!this.warehouse.containsKey(two)){
            System.out.println("Invalid Input");
            try{
                two = Row.valueOf(scanner.nextLine());
            }
            catch(IllegalArgumentException e){
            }
        }

        System.out.println("You have selected rows: " + one.toString() + " and " + two.toString());
        moveMessage.setRowOne(one);
        moveMessage.setRowTwo(two);
        System.out.println("Do you want to force the reverse? Doing so some resources may be lost. Y/N");
        String s = scanner.nextLine();
        while(!s.equals("N") && !s.equals("Y")){
            System.out.println("Invalid Input");
            s = scanner.nextLine();
        }
        if(s.equals("Y")){
            moveMessage.setForceSwap(true);
        }
        controller.sendMessageToServer(moveMessage);

    }


    @Override
    public void isYourTurn() {
        System.out.println("This is your turn! Play it wisely");
        this.normalAction = true;
        this.leaderAction = true;
        selectAction();
    }

    @Override
    public void checkPopeLine(boolean favorActive, Integer checkPosition, Integer faithPosition) {
        if(favorActive){
            System.out.format("You have activated the pope favor number: %d", (checkPosition+1));
            this.faith = faithPosition;
            this.faithMark[checkPosition]=true;
            printPopeLine();
        }
        else {
            System.out.format("You have discharged the pope favor number: %d", (checkPosition + 1));
            this.faith = faithPosition;
            printPopeLine();
        }
    }

    @Override
    public void updateFaith(Integer faithPosition) {
        this.faith = faithPosition;
        printPopeLine();
    }

    private void printPopeLine() {
        System.out.println("Popeline: ");
        for (int i = 1; i<=24; i++){
            System.out.format("%5d", i);
        }
        System.out.println();
        System.out.println("Your faith is: " + this.faith);
        System.out.format("Favor active: ");
        for(boolean b : this.faithMark){
            if(b)
                System.out.format("%15s", "Active");
            else
                System.out.format("%15s", "Inactive");
        }
        System.out.println();
    }

    private void loadProductionCard(){
        JSONArray array;
        JSONParser parser = new JSONParser();
        try {
            FileReader file = new FileReader("creation_cards.json");
            array = (JSONArray) parser.parse(file);
            for(Object o : array){
                JSONObject jsonObject = (JSONObject) ((JSONObject) o).get("card");
                jsonCardsProduction.put(Integer.parseInt((String) jsonObject.get("card_code")), jsonObject);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startGame(Integer numberPlayer) {
        this.numberOfPlayer = numberPlayer;
        loadProductionCard();
        loadLeaderCard();
        this.warehouse.put(Row.FIRST, new HashMap<>());
        this.warehouse.put(Row.SECOND, new HashMap<>());
        this.warehouse.put(Row.THIRD, new HashMap<>());
        System.out.println("Game has started!");
    }

    private void loadLeaderCard() {
        JSONArray array;
        JSONParser parser = new JSONParser();
        try {
            FileReader file = new FileReader("leader_cards.json");
            array = (JSONArray) parser.parse(file);
            for(Object o : array){
                JSONObject jsonObject = (JSONObject) ((JSONObject) o).get("leader_card");
                jsonCardsProduction.put(Integer.parseInt((String) jsonObject.get("card_code")), jsonObject);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateResources(HashMap<Resource, Integer> chestResources, HashMap<Row, HashMap<Resource, Integer>> wareHouseResources) {
        this.chest = chestResources;
        this.warehouse = wareHouseResources;
        printWareHouse();
        printChest();
    }

    @Override
    public void moveApplied() {
        System.out.println("Resource has been moved correctly");
        selectAction();
    }

    @Override
    public void looseResource() {
        System.out.println("Attention: some resources may be lost!!");
        moveResource();
    }

    @Override
    public void createdNewGame(String messageString, Integer gameId) {
        System.out.println(messageString);
        this.gameId = gameId;
        System.out.println("Whenever you want to start your game enter START");
        String start = scanner.nextLine().toUpperCase(Locale.ROOT);
        while(!start.equals("START")){
            System.out.println("Invalid Input");
            start = scanner.nextLine().toUpperCase(Locale.ROOT);
        }
        StartGameMessage message = new StartGameMessage(this.gameId, this.playerId, true);
        controller.sendMessageToServer(message);
    }

    @Override
    public void enteredNewGame(String messageString, Integer gameId) {
        System.out.println(messageString);
        this.gameId = gameId;
    }

    @Override
    public void finishedNormalAction(String message) {
        System.out.println(message);
        this.normalAction = false;
        selectAction();
    }

    @Override
    public void showLeaderCard(ArrayList<Integer> cards) {
        if(cards!=null) {
            showCardInLeaderCard(cards); //NEED MODIFIED
            System.out.println("Enter the Card code of the leader card");
            Integer cardCode = null;
            try {
                cardCode = Integer.parseInt(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                cardCode = 0;
            }
            while (!cards.contains(cardCode)) {
                System.out.println("Invalid Input");
                try {
                    cardCode = Integer.parseInt(scanner.nextLine());
                } catch (IllegalArgumentException e) {
                    cardCode = 0;
                }
            }

            LeaderCardMessage message = new LeaderCardMessage(this.gameId, this.playerId);
            message.setCardCode(cardCode);
            System.out.println("Enter S for selling card or A for activating it");
            String action = null;
            try {
                action = scanner.nextLine();
            } catch (Exception e) {
                action = "Z";
            }
            do {
                if (action.equals("A"))
                    message.setActivateCard(true);
                else if (action.equals("S"))
                    message.setSellingCard(true);
                else {
                    try {
                        action = scanner.nextLine();
                    } catch (Exception e) {
                        action = "Z";
                    }
                }
            }while (!action.equals("A") && !action.equals("S"));
            controller.sendMessageToServer(message);
        }
        else
            viewError("No leader card available");
    }

    @Override
    public void showProductionCard(HashMap<Integer, Integer> productionCard) {
        if(productionCard!=null) {
            this.productionLine = new HashMap<>(productionCard);
            System.out.println("Production line: ");
            showCardInProductionLine(productionCard);

            System.out.println("Enter the Card codes you want use to produce. If you want to stop write -1");
            Integer cardCode = null;
            ArrayList<Integer> producingCard = new ArrayList<>();
            try {
                cardCode = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                cardCode = 0;
            }
            while (!cardCode.equals(-1)) {
                if (productionCard.containsKey(cardCode)) {
                    producingCard.add(cardCode);
                    productionCard.remove(cardCode);
                    System.out.println("Added card to production with card code " + cardCode);
                } else
                    System.out.println("Card cannot be added to production");
                try {
                    cardCode = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    cardCode = 0;
                }
            }

            if(producingCard.isEmpty()){
                selectAction();
                return;
            }

            ProductionMessage message = new ProductionMessage(this.gameId, this.playerId);
            message.setSelectCard(true);
            message.setSelectedCard(producingCard);
            controller.sendMessageToServer(message);
        }
        else
            viewError("No production card available");
    }

    @Override
    public void setUpInformation() {
        System.out.println("Do you want to create a new account or log in to an existing one?");
        System.out.println("N for new account or L for log in");
        String action = null;
        try{
            action = scanner.nextLine();
        }catch(Exception e){
            action = "";
        }
        while(!action.equals("N") && !action.equals("L")){
            System.out.println("Invalid Input");
            try{
                action = scanner.nextLine();
            }catch(Exception e){
                action = "";
            }
        }
        System.out.println("Please insert your nickname");
        String nickname = scanner.nextLine();
        System.out.println("Please insert your password");
        Console console = System.console();
        String password;
        if(console == null)
            password = scanner.nextLine();
        else
            password = new String(console.readPassword());


        if(action.equals("N"))
            controller.sendInformationToServer(nickname, password, true);
        else
            controller.sendInformationToServer(nickname, password, false);
    }

    @Override
    public void getUpInformation(LoginMessage message) {
        if (message.isCorrectLogin()) {
            this.playerId = message.getPlayerId();
            this.nickname = message.getNickname();
        }
        System.out.println(message.getMessage());
    }

    @Override
    public void setUpGame() {
        System.out.println("Do you want to create a new game or join an existing one?");
        System.out.println("N for new game, J for joining one");
        String gameAction = null;
        try{
            gameAction = scanner.nextLine();
        }catch (Exception e){
            gameAction = "";
        }
        while(!gameAction.equals("N") && !gameAction.equals("J")){
            System.out.println("Invalid Input");
            try{
                gameAction = scanner.nextLine();
            }catch (Exception e){
                gameAction = "";
            }
        }
        if(gameAction.equals("N")){
            System.out.println("Please enter the maximum number of player from 1 to 4");
            Integer nop = null;
            try{
                nop = Integer.parseInt(scanner.nextLine());
            }catch(NumberFormatException e){
                nop = -1;
            }
            while(nop>4 || nop<1){
                System.out.println("Invalid Input");
                try{
                    nop = Integer.parseInt(scanner.nextLine());
                }catch(NumberFormatException e){
                    nop = -1;
                }
            }
            NewGameMessage newGame = new NewGameMessage(null, this.playerId, this.nickname);
            newGame.setNumberOfPlayer(nop);
            controller.sendMessageToServer(newGame);
        }
        else if(gameAction.equals("J")){
            EnterGameMessage enterGame = new EnterGameMessage(null, this.playerId, this.nickname);
            controller.sendMessageToServer(enterGame);
        }
    }

    @Override
    public void printInformation(String message) {
        System.out.println(message);
    }

    @Override
    public void finishedLeaderAction(String s) {
        System.out.println(s);
        this.leaderAction = false;
        selectAction();
    }

    @Override
    public void selectMarbleStarting(ArrayList<ArrayList<Marble>> marblesToSelect) {
        System.out.println("For each Row you have to select the Marble you prefer");
        ArrayList<Marble> toReturn = new ArrayList<>();
        for(ArrayList<Marble> arr : marblesToSelect){
            if(arr.size()==1){
                toReturn.add(arr.get(0));
            }
            else {
                ArrayList<Marble> marble = new ArrayList<>();
                for (int x = 0; x < arr.size(); x++) {
                    System.out.format("%15s", arr.get(x).toString());
                    marble.add(arr.get(x));
                }
                System.out.println();
                Marble m = null;
                try {
                    m = Marble.valueOf(scanner.nextLine());
                } catch (IllegalArgumentException e) {
                }
                while (!marble.contains(m)) {
                    System.out.println("Invalid Input");
                    try {
                        m = Marble.valueOf(scanner.nextLine());
                    } catch (IllegalArgumentException e) {
                    }
                }
                toReturn.add(m);
            }
        }

        ArrayList<Resource> resources = new ArrayList<>();
        toReturn.forEach((m) -> resources.add(Marble.getResource(m)));

        printWareHouse();
        placeResourceStarting(resources);
    }

    @Override
    public void placeResourceStarting(ArrayList<Resource> resourcesLeft) {
        if(!resourcesLeft.isEmpty()){
            System.out.println("Resources needed to be placed: ");
            resourcesLeft.forEach(res -> System.out.format("%15s", res.toString()));
            System.out.println();
            System.out.println("Select the Resource to place into warehouse");
            Resource resource = null;
            Row row = null;
            try {
                resource = Resource.valueOf(scanner.nextLine());
            } catch (IllegalArgumentException e) {
            }
            while (!resourcesLeft.contains(resource)) {
                System.out.println("Invalid Input");
                try {
                    resource = Resource.valueOf(scanner.nextLine());
                } catch (IllegalArgumentException e) {
                }
            }
            System.out.println("Now select the row you want to put in");
            try {
                row = Row.valueOf(scanner.nextLine());
            } catch (IllegalArgumentException e) {
            }
            while (!this.warehouse.containsKey(row)) {
                System.out.println("Invalid Input");
                try {
                    row = Row.valueOf(scanner.nextLine());
                } catch (IllegalArgumentException e) {
                }
            }

            System.out.println("You selected " + resource.toString() + " resource to put into " + row.toString() + " row");
            StartingResourceMessage message = new StartingResourceMessage(this.gameId, this.playerId);
            message.setPlaceResource(true);
            message.setRowToPlace(row);
            message.setResourceToPlace(resource);
            message.setResourcesLeft(resourcesLeft);
            controller.sendMessageToServer(message);
        }
        else{
            System.out.print("You have placed all resources!");
            StartingResourceMessage message = new StartingResourceMessage(this.gameId, this.playerId);
            controller.sendMessageToServer(message);
        }
    }

    @Override
    public void checkReturnedResource(ArrayList<Resource> returnedResource) {
        printWareHouse();
        getResourceToPlace(returnedResource);
    }

    @Override
    public void selectLeaderCard(ArrayList<Integer> cards) {
        showCardInLeaderCard(cards);
        ArrayList<Integer> toReturn = new ArrayList<>();
        System.out.println("You have to select two cards using the card code");
        int i=0;
        Integer cardCode;
        while(i<2){
            System.out.println("Select " + (i+1) + "° card");
            try{
                cardCode = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                cardCode =0;
            }
            if(cards.contains(cardCode)){
                toReturn.add(cardCode);
                System.out.println("Selection was correct");
                i++;
            }
            else{
                System.out.println("Invalid Input");
            }
        }

        LeaderCardMessage message = new LeaderCardMessage(this.gameId, this.playerId);
        message.setSelectLeaderCard(true);
        message.setCards(toReturn);
        controller.sendMessageToServer(message);
    }

    @Override
    public void updateFaithSingle(Integer faithPosition, Integer lorenzoFaith) {
        updateFaith(faithPosition);
        this.lorenzoFaith = lorenzoFaith;
        System.out.println("Lorenzo's Faith is: " + this.lorenzoFaith);
    }

    @Override
    public void checkPopeLineSingle(boolean favorActive, Integer checkPosition, Integer faithPosition, Integer lorenzoFaith, boolean lorenzoFavorActive) {
        checkPopeLine(favorActive, checkPosition, faithPosition);
        if(lorenzoFavorActive){
            System.out.format("Lorenzo has activated the pope favor number: %d\n", (checkPosition+1));
            this.lorenzoFaith=lorenzoFaith;
            this.lorenzoFaithMark[checkPosition] = true;
            System.out.println("Lorenzo's Faith is: " + this.lorenzoFaith);
        }
        else{
            System.out.format("Lorenzo has discharged the pope favor number: %d\n", (checkPosition+1));
            System.out.println("Lorenzo's Faith is: " + this.lorenzoFaith);
            this.lorenzoFaith=lorenzoFaith;
        }
    }

    @Override
    public void endGame(HashMap<Integer, Map.Entry<String, Integer>> playerRanking, boolean win) {
        System.out.println("Game has ended");
        for(Integer pos : playerRanking.keySet()){
            System.out.println(pos + ": " + playerRanking.get(pos).getKey() + " Score: " + playerRanking.get(pos).getValue());
        }

    }

    @Override
    public void resolveUnknown(HashMap<Integer, HashMap<Resource, Integer>> inputCard, HashMap<Integer, HashMap<Resource, Integer>> outputCard, ArrayList<Integer> selectedCard) {
        System.out.println("There are some unknown resources to resolve");
        HashMap<Integer, HashMap<Resource, Integer>> inputCardCopy = new HashMap<>(inputCard);
        HashMap<Integer, HashMap<Resource, Integer>> outputCardCopy = new HashMap<>(outputCard);
        for(Integer code : inputCard.keySet()){
            System.out.println("Card code: " + code);
            System.out.println("Input: ");
            for(Resource r : inputCard.get(code).keySet()){
                if(r.equals(Resource.UNKNOWN)){
                    ArrayList<Resource> toAdd = getUnknownResource(inputCard.get(code).get(r));
                    inputCardCopy.get(code).remove(Resource.UNKNOWN);
                    toAdd.forEach(resource -> {
                        if (inputCardCopy.get(code).containsKey(resource)) {
                            inputCardCopy.get(code).put(resource, (inputCardCopy.get(code).get(resource) +1));
                        }
                        else
                            inputCardCopy.get(code).put(resource, 1);
                    });
                }
            }
            System.out.println("Output: ");
            for(Resource r : outputCard.get(code).keySet()){
                if(r.equals(Resource.UNKNOWN)){
                    ArrayList<Resource> toAdd = getUnknownResource(outputCard.get(code).get(r));
                    outputCardCopy.get(code).remove(Resource.UNKNOWN);
                    toAdd.forEach(resource -> {
                        if (outputCardCopy.get(code).containsKey(resource)) {
                            outputCardCopy.get(code).put(resource, (outputCardCopy.get(code).get(resource) +1));
                        }
                        else
                            outputCardCopy.get(code).put(resource, 1);
                    });
                }
            }
        }

        ProductionMessage message = new ProductionMessage(this.gameId, this.playerId);
        message.setSelectedCard(selectedCard);
        message.setSelectCard(true);
        message.setResolveUnknown(true);
        message.setInputCard(inputCardCopy);
        message.setOutputCard(outputCardCopy);
        controller.sendMessageToServer(message);
    }

    @Override
    public void looseResourceIntoMarbleMarket(ArrayList<Resource> returnedResource) {
        System.out.println("Attention: some resources may be lost!!");
        getResourceToPlace(returnedResource);
    }

    @Override
    public void moveAppliedIntoMarbleMarket(ArrayList<Resource> returnedResource) {
        System.out.println("Resource has been moved correctly");
        getResourceToPlace(returnedResource);
    }

    @Override
    public void activateResourceLeaderCard(Integer cardCode, Resource res, Row row) {
        HashMap<Resource, Integer> add = new HashMap<>();
        add.put(res, 1);
        this.warehouse.put(row, add);
    }

    private ArrayList<Resource> getUnknownResource(int numberOfUnknown) {
        System.out.println("You have to resolve " + numberOfUnknown + " unknown resource");
        ArrayList<Resource> toReturn = new ArrayList<>();
        for(int i=0; i<numberOfUnknown; i++){
            System.out.println("Select one resource: SHIELD, STONE, COIN, SERVANT");
            Resource res = null;
            try{
                res = Resource.valueOf(scanner.nextLine());
            }catch (IllegalArgumentException e){
                res = Resource.UNKNOWN;
            }
            while(res.equals(Resource.UNKNOWN)){
                System.out.println("Invalid input");
                try{
                    res = Resource.valueOf(scanner.nextLine());
                }catch (IllegalArgumentException e){
                    res = Resource.UNKNOWN;
                }
            }
            toReturn.add(res);
        }
        return toReturn;
    }

    private void showCardInProductionLine(HashMap<Integer, Integer> cards){
        if(cards.isEmpty()){
            System.out.println("Empty");
            return;
        }
        for(Integer code : cards.keySet()){
            if(code.equals(0)){
                System.out.println("This is the base production card");
                JSONObject card = jsonCardsProduction.get(code);
                System.out.format("CARD CODE: %s %5s", ((String) card.get("card_code")), "");
                HashMap<Resource, Integer> input = parseHashMapResources((JSONObject) card.get("card_input"));
                System.out.print("Card input: ");
                for (Resource res : input.keySet()) {
                    System.out.print(res.toString() + " " + input.get(res) + " ");
                }
                System.out.format("%7s", "");
                HashMap<Resource, Integer> output = parseHashMapResources((JSONObject) card.get("card_output"));
                System.out.print("Card output: ");
                for (Resource res : output.keySet()) {
                    System.out.print(res.toString() + " " + output.get(res) + " ");
                }
                System.out.print("\n\n");
            }
            else {
                JSONObject card = jsonCardsProduction.get(code);
                System.out.format("CARD CODE: %s %5s", ((String) card.get("card_code")), "");
                System.out.format("POSITION: %s \n", cards.get(code));
                System.out.format("Card level: %s %5s", ((String) card.get("card_level")), "");
                System.out.format("Card faction: %s %5s", ((String) card.get("card_faction")), "");
                System.out.format("Card value: %s %5s", ((String) card.get("card_value")), "");
                HashMap<Resource, Integer> price = parseHashMapResources((JSONObject) card.get("card_price"));
                System.out.print("Card price: ");
                for (Resource res : price.keySet()) {
                    System.out.print(res.toString() + " " + price.get(res) + " ");
                }
                System.out.println();
                HashMap<Resource, Integer> input = parseHashMapResources((JSONObject) card.get("card_input"));
                System.out.print("Card input: ");
                for (Resource res : input.keySet()) {
                    System.out.print(res.toString() + " " + input.get(res) + " ");
                }
                System.out.format("%7s", "");
                HashMap<Resource, Integer> output = parseHashMapResources((JSONObject) card.get("card_output"));
                System.out.print("Card output: ");
                for (Resource res : output.keySet()) {
                    System.out.print(res.toString() + " " + output.get(res) + " ");
                }
                System.out.print("\n\n");
            }
        }
    }

    private void showBuyableCard(HashMap<Integer, Boolean> cards){
        for(Integer code : cards.keySet()){
            JSONObject card = jsonCardsProduction.get(code);
            System.out.format("CARD CODE: %s %5s", ((String)card.get("card_code")), "" );
            if(cards.get(code))
                System.out.format("BUYABLE: YES \n");
            else
                System.out.format("BUYABLE: NO \n");

            System.out.format("Card level: %s %5s", ((String)card.get("card_level")), "");
            System.out.format("Card faction: %s %5s", ((String)card.get("card_faction")), "");
            System.out.format("Card value: %s %5s", ((String)card.get("card_value")), "");
            HashMap<Resource, Integer> price = parseHashMapResources((JSONObject) card.get("card_price"));
            System.out.print("Card price: ");
            for(Resource res : price.keySet()){
                System.out.print(res.toString() + " " + price.get(res) + " ");
            }
            System.out.println();
            HashMap<Resource, Integer> input = parseHashMapResources((JSONObject) card.get("card_input"));
            System.out.print("Card input: ");
            for(Resource res : input.keySet()){
                System.out.print(res.toString() + " " + input.get(res) + " ");
            }
            System.out.format("%7s", "");
            HashMap<Resource, Integer> output = parseHashMapResources((JSONObject) card.get("card_output"));
            System.out.print("Card output: ");
            for(Resource res : output.keySet()){
                System.out.print(res.toString() + " " + output.get(res) + " ");
            }
            System.out.println("\n\n");
        }
    }

    private void showCardInLeaderCard(ArrayList<Integer> cards){
        for(Integer code : cards){
            JSONObject card = jsonCardsProduction.get(code);
            System.out.format("CARD CODE: %s %5s", ((String)card.get("card_code")), "" );
            System.out.format("Card value: %s %5s", ((String)card.get("card_value")), "");
            System.out.format("Specified resource: %s %5s", ((String)card.get("specified_resource")), "");
            System.out.format("Card type: %s %5s\n", ((String) card.get("card_type")), "");
            HashMap<Faction, Map.Entry<Integer, Integer>> price = parseHashMapFaction((JSONObject) card.get("price_cards"));
            System.out.print("Price cards: ");
            for(Faction fac : price.keySet()){
                System.out.print(fac.toString() + " Value: " + price.get(fac).getKey() + " Quantity: " + price.get(fac).getValue() + "\n");
            }
            System.out.println();
            HashMap<Resource, Integer> input = parseHashMapResources((JSONObject) card.get("price_resources"));
            System.out.print("Price resource: ");
            for(Resource res : input.keySet()){
                System.out.print(res.toString() + " " + input.get(res) + " ");
            }
            System.out.format("%7s", "");
            System.out.println("\n\n");
        }
    }

    private HashMap<Resource, Integer> parseHashMapResources(JSONObject resources){
        Resource[] keys = Resource.values();
        HashMap<Resource, Integer> temp = new HashMap<>();
        for(Resource key : keys){
            String resource = key.toString().toLowerCase();
            if(( resources.get(resource)) != null){
                temp.put(key, Integer.parseInt((String) resources.get(resource)));
            }
        }
        return temp;
    }

    private HashMap<Faction, Map.Entry<Integer, Integer>> parseHashMapFaction(JSONObject faction){
        Faction[] keys = Faction.values();
        HashMap<Faction, Map.Entry<Integer, Integer>> temp = new HashMap<>();
        Integer[] cardValue = {0, 1, 2, 3};
        for(Faction key : keys){
            String fac = key.toString().toLowerCase();
            if(faction.get(fac)!=null){
                JSONObject t = (JSONObject) faction.get(fac);
                for(Integer i : cardValue){
                    String value = i.toString();
                    if(t.get(value)!=null){
                        temp.put(key, new AbstractMap.SimpleEntry<Integer, Integer>(i, Integer.parseInt((String) t.get(value))));
                    }
                }
            }
        }
        return temp;
    }


}
