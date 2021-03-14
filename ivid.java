import java.util.*;
import java.io.*;

public class VoterBaseInfo {
    static Scanner cin = new Scanner(System.in);
    static Scanner fin = null;
    static PrintWriter fout = null;

    public static void main(String[]args) {
        ArrayList<Voter> Voters = new ArrayList<>();
                                            /* --------SETUP-------- */
        //does voterdata.csv exist, setup Scanner for reading and PrintWriter for writing.
        try {
            fin = new Scanner(new File("voterdata.csv"));
        } // try
        catch (FileNotFoundException ex) {
            System.out.println("file not found");
        } //catch
        //
        try {
            File ff = new File("voterdata.csv");
            if (ff.createNewFile())
                System.out.println("File created: " + ff.getName());
            else
            System.out.println("----------------------------------------");
            System.out.println("Voter data file ready.");//System.exit(1);
            System.out.println("----------------------------------------");
            fout = new PrintWriter(new FileWriter(ff, true));
        }
        catch (IOException e) {}

        //turn data into Voter objects, store in arrayList
        while (fin.hasNext()) {
            String str = fin.nextLine();
            String[] TempSplit = str.split(",");
            Voter temp = new Voter(TempSplit[0], TempSplit[1], (TempSplit[2]), TempSplit[3], TempSplit[4], TempSplit[5], TempSplit[6]);
            Voters.add(temp);
        } // while

        Voter newVoter = createProfile();
        //Voter newVoter = new Voter("Democrat", "Shay Iyer", "19", "Asian", "Snake", "siyer@smith.edu", "5085308566");
        Voters.add(newVoter);

        //add newVoter to .csv file
        menu(newVoter, Voters);

    } // main
                            /*-------------DRIVER METHODS--------------*/

    static void menu(Voter V, ArrayList <Voter> Voters) {
        System.out.print("MENU \n  1. See your information \n  2. Change your information \n  3. Get contact info of people of the same party as you. \n  4. View names of people in a party \n  5. Compile a resource textfile for yourself! \n  6. See menu again \n  7. Save (do before exiting) \n  0. Exit \n ");
        int op = 10;
        do{
            System.out.print("     Choice (6 to see menu again, 0 to exit): ");
            op = cin.nextInt();
            switch(op){
                case 0: break;
                case 1: System.out.println(V); break;
                case 2: changeInfo(V); break;
                case 3: printContactInfos(V.getParty(), Voters); break;
                case 4: printPartyNames(Voters); break;
                case 5: resourceFile(); break;
                case 6: System.out.println("MENU \n  1. See your information \n  2. Change your information \n  3. Get contact info of people of the same party as you. \n  4. View names of people in a party \n  5. Compile a resource textfile for yourself! \n  6. See menu again \n  7. Save (do before exiting) \n  0. Exit"); break;
                case 7: saveInfo(V); break;
                default: System.out.println("Invalid option! Please try again."); break;
            } // switch
        } while(op!=0);
    }//menu

    static void saveInfo(Voter V) {
        fout.append(V.csvString());
        fout.close();
        System.out.println("Your user profile has been saved to the dataset. ");
        System.out.println("----------------------------------------");
        return;
    }

    static void changeInfo(Voter V) {
        int numTimes = 1 ;
        System.out.println("----------------------------------------");
        System.out.println("To change your information, please confirm your identity.");
        System.out.println("What is your favorite animal? ");
        String guess = cin.next();
        while(!seCheck(guess, V)) {
            System.out.println("Incorrect. You have " +  (10-numTimes) + " attempts remaining: ");
            guess = cin.next();
            numTimes++;
            if (numTimes == 10) {
                System.out.println("Too many attempts. Returning to main menu..."); return;
            }//if
        }//while
        System.out.println("----------------------------------------");
        System.out.print("What info would you like to change? \n  1. Nevermind! \n  2. Name  \n  3. Race \n  4. Email \n  5. Phone \n  6. Age  \n  7. Set a new favorite animal. \n     Choice? ");
        int op = cin.nextInt();
        if (op == 1) {
            System.out.println("Ok! Taking you back to the main menu."); return;
        }
        System.out.println("----------------------------------------");
        System.out.print ("What would you like to change it to? ");
        String newInfo = cin.next();
        switch(op) {
            case 2: V.setName(newInfo); break;
            case 3: V.setRace(newInfo); break;
            case 4: V.setEmail(newInfo); break;
            case 5: V.setPhone(newInfo); break;
            case 6: V.setAge(newInfo); break;
            case 7: V.setAnimal(newInfo); break;
            default: System.out.println("Invalid option."); break;
        }//switch op
        System.out.println("Changed! Please make a new menu selection. ");
        System.out.println("----------------------------------------");
        return;
    }

