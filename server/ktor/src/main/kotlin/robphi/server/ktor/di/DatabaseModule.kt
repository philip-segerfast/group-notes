package robphi.server.ktor.di

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.driver.r2dbc.R2dbcDriver
import co.touchlab.kermit.Logger
import io.r2dbc.postgresql.PostgresqlConnectionFactoryProvider.POSTGRESQL_DRIVER
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.ConnectionFactoryOptions.*
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.runBlocking
import org.koin.dsl.module
import robphi.server.ktor.database.Database
import robphi.server.ktor.database.Database.Companion.Schema

private const val DB_IP = "127.0.0.1"
private const val DB_PORT = 5432
private const val DB_USER = "postgres"
private const val DB_PASSWORD = "root"
private const val DB_NAME = "group_notes_db"

val databaseModule = module {
    single<Database> { runBlocking { connectAndSetupDatabase() } }
}

private fun getConnectionFactory() = ConnectionFactories.get(
    ConnectionFactoryOptions.builder()
        .option(DRIVER, POSTGRESQL_DRIVER)
        .option(HOST, DB_IP)
        .option(PORT, DB_PORT)
        .option(USER, DB_USER)
        .option(PASSWORD, DB_PASSWORD)
        .option(DATABASE, DB_NAME)
        .build()
)

private suspend fun connectAndSetupDatabase(): Database {
    Logger.d { "Creating connection..." }
    val connection = getConnectionFactory().create().awaitSingle()
    Logger.d { "Creating driver..." }
    val driver = R2dbcDriver(connection)
    Logger.d("Creating database...")
    return DatabaseHelper(driver).initDatabase()
}

/** Used to create and possible migrate the database. */
private class DatabaseHelper(private val driver: R2dbcDriver) {

    private suspend fun execute(sql: String): Long =
        driver.execute(null, sql, 0, null).await()

    private suspend fun <R> executeQuery(sql: String, mapper: (SqlCursor) -> QueryResult<R>): R =
        driver.executeQuery(null, sql, mapper, 0).await()

    private suspend fun getCurrentVersion(): Long? = executeQuery(
        sql = "SELECT version FROM schema_version ORDER BY version DESC LIMIT 1;",
        mapper = { sqlCursor: SqlCursor ->
            QueryResult.AsyncValue {
                val hasNext = sqlCursor.next().await()
                when {
                    hasNext -> sqlCursor.getLong(0)
                    else -> null
                }
            }
        }
    )

    private suspend fun setCurrentVersion(version: Long) = execute(
        sql = "INSERT INTO schema_version (version) VALUES ($version);"
    )

    private suspend fun createVersionTableIfNotExists() {
        execute("CREATE TABLE IF NOT EXISTS schema_version (version INT NOT NULL);")
    }
    
    suspend fun initDatabase(): Database {
        createVersionTableIfNotExists()
        val currentVer = getCurrentVersion().also {
            Logger.d("Retrieved version: $it")
        } ?: 0
        if (currentVer == 0L) {
            Schema.create(driver).await()
            setCurrentVersion(Schema.version)
            Logger.d { "init: created tables, set Version to 1" }
        } else {
            val schemaVer = Schema.version
            if (schemaVer > currentVer) {
                Schema.migrate(driver, currentVer, schemaVer).await()
                setCurrentVersion(schemaVer)
                Logger.d { "init: migrated from $currentVer to $schemaVer" }
            } else {
                Logger.d { "Database is up to date." }
            }
        }
        return Database(driver)
    }
}
