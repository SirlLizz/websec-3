package com.example.server.source;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import static org.apache.tomcat.util.codec.binary.Base64.decodeBase64;
import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64String;

public class Decrypt {
    public Decrypt() throws NoSuchPaddingException, NoSuchAlgorithmException {
    }

    private static final String key = "aesEncryptionKey";
    private static final String initVector = "encryptionIntVec";
    private final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
    private final SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
    private final IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
    private final String salt = "x0vShoWB&1Xn4=EnEP0UKh!%Jk2xm)p-dPbUA%WVYpYr7-v&&j7IFUOt)cXm#tIgrrcYI0$3m9brFojQCk^0(mlPe&dzMQ-!OJAPCBVV#bcd^AEFrjRhiAV6LsA!ARFe271hwqNcSI6curARX@(WmQI8D0L*r$sBsKjoXZmSDEgZguXod9gwIC*f@dsE035&uqcya=8bz5QovBvQ9x7H-GOb$^SlSJlQ*QItnleZrHtwBNV(vZZ2lHZ5TGJ!zbTIxJht8bstx*1XDJvJCD*m=xY4Iwi69#ep4KJGj8zFr4MZX%A7Egc.fcua=pCCQPbcqYkT3ShAwza!wHuP-3PIYsBTTH9=WQB2mChMhJlra8f$uqBCWX!kIS(8p3!^r)^Xdx9Gd*iph-tYMFGPsAnb!007@R3h&eq#z5^OJH9Sm1EX=)UZUN1uIvngJVa))-M9Zz7ViZ5u%%^5biv%98HexzBHj66y1OL7W9osWv(XNGVV%J&6JgZm0JO4yY4brPN0z&jkw#L7&Tis1%se5wXf=!@PqYPDh@6.ij*gxedINVIh0Vfj50cJwSejy7HQRw*ZfSV^u8fa5w1s)B%CZbC&W8cKi(Rn7eL(^YSSg!7uJoZ4UwYrcu&6njisVStcw*jKj#sUL=PC3$TdAHL).Rs&4FAPEJ5L.ry8ZPhU.Ih$NPqQCNS21YVc=BDqMO%Mgzr3ay1!HlxgRs)o94mt.r8Mw)txa4ZhBuF1lk*i-cYueHElaCiCC86P71*X)8t@tUkDF*)KqR0I)9r8vknOoODlmdgQTC.wr7H=uf403KgoC7OHqfYw#fsrJKEX7x%MOuN)QEFG*@iEACTtTBlJEKyCEdPBZUl*3E%V3xyKsBTEHW.b4e(#K(Hu)n$JzrGuDnh-VlAQr*a532oQMG6FduBduAyNXPYPf$oUt.BMpHib3V&jvnYgHY@9(-C2I&!OwZn*y#bK^rMP)qYeNRG=HYCoV0F6)-4.98AQX=nfxNA";

    public String encrypt(String value) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            value+=salt;
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public String decrypt(String encrypted) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(decodeBase64(encrypted));
            return new String(original).substring(0, new String(original).length()-999);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        Decrypt decrypter = new Decrypt();
        String encrypted = decrypter.encrypt("111111Im");
        System.out.println(encrypted);
        String decrypted = decrypter.decrypt(encrypted);
        System.out.println(decrypted);
    }
}