    static boolean seCheck(String guess, Voter V) {
        if (guess.equals(V.getAnimal())) return true;
        else return false;
    }//seCheck

    static void resourceFile() {
        PrintWriter rfout = null;
        Scanner rfin = null;

        //reader/writer setup
        try {
            File ff = new File("myresourcefile.txt");
            System.out.println("----------------------------------------");
            if (ff.createNewFile()) System.out.println("Welcome to your new resource file: " + ff.getName());
            else System.out.println("Accessing your existing file, " + ff.getName());
            rfout = new PrintWriter(new FileWriter(ff, true));
            rfin = new Scanner(ff);
        } catch (IOException e) {}

        System.out.print("Input R to read, W to write: ");
        char op = cin.next().toUpperCase().charAt(0);
        switch(op) {
            case 'R':
                while ( rfin.hasNextLine() ){ //check if end of file
                    String s = rfin.nextLine();
                    System.out.print(s + "\n");
                }
                System.out.println("----------END OF FILE--------------\n");
                rfin.close();
                break;
            case 'W':
                System.out.print("Input any information. Hit enter to submit.");
                String entry = cin.nextLine();
                entry = cin.nextLine();
                System.out.print("What is the date you made this entry (MM/DD/YY)? ");
                String d = cin.next();
                cin.nextLine();
                System.out.println();
                rfout.write("-------Entry made " + d + "------- \n");// write in file
                rfout.write(entry + "\n");
                rfout.close();
                break;
            default: System.out.println("error"); break;
        }

        System.out.print("Returning to main menu...");


    }//resourceFile


    static void printContactInfos(String userParty, ArrayList <Voter> Voters){
        // go through contact infos, if it matches the user party, add it to a new array, print it
        //would you like to write this to your resource file? y --> directly write it. no need to make another function
        ArrayList <String> partyContacts = new ArrayList <>();
        for(int i = 0; i < Voters.size(); i++) {
            Voter temp = Voters.get(i);
            if (userParty.equals(temp.getParty())) {
                System.out.println(temp.getContact());
                partyContacts.add(temp.getContact());
            }//if
        }//for
        System.out.print("Would you like to add this contact info to your resource file (y/n)?");
        char choice = cin.next().toUpperCase().charAt(0);
        if (choice == 'N') {
            System.out.println("Okay! Taking you back to main menu now.");
            return;
        }
        //write to resource file.
        PrintWriter rfout = null;
        Scanner rfin = null;

        //reader/writer setup
        try {
            File ff = new File("myresourcefile.txt");
            if (ff.createNewFile()) System.out.println("Welcome to your new resource file: " + ff.getName());
            else System.out.println("Accessing your existing file, " + ff.getName());
            rfout = new PrintWriter(new FileWriter(ff, true));
            rfin = new Scanner(ff);
        } catch (IOException e) {}

        for (int i = 0; i < partyContacts.size(); i++) {
            rfout.write(partyContacts.get(i) + "\n");
        }//for
        rfout.close();

        System.out.println("Added! Select 5 to see your resource file, or add more. Taking you back to the main page now.");
    }//printContactInfos


    static void printPartyNames(ArrayList <Voter> Voters){
        System.out.print("Enter D to see Democratic voter names, R to see Republican voter names: ");
        char entry = cin.next().toUpperCase().charAt(0);
        for(int i = 0; i < Voters.size(); i++) {
            Voter temp = Voters.get(i);
            if (temp.getParty().charAt(0) == entry) System.out.println(temp.getName());
        } // for
        System.out.println("----------------------------------------");
        return;
    }


