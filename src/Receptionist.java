import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Receptionist implements ProtocolFunctionInterface {

    public static List<Customer> customerList = new ArrayList<>(); //Lista av typen Customer.  //
    private static final Receptionist instance = new Receptionist();  // Singleton instans av Receptionistklassen
    private static final Scanner scan = new Scanner(System.in);  // global användare av en scanner.

    public static List<Customer> getCustomerList() {
        return customerList;
    }

    private Receptionist() {
    }

    public static Receptionist getInstance() {
        return instance;
    }


    public static Iterator getIterator() {
        Iterator var = customerList.iterator();
        return var;
    }

    private static void printInformationFromList() {
        System.out.println("Customers in system:");
        System.out.println("Name\t\t\tPhone Number\tN.O Pets");

        Iterator var = getIterator();

        while (var.hasNext()) {
            Customer c = (Customer) var.next();
            String name = c.getName();
            String phNumber = c.getPhoneNr();
            String paddedName = String.format("%-16s", name);
            String paddedphNumber = String.format("%-16s", phNumber);
            System.out.println(paddedName + paddedphNumber + c.getPetList().size());
        }
        System.out.println();
    }


    private void addPetToCustomer() {
        boolean customerFound = false;
        while (!customerFound) {
            System.out.println("Enter the name of customer you wish to add another pet to");
            printInformationFromList();
            String userInput = scan.nextLine();
            Iterator var = getIterator();
            while (var.hasNext()) {
                Customer c = (Customer) var.next();
                if (c.getName().equalsIgnoreCase(userInput)) {
                    c.addPetNameAndType();
                    WriteToFile.getInstance().writeCustomerInfoToFile(c);
                    customerFound = true;
                }
            }
            if (!customerFound) {
                System.out.println("No customer found.");
            }
        }
    }

    private void showPetsofSelectedCustomer() {
        printInformationFromList();
        Iterator var = getIterator();

        System.out.println("Enter customer name");
        String s = scan.nextLine();
        while (var.hasNext()) {
            Customer c = (Customer) var.next();
            if (s.equalsIgnoreCase(c.getName())) {
                System.out.println(c.getName() + " has the following pet(s).");
                for (int i = 0; i < c.getPetList().size(); i++) {
                    System.out.println(c.getPetList().get(i).petName);
                }
            }

        }
        System.out.println();
    }

    protected void addCustomer() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Customer name?");
        String customerName = scan.nextLine();
        ReadFromFile.getInstance().checkInput(customerName);
        System.out.println("Customer phone number?");
        String addPhoneNr = scan.nextLine();
        ReadFromFile.getInstance().checkInput(addPhoneNr);
        Receptionist.customerList.add(new Customer(customerName, addPhoneNr));
        Receptionist.customerList.get(Receptionist.customerList.size() - 1).addPetNameAndType();
        System.out.println(customerName + " has been added as a customer.");

        //Skickar in customer objektet i metoden writeCustomerInfoToFIle. Använder sig av
        // nuvarande storleken av listan för att skicka rätt index.

        WriteToFile.getInstance().writeCustomerInfoToFile(Receptionist.customerList.get(Receptionist.customerList.size() - 1));

        Receptionist.getInstance().protocol();
    }

    //Metod som fyller på befintliga kunder i filen till customerList

    @Override
    public void protocol() {
        if (customerList.size() == 0) {
            ReadFromFile.getInstance().readFillCustomerListFromFile(); //Läser in befintliga kunder från filen till customerList.
        }
        printChoices();
        String s = scan.nextLine();
        switch (s) {
            case "1" -> Receptionist.getInstance().addCustomer();
            // Tar emot states och delegerar användaren till specifika delar av programmet.

            case "2" -> addPetToCustomer();

            case "3" -> printInformationFromList();

            case "4" -> showPetsofSelectedCustomer();

            case "5" -> AnimalHandler.getInstance().protocol();
            // Om du vill byta till att vara en djurhanterare istället för receptionist
            default -> System.err.println("Invalid input. Try again");
        }
        protocol();
    }

    @Override
    public void printChoices() {  // återkommande kod i programmet som delades upp i en metod istället.
        System.out.println("""
                1 to add customer
                2 to add pet to existing customer
                3 to add print customer information
                4 to show a customer's pets
                5 to go to Animal handler
                """);
    }

}

