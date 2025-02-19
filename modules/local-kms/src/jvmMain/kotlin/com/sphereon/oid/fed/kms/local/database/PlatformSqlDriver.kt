package com.sphereon.oid.fed.kms.local.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.sphereon.oid.fed.kms.local.Constants
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

actual class PlatformSqlDriver {
    actual fun createPostgresDriver(url: String, username: String, password: String): SqlDriver {
        val config = HikariConfig()
        config.jdbcUrl = url
        config.username = username
        config.password = password

        val dataSource = HikariDataSource(config)
        return dataSource.asJdbcDriver()
    }

    actual fun createSqliteDriver(path: String): SqlDriver {
        throw UnsupportedOperationException(Constants.SQLITE_IS_NOT_SUPPORTED_IN_JVM)
    }
}