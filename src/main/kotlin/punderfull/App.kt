package punderfull

import org.litote.kmongo.KMongo
import org.litote.kmongo.aggregate
import org.litote.kmongo.getCollection
import org.litote.kmongo.sample
import spark.kotlin.*

data class Pun(val line: String)

fun main(args: Array<String>) {
    val client = KMongo.createClient()
    val database = client.getDatabase("punderfull")
    val col = database.getCollection<Pun>()

    val http: Http = ignite()
    http.port(8080)

    http.get("/random") {
        (col.aggregate<Pun>(sample(1)).first() ?: Pun("No pun intended")).line
    }
}