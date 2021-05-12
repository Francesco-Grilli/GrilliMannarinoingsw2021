package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import it.polimi.ingsw.GrilliMannarino.Message.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
    public void activateLeaderCard(Integer activatedCard) {

    }

    @Override
    public void sellingLeaderCard(Integer cardCode) {

    }

    @Override
    public void viewError(String errorMessage) {
        System.out.println(errorMessage.toUpperCase(Locale.ROOT));
        selectAction();
    }

    @Override
    public void showMarbleMarket(Marble[][] marbleList, Marble marbleOut) {
        String rowColumn;
        int number;

        System.out.println("Marble Market:");
        System.out.println();
        String s = "";
        int i=0;
        System.out.format("%15s", s);
        for(int x=0; x<marbleList[0].length; x++){
            String column = "Column:" + (x+1);
            System.out.format("%15s", column);

        }
        System.out.println();
        for (int x=0; x<marbleList.length; x++){
            String row = "Row: " + (i+1);
            System.out.format("%15s", row);
            i++;
            for(int y=0; y<marbleList[x].length; y++){
                System.out.format("%15s", marbleList[x][y].toString());
            }
            System.out.println();
        }
        String out = "Marble Out " + marbleOut.toString();
        System.out.format("%15s", out);
        System.out.println();

        System.out.println("Insert R for Row and C for Column");
        String scan = scanner.nextLine();
        while(!scan.equals("C") && !scan.equals("R")){
            System.out.println("Error, Invalid Input");
            scan = scanner.nextLine();
        }
        System.out.println("Insert number of Row/Column");
        rowColumn = scan;
        if(scan.equals("R")){
            int row = scanner.nextInt();
            while(row > 3 || row < 1){
                System.out.println("Error, Invalid Input");
                row = scanner.nextInt();
            }
            number = row;
        }
        else{
            int column = scanner.nextInt();
            while(column > 4 || column < 1){
                System.out.println("Error, Invalid Input");
                column = scanner.nextInt();
            }
            number = column;
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

        printWareHouse();
        System.out.println("Resources needed to be placed: ");
        resources.forEach(res -> System.out.format("%15s", res.toString()));
        System.out.println();

        getResourceToPlace(resources);
    }

    private void getResourceToPlace(ArrayList<Resource> resources) {

        System.out.println("Do you want to add Resources into warehouse? Otherwise they will be lost, Y/N");
        if(scanner.nextLine().equals("N")){
            MarbleMarketMessage message = new MarbleMarketMessage(this.gameId, this.playerId);
            message.setReturnedResource(resources);
            message.setDestroyRemaining(true);
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
        for(Row r : this.warehouse.keySet()){
            System.out.format("%15s", r.toString());
            if(this.warehouse.get(r).isEmpty()){
                for(int x=0; x<r.getMaxValue(); x++)
                    System.out.format("%15s", "Empty");
            }
            else{
                for(Resource res : this.warehouse.get(r).keySet()){
                    for(int x=0; x<this.warehouse.get(r).get(res); x++){
                        System.out.format("%15s", res.toString());
                    }
                    if(r.getMaxValue()>this.warehouse.get(r).get(res)){
                        for(int x=0; x<(r.getMaxValue() - this.warehouse.get(r).get(res)); x++){
                            System.out.format("%15s", "Empty");
                        }
                    }
                }
            }
            System.out.println();
        }
    }

    @Override
    public void addedResource(Resource resourceType, Row insertRow, ArrayList<Resource> remainingResource, boolean addResourceCorrect) {
        if(addResourceCorrect){
            System.out.println("The resource selected: " + resourceType.toString() + " has been added correctly");
            printWareHouse();
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
    public void showCardMarket(HashMap<Integer, Boolean> buyableCard) {
        if(buyableCard!=null) {
            showBuyableCard(buyableCard);

            System.out.println("Enter the card code of the production card you want to buy");
            Integer cardCode = null;
            try {
                cardCode = Integer.parseInt(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                cardCode = 0;
            }
            while (!buyableCard.containsKey(cardCode) || !buyableCard.get(cardCode)) {
                if(!buyableCard.containsKey(cardCode))
                    System.out.println("Invalid Input");
                else
                    System.out.println("You can't buy that card");
                try {
                    cardCode = Integer.parseInt(scanner.nextLine());
                } catch (IllegalArgumentException e) {
                    cardCode = 0;
                }
            }

            BuyProductionCardMessage message = new BuyProductionCardMessage(this.gameId, this.playerId);
            message.setSelectedCard(cardCode);
            showCardInProductionLine(this.productionLine);
            Integer position = null;
            System.out.println("Enter the position you want to place your card");
            try{
                position = Integer.parseInt(scanner.nextLine());
            }catch (IllegalArgumentException e){
                position = -1;
            }
            while(position<0){
                System.out.println("Invalid position");
                try{
                    position = Integer.parseInt(scanner.nextLine());
                }catch (IllegalArgumentException e){
                    position = -1;
                }
            }

            message.setPositionCard(position);
            controller.sendMessageToServer(message);

        }
        else
            viewError("No card in the market to show");
    }


    @Override
    public void setCardIntoProductionLine(Integer selectedCard, Integer positionCard) {

    }

    public void selectAction(){
        System.out.println("What do you want to do?");
        if(this.normalAction) {
            System.out.println("B for buying a card to the market");
            System.out.println("M for going to the Marble market");
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
            }else {
                System.out.println("Invalid Input");
                s = scanner.nextLine();
            }

        }
    }

    private void moveResource() {
        MoveResourceMessage moveMessage = new MoveResourceMessage(this.gameId, this.playerId);
        System.out.println("Warehouse: ");
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
        System.out.println("Do you want to force the reverse? Doing some resources may be lost. Y/N");
        if(scanner.nextLine().equals("Y")){
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

    }

    @Override
    public void updateFaith(Integer faithPosition) {

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
    public void startGame() {
        loadProductionCard();
        loadLeaderCard();
    }

    private void loadLeaderCard() {
        JSONArray array;
        JSONParser parser = new JSONParser();
        try {
            FileReader file = new FileReader("leader_card.json");
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
    public void updateResources(HashMap<Resource, Integer> chestResources, HashMap<Row, HashMap<Resource, Integer>> wareHouseResources) {

    }

    @Override
    public void moveApplied() {

    }

    @Override
    public void looseResource() {

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
        StartGameMessage message = new StartGameMessage(this.gameId, this.playerId);
        controller.sendMessageToServer(message);
    }

    @Override
    public void enteredNewGame(String messageString, Integer gameId) {

    }

    @Override
    public void finishedNormalAction(String message) {
        System.out.println(message);
        this.normalAction = false;
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
            while (!action.equals("A") && !action.equals("S")) {
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
            }
            controller.sendMessageToServer(message);
        }
        else
            viewError("No leader card available");
    }

    @Override
    public void showProductionCard(HashMap<Integer, Integer> productionCard) {
        if(productionCard!=null) {
            this.productionLine = new HashMap<>(productionCard);
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

            ProductionMessage message = new ProductionMessage(this.gameId, this.playerId);
            message.setSelectCard(true);
            message.setSelectedCard(producingCard);
            controller.sendMessageToServer(message);
        }
        else
            viewError("No production card available");
    }

    @Override
    public LoginMessage setUpInformation() {
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
        LoginMessage message = new LoginMessage(nickname);
        if(action.equals("N"))
            message.setNewAccount(true);

        return message;
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

    private void showCardInProductionLine(HashMap<Integer, Integer> cards){
        for(Integer code : cards.keySet()){
            JSONObject card = jsonCardsProduction.get(code);
            System.out.format("CARD CODE: %s %5s",((String)card.get("card_code")), "");
            System.out.format("POSITION: %s \n", cards.get(code));
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
            System.out.print("\n\n");
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


}
