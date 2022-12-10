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

    public String encrypt(String value) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
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
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        Decrypt decrypter = new Decrypt();
        String encrypted = decrypter.encrypt("111111Im");
        System.out.println(encrypted);
        String decrypted = decrypter.decrypt("rQq/pJ1iooED9f6I5oSPIvVR4tYHkdqHxjU8K/zi8KRWFqrkyE/rBOP12xzhVWqapWD0S9kIxt7OpBiJZWtjd7I3LZWcC52v1WGxSTw697lhejtl37j5uk9w3zkrpIE+qCep02PPmjbjgSnalO3RSoIS5VFgBYWTXmnGrSNTbx4Otj2pOr5ZD4caH+5LnlHy4A+9LKWh0OsC6CdhJcIE1ZgMqlUaXP38jna2Ugt0AiEvGpnY+mDfX7yMCgdmt4a8uX3PUfSOTOoGaEUIx87WIzCGzF5O+ryxJdt/J2kCndz8gcKARZg0dxQ7Hk9Me4aZg9FYJDlfPfC60LSGBo/+g6TfIu05KFOWKE85E5eMbXntlP1tLBom/b3Ob0RrcTH5Aa28juC39DGyfLmj4lZEC5euT1fwmL+e/R15rNR35/OvwKzGMkfGUwskQGayIXihJjRD6HiYKq9lCtD8dRmH3AhKL1oECT0UuSG/yHhrXoBVttxDMQ4u3ZahlqoUazj+Wkvnv3+tCt4I+iOovSwo3OEl2VsJ6cH3OGCdyxn8y5AalK2evziHAfP5Tm0Wl7AbcPWg2tD6WQYXmUaVzVB2jd2pDjHii+l3mzc/ypef3nIA2EQG4lVRihHS9d3J5P00lYnoeiV0rWQDhSZAtIopP9CCIDSL+D9ann/Yb3eV+CRIj6MpDdhunn1pkImkGT5KvGdq24QuBLd5LZurATADYHoQ1vBVR2U5yRuUDJUGzFpjmFyMbTb6NHi66E3x9eYIN/uy4seunUnTYnmocCbyAUN77xmrP06Kku0EZhBGRZk4lC9O5i1e4mwDvXvSbbuKbORlRU4fhVtkr4QdlaDY9Otkq7g12EwXeGV9wMj8/g+qnRc7C46pJ6pxkxyTXJDRZEMWBVnhx1O8zA3l6anl1tWJlxBgHD26J8uyoS1c5oqtHgMrkrZwMNh/vPT4OXB94mPYPnSdK9o4bVQzdJR/JFI8z9GsxOlKdXuQ2nYnPwNS15i3K0mX/M/uv4hdY67NfC9ttkfuhCY1SWYlmhMJ9Jf2pXUgMBRPbl7D4Xe9qGieSEEi+yS1zI2U5hSlf4rhMaZqa/q80m4IJJLCbylXAlbO90Wr3xCsfSpNibVKVVoQjBi6L+K31WUANI5IEqqMajMoY8vxf7JT7k8PwSsbPX/DJAqriWli/n0y8IRuj6Vo/+e6EPoLEbiIx73Y6Yq/JiKeX5NXCmAfpZxueqeo+2iipkiid5T3Oeuq4wlASaM1iyywiN7hLUjGAB046kd7");
        System.out.println(decrypted);
    }
}