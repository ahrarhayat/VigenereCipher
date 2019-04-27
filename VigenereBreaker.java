import java.util.*;
import edu.duke.*;
import java.io.*;
public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder input = new StringBuilder();
  
        for(int i =whichSlice; i<message.length();i+= totalSlices)
        {
            char currchar = message.charAt(i);
            if(Character.isLetter(currchar))
            {
                input.append(currchar);
            }
        }
        return input.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker cc = new CaesarCracker(mostCommon);
        String sliced="";
        for(int i=0;i<klength;i++)
        {
            sliced= sliceString(encrypted,i,klength);
            key[i]=cc.getKey(sliced);
        }
        return key;
    }


    public HashSet readDictionary(FileResource fr)
    {
        HashSet<String> dictionary = new HashSet<String> ();
        for(String line: fr.lines())
        {
            String word = line.toLowerCase();
            dictionary.add(word);
        }
        return dictionary;
    }
    public int countWords(String message,HashSet dictionary)
    {
        String [] splitWords = message.split("\\W");
        int count = 0;
        //FileResource fr = new FileResource("dictionaries/English");
        //dictionary=readDictionary(fr);
        for(String word: splitWords)
        {
            String lower= word.toLowerCase();
            if(dictionary.contains(lower)==true)
            {
               count = count +1; 
            }
        }
        return count;
    }
    
    public char mostCommonCharIn(HashSet<String> dictionary)
    {
        HashMap<String, Integer> alpha = new   HashMap<String, Integer>();
        int charMax=0;
        String MostComChar= "";
        int count =0;
        for(String word: dictionary)
        {
         String lower= word.toLowerCase();
         for(int i=0;i<lower.length();i++)
         {
            char currChar=lower.charAt(i);
            StringBuilder character = new StringBuilder();
            character.append(currChar);
            String alphaS = character.toString();
            if(!alpha.containsKey(alphaS))
            {
                alpha.put(alphaS,1);
            }
            else{
               int value= alpha.get(alphaS);
               alpha.put(alphaS,value+1);
            }
         } 
        }
         for (String s : alpha.keySet()) {
          int value = alpha.get(s);
          if(value>charMax)
          {
              charMax= value;
              MostComChar=s;
          }
        } 
        char mostC= MostComChar.charAt(0);	
        return mostC;
    }
        public void breakVigenere () {
        HashMap<String,HashSet<String>> languages= new HashMap<String,HashSet<String>>();
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
          String name= f.getName();
          FileResource fr = new FileResource(f);
          HashSet dictionary=readDictionary(fr);
          languages.put(name,dictionary);
          System.out.println("Done reading the "+name+" dictionary");
        }
        
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        String decrypted = breakForAllLangs(encrypted,languages);
        System.out.println(decrypted);
    }
    public String breakForAllLangs(String encrypted,HashMap<String,HashSet<String>> languages)
    {
        String decrypted= null;
        String lower = encrypted.toLowerCase();
        for (String s : languages.keySet()) {
         HashSet value = languages.get(s);
         char mostCommon= mostCommonCharIn(value);
         decrypted = breakForLanguage(lower,value,mostCommon);
        } 
        return decrypted;
    }
    public String breakForLanguage(String encrypted,HashSet<String> dictionary,char mostCommon)
    {
        int keylength=1;
        int keys[];
        int countMax=0;
        int keyLengthMax=1;
        while(true)
        {
            keys = tryKeyLength(encrypted,keylength,mostCommon);
            VigenereCipher vc = new VigenereCipher(keys);
            String decrypted = (vc.decrypt(encrypted));
            int count=countWords(decrypted, dictionary);
            if(count>countMax)
            {
               countMax=count;
               keyLengthMax=keylength;
            }
            keylength= keylength+1;
            if(keylength==encrypted.length())
            {
                break;
            }
        }
        keys=tryKeyLength(encrypted,keyLengthMax,mostCommon);
        VigenereCipher vc1 = new VigenereCipher(keys);
        String message = vc1.decrypt(encrypted);
        //System.out.println("Key Length is "+keyLengthMax);
        //System.out.println("Valid word count "+countMax);
        
       // for(int key: keys)
        //{
        //    System.out.println(key);
       // }
        return message;
    }
}
