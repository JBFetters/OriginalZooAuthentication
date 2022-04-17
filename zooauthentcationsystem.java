import java.util.Scanner;
import java.security.MessageDigest;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

public class zooauthentcationsystem {
  
  public static void main(String[] args)throws Exception {
      Scanner scnr = new Scanner(System.in); 
      UserInfo accountInfo = new UserInfo ();
     
      int numberOfAttempts = 0;
      boolean passwordCorrect = false;
      String userExit = "Run";
      
         BufferedReader zooKeeper = null;
         BufferedReader vet = null;
         BufferedReader admin = null;
         String zooKeeperFile = null;
         String vetFile = null;
         String adminFile = null;
         
         String credentialsName;
         String credentialsPassword;
         String credentialsRoll;
         String idName;
         
// Ask for user inputs for User ID and Password
// DO input user name and password WHILE number of attempts is not equal to 3.
      
      do{
          System.out.println ("***Welcome***"); // Welcome the User
          System.out.print ("Enter User Name or ID: "); // Instructs the user to input User Name or ID
          accountInfo.setUserName(scnr.nextLine ());  // Gets user inputs 
          System.out.print ("Enter Password: "); // Instrucst the user to input password
          accountInfo.setUserPassword(scnr.nextLine()); // Gets user inputs

          // Turns password to hash
            String original = accountInfo.getPassword();  //Replace "password" with the actual password inputted by the user
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(original.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }

            String passwordHash = sb.toString(); //creates verable for hash return
            // Open File 
            
            File file = new File ("C:\\Users\\jnfet_000\\Documents\\College Work\\IT-145\\ZooAuthenticationSystem\\src\\Credentials.txt");
            
            FileReader fileReader = new FileReader (file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            
//Parse the string to get user id, password in hash and their roll
            
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(passwordHash)){
                    line = line.replaceAll ("\\s+", " ");
                    int firstSpace = line.indexOf(" ");
                    int secondSpace = line.indexOf(" ", firstSpace +1);
                    int quote = line.lastIndexOf("\"");
                    
                    accountInfo.setCredentialsName(line.substring(0, firstSpace));
                    accountInfo.setCredentialsPassword(line.substring(firstSpace + 1, secondSpace));
                    accountInfo.setCredentialsRoll(line.substring(quote + 1, line.length()));
                }                
            }
            fileReader.close();
            // Parsed out formthe credential file
           
           
          //open each roll files based off their credentials
          zooKeeper = new BufferedReader(new FileReader("C:\\Users\\jnfet_000\\Documents\\College Work\\IT-145\\ZooAuthenticationSystem\\src\\Zookeeper.txt"));
          vet = new BufferedReader(new FileReader("C:\\Users\\jnfet_000\\Documents\\College Work\\IT-145\\ZooAuthenticationSystem\\src\\Veterinarian.txt"));
          admin = new BufferedReader(new FileReader("C:\\Users\\jnfet_000\\Documents\\College Work\\IT-145\\ZooAuthenticationSystem\\src\\Admin.txt"));
          
         credentialsName = (accountInfo.getcredentialsName()); //credential name equals credential files name
         credentialsPassword = (accountInfo.getcredentialsPassword()); // credential password equals credential file password
         credentialsRoll = (accountInfo.getcredentialsRoll().replace(" ", "")); // credential roll equals credential roll from file
         idName = accountInfo.getName(); // idName is the user input of user name
           
           if (passwordHash.equals(accountInfo.getcredentialsPassword())){ //if hash password (created when user inputed password) equals credential password
               passwordCorrect = true;               //passwordCorrect is true
           }
           else if (numberOfAttempts < 2) // if number of attempts is less then 2
           {
                numberOfAttempts ++; // increment number of attemtps by 1
                System.out.println("ERROR....User Name and/or Password Incorrect. Please re-enter credentials"); // prints when the number of attempts hasn't been met and prompts user to re-enter user name and password
           }
           else 
           {
               numberOfAttempts ++; // increment number of attempts by 1
                              
           }    
      }while((numberOfAttempts <= 3) && (passwordCorrect != true)); // while number of attempts is less than or equaled to 3 and the password correct is not true
      
      if ((numberOfAttempts < 2) && (passwordCorrect == true)){  // if number of attempts is less than 2 and the password is ture     
        if (credentialsRoll.equals("zookeeper")) {    //check to see if the roll equal zookeeper
                while ((zooKeeperFile = zooKeeper.readLine()) != null) { //if so open file
                    System.out.println (zooKeeperFile); // prints file
                }   
        }
        
        if (credentialsRoll.equals("veterinarian")) {
                while ((vetFile = vet.readLine()) != null) {
                    System.out.println (vetFile);
                    
                }
        } 
        if (credentialsRoll.equals("admin")) {
            while ((adminFile = admin.readLine()) != null) {
            System.out.println (adminFile);
            }
        }
        
        while (userExit.equals("Run")){ //run until the user inputs exit
            System.out.print ("Enter Exit to quite: ");
            userExit = scnr.nextLine ();
        }
       } 

      else{
            System.out.println("Maxed number of attempts has been reach. Locked Out");
      }
 }

}
