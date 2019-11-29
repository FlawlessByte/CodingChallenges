/*
===============================================================================
Cricket County Selections (100 Marks)
A local cricket county invited players from two neighbouring towns Norwich, Ipswich to form a cricket team of 11 players to participate in an upcoming cricket tournament. After selection process, it has shortlisted 22 players from both towns together and tagged each player skill points between 5 to 10 (both numbers included, only whole numbers are considered) based on their performance. The county has also categorised players into pool of batsmen, bowlers, wicket keepers and all-rounders. A player can only belong to one pool. Now the county has asked its final selection committee to pick 11 players from all shortlisted players following the below rules:

A minimum of 3 and a maximum of 6 batsmen must be selected.

A minimum of 3 and a maximum of 6 bowlers must be selected.
A minimum of 1 and a maximum of 4 Wicket Keeper must be selected.

A minimum of 1 and a maximum of 4 All-rounders must be selected.

A maximum of 7 players can be selected from each town.

Skill points of selected players combined must not exceed 100 points.

 


Can you help the selection committee to understand in how many ways they can pick final 11?



Input Format
There will be 22 lines of input.

Each line of the input consists of skill of player, skill points of player and town of player space separately.




Constraints
5<= Skill Points <=10

Output Format
Print the total number of unique 11 member teams that can be formed with all the criteria mentioned in a separate line.

Sample TestCase 1
Input
Batsman 10 Ipswich
Batsman 10 Ipswich
Batsman 10 Ipswich
Batsman 10 Ipswich
Batsman 10 Ipswich
Batsman 10 Ipswich
Batsman 10 Ipswich
Batsman 10 Ipswich
Batsman 10 Ipswich
Batsman 10 Ipswich
Batsman 10 Ipswich
Bowler 10 Suffolk
Bowler 5 Suffolk
Bowler 5 Suffolk
WicketKeeper 10 Suffolk
AllRounder 10 Suffolk
Bowler 10 Suffolk
Bowler 10 Suffolk
Bowler 10 Suffolk
Bowler 10 Suffolk
Bowler 10 Suffolk
Bowler 10 Suffolk

Bowlers : 9
Batsman : 11


Output
24486

Time Limit(X):
1.00 sec(s) for each input.
Memory Limit:
512 MB
Source Limit:
100 KB
Allowed Languages:
Java, Java 8, Js
==========================================================================
*/

import java.util.*;


public class CandidateCode {
    static PlayerNode bowl, bats, wkt, allr;
    static Stack<String> stk = new Stack<>();

    public static void main(String args[]) throws Exception {
        // Write code here
        ArrayList<PlayerNode> bowlers = new ArrayList<>();
        ArrayList<PlayerNode> batsman = new ArrayList<>();
        ArrayList<PlayerNode> wicketKeepers = new ArrayList<>();
        ArrayList<PlayerNode> allRounders = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 22; i++) {
            String line[] = scanner.nextLine().split(" ");

            switch (line[0]) {
            case "Batsman": {
                batsman.add(new PlayerNode(line[0], Integer.parseInt(line[1]), line[2]));
                break;
            }
            case "WicketKeeper": {
                wicketKeepers.add(new PlayerNode(line[0], Integer.parseInt(line[1]), line[2]));
                break;
            }
            case "AllRounder": {
                allRounders.add(new PlayerNode(line[0], Integer.parseInt(line[1]), line[2]));
                break;
            }
            case "Bowler": {
                bowlers.add(new PlayerNode(line[0], Integer.parseInt(line[1]), line[2]));
                break;
            }
            default: {
                break;
            }
            }
        }

        // 3 to 6 from batsman
        // 3 to 6 from bowlers
        // 1 to 4 from wicketkeepers
        // 1 to 4 from allrounders
        // only upto seven players from each town


        int possibilities = findPossibs(wicketKeepers.size(), allRounders.size(), bowlers.size(), batsman.size(), wicketKeepers,
                                        allRounders, bowlers, batsman);

        System.out.println(possibilities);

    }

    private static int findPossibs(int a, int b, int c, int d, ArrayList<PlayerNode> wicketKeepers, ArrayList<PlayerNode> allRounders, 
                ArrayList<PlayerNode> bowlers, ArrayList<PlayerNode> batsman){
        ArrayList<CombBox> combBox = new CombValues().getValues(a, b, c, d);
        Stack<PlayerNode> stack = new Stack<>();
        int possibilities = 0;

        Combination comb = new Combination();


        for(int boxnum = 0; boxnum<combBox.size(); boxnum++){
            CombBox box = combBox.get(boxnum);
            
            ArrayList<PlayerNode> wkt_keeps = new ArrayList<>();
            wkt_keeps.addAll(comb.printCombination(wicketKeepers, wicketKeepers.size(), box.a));
            ArrayList<PlayerNode> all_rounds = new ArrayList<>();
            all_rounds.addAll(comb.printCombination(allRounders, allRounders.size(), box.b));
            ArrayList<PlayerNode> bowl_ers = new ArrayList<>();
            bowl_ers.addAll(comb.printCombination(bowlers, bowlers.size(), box.c));
            ArrayList<PlayerNode> bats_man = new ArrayList<>();
            bats_man.addAll(comb.printCombination(batsman,batsman.size(), box.d));
            for(int i=0;i<wkt_keeps.size();i+=box.a){
                //take a combination 
                for(int x = i;x<i+box.a;x++){
                    stack.push(wkt_keeps.get(x));
                }

                //find next combo of allrounders and add to stack
                for(int j=0; j<all_rounds.size(); j+=box.b){

                    //take a combination
                    for(int y = j; y<j+box.b; y++){
                        stack.push(all_rounds.get(y));
                    }


                    //find next combo of bowlers
                    for(int k=0; k<bowl_ers.size(); k+=box.c){

                        //take a combo
                        for(int z = k; z<k+box.c; z++){
                            stack.push(bowl_ers.get(z));
                        }

                        //find next combo of batsman
                        for(int l=0; l<bats_man.size(); l+=box.d){

                            //take a combo
                            for(int za = l; za < l+box.d; za++){
                                stack.push(bats_man.get(za));
                            }


                            int ipswichCount = 0;
                            int suffolkCount = 0;
                            int tot_points = 0;
                            //check if the conditions meet
                            for(int ab = 0; ab<stack.size(); ab++){
                                PlayerNode node = stack.get(ab);
                                if(node.location.contains("Suffolk")){
                                    suffolkCount++;
                                }
                                else{
                                    ipswichCount++;
                                }

                                tot_points += node.point;
                            }
                            if(suffolkCount>7 || ipswichCount>7 || tot_points>100){
                                //Dont select
                            }
                            else{
                                possibilities++;
                            }

                            for(int dd=0;dd<box.d;dd++){
                                stack.pop();
                            }

                        }

                        for(int cc=0;cc<box.c;cc++){
                            stack.pop();
                        }

                    }

                    for(int bb=0;bb<box.b;bb++){
                        stack.pop();
                    }

                }

                for(int aa=0;aa<box.a;aa++){
                    stack.pop();
                }
                
            }


        }
        return possibilities;
    }

}


