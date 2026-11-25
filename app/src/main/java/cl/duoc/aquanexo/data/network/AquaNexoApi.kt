package cl.duoc.aquanexo.data.network

import cl.duoc.aquanexo.model.ItemChequeo
import cl.duoc.aquanexo.model.PreChequeo
import retrofit2.http.*

data class SyncRequest(val registro: PreChequeo, val items: List<ItemChequeo>)
data class SyncResponse(val id: Long, val localId: Long, val estado: String)
data class HealthResponse(val ok: Boolean, val servicio: String)

interface AquaNexoApi {
    @GET("api/health") suspend fun health(): HealthResponse
    @POST("api/prechequeos/sync") suspend fun sincronizar(@Body request: SyncRequest): SyncResponse
}
