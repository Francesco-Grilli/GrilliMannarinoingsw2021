package it.polimi.ingsw.GrilliMannarino;

import it.polimi.ingsw.GrilliMannarino.GameData.Marble;
import it.polimi.ingsw.GrilliMannarino.GameData.Resource;
import it.polimi.ingsw.GrilliMannarino.GameData.Row;
import it.polimi.ingsw.GrilliMannarino.Message.BuyProductionCardMessage;
import it.polimi.ingsw.GrilliMannarino.Message.MarbleMarketMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class CliView extends ClientView {

    private Scanner scanner;
    private ClientController controller;

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
            ArrayList<Marble> marble = new ArrayList<>();
            for(int x=0; x<arr.size(); x++){
                System.out.format("%15s", arr.get(x).toString());
                marble.add(arr.get(x));
            }
            System.out.println();
            Marble m = null;
            try {
                m = Marble.valueOf(scanner.nextLine());
            }
            catch(IllegalArgumentException e){
            }
            while(!marble.contains(m)){
                System.out.println("Invalid Input");
                try {
                    m = Marble.valueOf(scanner.nextLine());
                }
                catch(IllegalArgumentException e){
                }
            }
            toReturn.add(m);
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
            controller.sendMessageToServer(message);
            return;
        }
        getResourceToPlace(remainingResource);
    }

    @Override
    public void showProductionCard(HashMap<Integer, Boolean> buyableCard) {

    }

    @Override
    public void setCardIntoProductionLine(Integer selectedCard, Integer positionCard) {

    }

    @Override
    public void isYourTurn() {
        System.out.println("This is your turn! Play it wisely");
        System.out.println("What do you want to do?");
        System.out.println("B for buying a card to the market");
        System.out.println("M for going to the Marble market");
        System.out.println("L for Leader card action");
        System.out.println("S for swapping resources from your warehouse");
        String s = scanner.nextLine();
        switch (s){
            case "M":
                MarbleMarketMessage marbleMessage = new MarbleMarketMessage(this.gameId, this.playerId);
                marbleMessage.setDisplayMarbleMarket(true);
                controller.sendMessageToServer(marbleMessage);
                break;
            case "B":
                BuyProductionCardMessage buyProductionMessage = new BuyProductionCardMessage(this.gameId, this.playerId);
                buyProductionMessage.setDisplayCard(true);
                controller.sendMessageToServer(buyProductionMessage);
                break;
        }
    }

    @Override
    public void checkPopeLine(boolean favorActive, Integer checkPosition, Integer faithPosition) {

    }

    @Override
    public void updateFaith(Integer faithPosition) {

    }

    @Override
    public void startGame() {

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

    }

    @Override
    public void enteredNewGame(String messageString, Integer gameId) {

    }

    @Override
    public void finishedNormalAction() {

    }


}