class PlayerNode{
    ArrayList<PlayerNode> children = new ArrayList<>();
    String type;
    int point;
    String location;
    PlayerNode(){
    }
    PlayerNode(String type, int point, String location){
        this.type = type;
        this.point = point;
        this.location = location;
    }
}


class Combination { 
    ArrayList<PlayerNode> listofPlayers = new ArrayList<>();

     void combinationUtil(ArrayList<PlayerNode> arr, int n, int r, int index, 
                                ArrayList<PlayerNode> data, int i) 
    { 
        // Current combination is ready to be printed, print it 
        if (index == r) 
        { 

        for (int j=0; j<r; j++) {
            listofPlayers.add(data.get(j));
        } 
        return; 
        } 
  
        // When no more elements are there to put in data[] 
        if (i >= n) 
        return; 
  
        // current is included, put next at next location 
        data.add(index, arr.get(i)); 
        combinationUtil(arr, n, r, index+1, data, i+1); 
  
        // current is excluded, replace it with next (Note that 
        // i+1 is passed, but index is not changed) 
        combinationUtil(arr, n, r, index, data, i+1); 
    } 
  
    // The main function that prints all combinations of size r 
    // in arr[] of size n. This function mainly uses combinationUtil() 
    ArrayList<PlayerNode> printCombination(ArrayList<PlayerNode> arr, int n, int r) 
    { 
        listofPlayers.clear();
        // A temporary array to store all combination one by one 
        ArrayList<PlayerNode> data = new ArrayList<>();
  
        // Print all combination using temprary array 'data[]' 
        combinationUtil(arr, n, r, 0, data, 0); 

        return listofPlayers;
    } 

} 

class CombBox{
    int a,b,c,d;
    CombBox(int a, int b, int c, int d){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
}

class NumBox{
    NumBox(int wkt[],int allr[],int bowl[],int bat[]){
        this.wkt = wkt;
        this.allr = allr;
        this.bowl = bowl;
        this.bat = bat;
    }
    int wkt[], allr[], bowl[], bat[];
}

class CombValues {

    ArrayList<CombBox> getValues(int wkt, int allr, int bowl, int bats){
        int len_wkt;
        if(wkt>4)
            len_wkt = 4;
        else    
            len_wkt = wkt;

        int len_allr;
        if(allr>4)
            len_allr = 4;
        else    
            len_allr = allr;


        int wkt_arr[] = new int[len_wkt];
        int allr_arr[] = new int[len_allr];
        int bowl_arr[] = new int[bowl];
        int bats_arr[] = new int[bats];


        

        for(int i=1, j=0;j<wkt;i++,j++){
            wkt_arr[j] = i;
        }
        for(int i=1, j=0;j<allr;i++,j++){
            allr_arr[j] = i;
        }
        for(int i=3, j=0; j<bowl && j<4; i++,j++){
            bowl_arr[j] = i;
        }
        for(int i=3, j=0; j<bats && j<4; i++,j++){
            bats_arr[j] = i;
        }

        int len_bowl;
        if(bowl>6){
            len_bowl = 4;
        }
        else{
            len_bowl = bowl-2;
        }
        int new_bowl[] = new int[len_bowl]; 
        for(int i=0;i<len_bowl;i++)
            new_bowl[i] = bowl_arr[i];


        if(bats>6){
            len_bowl = 4;
        }
        else{
            len_bowl = bats-2;
        }
        int new_bats[] = new int[len_bowl]; 
        for(int i=0;i<len_bowl;i++)
            new_bats[i] = bats_arr[i];

        return startSequence(new NumBox(wkt_arr, allr_arr, new_bowl, new_bats));

    }


    ArrayList<CombBox> startSequence(NumBox numBox){
        ArrayList<CombBox> combBox = new ArrayList<>();
        int wkt[] = numBox.wkt;
        int allr[] = numBox.allr;
        int bowl[] = numBox.bowl;
        int bat[] = numBox.bat;
        for(int a=0;a<wkt.length;a++){
            for(int b=0;b<allr.length;b++){
                for(int c=0;c<bowl.length;c++){
                    for(int d=0;d<bat.length;d++){
                        int sum = wkt[a] + allr[b] + bowl[c] + bat[d];
                        if(sum==11){
                            combBox.add(new CombBox(wkt[a],allr[b],bowl[c],bat[d]));
                        }
                    }
                }
            }
        }

        return combBox;
    }
}
