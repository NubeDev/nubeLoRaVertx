package nubeLoRaVertx.SerialApp

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class kotlinHash {
    fun run(msg: String, inKey: String) :String {
        try {
            val text = msg
            val key = inKey
            val aesKey = SecretKeySpec(key.toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.ENCRYPT_MODE, aesKey)
            val encrypted = cipher.doFinal(text.toByteArray())
//            println(String(encrypted))
            cipher.init(Cipher.DECRYPT_MODE, aesKey)
            val decrypted = String(cipher.doFinal(encrypted))
            return decrypted

        } catch (e: Exception) {
           throw e
        }

    }


}