package example.security

import java.security.KeyPair;
import javax.xml.bind.DatatypeConverter;
import org.springframework.security.crypto.password.PasswordEncoder;

class RSAPasswordEncoder : org.springframework.security.crypto.scrypt.SCryptPasswordEncoder()
, PasswordEncoder {

    val keypair: KeyPair =  RSAUtils.generateRSAKeyPair();

  override fun encode(rawPassword: CharSequence): String {
     try {
       val plainText = rawPassword.toString();
       val rsaText = RSAUtils.encrypt(plainText, keypair);
       return super.encode(DatatypeConverter.printHexBinary(rsaText));
     } catch (e: Exception) {
      e.printStackTrace();
     }
     return super.encode(rawPassword);
   }

   override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean
   {
    try {
       val plainText = rawPassword.toString();
       val rsaText = RSAUtils.encrypt(plainText, keypair);
       val plain = DatatypeConverter.printHexBinary(rsaText);
      return super.matches(plain, encodedPassword);
    } catch (e: Exception) {}
    return false;
   }
}
