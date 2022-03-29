import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    static HashMap<String,SlangWord> slangWords = new HashMap<String,SlangWord>();
    static ArrayList<SlangWord> slangWordArrayList = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        initSlangWords();
        boolean isContinue = true;
        while (isContinue){
            try{
                printMenu();
                // Tham khảo Slide Tuần 2 thầy Nguyễn Văn Khiết
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(System.in, "utf8"));
                String str = br.readLine();
                int choose = Integer.parseInt(str);
                switch (choose){
                    case 1:{
                        System.out.println("Input SlangWord you want to find: ");
                        str = br.readLine();
                        SlangWord slangWord = findSlangWord(str);
                        if(slangWord ==null){
                            System.out.println("SlangWord doesn't exist .");
                        }
                        else{
                            System.out.println("Your SlangWord: "+ slangWord);
                            updateSlangHistory(slangWord);
                        }
                        break;
                    }
                    case 2:{
                        System.out.println("Input definition:");
                        str = br.readLine();
                        List<SlangWord> filterList = findSlangWordByDefinition(str);
                        if(filterList.size()==0){
                            System.out.println("No SlangWord is suitable");
                        }
                        for (int i=0;i<filterList.size();i++){
                            System.out.println(filterList.get(i));
                        }
                        updateDefinitionHistory(str,filterList);
                        break;
                    }
                    case 3:{
                        System.out.println("1. Find by SlangWord");
                        System.out.println("2. Find by Definition");
                        str = br.readLine();
                        switch (Integer.parseInt(str)){
                            case 1:{
                                if(printSlangHistory()){
                                    System.out.println("\nDo you want to delete all the history? ");
                                    System.out.println("1. Yes       2. No");
                                    if(br.readLine().equals("1")){
                                        clearSlangHistory();
                                    }
                                }

                                break;
                            }
                            case 2:{
                                if(printDefinitionHistory()){
                                    System.out.println("Do you want to delete all the history? ");
                                    System.out.println("1. Yes       2. No");
                                    if(br.readLine().equals("1")){
                                        clearDefinitionHistory();
                                    }
                                }

                                break;
                            }
                        }

                        break;
                    }
                    case 4:{
                        Boolean isCorrect = false;
                        do{
                            System.out.println("Input the SlangWord:");
                            String slang = br.readLine();
                            System.out.println("Input definition:");
                            String definition = br.readLine();
                            isCorrect = addNewSlangWord(slang,definition);
                            if(!isCorrect){
                                System.out.println("SlangWord existed.");
                            }
                        }
                        while (!isCorrect);
                        break;
                    }
                    case 5:{
                        System.out.println("Input SlangWord you want to modify:");
                        String oldSlang  = br.readLine();
                        SlangWord editSlang = findSlangWord(oldSlang);
                        if(editSlang==null){
                            System.out.println("SlangWord doesn't exist.");
                        }
                        else{
                            System.out.println(editSlang);
                            System.out.println("Choose property:");
                            System.out.println("1. Slang        2. Definition");
                            str = br.readLine();
                            String newSlang = editSlang.getSlangWord();
                            String newDefinition = editSlang.getDefinition();
                            switch (Integer.parseInt(str)){
                                case 1:{
                                    System.out.println("New SlangWord:");
                                    newSlang = br.readLine();

                                    break;
                                }
                                case 2:{
                                    System.out.println("New Definition:");
                                    newDefinition = br.readLine();
                                    break;
                                }
                            }
                            boolean res = editSlangword(editSlang,newSlang,newDefinition);
                            if(res){
                                System.out.println("Success");
                                System.out.println("Result: "+ editSlang);
                            }
                            else{
                                System.out.println("SlangWord may be existed.");
                            }

                        }
                        break;
                    }
                    case 6:{
                        System.out.println("Input SlangWord you want to delete:");
                        str = br.readLine();
                        boolean res = deleteSlangWord(str);
                        if(res){
                            System.out.println("Deleted");
                        }
                        else{
                            System.out.println("Failed");
                        }
                        break;
                    }
                    case 7:{
                        System.out.println("Do you want to reset the SlangWord original list");
                        System.out.println("1. Yes       2. No");
                        str = br.readLine();
                        if(Integer.parseInt(str)==1){
                            if(resetSlangWords()){
                                System.out.println("Success");
                            }
                            else {
                                System.out.println("Some error happened.");
                            }
                        }
                        else{
                            System.out.println("Failed");
                        }
                        break;
                    }
                    case 8:{
                        System.out.println("The random word : "+randomSlangWord());
                        break;
                    }
                    case 9:{
                        miniGameSlang();
                        break;
                    }
                    case 10:{
                        miniGameDefinition();
                        break;
                    }
                    case 11:{
                        System.out.println("Are you sure want to exit:");
                        System.out.println("1.  Yes      2. No");
                        str = br.readLine();
                        if(str.equals("1")){
                            return;
                        }

                    }
                    default:{
                        break;
                    }
                }

                System.out.println("Enter to continue.");
                br.readLine();
                cls();
            }
            catch (Exception exception){
                continue;
            }
            updateSlangFile();
        }
    }
    public static void miniGameSlang() throws IOException {
        ArrayList<SlangWord> listSlangWordForMiniGame = new ArrayList<>();
       for(int i=0;i<4;i++){
           listSlangWordForMiniGame.add(randomSlangWord());
       }
       Random random = new Random();
       int indexResult = random.nextInt(4);
       SlangWord result = listSlangWordForMiniGame.get(indexResult);
        System.out.println("Which is the definition of \""+result.getSlangWord()+"\":");
        System.out.println("A. "+ listSlangWordForMiniGame.get(0).getDefinition());
        System.out.println("B. "+ listSlangWordForMiniGame.get(1).getDefinition());
        System.out.println("C. "+ listSlangWordForMiniGame.get(2).getDefinition());
        System.out.println("D. "+ listSlangWordForMiniGame.get(3).getDefinition());
        System.out.println("Your choice: ");
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in, "utf8"));
        String str = br.readLine();
        int userChosen = -1;
        switch (str){
            case "A","a":{
                userChosen = 0;
                break;
            }
            case "B","b":{
                userChosen = 1;
                break;
            }
            case "C","c":{
                userChosen = 2;
                break;
            }
            case "D","d":{
                userChosen = 3;
                break;
            }
        }
        if(userChosen == indexResult){
            System.out.println("Correct: "+ result.toString());
        }
        else{
            System.out.println("Not correct. The answer is: "+ result.toString());
        }
        System.out.println("Continue?: ");
        System.out.println("1. Yes       2. No");
        str = br.readLine();
        if(Integer.parseInt(str)==1){
            miniGameSlang();
        }
    }
    public static void miniGameDefinition() throws IOException {
        ArrayList<SlangWord> listSlangWordForMiniGame = new ArrayList<>();
        for(int i=0;i<4;i++){
            listSlangWordForMiniGame.add(randomSlangWord());
        }
        Random random = new Random();
        int indexResult = random.nextInt(4);
        SlangWord result = listSlangWordForMiniGame.get(indexResult);
        System.out.println("Which is the write SlangWord of \""+result.getDefinition()+"\" in the SlangWords below:");
        System.out.println("A. "+ listSlangWordForMiniGame.get(0).getSlangWord());
        System.out.println("B. "+ listSlangWordForMiniGame.get(1).getSlangWord());
        System.out.println("C. "+ listSlangWordForMiniGame.get(2).getSlangWord());
        System.out.println("D. "+ listSlangWordForMiniGame.get(3).getSlangWord());
        System.out.println("Your choice: ");
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in, "utf8"));
        String str = br.readLine();
        int userChosen = -1;
        switch (str){
            case "A","a":{
                userChosen = 0;
                break;
            }
            case "B","b":{
                userChosen = 1;
                break;
            }
            case "C","c":{
                userChosen = 2;
                break;
            }
            case "D","d":{
                userChosen = 3;
                break;
            }
        }
        if(userChosen == indexResult){
            System.out.println("Correct: "+ result.toString());
        }
        else{
            System.out.println("Not correct. The answer is: "+ result.toString());
        }
        System.out.println("Continue? ");
        System.out.println("1. Yes       2. No");
        str = br.readLine();
        if(Integer.parseInt(str)==1){
            miniGameDefinition();
        }
    }
    public static SlangWord findSlangWord(String slang){
        SlangWord slangWord  = slangWords.get(slang);
        return slangWord;
    }
    public static List<SlangWord> findSlangWordByDefinition(String definition){
        CharSequence charSequence = definition;
        // Tham khảo: https://stackoverflow.com/questions/24112715/java-8-filter-array-using-lambda
        Predicate<SlangWord> definitionFilter =  slangWord -> slangWord.getDefinition().contains(charSequence);
        List<SlangWord> filterList = slangWordArrayList.stream().filter(definitionFilter).collect(Collectors.toList());
        return filterList;
    }
    public static boolean addNewSlangWord(String slang, String definition) throws IOException {
        SlangWord slangWord = new SlangWord(slang,definition);
        if(slangWords.containsKey(slang)){
            return false;
        }
        else{
            slangWordArrayList.add(slangWord);
            slangWords.put(slang,slangWord);
            return true;
        }
    }
    public static boolean editSlangword(SlangWord oldSlangWord,String newSlang, String newDefinition) {
        if(findSlangWord(newSlang)!=null && !oldSlangWord.getSlangWord().equals(newSlang)){
            return false;
        }
        try {
            slangWords.remove(oldSlangWord.getSlangWord());
            oldSlangWord.setSlangWord(newSlang);
            oldSlangWord.setDefinition(newDefinition);
            slangWords.put(newSlang,oldSlangWord);
            return true;
        }
        catch (Exception e){

            return false;
        }
    }
    public static boolean deleteSlangWord(String slang) throws IOException {
        SlangWord removeSlang = slangWords.get(slang);
        if(removeSlang==null){
            return false;
        }
        else{
            System.out.println("Are you sure you want to delete this SlangWord:  "+ removeSlang);
            System.out.println("1. Yes          2. No");
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in, "utf8"));
            String str = br.readLine();
            if(Integer.parseInt(str)==1){
                slangWords.remove(removeSlang.getSlangWord());
                slangWordArrayList.remove(removeSlang);

                return true;
            }
            else {
                return  false;
            }
        }
    }
    public static SlangWord randomSlangWord(){
        Random random = new Random();
        int index = random.nextInt(slangWordArrayList.size());

        return slangWordArrayList.get(index);
    }
    public static void printMenu()  {
        System.out.println("----------SLANGWORD----------");
        System.out.println("1.Find a SlangWord");
        System.out.println("2.Find by definition");
        System.out.println("3.History");
        System.out.println("4.New SlangWord");
        System.out.println("5.Edit a SlangWord");
        System.out.println("6.Delete SlangWord");
        System.out.println("7.Reset Original SlangWord List");
        System.out.println("8.Random a SlangWords");
        System.out.println("9.MiniGame SlangWord");
        System.out.println("10.MiniGame Definition");
        System.out.println("11.Exit");
        System.out.println("Input feature you want to choose:");
    }
    public static boolean resetSlangWords() throws IOException {
        try {
            slangWordArrayList.clear();
            slangWords.clear();
            BufferedReader br = new BufferedReader(new FileReader("slang.txt"));
            String str = br.readLine();
            while(true){
                str= br.readLine();
                if(str ==null){
                    break;
                }
                else{
                    SlangWord slangWord = convertStringToSangWord(str);
                    slangWordArrayList.add(slangWord);
                    slangWords.put(slangWord.getSlangWord(),slangWord);
                }
            }
            updateSlangFile();
            br.close();
        }
        catch (Exception e){
            System.out.println("Need file slang.txt");
            return false;
        }
        return true;
    }
    public static void updateSlangFile() throws IOException {
        BufferedWriter br = new BufferedWriter(
                new FileWriter("edited-slang.txt"));
        for(int i = 0;i<slangWordArrayList.size();i++){
            br.write(slangWordArrayList.get(i).getSlangWord()+"`"+slangWordArrayList.get(i).getDefinition()+"\n");
        }
        br.flush();
        br.close();
    }
    public static void clearSlangHistory() throws IOException {
        File slangHistoryFile = new File("slangHistory.txt");
        if(slangHistoryFile.exists()){
            slangHistoryFile.delete();
        }

    }
    public static void clearDefinitionHistory(){
        File definitionHistoryFile = new File("definitionHistory.txt");
        if(definitionHistoryFile.exists()){
            definitionHistoryFile.delete();
        }
    }
    public static void updateSlangHistory(SlangWord slangWord) throws IOException {
        // Tham khảo: https://www.javatpoint.com/java-date-to-string
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String dateStr = formatter.format(date);
        BufferedWriter br = new BufferedWriter(
                new FileWriter("slangHistory.txt",true));
        br.write(dateStr+":     "+slangWord.toString()+"\n");
        br.flush();
        br.close();
    }
    public static boolean printSlangHistory(){
        try {
            File slangHistoryFile = new File("slangHistory.txt");
            if(!slangHistoryFile.exists()){
                System.out.println("No history found");
                return false;
            }
            BufferedReader br = new BufferedReader(new FileReader(slangHistoryFile));
            String str = br.readLine();
            while(true){
                if(str ==null){
                    break;
                }
                else{
                    System.out.println(str);
                }
                str= br.readLine();
            }
            br.close();

        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public static void updateDefinitionHistory(String definition,List<SlangWord> slangWords) throws IOException {
        // Tham khảo: https://www.javatpoint.com/java-date-to-string
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String dateStr = formatter.format(date);

        BufferedWriter br = new BufferedWriter(
                new FileWriter("definitionHistory.txt",true));
        br.write(dateStr+":     SearchString: "+definition+"    List Slang:"+ slangWords.toString() +"\n");
        br.flush();
        br.close();
    }
    public static boolean printDefinitionHistory(){
        try {
            File definitionHistoryFile = new File("definitionHistory.txt");
            if(!definitionHistoryFile.exists()){
                System.out.println("No history found");
                return false;
            }
            BufferedReader br = new BufferedReader(new FileReader(definitionHistoryFile));
            String str = br.readLine();
            while(true){
                if(str ==null){
                    break;
                }
                else{
                    System.out.println(str);
                }
                str= br.readLine();
            }

            br.close();
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
    public static void initSlangWords() throws IOException {
        try {
            BufferedReader br = new BufferedReader(new FileReader("edited-slang.txt"));
            String str;
            while(true){
                str= br.readLine();
                if(str ==null){
                    break;
                }
                else{
                    SlangWord slangWord = convertStringToSangWord(str);
                    slangWordArrayList.add(slangWord);
                    slangWords.put(slangWord.getSlangWord(),slangWord);
                }
            }
            br.close();
        }
        catch (Exception e){
            resetSlangWords();
        }
    }
    public static SlangWord convertStringToSangWord(String str){
        String[] tokens =  str.split("`");
        return new SlangWord(tokens[0],tokens[1]);
    }
    // Tham khảo : https://www.delftstack.com/howto/java/java-clear-console/
    public static void cls(){
        try{
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();

        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
