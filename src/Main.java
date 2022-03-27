import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    static HashMap<String,SlangWord> slangWords = new HashMap<String,SlangWord>();
    static ArrayList<SlangWord> slangWordArrayList = new ArrayList<>();
    static ArrayList<SlangWord> slangSearchHistory =  new ArrayList<>();
    static HashMap<String,List<SlangWord>> definitionSearchHistory = new HashMap<String,List<SlangWord>>();
    public static void main(String[] args) throws IOException {
        initSlangWords();
        boolean isContinue = true;
        while (isContinue){
            try{
                printMenu();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(System.in, "utf8"));
                String str = br.readLine();
                int choose = Integer.parseInt(str);
                switch (choose){
                    case 1:{
                        System.out.println("Nhập SlangWord muốn tìm: ");
                        str = br.readLine();
                        SlangWord slangWord = findSlangWord(str);
                        if(slangWord ==null){
                            System.out.println("Không tồn tại SlangWord.");
                        }
                        else{
                            System.out.println(slangWord);
                            slangSearchHistory.add(slangWord);
                        }
                        break;
                    }
                    case 2:{
                        System.out.println("Nhập định nghĩa:");
                        str = br.readLine();
                        List<SlangWord> filterList = findSlangWordByDefinition(str);
                        for (int i=0;i<filterList.size();i++){
                            System.out.println(filterList.get(i));
                        }
                        definitionSearchHistory.put(str,filterList);

                        break;
                    }
                    case 3:{
                        System.out.println("1. Tìm theo SlangWord");
                        System.out.println("2. Tìm theo Definition");
                        str = br.readLine();
                        switch (Integer.parseInt(str)){
                            case 1:{
                                for (int i=0;i<slangSearchHistory.size();i++){
                                    System.out.println(i+1 +" - " +slangSearchHistory.get(i));
                                }
                                break;
                            }
                            case 2:{
                                int i = 0;
                                for(String key:definitionSearchHistory.keySet()){
                                    System.out.println(i+1+" - " + key+" - "+definitionSearchHistory.get(key));
                                    i++;
                                }
                                break;
                            }
                        }
                        break;
                    }
                    case 4:{
                        Boolean isCorrect = false;
                        do{
                            System.out.println("Nhập Slangword cần thêm:");
                            String slang = br.readLine();
                            System.out.println("Nhập định nghĩa:");
                            String definition = br.readLine();
                            isCorrect = addNewSlangWord(slang,definition);
                            if(!isCorrect){
                                System.out.println("SlangWord đã tồn tại.");
                            }
                        }
                        while (!isCorrect);
                        break;
                    }
                    case 5:{
                        System.out.println("Nhập SlangWord cần chỉnh sửa:");
                        String oldSlang  = br.readLine();
                        SlangWord editSlang = findSlangWord(oldSlang);
                        if(editSlang==null){
                            System.out.println("SlangWord không tồn tại.");
                        }
                        else{
                            System.out.println(editSlang);
                            System.out.println("Chọn thành phần muốn chỉnh sửa:");
                            System.out.println("1. Slang        2. Definition");
                            str = br.readLine();
                            String newSlang = editSlang.getSlangWord();
                            String newDefinition = editSlang.getDefinition();
                            switch (Integer.parseInt(str)){
                                case 1:{
                                    System.out.println("Nhập Slangword mới:");
                                    newSlang = br.readLine();

                                    break;
                                }
                                case 2:{
                                    System.out.println("Nhập định nghĩa mới:");
                                    newDefinition = br.readLine();
                                    break;
                                }
                            }
                            boolean res = editSlangword(editSlang,newSlang,newDefinition);
                            if(res){
                                System.out.println("Chỉnh sưa thành công");
                                System.out.println("Kết quả: "+ editSlang);
                            }
                            else{
                                System.out.println("SlangWord mới có thể đã tồn tại.");
                            }

                        }
                        break;
                    }
                    case 6:{
                        System.out.println("Nhập SlangWord muốn xóa:");
                        str = br.readLine();
                        boolean res = deleteSlangWord(str);
                        if(res){
                            System.out.println("Xóa thành công");
                        }
                        else{
                            System.out.println("Xóa thất bại");
                        }
                        break;
                    }
                    case 7:{
                        System.out.println("Bạn có muốn reset lại danh sách SlangWord");
                        System.out.println("1. Có       2. Không");
                        str = br.readLine();
                        if(Integer.parseInt(str)==1){
                            if(resetSlangWords()){
                                System.out.println("Reset thành công");
                            }
                            else {
                                System.out.println("Đã xảy ra lỗi.");
                            }
                        }
                        else{
                            System.out.println("Reset thất bại");
                        }
                        break;
                    }
                    case 8:{
                        System.out.println("Từ vừa Random: "+randomSlangWord());
                        break;
                    }
                    case 9:{
                        miniGameSlang();
                        break;
                    }
                    case 10:{
                        miniGameDefinition();
                    }
                    case 11:{
                        System.out.println("Bạn có chắc muốn thoát chương trình:");
                        System.out.println("1.  Có      2. Không");
                        str = br.readLine();
                        if(str.equals("1")){
                            return;
                        }

                    }
                    default:{
                        break;
                    }
                }
                System.out.println("Nhấn Enter để tiếp tục.");
                br.readLine();

            }
            catch (Exception exception){
                continue;
            }

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
        System.out.println("Đâu là định nghĩa của \""+result.getSlangWord()+"\":");
        System.out.println("A. "+ listSlangWordForMiniGame.get(0).getDefinition());
        System.out.println("B. "+ listSlangWordForMiniGame.get(1).getDefinition());
        System.out.println("C. "+ listSlangWordForMiniGame.get(2).getDefinition());
        System.out.println("D. "+ listSlangWordForMiniGame.get(3).getDefinition());
        System.out.println("Mời chọn kết quả: ");
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
            System.out.println("Chính xác: "+ result.toString());
        }
        else{
            System.out.println("Không chính xác. Kết quả là: "+ result.toString());
        }
        System.out.println("Bạn có muốn tiếp tục: ");
        System.out.println("1. Có       2. Không");
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
        System.out.println("Đâu là SlangWord của \""+result.getDefinition()+"\" trong các SlangWord sau:");
        System.out.println("A. "+ listSlangWordForMiniGame.get(0).getSlangWord());
        System.out.println("B. "+ listSlangWordForMiniGame.get(1).getSlangWord());
        System.out.println("C. "+ listSlangWordForMiniGame.get(2).getSlangWord());
        System.out.println("D. "+ listSlangWordForMiniGame.get(3).getSlangWord());
        System.out.println("Mời chọn kết quả: ");
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
            System.out.println("Chính xác: "+ result.toString());
        }
        else{
            System.out.println("Không chính xác. Kết quả là: "+ result.toString());
        }
        System.out.println("Bạn có muốn tiếp tục: ");
        System.out.println("1. Có       2. Không");
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
            System.out.println("Bạn có chắc muốn xóa SlangWord này:  "+ removeSlang);
            System.out.println("1. Đồng ý           2. Không đồng ý");
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
        System.out.println("SLANGWORD");
        System.out.println("1.Tiềm kiếm SlangWord");
        System.out.println("2.Tìm kiếm theo định nghĩa");
        System.out.println("3.Lịch sử tìm kiếm");
        System.out.println("4.Thêm SlangWord mới");
        System.out.println("5.Chỉnh sửa một SlangWord");
        System.out.println("6.Xóa SlangWord");
        System.out.println("7.Reset SlangWords gốc");
        System.out.println("8.Random 1 SlangWords");
        System.out.println("9.Đố vui SlangWord");
        System.out.println("10.Đố vui Definition");
        System.out.println("11.Thoát chương trình");
        System.out.println("Nhập số tương ứng với tính năng muốn chọn:");
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
            br.close();
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }
    public static void initSlangWords() throws IOException {
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
        br.close();
    }
    public static SlangWord convertStringToSangWord(String str){
        String[] tokens =  str.split("`");
        return new SlangWord(tokens[0],tokens[1]);
    }
}
