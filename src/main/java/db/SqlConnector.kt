package db

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class SqlConnector// Append the file path to create the jdbc connection string
(path: String) {



    // Holds the JDBC file url
    val url: String

    // Holds the connection that will be used for CRUD ops
    private var connection: Connection? = null

    init {
        url = "jdbc:sqlite:$path"
        connection = null
    }

    // Returns connection if successful, null otherwise, should check for null
    fun openConnection(): Connection? {
        try {
            // create a connection to the database
            connection = DriverManager.getConnection(url)

        } catch (e: SQLException) {
            println("ERROR" +e.message)
        }

        return connection
    }

    // Close the connection, false when unsuccessful
    @Throws(Exception::class)
    fun closeConnection(): Boolean {
        if (connection != null) {
            connection!!.close()

            return true
        }

        return false

    }
}
