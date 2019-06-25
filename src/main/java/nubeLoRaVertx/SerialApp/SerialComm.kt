package nubeLoRaVertx.SerialApp

import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortDataListener
import com.fazecast.jSerialComm.SerialPortEvent
import io.reactivex.Observable
import java.io.PrintWriter
import java.util.*


class SerialComm2(port: String, private var baudRate: Int = 115200) {

    private var serialPort = SerialPort.getCommPort(port).also {
        it.baudRate = baudRate
        it.openPort()
    }

    fun readString(): String {

        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 1, 1)
        var out = ""
        val input = Scanner(serialPort.inputStream)
        try {
            while (input.hasNext())
                out += input.next() + "\n"
            input.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return out
    }

}
