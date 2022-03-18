import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    static HashMap<String,SlangWord> slangWords = new HashMap<String,SlangWord>();
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
                        SlangWord slangWord  = slangWords.get(str);
                        if(slangWord ==null){
                            System.out.println("Không tồn tại SlangWord.");
                        }
                        else{
                            System.out.println(slangWord);
                        }
                        System.out.println();
                        System.out.println("Nhấn Enter để tiếp tục.");
                        br.readLine();
                        break;
                    }
                    case 2:{
                        break;
                    }
                    case 3:{
                        break;
                    }
                    case 4:{
                        break;
                    }
                    case 5:{

                        break;
                    }
                    case 6:{

                        break;
                    }
                    default:{
                        isContinue = false;
                        break;
                    }
                }
            }
            catch (Exception exception){
                continue;
            }
        }
    }
    public static void printMenu(){
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
        System.out.println("Nhập số tương ứng với tính năng muốn chọn:");
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
