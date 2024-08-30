package com.sphereon.oid.fed.kms.local.jwt

import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator
import com.sphereon.oid.fed.openapi.models.EntityConfigurationStatement
import com.sphereon.oid.fed.openapi.models.JWKS
import com.sphereon.oid.fed.openapi.models.JWTHeader
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlin.test.Test
import kotlin.test.assertTrue

class JoseJwtTest {

    //TODO Fix it
//    @Test
//    fun signTest() {
//        val key = RSAKeyGenerator(2048).keyID("key1").generate()
//        val entityStatement =
//            EntityConfigurationStatement(iss = "test", sub = "test", exp = 111111, iat = 111111, jwks = JWKS())
//        val payload: JsonObject = Json.encodeToJsonElement(entityStatement) as JsonObject
//        val signature = sign(
//            payload, JWTHeader(alg = "RS256", typ = "JWT", kid = key.keyID), mutableMapOf("key" to key)
//        )
//        assertTrue { signature.startsWith("ey") }
//    }
//
//    @Test
//    fun verifyTest() {
//        val kid = "key1"
//        val key: RSAKey = RSAKeyGenerator(2048).keyID(kid).generate()
//        val entityStatement =
//            EntityConfigurationStatement(iss = "test", sub = "test", exp = 111111, iat = 111111, jwks = JWKS())
//        val payload: JsonObject = Json.encodeToJsonElement(entityStatement) as JsonObject
//        val signature = sign(
//            payload, JWTHeader(alg = "RS256", typ = "JWT", kid = key.keyID), mutableMapOf("key" to key)
//        )
//        assertTrue { verify(signature, key, emptyMap()) }
//    }
}
