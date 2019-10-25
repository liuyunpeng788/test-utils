package ConfigEncrypt;

import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricConfig;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricStringEncryptor;
import org.jasypt.encryption.StringEncryptor;

/**
 * 配置文件加密测试
 * @author: liumch
 * @create: 2019/8/15 10:54
 **/

public class ConfigEncrypt {


    public static void main(String[] args) {
        SimpleAsymmetricConfig config = new SimpleAsymmetricConfig();
        config.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArQfyGCvBOdgmDGU6ciGPVNB6jHsMip0b0qOrPvVTSJ/x0offjKARogA2tjGjyr3rUtwg9woMBqv/iyENR0GBnIUa0jkYsznCKeygcflnNa4mrVf7XKXLhSwtY+kCe3diPk+0QPfEsfF9/aK6pWBUFcrE8P2k2sF/8mo8dFJU1t6zQGPspHkNAgR6MLU8SjPZxnMS6EG722MdYhvSYAKsnu02Hozqb4jh/gaQ/E6NkvM3DkqIyIYsRH2smstIFEb9CCiTdiz/OsJKQLgGy/pqIVKtai3lnUxAayEV45Z61rNTOusNJf+icGhZxjqhAeoWjMxOCVmVC2GKa9sisqBgkQIDAQAB");
        StringEncryptor encryptor = new SimpleAsymmetricStringEncryptor(config);
        String message = "liumch";
        String encrypted = encryptor.encrypt(message);
        System.out.printf("Encrypted message %s\n", encrypted);
        encryptor.decrypt(encrypted);
    }
}
