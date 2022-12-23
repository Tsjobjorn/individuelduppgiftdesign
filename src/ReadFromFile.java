import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class ReadFromFile {
    private static final ReadFromFile read = new ReadFromFile();

    private ReadFromFile() {
    }

    public static ReadFromFile getInstance() {
        return read;
    }

    protected void readFillCustomerListFromFile() {

        try (Scanner readTextFile = new Scanner(new File(FilePath.CUSTOMER_INFO_FILE.filePath))) {
            while (readTextFile.hasNextLine()) {
                boolean newCustomer = true;
                if (readTextFile.hasNextLine()) {
                    String customerInfo = readTextFile.nextLine();



                    for (int i = 0; i < Receptionist.customerList.size(); i++) {
                        if(Receptionist.customerList.get(i).getName().equalsIgnoreCase(customerInfo.substring(0, customerInfo.indexOf(':')))){
                            newCustomer = false;
                        }
                    }
                    if (newCustomer) {
                        Receptionist.customerList.add(
                                new Customer((customerInfo.substring(0,
                                        customerInfo.indexOf(':'))),
                                        customerInfo.substring(customerInfo.indexOf(':') + 1)));

                        if (readTextFile.hasNextLine()) {
                            String petInfo = readTextFile.nextLine();

                            Receptionist.customerList.get(
                                    Receptionist.customerList.size() - 1).setPetType(petInfo.substring(0,
                                            petInfo.indexOf(':')),
                                    petInfo.substring(petInfo.indexOf(':') + 1));
                        }


                    } else if (!newCustomer) {
                        if (readTextFile.hasNextLine()) {
                            String petInfo = readTextFile.nextLine();
                            for (int i = 0; i < Receptionist.customerList.size(); i++) {
                                if(Receptionist.customerList.get(i).getName().equalsIgnoreCase(customerInfo.substring(0, customerInfo.indexOf(':')))){
                                    Receptionist.customerList.get(i).setPetType(petInfo.substring(0,
                                                    petInfo.indexOf(':')),
                                            petInfo.substring(petInfo.indexOf(':') + 1));
                                }
                            }
                        }
                        newCustomer=true;
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    }

    protected void checkInput(String input) {
        if (input.isBlank()) {
            System.err.println("Invalid input. Try again.");
            Receptionist.getInstance().addCustomer();
        }
    }
}