package com.sphereon.oid.fed.services.mappers

import com.sphereon.oid.fed.openapi.models.*
import kotlinx.serialization.json.Json
import com.sphereon.oid.fed.persistence.models.Jwk as JwkEntity

fun JwkEntity.toDTO(): Jwk {
    val key = Json.decodeFromString<Jwk>(this.key)

    return Jwk(
        id = this.id,
        e = key.e,
        x = key.x,
        y = key.y,
        n = key.n,
        alg = key.alg,
        crv = key.crv,
        kid = key.kid,
        kty = key.kty,
        use = key.use,
        x5c = key.x5c,
        x5t = key.x5t,
        x5u = key.x5u,
        x5tS256 = key.x5tS256,
        revokedAt = this.revoked_at.toString(),
        revokedReason = this.revoked_reason,
    )
}

fun JwkEntity.toHistoricalKey(): HistoricalKey {
    val key = Json.decodeFromString<Jwk>(this.key)

    return HistoricalKey(
        e = key.e,
        x = key.x,
        y = key.y,
        n = key.n,
        alg = key.alg,
        crv = key.crv,
        kid = key.kid,
        kty = key.kty,
        use = key.use,
        x5c = key.x5c,
        x5t = key.x5t,
        x5u = key.x5u,
        x5tS256 = key.x5tS256,
        revoked = key.revokedAt?.let { JwkRevoked(it, key.revokedReason) }
    )
}

fun JwkEntity.toBaseJwk(): BaseJwk {
    val key = Json.decodeFromString<BaseJwk>(this.key)

    return BaseJwk(
        e = key.e,
        x = key.x,
        y = key.y,
        n = key.n,
        alg = key.alg,
        crv = key.crv,
        kid = key.kid,
        kty = key.kty,
        use = key.use,
        x5c = key.x5c,
        x5t = key.x5t,
        x5u = key.x5u,
        x5tS256 = key.x5tS256,
    )
}