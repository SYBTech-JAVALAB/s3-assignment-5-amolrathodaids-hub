import java.util.Scanner;

// 1. Custom Exception Class for Handling Invalid Inputs
class InvalidEmployeeDataException extends Exception {
    public InvalidEmployeeDataException(String message) {
        super(message);
    }
}

// 2. Base Class (Parent)
class Employee {
    protected String name;
    protected double salary;
    protected String role;

    // Constructor using the 'this' keyword
    public Employee(String name, double salary, String role) throws InvalidEmployeeDataException {
        // Validation for empty name
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidEmployeeDataException("Employee name cannot be empty.");
        }
        // Validation for negative salary
        if (salary < 0) {
            throw new InvalidEmployeeDataException("Salary cannot be negative.");
        }
        
        this.name = name;
        this.salary = salary;
        this.role = role;
    }

    // Method meant to be overridden by child classes
    public void displayRoleResponsibilities() {
        System.out.println("Responsibilities: General company tasks.");
    }

    // Common method to display basic info
    public void displayDetails() {
        System.out.println("\n--- Employee Details ---");
        System.out.println("Name: " + this.name);
        System.out.println("Role: " + this.role);
        System.out.println("Base Salary: $" + this.salary);
        // Calls the overridden method based on the object type
        displayRoleResponsibilities(); 
    }
}

// 3. Child Class 1: Manager using Inheritance
class Manager extends Employee {
    private double bonus;

    // Constructor using 'super()' to invoke parent constructor
    public Manager(String name, double salary, double bonus) throws InvalidEmployeeDataException {
        super(name, salary, "Manager"); 
        
        if (bonus < 0) {
            throw new InvalidEmployeeDataException("Bonus cannot be negative.");
        }
        this.bonus = bonus;
    }

    // Function Overriding
    @Override
    public void displayRoleResponsibilities() {
        System.out.println("Responsibilities: Manages team operations, conducts performance reviews, and sets strategic goals.");
        System.out.println("Total Compensation (Salary + Bonus): $" + (this.salary + this.bonus));
    }
}

// 4. Child Class 2: Developer using Inheritance
class Developer extends Employee {
    private String primaryLanguage;

    // Constructor using 'super()'
    public Developer(String name, double salary, String primaryLanguage) throws InvalidEmployeeDataException {
        super(name, salary, "Developer");
        
        if (primaryLanguage == null || primaryLanguage.trim().isEmpty()) {
             throw new InvalidEmployeeDataException("Programming language cannot be empty.");
        }
        this.primaryLanguage = primaryLanguage;
    }

    // Function Overriding
    @Override
    public void displayRoleResponsibilities() {
        System.out.println("Responsibilities: Writes clean code, debugs software, and builds scalable applications.");
        System.out.println("Primary Programming Language: " + this.primaryLanguage);
    }
}

// 5. Main Execution Class
public class EmployeeManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Exception Handling Block
        try {
            System.out.println("Choose role to create (1: Manager, 2: Developer): ");
            String roleInput = scanner.nextLine();
            
            // Validation for empty role input
            if(roleInput.trim().isEmpty()) {
                throw new InvalidEmployeeDataException("Role input cannot be empty.");
            }
            
            int choice = Integer.parseInt(roleInput);

            System.out.print("Enter Employee Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Base Salary: ");
            double salary = Double.parseDouble(scanner.nextLine());

            if (choice == 1) {
                System.out.print("Enter Manager Bonus: ");
                double bonus = Double.parseDouble(scanner.nextLine());
                
                Manager manager = new Manager(name, salary, bonus);
                manager.displayDetails();
                
            } else if (choice == 2) {
                System.out.print("Enter Primary Programming Language: ");
                String language = scanner.nextLine();
                
                Developer developer = new Developer(name, salary, language);
                developer.displayDetails();
                
            } else {
                throw new InvalidEmployeeDataException("Invalid role selected. Please enter 1 or 2.");
            }

        // Catches letters inputted where numbers are expected
        } catch (NumberFormatException e) {
            System.out.println("\nError: Invalid number format. Please enter valid numerical values for choices, salary, or bonus.");
        // Catches our custom logic errors (negative salaries, empty strings)
        } catch (InvalidEmployeeDataException e) {
            System.out.println("\nValidation Error: " + e.getMessage());
        // Catches any other unforeseen errors
        } catch (Exception e) {
            System.out.println("\nAn unexpected error occurred: " + e.getMessage());
        } finally {
            scanner.close(); // Prevent resource leaks
        }
    }
}