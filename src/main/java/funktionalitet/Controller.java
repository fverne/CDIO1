package funktionalitet;

public class Controller {

    public Controller(){}

    public void addUser(int userID, String userName, String initial, String cpr){
        /*
        Kan også lave det her til 1 if statement og rykke "indtast igen"-scenarierne ned i check-metoder
         */
        if(checkUserID(userID)){
            if(checkUserName(userName)){
                if(checkInitial(initial)){
                    if(checkCpr(cpr)){
                        //alt godkendt
                        //opret kodeord
                        //lav bruger objekt og send til data
                    }
                    else{
                        //nyt cpr skal indtastes
                    }
                }
                else{
                    //nye initialer skal indtastes
                }
            }
            else{
                //nyt username
            }
        }
        else{
            //nyt userID
        }
    }

    private boolean checkUserID(int userID){
        /*
        get list of ID's from data
        run through list
        if not used return true else return false
         */
    }

    private boolean checkUserName(String userName){
        if(userName.length() < 2){
            System.out.println("Username too short. Must contain 2-20 characters.");
            return false;
        }
        else if(userName.length() > 20){
            System.out.println("Username too long. Must contain 2-20 characters.");
            return false;
        }
        else{
            return true;
        }
    }

    private boolean checkInitial(String initial){
        if(initial.length() < 2){
            System.out.println("Initials too short. Must contain 2-4 characters.");
            return false;
        }
        else if(initial.length() > 4){
            System.out.println("Initials too long. Must contain 2-4 characters.");
            return false;
        }
        else{
            return true;
        }
    }

    private boolean checkCpr(String cpr){
        if(cpr.length() < 10){
            System.out.println("CPR too short. Must contain 10 characters.");
            return false;
        }
        else if(cpr.length() > 10){
            System.out.println("CPR too long. Must contain 10 characters.");
            return false;
        }
        else{
            return true;
        }
    }
}
