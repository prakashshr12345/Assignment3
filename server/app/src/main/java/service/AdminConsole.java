package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import modal.User;

@Service
public class AdminConsole {

    List<User> users;

    public AdminConsole() {
        users = new ArrayList<>();
        users.add(new User(1, "admin", "adminpassword", "admin"));
        users.add(new User(2, "user1", "user1password", "user"));
        users.add(new User(2, "user2", "user2password", "user"));
    }

    // @GetMapping("/checkuser")
    // @Override
    public void UserCheck() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please select an option:");
        System.out.println("1. Login");
        System.out.println("2. Add User");
        System.out.println("3. Modify User");
        System.out.println("4. Delete User");
        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case 1:
                loginUser(sc);
                break;
            case 2:
                addUser(sc);
                break;
            case 3:
                modifyUser(sc);
                break;
            case 4:
                deleteUser(sc);
                break;
            default:
                System.out.println("Invalid option");
        }
        sc.close();

    }

    public void loginUser(Scanner sc) {

        System.out.println("Enter username:");
        String username = sc.nextLine();

        System.out.println("Enter password:");
        String password = sc.nextLine();

        User authenticatedUser = authenticateUser(username, password);

        if (authenticatedUser != null) {
            System.out.println("Welcome to the secure console!");
            System.out.println("You have access as a " + authenticatedUser.getRole() + " user.");
        } else {
            System.out.println("Access denied. Invalid credentials.");
        }

    }

    private User authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void addUser(Scanner sc) {
        System.out.println("Enter username for the new user:");
        String username = sc.nextLine();

        System.out.println("Enter password for the new user:");
        String password = sc.nextLine();

        System.out.println("Enter role for the new user (admin/user):");
        String role = sc.nextLine();

        int userId = users.size() + 1;
        User newUser = new User(userId, username, password, role);
        users.add(newUser);

        System.out.println("user added successfully.");

    }

    public void modifyUser(Scanner sc){
        System.out.println("Enter the username to modify the user");
        String username = sc.nextLine();

        User userToModify = null;
        for(int i = 0; i< users.size(); i++){
            User user = users.get(i);
            if(user.getUsername().equals(username)){
                userToModify = user;   
                break;
            }
        }

        if(userToModify != null){
            System.out.println("Enter the new password for:" + username);
            String newPassword = sc.nextLine();

            System.out.println("Enter the new role for:" + username);
            String newRole = sc.nextLine();

            userToModify.setPassword(newPassword);
            userToModify.setRole(newRole);

            System.out.println("User modified successfully.");
        }
        else{
            System.out.println("User not found.");
        }
    }

    public void deleteUser(Scanner sc) {
        System.out.println("Enter the user you want to delete :");
        String username = sc.nextLine();

        User userToDelete = null;

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUsername().equals(username)) {
                userToDelete = user;
                break;
            }
        }

        if (userToDelete != null) {
            users.remove(userToDelete);
            System.out.println("users deleted successfully");
        } else{
            System.out.println("user not found");
        }
    }

}
