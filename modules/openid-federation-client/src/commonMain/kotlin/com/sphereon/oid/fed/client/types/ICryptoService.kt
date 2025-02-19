package com.sphereon.oid.fed.client.types

import com.sphereon.oid.fed.openapi.models.BaseJwk
import kotlin.js.JsExport

@JsExport.Ignore
interface ICryptoService {
    suspend fun verify(
        jwt: String,
        key: BaseJwk
    ): Boolean
}