    static Voter createProfile() {
        Quiz partyQuiz = new Quiz();
        System.out.println("Your profile building will start with a simple quiz to decide your party based on your beliefs");
        partyQuiz.question("1. Taxes should stay the same for everyone, and not be raised for the wealthy 1%.");
        partyQuiz.question("2. The means of production shouldn't be placed into the hands of the people - it's too irresponsible.");
        partyQuiz.question("3. You place great value on conserving tradition.");
        partyQuiz.question("4. The government provides an equitable budget for the military, healthcare, student debt, education costs etc; this budget should NOT be changed. ");
        partyQuiz.question("5. As of 2020, we have sucessfully achieved a meritocracy (people in government are selected only on the basis of their ability). ");
        partyQuiz.question("6. The current structure of corporations is not an ethical issue.  ");
        partyQuiz.question("7. The right to carry a gun is given through the 2nd ammendment, and so any individual has the right to own a gun.");
        partyQuiz.question("8. Stricter environment laws are way too costly for their benefits.");
        partyQuiz.question("9. Anyone who came into this country illegally should be sent back to their place of birth.");
        partyQuiz.question("10. There are way too many people benefitting off of welfare while lazing around.");
        partyQuiz.question("11. The economic system in our country is generally fair to most Americans.");
        String userResult = partyQuiz.getResult();
        System.out.println("----------------------------------------");
        System.out.println("Based on your opinions, your party is " + userResult + ".");
        System.out.println("----------------------------------------");
        System.out.println("Now, please input some basic information to build your profile. ");
        StringBuilder sb = new StringBuilder();
        String userParty, userName, userRace, userAnimal, userEmail, userPhone, userAge;
        userParty = userResult;
        System.out.print("First name: ");
        sb.append(cin.next());
        System.out.print("Last name: ");
        sb.append(" " + cin.next());
        userName = sb.toString();
        System.out.print("Age: ");
        userAge = cin.next();
        System.out.print("Preferred race identifier (e.g. White, Indigenous, Asian, Black, Hispanic-Latinx, Multiracial, etc): ");
        userRace = cin.next();
        System.out.print("Security question (favorite animal): ");
        userAnimal = cin.next();
        System.out.print("Email address (Format: username@website.com): ");
        userEmail = cin.next();
        System.out.print("Phone number (Format: 0123456789): ");
        userPhone = cin.next();
        System.out.println("----------------------------------------");
        Voter newUser = new Voter(userParty, userName, userAge, userRace, userAnimal, userEmail, userPhone);
        saveInfo(newUser);
        return newUser;
    } // createProfile
} // driver


class Voter {
    private String party, name, race, animal, email, phone, age;

    public Voter(String party, String name, String age, String race, String animal, String email, String phone) {
        this.party = party;
        this.name = name;
        this.age = age;
        this.race = race;
        this.animal = animal;
        this.email = email;
        this.phone = phone;
    } // constructor

    public String csvString() {
        return "\n" + party + "," + name + "," + age + "," + race + "," + animal + ","+ email + "," + phone;
    }//csvString

    //getters
    public String getParty() { return party; }
    public String getName() { return name; }
    public String getAge() { return age; }
    public String getRace() { return race; }
    public String getAnimal() { return animal; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getContact() { return name + " can be reached at " + email + " or " + phone + "\n"; }

    //setters
    public void setName(String nn) { System.out.println("Setting name to " + nn); name = nn; }
    public void setAge(String nn) { System.out.println("Setting age to " + nn); age = nn; }
    public void setRace(String nn) { System.out.println("Setting race to " + nn); race = nn; }
    public void setAnimal(String nn) { System.out.println("Setting favorite animal to " + nn); animal = nn; }
    public void setEmail(String nn) { System.out.println("Setting email to " + nn); email = nn; }
    public void setPhone(String nn) { System.out.println("Setting phone number to " + nn); phone = nn; }

    public String toString() {
        return "\n ---------------------------\n" + name + ", " + party + ": \n age: " + age + " \n race: " + race + "\n favorite animal: " + animal + "\n email: " + email + "\n phone: " + phone + "\n ---------------------------\n";
    }//toString

} // Voter class




//Quiz class
class Quiz {
    static Scanner cin = new Scanner(System.in);
    // keeps count of category answers to the quiz questions
    int demCount = 0;
    int repCount = 0;

    // increases either democrat or republican (the functions actually tallying this)
    public int incDem() { return demCount++; }
    public int incRep() { return repCount++; }

    public void question(String question) {
        System.out.println(question); // A must coincide with republican response, B must coincide with democrat response
        // System.out.println("Respond A or B");
        // takes in the user response
        char response;

        do{
            System.out.print("Answer Y to agree, N to disagree: ");
            response = cin.next().toUpperCase().charAt(0);
        }while((response != 'Y') && (response != 'N'));

        if (response == 'Y') { incRep(); }
        else if (response == 'N') { incDem(); }
        System.out.println("Logged response " + response);
    }//question

    public String getResult() {
        if (demCount > repCount) { return "Democrat"; }
        else { return "Republican"; }
    } // getResult

} // Quiz class
