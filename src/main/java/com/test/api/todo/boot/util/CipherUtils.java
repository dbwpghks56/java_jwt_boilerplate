package com.test.api.todo.boot.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Slf4j
@Component
public class CipherUtils {
    private String cipherSecret;
    private Cipher cipher;
    private SecretKey secretKey;


    public CipherUtils(@Value("${cipher.secret}") String cipherSecret) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipherSecret = cipherSecret;
        this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        this.secretKey = new SecretKeySpec(cipherSecret.getBytes(StandardCharsets.UTF_8), "AES");
    }

    /**
     * 암호화
     * @param plainText
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     */
    public String encrypt(String plainText) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
        byte[] encBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        return new String(Base64.getEncoder().encode(encBytes), StandardCharsets.UTF_8);
    }

    /**
     * 복호화
     * @param encText
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException
     */
    public String decrypt(String encText) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
        byte[] decBytes = Base64.getDecoder().decode(encText.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
        String decStr = new String(cipher.doFinal(decBytes), StandardCharsets.UTF_8);

        return decStr;
    }

    /**
     * 평문 텍스트와 암호화된 텍스트의 일치 여부를 검사한다.
     * @param plainText
     * @param encText
     * @return
     */
    public Boolean matches(String plainText, String encText) throws InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] decBytes = Base64.getDecoder().decode(encText.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
        String decStr = new String(cipher.doFinal(decBytes), StandardCharsets.UTF_8);

        // 일치 여부를 확인한다.
        return plainText.equals(decStr);
    }

}
