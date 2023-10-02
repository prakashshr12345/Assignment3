/**
 * Modify this class to add the authentication and access control features specified in the assignment specifications.
 */

package server;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerResponse.Context;
// import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import modal.User;
import service.AdminConsole;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class Controller {
 
    
    @GetMapping("/")
    public String getHello() {
        AdminConsole adminConsole = new AdminConsole();
        adminConsole.UserCheck();
        return "login successful"; // Assuming this is a response for your frontend
    }

    /*
     * This function runs when the url http://<host>:<port>/ is requested,
     * sub-urls can be specified but putting different arguments
     * into @GetMapping(args)
     * (or PostMapping(args) for a post http message)
     * The return value is sent over the network to the client.
     */
    // @GetMapping("/")
    // public String getHello() {
    
    //     return "login successfull";
    // }

    /*
     * For this, we show how to use information in the url as an argument to the
     * function.
     * The contents of the curly brackets in the route are the name of variable
     * which gains the value
     * of what the client enters in its place. E.g. if the client requests
     * `http://127.0.0.1:2250/hello/charlie`
     * then name = "charlie".
     */
    @GetMapping("hello/{name}")
    public String getHelloName(@PathVariable String name) {
        return String.format("Hello %s", name);
    }

    /*
     * This shows an example of a post request, one of the types of http message.
     * This allows a client to send information inside the payload part of the http
     * packet, and the server can read that information in the form of the arguments
     * to the function.
     * If json data is sent, you can instead use a Class with corresponding
     * variables as the argument.
     */
    @PostMapping("/hello")
    public String postHello(@RequestBody String name) {
        return String.format("Hello %s", name);
    }

    /*
     * With this, we show how to redirect a client to a different function that we
     * provide.
     */
    @GetMapping("/redirect")
    public String moveToHelloName() {
        return getHelloName("Bob");
    }

    /*
     * Since this is an http server, it is stateless, allowing it handle many
     * clients simultaneously, but also
     * meaning we can not directly track users as they progress through the app.
     * So, instead to keep information in any long term basis, we need to save to
     * external service, such as a file.
     * Note that using a file is in reality bad practice, but it is convenient for
     * this assignment.
     */
    @GetMapping("/visitors")
    public String visitorList(@RequestParam String name) throws Exception {
        BufferedWriter fileOut = new BufferedWriter(new FileWriter("visitors.txt", true));
        fileOut.append(name + "\n");
        fileOut.close();
        Scanner fileIn = new Scanner(new File("visitors.txt"));
        String visitorListContents = "";
        while (fileIn.hasNextLine()) {
            visitorListContents += fileIn.nextLine() + "\n";
        }
        fileIn.close();
        return visitorListContents;
    }

    /*
     * With this, we demonstrate how more complex data can be returned as a
     * dictionary, specifically,
     * it gets translated into a json file and sent back to the client. This can be
     * a bit limited, so
     * it may be convenient to send back custom objects in a similar way.
     */
    @GetMapping("/json")
    public Map<String, String> jsonData() {
        Map<String, String> data = new HashMap<>();
        data.put("data", "hello");
        data.put("numbers", "1, 2, 3");
        return data;
    }

    // End example functions

    // @PostMapping("/admin_console")
    // public String adminConsole(@RequestBody String username, @RequestBody String password) {

    //     return "Access denied";

    // }

    @PostMapping("/audit_expenses")
    public String auditExpenses() {
        try (
                Scanner fileIn = new Scanner(new File("expenses.txt"))) {
            String expenses = "";
            while (fileIn.hasNextLine()) {
                expenses += fileIn.nextLine() + "\n";
            }
            return expenses;
        } catch (FileNotFoundException fne) {
            return "No expenses yet";
        }
    }

    @PostMapping("/add_expense")
    public String addExpense(@RequestBody String newExpense) {
        try (
                BufferedWriter fileOut = new BufferedWriter(new FileWriter("expenses.txt", true))) {
            fileOut.append(newExpense + "\n");
            return "Expense added";
        } catch (IOException ioe) {
            return "Unable to add expense";
        }
    }

    @PostMapping("/audit_timesheets")
    public String auditTimesheets() {
        try (
                Scanner fileIn = new Scanner(new File("timesheets.txt"))) {
            String timesheets = "";
            while (fileIn.hasNextLine()) {
                timesheets += fileIn.nextLine() + "\n";
            }
            return timesheets;
        } catch (FileNotFoundException fne) {
            return "No timesheets yet";
        }
    }

    @PostMapping("/submit_timesheet")
    public String submitTimesheet(@RequestBody String newTimesheet) {
        try (
                BufferedWriter fileOut = new BufferedWriter(new FileWriter("timesheets.txt", true))) {
            fileOut.append(newTimesheet + "\n");
            return "Timesheet added";
        } catch (IOException ioe) {
            return "Unable to add timesheet";
        }
    }

    @PostMapping("/view_meeting_minutes")
    public String viewMeetingMinutes() {
        try (
                Scanner fileIn = new Scanner(new File("meeting_minutes.txt"))) {
            String meetingMinutes = "";
            while (fileIn.hasNextLine()) {
                meetingMinutes += fileIn.nextLine() + "\n";
            }
            return meetingMinutes;
        } catch (FileNotFoundException fne) {
            return "No meeting minutes yet";
        }
    }

    @PostMapping("/add_meeting_minutes")
    public String addMeetingMinutes(@RequestBody String newMeetingMinutes) {
        try (
                BufferedWriter fileOut = new BufferedWriter(new FileWriter("meeting_minutess.txt", true))) {
            fileOut.append(newMeetingMinutes + "\n");
            return "Meeting minutes added";
        } catch (IOException ioe) {
            return "Unable to add meeting minutes";
        }
    }

    @PostMapping("/view_roster")
    public String viewroster() {
        try (
                Scanner fileIn = new Scanner(new File("roster.txt"))) {
            String roster = "";
            while (fileIn.hasNextLine()) {
                roster += fileIn.nextLine() + "\n";
            }
            return roster;
        } catch (FileNotFoundException fne) {
            return "No roster yet";
        }
    }

    @PostMapping("/roster_shift")
    public String addRoster(@RequestBody String newRoster) {
        try (
                BufferedWriter fileOut = new BufferedWriter(new FileWriter("roster.txt", true))) {
            fileOut.append(newRoster + "\n");
            return "Shift rostered";
        } catch (IOException ioe) {
            return "Unable to roster shift";
        }
    }
}