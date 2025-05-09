package com.sphereon.oid.fed.client.crypto

import ICryptoServiceJS
import com.sphereon.oid.fed.client.mapper.decodeJWTComponents
import com.sphereon.oid.fed.client.types.ICryptoService
import com.sphereon.oid.fed.openapi.models.Jwk
import kotlinx.coroutines.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.js.Promise

@JsModule("jose")
@JsNonModule
external object Jose {
    fun importJWK(jwk: Jwk, alg: String, options: dynamic = definedExternally): Promise<dynamic>
    fun jwtVerify(jwt: String, key: Any, options: dynamic = definedExternally): Promise<dynamic>
}

class CryptoServiceAdapter(private val jsCryptoService: ICryptoServiceJS) : ICryptoService {
    override suspend fun verify(jwt: String, key: Jwk): Boolean {
        return jsCryptoService.verify(jwt, key).await()
    }
}

object CryptoServiceJS : ICryptoServiceJS {
    override fun verify(jwt: String, key: Jwk): Promise<Boolean> {
        return Promise { resolve, reject ->
            try {
                val decodedJwt = decodeJWTComponents(jwt)

                Jose.importJWK(
                    JSON.parse<dynamic>(Json.encodeToString(key)),
                    alg = decodedJwt.header.alg ?: "RS256"
                ).then { publicKey: Any ->
                    Jose.jwtVerify(jwt, publicKey).then<Any> {
                        resolve(true)
                    }.catch {
                        resolve(false)
                    }
                }.catch {
                    resolve(false)
                }
            } catch (e: Throwable) {
                resolve(false)
            }
        }
    }
}

@JsExport.Ignore
actual fun cryptoService(): ICryptoService {
    return object : ICryptoService {
        override suspend fun verify(jwt: String, key: Jwk): Boolean {
            return CryptoServiceJS.verify(jwt, key).await()
        }
    }
}
