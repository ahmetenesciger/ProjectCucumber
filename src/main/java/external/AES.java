package external;

 

import java.security.*;

import javax.crypto.*;

import javax.crypto.spec.SecretKeySpec;

import sun.misc.*;

public class AES {


       public static String yukle(String encryptedData){

             try {

                    Key key = new SecretKeySpec("Siradaki Senaryo".getBytes(), "AES");
                    Cipher c = Cipher.getInstance("AES");
                    c.init(Cipher.DECRYPT_MODE, key);
                    byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);

                    byte[] decValue = c.doFinal(decordedValue);

                    String decryptedValue = new String(decValue);

                    return decryptedValue;

                    } catch (Exception e) {

                           // TODO Auto-generated catch block

                           e.printStackTrace();

                    }

                    return null;

       }

}