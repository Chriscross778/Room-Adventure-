
import java.util.Scanner;

public class RoomAdventure {
    // class variables
    private static Room currentRoom;
    @SuppressWarnings("FieldMayBeFinal")
    private static String[] inventory = {null, null, null, null, null};
    @SuppressWarnings("unused")
    private static String status;

    // constant
    final private static String DEFAULT_STATUS = "Sorry, I do not[verb] [noun]. Vaild verbs include 'go', 'look', and 'take'. ";

    public static void main(String[] args) {
        setupGame();

        while (true) { 
            System.out.println(currentRoom.toString());
            System.out.println("Inventory: ");

            for(int i = 0; i < inventory.length; i++){
                System.out.print(inventory[i] + " ");
            }

            System.out.println("\nWhat would you like to do? ");

            Scanner s = new Scanner(System.in);
            String input = s.nextLine();

            String[] words = input.split(" ");

            if(words.length != 2){
                status = DEFAULT_STATUS;
            }

            String verb = words[0];
            String noun = words[1];

            switch (verb){
                case "go":
                    handleGO(noun);
                    break;
                case "look":
                    handleLook(noun);
                    break;
                case "take":
                    handleTake(noun);
                    break;
                default: status = DEFAULT_STATUS;
            }
            System.out.println(status);

        }
    }

    private static void handleGO(String noun){
        status = "I don't see that room";
        String[] directions = currentRoom.getExitDirections();
        Room[] rooms = currentRoom.getExitDestinations();

        for (int i=0; i < directions.length; i++){
            // for strings we use .equals() to compare
            if (noun.equals(directions[i])){
                if (rooms[i] != null){
                    currentRoom = rooms[i];
                    status = "Changed Room";
                }
                else {
                    status = "That way is blocked look for a different way";
                }
            }
        }
    }

    private static void handleLook(String noun){
        status = "I don't see that item";
        String[] items = currentRoom.getItems();
        String[] descriptions = currentRoom.getItemDescription();

        for (int i=0; i < items.length; i++){
            if (noun.equals(items[i])){
            status = descriptions[i];
            }
        }
    }

    private static void handleTake(String noun){
        status = "I can't grab that";
        String[] grabs = currentRoom.getGrabbables();

        // maybe make a addToInventory() func?
        // maybe expaned the inventory to any number of items
        for (int i=0; i < grabs.length; i++){
            if (noun.equals(grabs[i])){
                for (int j=0; j < inventory.length; j++){
                    if (inventory[j] == null){
                        inventory[j] = noun;
                        // maybe say what item was added
                        status = "Added item to inventory";
                        break;
                    }
                }

            }
        }

    }

    public static void setupGame() {
        Room room1 = new Room("Lobby");
        Room room2 = new Room("Pool Room");
        Room room3 = new Room("Cafeteria");
        Room room4 = new Room("Hallway");
        Room room5 = new Room("Garden");

        // Room 1 setup
        String[] room1ExitDirections = {"east", "south", "north"};
        Room[] room1ExitDestinations = {room2, room3, null}; // north leads nowhere
        String[] room1Items = {"chair", "desk"};
        String[] room1ItemDescriptions = {
            "It is a chair looks old",
            "It's a desk, there is an key on it."
        };
        String[] room1Grabbables = {"key"};

        room1.setExitDirections(room1ExitDirections);
        room1.setExitDestinations(room1ExitDestinations);
        room1.setItems(room1Items);
        room1.setItemDescription(room1ItemDescriptions);
        room1.setGrabbables(room1Grabbables);

        // room 2
        String[] room2ExitDirections = {"west", "south"};
        Room[] room2ExitDestinations = {room1, room4}; 
        String[] room2Items = {"pool", "tower"};
        String[] room2ItemDescriptions = {
            "It's an empty pool look likes there is a boltcutter down there",
            "The tower looks unstable maybe I should not go up there"
        };
        String[] room2Grabbables = {"boltcutter"};

        room2.setExitDirections(room2ExitDirections);
        room2.setExitDestinations(room2ExitDestinations);
        room2.setItems(room2Items);
        room2.setItemDescription(room2ItemDescriptions);
        room2.setGrabbables(room2Grabbables);

        // room 3
        String[] room3ExitDirections = {"east", "north", "west"};
        Room[] room3ExitDestinations = {room4, room1, room5}; 
        String[] room3Items = {"Buffet", "trash"};
        String[] room3ItemDescriptions = {
            "There is rotted food here ew",
            "There is a bottle here looks clean"
        };
        String[] room3Grabbables = {"bottle"};

        room3.setExitDirections(room3ExitDirections);
        room3.setExitDestinations(room3ExitDestinations);
        room3.setItems(room3Items);
        room3.setItemDescription(room3ItemDescriptions);
        room3.setGrabbables(room3Grabbables);

        // room 4
        String[] room4ExitDirections = {"west", "north"};
        Room[] room4ExitDestinations = {room3, room2}; 
        String[] room4Items = {"table", "lamp"};
        String[] room4ItemDescriptions = {
            "A nice and sturdy table",
            "A lamp sadly it doesn't work"
        };
        String[] room4Grabbables = {};

        room4.setExitDirections(room4ExitDirections);
        room4.setExitDestinations(room4ExitDestinations);
        room4.setItems(room4Items);
        room4.setItemDescription(room4ItemDescriptions);
        room4.setGrabbables(room4Grabbables);

        // room 5 
        String[] room5ExitDirections = {"east", "north"};
        Room[] room5ExitDestinations = {room3, null}; 
        String[] room5Items = {"gate", "field"};
        String[] room5ItemDescriptions = {
            "The gate looks like it is lock and has chains around it",
            "There a flower field it looks great who would have known there is a flower I could grab"
        };
        String[] room5Grabbables = {"flower"};

        room5.setExitDirections(room5ExitDirections);
        room5.setExitDestinations(room5ExitDestinations);
        room5.setItems(room5Items);
        room5.setItemDescription(room5ItemDescriptions);
        room5.setGrabbables(room5Grabbables);

        currentRoom = room1;
    }
}



class Room {
    private String name;
    private String[] exitDirections; // north, south, east, west
    private Room[] exitDestinations;
    private String[] items;
    private String[] itemDescription;
    private String[] grabbables;

    public Room(String name) {
        this.name = name;
    }

    public void setExitDirections(String[] exitDirections) {
        this.exitDirections = exitDirections;
    }

    public String[] getExitDirections() {
        return exitDirections;
    }

    public void setExitDestinations(Room[] exitDestinations) {
        this.exitDestinations = exitDestinations;
    }

    public Room[] getExitDestinations() {
        return exitDestinations;
    }

    public void setItems(String[] items) {
        this.items = items;
    }

    public String[] getItems() {
        return items;
    }

    public void setItemDescription(String[] itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String[] getItemDescription() {
        return itemDescription;
    }

    public void setGrabbables(String[] grabbables) {
        this.grabbables = grabbables;
    }

    public String[] getGrabbables() {
        return grabbables;
    }

    
    public String toString() {
        String result = "\nLocation: " + name;

        result += "\nYou see: ";

        // for i loop
        for (int i = 0; i < items.length; i++) {
            result += items[i] + " ";
        }

        result += "\nExits: ";
        //for each loop
        for (String direction : exitDirections) {
            result += direction + " ";
        }

        return result + "\n";
    }
}