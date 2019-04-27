import edu.duke.*;
/**
 * Write a description of TestAll here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TestAll {

    public void testCaesarCipher()
    {
        FileResource fr = new FileResource();
        String message=fr.asString();
        System.out.println(message);
        CaesarCipher cc = new CaesarCipher(3);
        String encrypted=cc.encrypt(message);
        System.out.println(encrypted);
        String decrypted=cc.decrypt(encrypted);
        System.out.println(decrypted);
    }
    public void testCaesarCrackcer()
    {
        FileResource fr = new FileResource();
        String encrypted=fr.asString();
        System.out.println(encrypted);
        CaesarCracker cc = new CaesarCracker();
        String decrypted= cc.decrypt(encrypted);
        System.out.println(decrypted);
    }
    public void testVigenereCipher()
    {
        FileResource fr = new FileResource();
        String message=fr.asString();
        int [] keys={17,14,12,4}; //key=rome
        VigenereCipher vc = new VigenereCipher(keys);
        String encrypted=vc.encrypt(message);
        System.out.println(encrypted);
        String decrypted = vc.decrypt(encrypted);
        System.out.println(decrypted);
    }
    public void testSliceString()
    {
        String input = "abcdefghijklm";
        VigenereBreaker vb = new VigenereBreaker();
        String output=vb.sliceString(input,4,5);
        System.out.println(output);
    }
    public void testTryKeyLength()
    {
        FileResource fr = new FileResource();
        String message=fr.asString();
        VigenereBreaker vb = new VigenereBreaker();
        int [] keys = vb.tryKeyLength(message,4,'e');
        for(int i:keys)
        {
        System.out.println(i);
        }
    }
    public void testBreakVigenere()
    {
        VigenereBreaker vb = new VigenereBreaker();
        vb.breakVigenere();
    }
    
}
