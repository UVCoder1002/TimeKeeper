package Manager;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class UniqueCode {
    UUID uniqueKey ;
    FileInputStream fis;
    ObjectInputStream ois;
    FileOutputStream fos;
    ObjectOutputStream oos;

    public UUID generateunicode() {
        ArrayList<UUID> codes = new ArrayList<>();
        try {
            fis = new FileInputStream("Unicode.dat");

            ois = new ObjectInputStream(fis);

//        UniqueCode uni=new UniqueCode();
//        uniqueKey = UUID.randomUUID();
//            System.out.println(uniqueKey);
//            System.out.println(UNI);

            while (fis.available() > 0) {
                codes.add((UUID) ois.readObject());
            }
            ois.close();
        }catch(Exception e){}
        System.out.println("checking uniqueness");
            int flag=1;
            while(flag==1) {
                uniqueKey=UUID.randomUUID();
                System.out.println("generating new uuid " + uniqueKey.toString());
                Iterator<UUID> it = codes.iterator();
                flag = 0;
                while (it.hasNext()) {
                    UUID itU = it.next();
                    System.out.println(itU.toString());
                    if (itU.toString().compareTo(uniqueKey.toString())==0){
                        flag=1;
                        break;}
                }
            }
            codes.add(uniqueKey);

        Iterator<UUID> it2 = codes.iterator();
        try {
            fos = new FileOutputStream("Unicode.dat");
            oos = new ObjectOutputStream(fos);
            while (it2.hasNext()) {
                oos.writeObject(it2.next());
            }
            ois.close();
        }catch(Exception e){}

        System.out.println(uniqueKey);
        return uniqueKey;
    }

//    public static void main(String[] args){
//        UniqueCode uni=new UniqueCode();
//        uni.generateunicode();
//    }
}
