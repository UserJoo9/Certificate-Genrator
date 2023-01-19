package sample;

import javax.crypto.Cipher;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

 class RSA {


        private PrivateKey privateKey;
        private PublicKey publicKey;
        private PublicKey sec_publicKey;

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        private String PRIVATE_KEY_STRING="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKJPOrYLeLYRY4AE7QxM3znJu0MwNdudRWYsnDzLU9w3Xz4xESAtO/phnlz9bEiByoDryxVzzKfLolp71Abc6fHFj9t9Cv+xAEYVTyYhf74vrHpyMWjmSVbYvxB+hM8i1UElqPuH16Y5wHEqUPWokyqPWazb7HRg9U+DaiiVWKwbAgMBAAECgYACCXmEqF8y9cPoaudotJxGluQNQ1EhuPcSgbPSRYD/bgh5eNcsrrSmf0wkAaA2xr46YPszATmLz4/xj8VI8sPS2ks/SZs0kMVJTnnJ5ykY1BQ9XPhX2YTo4laPi2BcWZJsF0l5uCpDRyFGFxFQRqhnZxJ0dDQODfafCr326h0XmQJBALDIdc2UKo+CiCXRUli2UT0WdVD8f6Oy4o10iwIxApKVEtVsUCOY1mb8iNH00LAxm0K1u4UjjbF96IUHiqznarkCQQDrCnIGdbCRHnoAlJYEi81hDqVbMJwdKE2fWEC4RZEi6yj1BWvahkK03ps0mLr/81wbWitWUK4IbZiuFOmEOBNzAkBNg5gjzBPdWR7Zdi7lNwvn/TDzkcNlw8oSvEc3bsXx/+uCKTv6X8mi3pgfd3kJyLaLSaQvfKdkbBDIQ8ZKii4BAkEAqg1Mok/UB6q9Kl5Wi8N6PJaUHH329UGLp5X6fVgXwePSJjRd7CTnbmUAdfVqlnREV6XNZQ3KXF6u8IRAz2Ci8QJBAKJ0oucUIQ7NidxQb6IEskYrDUnKh/dqguTrpeTkGyXxusXd6g3prTXwUbxmJzDUZZAoPKjpOSzDP334axEgTJM=";
        private String PUBLIC_KEY_STRING="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiTzq2C3i2EWOABO0MTN85ybtDMDXbnUVmLJw8y1PcN18+MREgLTv6YZ5c/WxIgcqA68sVc8yny6Jae9QG3OnxxY/bfQr/sQBGFU8mIX++L6x6cjFo5klW2L8QfoTPItVBJaj7h9emOcBxKlD1qJMqj1ms2+x0YPVPg2oolVisGwIDAQAB";

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        private String PRIVATE_KEY_PATH = "D:\\keys\\private.key";
        private String PUBLIC_KEY_STRINGPATH = "D:\\keys\\public.key";
        private String sec_PUBLIC_KEY_STRINGPATH = "D:\\keys\\sec_public.key";
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        File public_file = new File(PUBLIC_KEY_STRINGPATH);
        File private_file = new File(PRIVATE_KEY_PATH);
        File sec_public_file = new File(sec_PUBLIC_KEY_STRINGPATH);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        RSA() {

            try {
                if (public_file.getParentFile() != null) {
                    public_file.getParentFile().mkdirs();
                }
                if (!public_file.exists()) {
                    public_file.createNewFile();
                }
                if (!private_file.exists()) {
                    private_file.createNewFile();
                }
                if (!sec_public_file.exists()) {
                    sec_public_file.createNewFile();

                }

            } catch (Exception v) {
            }
        }


        public void init() {
            try {

                PrintWriter public_writer = new PrintWriter(public_file);
                PrintWriter private_writer = new PrintWriter(private_file);

                KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
                generator.initialize(1024);
                KeyPair pair = generator.generateKeyPair();
                privateKey = pair.getPrivate();
                publicKey = pair.getPublic();
                public_writer.println(encode(publicKey.getEncoded()));
                private_writer.println(encode(privateKey.getEncoded()));
                private_writer.close();
                public_writer.close();

            } catch (Exception ignored) {

            }
        }


        public void initFromStrings() throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
            RSA rsa = new RSA();
            Scanner in = new Scanner(System.in);

            Scanner public_scanner = new Scanner(public_file);
            Scanner private_scanner = new Scanner(private_file);
            Scanner sec_public_scanner = new Scanner(sec_public_file);
            if (!private_scanner.hasNext() || !public_scanner.hasNext()) {
                init();
                JOptionPane.showMessageDialog(null,"there was no essential keys so \n new keys has been generated");
            }

            String Public = public_scanner.nextLine();
            String sec_Public = sec_public_scanner.nextLine();
            String Private = private_scanner.nextLine();
            private_scanner.close();
            public_scanner.close();
            sec_public_scanner.close();
            X509EncodedKeySpec keySpecPublic = new X509EncodedKeySpec(decode(Public));
            X509EncodedKeySpec keySpec_sec_sePublic = new X509EncodedKeySpec(decode(sec_Public));
            PKCS8EncodedKeySpec keySpecPrivate = new PKCS8EncodedKeySpec(decode(Private));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpecPublic);
            sec_publicKey = keyFactory.generatePublic(keySpec_sec_sePublic);
            privateKey = keyFactory.generatePrivate(keySpecPrivate);

        }


        public String segnet_encrypt(String sha) throws Exception {
            String Public = PUBLIC_KEY_STRING;
            String Private =PRIVATE_KEY_STRING;
            X509EncodedKeySpec CAPublic = new X509EncodedKeySpec(decode(Public));
            PKCS8EncodedKeySpec keyPrivate = new PKCS8EncodedKeySpec(decode(Private));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(CAPublic);
            privateKey = keyFactory.generatePrivate(keyPrivate);
            byte[] messageToBytes = sha.getBytes();
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] encryptedBytes = cipher.doFinal(messageToBytes);
            return encode(encryptedBytes);
        }

        private static String encode(byte[] data) {
            return Base64.getEncoder().encodeToString(data);
        }
        private static byte[] decode(String data) {
            return Base64.getDecoder().decode(data);
        }

        String[] shaSplit(String Sha){
            char[]sha_char=Sha.toCharArray();
            String p1="";
            String p2="";
            String[]shaArr= {"",""};

            for (int i=0;i<sha_char.length;i++){
                if (i<100) {
                    p1 += sha_char[i];
                }else {
                    p2 += sha_char[i];
                }
            }
            shaArr[0]=p1;
            shaArr[1]=p2;
            return shaArr;
        }


        public String[] hash_enc(String Hash){
            String[]enc_hash=shaSplit(Hash);
            try {
                enc_hash[0]=segnet_encrypt(enc_hash[0]);

                enc_hash[1]=segnet_encrypt(enc_hash[1]);
                char b[]=enc_hash[0].toCharArray();
//                System.out.println("f1"+":"+b.length+"  :"+enc_hash[0]);
                char b2[]=enc_hash[1].toCharArray();
//                System.out.println("f2"+":"+b2.length+"  :"+enc_hash[1]);
            }catch (Exception d){}
            return enc_hash;
        }


}
