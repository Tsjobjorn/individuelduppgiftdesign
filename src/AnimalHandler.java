import java.util.Iterator;
import java.util.Scanner;

public class AnimalHandler implements ProtocolFunctionInterface {
    private static final AnimalHandler instance = new AnimalHandler();
    static Scanner scan = new Scanner(System.in);

    private AnimalHandler() {  // Tom konstruktor för singleton design pattern.
    }

    public static AnimalHandler getInstance() {  // Hämtar en instance av klassen Animalhandler (singleton)
        return instance;
    }

    private void displayUnfedPets() {
        System.out.println("\nThe following animals have not been fed:");
        Iterator var = getIterator();
        while (var.hasNext()) {
            Customer c = (Customer) var.next();
            for (int i = 0; i < c.getPetList().size(); i++) {

                if (!c.getPetList().get(i).isFed()) {
                    String petName = c.getPetList().get(i).getPetName();
                    System.out.println(petName);
                }
            }
        }
        System.out.println();
    }

    private void informationAnimal() {
        System.out.println("Pet name\t\t\tPet Type\t\tFed\t\t\t\t\tOwner"); //Skippa visa pet Type?
        Iterator var = getIterator();

        while (var.hasNext()) {
            Customer c = (Customer) var.next();

            for (int i = 0; i < c.getPetList().size(); i++) {
                String petName = c.getPetList().get(i).getPetName();
                String petType = c.getPetList().get(i).getType();
                String fedStatus;
                if (c.getPetList().get(i).isFed()) {
                    fedStatus = ("Has been fed");
                } else {
                    fedStatus = ("Needs to be fed");
                }
                String paddedpetName = String.format("%-20s", petName);
                String paddedpetType = String.format("%-16s", petType.trim());
                String paddedFedStatus = String.format("%-20s", fedStatus);
                System.out.println(paddedpetName + paddedpetType + paddedFedStatus+c.getName());
            }
        }
        System.out.println();
    }

    private static Iterator getIterator() {
        Iterator var = Receptionist.getCustomerList().iterator();
        return var;
    }

    private void feedAnimal() {
        Iterator var = Receptionist.getCustomerList().iterator();
        displayUnfedPets();
        System.out.println("Enter the name of the animal you want to set to fed.");
        String s = scan.nextLine();
        while (var.hasNext()) {
            Customer c = (Customer) var.next();
            for (int i = 0; i < c.getPetList().size(); i++) {
                if (s.equalsIgnoreCase(c.getPetList().get(i).getPetName())) {
                    c.getPetList().get(i).setFed(true);
                    c.getPetList().get(i).getFoodInfo();
                    s = scan.nextLine();
                    System.out.println(c.getPetList().get(i).getPetName() + " have been set as fed.");
                    System.out.println();
                    break;
                }
            }

        }
    }

    @Override
    public void protocol() {  // en protokollmetod för att kontrollera vilket state programmet befinner sig i.
        if (Receptionist.getCustomerList().size() == 0) {
            ReadFromFile.getInstance().readFillCustomerListFromFile();
        }
        printChoices();
        switch (scan.nextLine()) {
            case "1" -> feedAnimal();  // Case 1 skickar dig till feedAnimal() metoden osv osv.
            case "2" -> informationAnimal();
            case "3" -> Receptionist.getInstance().protocol();
            // Returnerar dig till instansen av Receptionisten och pga att det
            // är en singleton så är det alltid samma objekt och inga nya instanser skapas / sparar minne

            default -> System.out.println("Invalid input. Try again");
        }
        protocol();  // Om inte protokollet inte skickar dig vidare till en ny metod 1, 2 eller 3 så körs protokollet om.
    }

    @Override
    public void printChoices() {  // Alternativen som har för varje klass, metoden implementeras av ProtocolFunctionInterface
        System.out.println("""  
                Press 1 to feed animal
                Press 2 to get information about animals
                Press 3 to go to reception""");
    }
}
