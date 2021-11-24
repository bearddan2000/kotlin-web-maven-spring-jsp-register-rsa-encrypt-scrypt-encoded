package example.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

object RSAUtils {

  val RSA = "RSA";

  // Generating public & private keys
  // using RSA algorithm.
  fun generateRSAKeyPair(): KeyPair
  {
      val secureRandom = SecureRandom();
      val keyPairGenerator = KeyPairGenerator.getInstance(RSA);

      keyPairGenerator.initialize(2048, secureRandom);

      val keypair = keyPairGenerator.generateKeyPair();

      println("The Public Key is: " + DatatypeConverter.printHexBinary(keypair.public.getEncoded()));

      println("The Private Key is: " + DatatypeConverter.printHexBinary(keypair.private.getEncoded()));

      return keypair;
  }

  // Encryption function which converts
  // the plainText into a cipherText
  // using private Key.
  fun encrypt(plainText: String, keypair: KeyPair): ByteArray
  {

    val privateKey = keypair.private;

    val cipher = Cipher.getInstance(RSA);

    cipher.init(Cipher.ENCRYPT_MODE, privateKey);

    val cipherText = cipher.doFinal(plainText.toByteArray());

    print("The Encrypted Text is: ");

    val rsaText = DatatypeConverter.printHexBinary(cipherText);

    println(rsaText);

    println("The Encrypted Text length is: %d".format(rsaText.length));

    val out = GZIPCompression.compress(rsaText);

    if(out == null){
      return cipherText
    }

    return out
  }

}
