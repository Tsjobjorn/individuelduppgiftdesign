import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Customer extends Person {
    Scanner scanner = new Scanner(System.in);


    private List<Animal> petList = new ArrayList<>();


    public List<Animal> getPetList() {
        return petList;
    }

    public void setPetList(List<Animal> petList) {
        this.petList = petList;
    }

    public Customer(String name, String phoneNr) {
        super(name, phoneNr);
    }



    public void addPetNameAndType() {

        System.out.println("Pet type? Dog, Cat, Rabbit, Bird.");
        String petType = addPetType();
        System.out.println("Pet name?");
        String petName = addPetName();
        setPetType(petType, petName);
    }

    public String addPetName() {
        String petName = "";
        while (petName.isBlank()) {
            petName = scanner.nextLine();
            if (petName.isBlank()) {
                System.out.println("Invalid input. Try again.");
            }
        }
        return petName.trim();
    }


    public String addPetType() {



        while (true) {
            String petType = scanner.nextLine().trim();
            switch (petType.toLowerCase()) {
                case "dog":
                case "cat":
                case "bird":
                case "rabbit":
                    break;
                default:
                    System.out.println("Invalid input. Try again.");
                    continue;
            }
            return petType;
        }

    }


    public void setPetType(String petType, String petName) {
        switch (petType.toLowerCase()) {
            case "dog" -> petList.add(new Dog(petName));
            case "cat" -> petList.add(new Cat(petName));
            case "rabbit" -> petList.add(new Rabbit(petName));
            case "bird" -> petList.add(new Bird(petName));
            default -> System.out.println("Invalid pet type");
        }
    }
}